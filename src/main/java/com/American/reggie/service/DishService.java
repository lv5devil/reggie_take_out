package com.American.reggie.service;

import com.American.reggie.dto.DishDto;
import com.American.reggie.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(long id);

    void updateWithFlavor(DishDto dishDto);
}
