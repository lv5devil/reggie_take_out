package com.American.reggie.service.impl;

import com.American.reggie.common.CustomException;
import com.American.reggie.dto.SetmealDto;
import com.American.reggie.entity.Category;
import com.American.reggie.entity.Dish;
import com.American.reggie.entity.Setmeal;
import com.American.reggie.entity.SetmealDish;
import com.American.reggie.mapper.SetmealMapper;
import com.American.reggie.service.CategoryService;
import com.American.reggie.service.DishService;
import com.American.reggie.service.SetmealDishService;
import com.American.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes=setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public void deleteWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal>lqw=new LambdaQueryWrapper<>();
        lqw.in(ids!=null,Setmeal::getId,ids).eq(Setmeal::getStatus,1);
        int count = this.count(lqw);
        if(count>0){
            throw new CustomException("正在售卖，无法删除！");
        }
        this.removeByIds(ids);

      LambdaQueryWrapper<SetmealDish>lqwDish=new LambdaQueryWrapper<>();
      lqwDish.in(SetmealDish::getSetmealId,ids);
      setmealDishService.remove(lqwDish);

    }
}
