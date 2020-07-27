package cloud.catisland.ivory.common.dao.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cloud.catisland.ivory.common.dao.model.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(long id);
    Optional<User> findByUserName(String username);
    void deleteById(Long id);
    Optional<User> findByNickName(String nickname);
    
    /** 
     * 更新用户头像
     */
    @Modifying
    @Query("update User u set u.avatar=?2 where u.uid=?1")
    int updateAvatarById(Long id,String avatar);
}