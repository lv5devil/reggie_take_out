package com.American.reggie.controller;

import com.American.reggie.common.R;
import com.American.reggie.dto.DishDto;
import com.American.reggie.entity.Category;
import com.American.reggie.entity.Dish;
import com.American.reggie.entity.DishFlavor;
import com.American.reggie.service.CategoryService;
import com.American.reggie.service.DishFlavorService;
import com.American.reggie.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public R<String>save(@RequestBody DishDto dishDto){
        log.info("Dish controller 开始启动");
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功！");
    }
    @GetMapping("/page")
    public R<Page>page(int page,int pageSize,String name){

        Page<Dish>pageInfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Dish> lqw=new LambdaQueryWrapper<>();
        lqw.like(name!=null,Dish::getName,name);
        lqw.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo,lqw);

        Page<DishDto>pageDto=new Page<>();
        BeanUtils.copyProperties(pageInfo,pageDto,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto>list=records.stream().map((item)->{
            DishDto dishDto=new DishDto();
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            BeanUtils.copyProperties(item,dishDto);
            if(category!=null)
                dishDto.setCategoryName(category.getName());
            return dishDto;
        }).collect(Collectors.toList());
        pageDto.setRecords(list);
        return R.success(pageDto);
    }
   @GetMapping("/{id}")
    public R<DishDto>get(@PathVariable long id){

        return R.success(dishService.getByIdWithFlavor(id));

   }
   @PutMapping
    public R<String>update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功");
   }
//   @GetMapping("/list")
//    private R<List<Dish>>list(Dish dish){
//        LambdaQueryWrapper<Dish>lqw=new LambdaQueryWrapper<>();
//        lqw.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId()).eq(Dish::getStatus,1);
//        lqw.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//       List<Dish> list = dishService.list(lqw);
//       return R.success(list);
//   }
   @GetMapping("/list")
    private R<List<DishDto>>list(Dish dish){
        LambdaQueryWrapper<Dish>lqw=new LambdaQueryWrapper<>();
        lqw.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId()).eq(Dish::getStatus,1);
        lqw.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
       List<Dish> list = dishService.list(lqw);

       List<DishDto>listDto=list.stream().map((item)->{
           DishDto dishDto=new DishDto();
           Long categoryId = item.getCategoryId();
           Category category = categoryService.getById(categoryId);
           BeanUtils.copyProperties(item,dishDto);
           if(category!=null)
           dishDto.setCategoryName(category.getName());
           Long dishId = item.getId();
           LambdaQueryWrapper<DishFlavor>queryWrapper=new LambdaQueryWrapper<>();
           queryWrapper.eq(DishFlavor::getDishId,dishId);
           List<DishFlavor> flavors= dishFlavorService.list(queryWrapper);
           dishDto.setFlavors(flavors);
           return dishDto;
       }).collect(Collectors.toList());

       return R.success(listDto);
   }
}
