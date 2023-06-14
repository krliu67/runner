package com.example;

import com.example.utils.ReturnData;
import com.example.model.User;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "用户接口")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    public ReturnData addUser(@RequestBody User user){
        User newUser = userService.addUser(user);
        return new ReturnData(200,newUser,"add new user successfully",1);
    }

    @PutMapping("/update")
    public ReturnData updateUser(@RequestBody User user){
        User updateUser = userService.updateUser(user);
        return new ReturnData(200,updateUser,"update user successfully",1);
    }

    @DeleteMapping("/delete")
    public ReturnData deleteUser(String userId){
        userService.deleteEmployee(userId);
        return new ReturnData(200,null,"delete user successfully",1);
    }

    @GetMapping("/find/{userId}")
    public ReturnData findAUser(@PathVariable("userId") String userId){
        User user = userService.findUserByUserId(userId);
        return new ReturnData(200,user,"find sb successfully",1);
    }

    @GetMapping("/find/{userIds}")
    public ReturnData findUsers(@PathVariable("userIds") List<String> userIds){
        List<User> users = userService.findUserByUserIds(userIds);
        return new ReturnData(200,users,"find sbs successfully",users.size());
    }

    @PostMapping("/login")
    public ReturnData login(HttpServletRequest request, @RequestBody User user){
        //1.将页面提交的密码password进行md5加密处理
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(password);
        System.out.println(user.toString());
        //2.根据页面提交的用户名username查询数据库
        User realUser = userService.findUserByUserName(user.getUsername());
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
