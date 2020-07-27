package cloud.catisland.ivory.common.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cloud.catisland.ivory.system.model.BO.RegBO;
import cloud.catisland.ivory.system.model.BO.UserInfoBO;
import cn.hutool.core.util.RandomUtil;
import lombok.Data;

/**
 * 用户类/DO
 * @author Xy718
 * 
 */
//TODO 记得把一些Unique字段弄一下
@Data
@Entity
@Table(name="user")
public class User {
    //用户ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uid;
    //用户名
    @Column(name="user_name",nullable = false, unique = true)
    private String userName;
    //昵称
    @Column(name="nick_name",nullable = false)
    private String nickName;
    //用户游戏账号
    @Column(name="user_game_id",nullable = false)
    private String userGameID="";
    //用户游戏UUID
    @Column(name="user_game_uuid",nullable = false)
    private String userGameUUID="";
    //手机号
    @JsonIgnore
    @Column(name="phone",nullable = false)//, unique = true
    private String phone="";
    //邮箱
    @Column(name="email",nullable = false)//, unique = true
    private String email="";
    //密码(BCrypt加密)
    @JsonIgnore
    @Column(name="password",nullable = false)
    private String password;
    //密码加密盐
    @JsonIgnore
    @Column(name="password_salt",nullable = false)
    private String passwordSalt="";

    //头像
    @Column(name="avatar")
    private String avatar="";

    //用户简介
    @Column(name="userintro")
    private String userintro="";

    // @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    // @JoinTable(name = "m_user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    // private Set<Role> roles = new HashSet<>();

    /**
     * 根据传入的RegBO创建一个待保存的新用户对象
     */
    public User(RegBO regBO){
        this.userName=regBO.getUsername();
        // TODO 这个部分的密码盐持争议
        this.passwordSalt=RandomUtil.randomString(8);
        this.password="{bcrypt}"+new BCryptPasswordEncoder().encode(regBO.getPassword());
        this.nickName=this.userName;
    }

    /**
     * 不带参的构造函数，用于支持Hibernate反射创造对象
     */
    public User(){}

    /**
     * 从用户信息BO合并数据到user对象
     * @param userInfo
     */
	public void mergeFromBO(UserInfoBO userInfo) {
        if(StringUtils.isBlank(userInfo.getUserName())){
            this.userName=userInfo.getUserName();
        }
        if(StringUtils.isBlank(userInfo.getUserGameID())){
            this.userGameID=userInfo.getUserGameID();
        }
    }
}
