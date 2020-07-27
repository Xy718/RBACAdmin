package cloud.catisland.ivory.system.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cloud.catisland.ivory.system.exception.base.LoginUserNotFoundException;
import cloud.catisland.ivory.system.model.BO.ResultBean;
import cloud.catisland.ivory.system.model.BO.TopicBO;
import cloud.catisland.ivory.common.dao.model.User;
import cloud.catisland.ivory.common.service.TopicService;
import cloud.catisland.ivory.common.service.UserService;

@RestController
@RequestMapping("/topic")
public class TopicController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    @Resource
    private TopicService topicService;
    @Resource
    private UserService userService;

    // TODO 展示贴接口
    /**
     * 展示贴接口
     * 
     * @param pageNo
     * @param pageCount
     * @return
     */
    @GetMapping("/all")
    public ResultBean getTopics(
        @RequestParam(value = "page", defaultValue = "0") Integer pageNo,
        @RequestParam(value = "count", defaultValue = "5") Integer pageCount
        ) {
        return ResultBean.success(topicService.getTopicsPage(pageNo, pageCount, Sort.by(Direction.DESC, "tid")));
    }

    // TODO 发布帖子
    @PostMapping
    public ResultBean addTopic(@RequestBody TopicBO topicBO) {
        return ResultBean.success();
    }

    // TODO 删除帖子
    // TODO 修改帖子
    // TODO 喜欢/分享
    // TODO 举报某一个帖子
    // TODO 查看我的帖子
    @GetMapping("/me")
    public ResultBean getMineTopics(
        @RequestParam(value = "page", defaultValue = "0") Integer pageNo,
        @RequestParam(value = "count", defaultValue = "5") Integer pageCount
    ) {
        try {
            User u = userService.getLoginUserORException();
        } catch (LoginUserNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResultBean.success(topicService.getTopicsPage(pageNo,pageCount,Sort.by(Direction.DESC , "tid")));
    }
    //TODO 查看某个人的帖子，这个可以和查看我的帖子合并
    //TODO 查看我喜欢的帖子，查看我分享过的帖子

    //TODO 评论帖子，或者回复其他人的评论
    //TODO 删除某一条评论
    //TODO 举报某一条评论
}