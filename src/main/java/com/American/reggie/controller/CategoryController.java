package com.American.reggie.controller;

import com.American.reggie.common.R;
import com.American.reggie.entity.Category;
import com.American.reggie.entity.Dish;
import com.American.reggie.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        return R.success("添加分类成功");
    }
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        Page pageInfo=new Page(page,pageSize);
        LambdaQueryWrapper<Category> lqw=new LambdaQueryWrapper<>();
        lqw.orderByAsc(Category::getSort);
        categoryService.page(pageInfo,lqw);

        return R.success(pageInfo);
    }
    @DeleteMapping
   public R<String>delete(long id){
        categoryService.remove(id);
        return R.success("删除成功");
    }
    @PutMapping
    public R<String>update(@RequestBody Category category){

        categoryService.updateById(category);
        log.info("修改数据名称为:{}",category.getName());
        return R.success("修改成功！");
    }
    @GetMapping("list")
    public R<List<Category>>list(Category category){
        LambdaQueryWrapper<Category> lqw=new LambdaQueryWrapper<>();
        lqw.eq(category.getType()!=null,Category::getType,category.getType());
        lqw.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(lqw);
        return R.success(list);
    }
}
