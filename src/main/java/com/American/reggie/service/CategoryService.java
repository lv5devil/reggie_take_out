package com.American.reggie.service;

import com.American.reggie.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CategoryService extends IService<Category> {

    void remove(long id);
}
