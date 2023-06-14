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
    // find all users
    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    // add a user
    @PostMapping("/add")
    public ReturnData addUser(@RequestBody User user){
        User newUser = userService.addUser(user);
        return new ReturnData(200,newUser,"add new user successfully",1);
    }
    // update a user
    @PutMapping("/update")
    public ReturnData updateUser(@RequestBody User user){
        User updateUser = userService.updateUser(user);
        return new ReturnData(200,updateUser,"update user successfully",1);
    }
    // search more than one user
    @DeleteMapping("/delete")
    public ReturnData deleteUser(String userId){
        userService.deleteEmployee(userId);
        return new ReturnData(200,null,"delete user successfully",1);
    }
    // search one user
    @GetMapping("/findOne/{userId}")
    public ReturnData findAUser(@PathVariable("userId") String userId){
        User user = userService.findUserByUserId(userId);
        return new ReturnData(200,user,"find sb successfully",1);
    }
    // search more than one user
    @GetMapping("/findMore/{userIds}")
    public ReturnData findUsers(@PathVariable("userIds") List<String> userIds){
        List<User> users = userService.findUserByUserIds(userIds);
        return new ReturnData(200,users,"find sbs successfully",users.size());
    }
    // for login in
    @PostMapping("/login")
    public ReturnData login(HttpServletRequest request, @RequestBody User user){
        return userService.login(request,user);
    }

}
