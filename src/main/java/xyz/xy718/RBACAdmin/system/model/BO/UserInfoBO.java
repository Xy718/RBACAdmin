package cloud.catisland.ivory.system.model.BO;

import cloud.catisland.ivory.common.dao.model.User;
import lombok.Data;

@Data
public class UserInfoBO {
	//用户ID
    private long uid;
    //用户名
    private String userName="";
    //昵称
    private String nickName="";
    //用户游戏账号
    private String userGameID="";
    //用户游戏UUID
    private String userGameUUID="";
    //手机号
    private String phone="";
    //邮箱
    private String email="";
    //头像
    private String avatar="";
    //用户简介
    private String userintro="";


    /**
     * 从DO中创建BO角色
     * @param userDO
     */
    public UserInfoBO(User userDO) {
        this.uid=userDO.getUid();
        this.userName=userDO.getUserName();
        this.userGameID=userDO.getUserGameID();
        this.avatar=userDO.getAvatar();
        this.email=userDO.getEmail();
        this.nickName=userDO.getNickName();
        this.userintro=userDO.getUserintro();
        this.phone=userDO.getPhone();
        this.userGameUUID=userDO.getUserGameUUID();
	}
}