package com.American.reggie.controller;

import com.American.reggie.common.R;
import com.American.reggie.entity.User;
import com.American.reggie.filter.LoginCheckFilter;
import com.American.reggie.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
     public R<User>login(@RequestBody Map user, HttpSession session){
        String phone = user.get("phone").toString();
        String code = user.get("code").toString();

        LambdaQueryWrapper<User>lqw=new LambdaQueryWrapper<>();
        lqw.eq(User::getPhone,phone);
        User userOne = userService.getOne(lqw);
        if(userOne==null){
            userOne=new User();
            userOne.setPhone(phone);
            userOne.setStatus(1);
            userService.save(userOne);
        }
        session.setAttribute("user",userOne.getId());
        return R.success(userOne);
    }
}
