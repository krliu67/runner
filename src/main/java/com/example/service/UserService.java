package com.example.service;

import com.example.model.User;
import com.example.repo.UserRepo;
import com.example.utils.GenCode;
import com.example.common.ReturnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;


    @Autowired
    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User addUser(User user){
        //user.setUserId(UUID.randomUUID().toString());
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(password);
        user.setUserId(new GenCode().GenUserId());
        return userRepo.save(user);
    }

    public User updateUser(User user){
        return userRepo.save(user);
    }
    public void deleteEmployee(String userId){
        userRepo.deleteUserByUserId(userId);
    }
    public User findUserByUserId(String userId){
        return userRepo.findUserByUserId(userId);
    }
    public List<User> findUserByUserIds(List<String> userIds){
        return userRepo.findUserByUserIds(userIds);
    }
    public User findUserByUserName(String username){
        return userRepo.findUserByUserName(username);
    }
    public ReturnData login(HttpServletRequest request, User user){
        //1.将页面提交的密码password进行md5加密处理
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(password);
        System.out.println(user.toString());
        //2.根据页面提交的用户名username查询数据库
        User realUser = findUserByUserName(user.getUsername());
        //3.如果没有查询到则返回登陆失败结果
        if (realUser==null){
            return new ReturnData(0,null,"登录失败，用户不存在",0);
        }
        //4.密码比对
        if (!realUser.getPassword().equals(password)){
            return new ReturnData(0,null,"登陆失败，密码错误",0);
        }
        //5.登陆成功，将userId存入Session并返回登陆成功结果
        request.getSession().setAttribute("user",realUser.getUserId());
        return new ReturnData(200,realUser,"登录success",1);
    }
}
