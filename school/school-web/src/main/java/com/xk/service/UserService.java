package com.xk.service;

import com.xk.domain.User;
import org.springframework.data.repository.query.Param;

/**
 * Created by hengxiaokang
 * Date:2018/6/22
 * Time:16:39
 */
public interface UserService
{
    User findByUserName(String userName);

    User findByUserNameOrPhone(String username, String phone);

    User findByPhone(String phone);

    User findById(long id);

    int setOutDateAndValidataCode(@Param("outDate") String outDate, @Param("validataCode") String validataCode, @Param("email") String email);

    int setNewPassword(@Param("passWord") String passWord, @Param("email") String email);

    int setIntroduction(@Param("introduction") String introduction, @Param("email") String email);

    int setUserName(@Param("userName") String userName, @Param("email") String email);

    int setProfilePicture(@Param("profilePicture") String profilePicture, @Param("id") Long id);

    int setBackgroundPicture(@Param("backgroundPicture") String backgroundPicture, @Param("id") Long id);

    User save(User user);
}
