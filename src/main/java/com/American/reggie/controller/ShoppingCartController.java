package com.American.reggie.controller;

import ch.qos.logback.core.ContextBase;
import com.American.reggie.common.BaseContext;
import com.American.reggie.common.R;
import com.American.reggie.entity.ShoppingCart;
import com.American.reggie.service.ShoppingCartService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        LambdaQueryWrapper<ShoppingCart>lqw=new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,userId);
        if(shoppingCart.getDishId()!=null){
            lqw.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
        }else {
            lqw.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart shoppingCartOne= shoppingCartService.getOne(lqw);
        if(shoppingCartOne!=null){
            shoppingCartOne.setNumber(shoppingCartOne.getNumber()+1);
            shoppingCartService.updateById(shoppingCartOne);
        }else {
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            shoppingCartOne=shoppingCart;
        }
        return R.success(shoppingCartOne);
    }
    @GetMapping("/list")
    public R<List<ShoppingCart>>list(){
        LambdaQueryWrapper<ShoppingCart>lqw=new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        lqw.orderByAsc(ShoppingCart::getCreateTime)
                ;
        List<ShoppingCart> list = shoppingCartService.list(lqw);
        return R.success(list);
    }
    @DeleteMapping("/clean")
    public R<String>remove(){
        LambdaQueryWrapper<ShoppingCart>lqw=new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartService.remove(lqw);
        return R.success("清空成功");
    }
}
