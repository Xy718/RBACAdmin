package cloud.catisland.ivory.system.controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cloud.catisland.ivory.system.config.ProjectConfig;
import cloud.catisland.ivory.system.exception.base.LoginUserNotFoundException;
import cloud.catisland.ivory.system.exception.base.UserNickNameNotFoundException;
import cloud.catisland.ivory.system.model.BO.ResultBean;
import cloud.catisland.ivory.system.model.BO.UserInfoBO;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import cloud.catisland.ivory.common.dao.model.User;
import cloud.catisland.ivory.common.dao.model.enums.AvatarChangeType;
import cloud.catisland.ivory.common.service.UserService;
import cloud.catisland.ivory.common.service.impl.UpyunUSSService;
import cloud.catisland.ivory.common.util.XyRandom;

@Validated
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;
    // @Autowired
    // private ImageService imgSrv;
    @Resource
    private ProjectConfig config;

    /**
     * 获取登录用户自身的信息
     * 
     * @throws LoginUserNotFoundException
     */
    @GetMapping
    public ResponseEntity<ResultBean> getuserinfo(

    ) throws LoginUserNotFoundException {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.equals("anonymousUser")) {
            // 匿名用户
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResultBean.error("请先登录"));
        } else {
            User u = userService.getLoginUserORException();
            return ResponseEntity.ok(ResultBean.success(new UserInfoBO(u)));
        }
    }

    /**
     * 通过uid或者username获取其他用户的信息
     * 
     * @throws UserNickNameNotFoundException
     */
    @GetMapping("/{nickname}")
    public ResultBean getuserinfoByNickName(
            // TODO 这里处理一下
            @PathVariable("nickname") @NotEmpty(message = "该参数不允许为空") String nickname)
            throws UserNickNameNotFoundException {
        User user = userService.findByNickName(nickname);

        return ResultBean.success(user);
    }

    /**
     * 更新登录用户自身的信息<br>
     * 如果未登录抛出异常
     * 
     * @throws LoginUserNotFoundException
     */
    @PutMapping
    public ResultBean updateInfo(@RequestBody UserInfoBO user) throws LoginUserNotFoundException {
        // 先获取登录用户的ID
        User u = userService.getLoginUserORException();
        // 合并到用户
        u.mergeFromBO(user);
        return ResultBean.success("", new UserInfoBO(userService.save(u)));
    }

    // TODO 修改密码的接口

    // TODO 关注某一个好哥哥

    // TODO 修改头像
    @PostMapping("/avatar")
    public ResultBean changeAvatar(
        @RequestParam("type") Integer type
        , @RequestParam("file") MultipartFile avatar
    )throws LoginUserNotFoundException, FileUploadException {
        User u = userService.getLoginUserORException();
        log.info("用户{}{}头像", u.getUserName(),avatar.isEmpty()?"删除":"修改");
        if(type==AvatarChangeType.REMOVE.getValue()){
            return changeUserAvatar(u,"",null);
        }
        if (avatar.isEmpty()) {
            log.error("文件为空");
            throw new FileUploadException("上传失败");
        }
        // 生成图片名称
        String fileName = RandomUtil.randomString(XyRandom.get62ByteString(), 16);// 文件名
        File OSSImage; 
        try {
            OSSImage = File.createTempFile(fileName, ".png");
            //接受图片文件
            avatar.transferTo(OSSImage);
        } catch (IOException e) {
            throw new FileUploadException(e.getMessage());
        }
        UpyunUSSService imgSrv=new UpyunUSSService();
        //上传到USS
        Optional<String> imageUrl=imgSrv.upImage(OSSImage);
        if(imageUrl.isPresent()){
            return changeUserAvatar(u,config.getOssdomain()+imageUrl.get(),OSSImage);
        }else{
            throw new FileUploadException("上传到OSS服务器失败");
        }
    }

    /**
     * 修改用户的头像
     * @param u user对象
     * @param imageUrl url路径
     * @param OSSImage 待删除的临时图片文件
     * @return
     */
    ResultBean changeUserAvatar(User u,String imageUrl,File OSSImage){
        if(OSSImage!=null){
            log.info("上传成功路径：{} 删除临时文件：{}",imageUrl,OSSImage.delete());
        }
        //写库，更新数据
        int result=userService.changeAvatar(u.getUid(),imageUrl);
        //返回
        return result==1?
            ResultBean.success(imageUrl):
            ResultBean.error("更新失败："+result);
    }
}