package com.American.reggie.controller;

import com.American.reggie.common.R;
import com.American.reggie.entity.User;
import com.American.reggie.filter.LoginCheckFilter;
import com.American.reggie.service.UserService;
import com.American.reggie.utils.ValidateCodeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping("/sendMsg")
    public R<String>senMsg(@RequestBody User user,HttpSession session){
        String phone = user.getPhone();
        if(phone!=null){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            //session.setAttribute(phone,code);
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.success("发送成功");
        }
        return R.error("发送成功");
    }

    @PostMapping("/login")
     public R<User>login(@RequestBody Map user, HttpSession session){
        String phone = user.get("phone").toString();
        String code = user.get("code").toString();

        Object codeInRedis = redisTemplate.opsForValue().get(phone);

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
