package xyz.xy718.RBACAdmin.common.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import cloud.catisland.ivory.common.dao.model.User;
import cloud.catisland.ivory.common.dao.model.enums.UserRegStatus;
import cloud.catisland.ivory.common.dao.repository.UserRepository;
import cloud.catisland.ivory.system.exception.base.LoginUserNotFoundException;
import cloud.catisland.ivory.system.exception.base.UserNickNameNotFoundException;
import cloud.catisland.ivory.system.model.BO.RegBO;

/**
 * UserService
 * @Author: Xy718
 * @Date: 2020-06-05 11:29:37
 * @LastEditors: Xy718
 * @LastEditTime: 2020-06-13 05:59:52
 */
@Service
public class UserService {

    @Autowired
    UserRepository uRepo;

    /**
     * 根据用户名检测一个用户是否已注册
     * @param username
     * @return {@link UserRegStatus}
     */
    public UserRegStatus checkUserRegedByUsername(String username){
        //TODO add：根据传入的所有内容检测非空Unique字段是否已被占用
        return uRepo.findByUserName(username) == null ? UserRegStatus.UNREG : UserRegStatus.REGED;
    }

    public Optional<User> registUser(RegBO regBO){
        User inSaveUser=new User(regBO);
        return Optional.ofNullable(uRepo.save(inSaveUser));
    }

	public Optional<User> findByUserName(String username) {
		return uRepo.findByUserName(username);
	}

	public Optional<User> findById(long uid) {
		return uRepo.findById(uid);
	}

	public User save(User user) {
		return uRepo.save(user);
	}

	public User findByNickName(String nickname) throws UserNickNameNotFoundException {
        Optional<User> userOptn=uRepo.findByNickName(nickname);
        if(userOptn.isPresent()){
            throw new UserNickNameNotFoundException();
        }
		return userOptn.get();
	}

    public User getLoginUserORException() throws LoginUserNotFoundException {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.equals("anonymousUser")){
            //匿名用户
            throw new LoginUserNotFoundException();
        }else{
            return uRepo.findByUserName(((UserDetails)user).getUsername()).orElseThrow(LoginUserNotFoundException::new);
        }
    }

	public int changeAvatar(Long uid, String avatar) {
        return uRepo.updateAvatarById(uid, avatar);
	}

}