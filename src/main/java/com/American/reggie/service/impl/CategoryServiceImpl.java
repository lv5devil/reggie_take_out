package com.American.reggie.service.impl;

import com.American.reggie.common.CustomException;
import com.American.reggie.entity.Category;
import com.American.reggie.entity.Dish;
import com.American.reggie.entity.Setmeal;
import com.American.reggie.mapper.CategoryMapper;
import com.American.reggie.service.CategoryService;
import com.American.reggie.service.DishService;
import com.American.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService{
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private DishService dishService;

    @Override
    public void remove(long id){
        LambdaQueryWrapper<Setmeal> lqw1=new LambdaQueryWrapper<>();
        lqw1.eq(Setmeal::getCategoryId,id);

        LambdaQueryWrapper<Dish>lqw2=new LambdaQueryWrapper<>();
        lqw2.eq(Dish::getCategoryId,id);

        int count1 = setmealService.count(lqw1);
        if(count1>0){
            throw new CustomException("套餐有关联，无法删除！");
        }

        int count2 = dishService.count(lqw2);

        if(count2>0){
            throw new CustomException("菜品有关联，无法删除！");
        }
        this.removeById(id);
    }
}
