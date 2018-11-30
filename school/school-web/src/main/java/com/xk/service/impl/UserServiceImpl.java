package com.xk.service.impl;

import com.xk.domain.User;
import com.xk.repository.UserRepository;
import com.xk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hengxiaokang
 * Date:2018/8/16
 * Time:16:54
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

//    @Cacheable(value = "user", key = "#userName")
    @Override
    public User findByUserName(String userName)
    {
        return userRepository.findByUserName(userName);
    }

//    @Cacheable(value = "user", key = "#username+''+#phone")
    @Override
    public User findByUserNameOrPhone(String username, String phone)
    {
        return userRepository.findByUserNameOrPhone(username, phone);
    }

//    @Cacheable(value = "user", key = "#phone")
    @Override
    public User findByPhone(String phone)
    {
        return userRepository.findByPhone(phone);
    }

//    @Cacheable(value = "user", key = "#id",unless="#result == null")
    @Override
    public User findById(long id)
    {
        return userRepository.findById(id);
    }

    @Override
    public int setOutDateAndValidataCode(String outDate, String validataCode, String email)
    {
        return userRepository.setOutDateAndValidataCode(outDate, validataCode, email);
    }

    @Override
    public int setNewPassword(String passWord, String email)
    {
        return userRepository.setNewPassword(passWord, email);
    }

    @Override
    public int setIntroduction(String introduction, String email)
    {
        return userRepository.setIntroduction(introduction, email);
    }

    @Override
    public int setUserName(String userName, String email)
    {
        return userRepository.setUserName(userName, email);
    }

    @Override
    public int setProfilePicture(String profilePicture, Long id)
    {
        return userRepository.setProfilePicture(profilePicture, id);
    }

    @Override
    public int setBackgroundPicture(String backgroundPicture, Long id)
    {
        return userRepository.setBackgroundPicture(backgroundPicture, id);
    }

//    @CachePut(value = "user", key = "#user.userName")
    @Override
    public User save(User user)
    {
        return userRepository.save(user);
    }
}