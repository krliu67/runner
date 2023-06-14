package com.example.repo;

import com.example.model.CodeList;
import com.example.model.User;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<User,Long> {

    @Modifying
    @Query(value = "Delete from runner.user WHERE user_id = ?", nativeQuery = true)
    void deleteUserByUserId(String userId);

//    @Query(value = "SELECT avatar_url, invite_code,phone, score, signature, username from runner.user where user_id = ?", nativeQuery = true)
    @Query(value = "SELECT * from runner.user where user_id = ?", nativeQuery = true)
    User findUserByUserId(String userId);

    @Query(value = "SELECT * from runner.user where user_id in :ids", nativeQuery = true)
    List<User> findUserByUserIds(@Param("ids") List<String> userIds);

    @Query(value = "SELECT * from runner.user where username = ?", nativeQuery = true)
    User findUserByUserName(String username);
}
