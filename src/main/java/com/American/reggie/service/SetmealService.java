package com.American.reggie.service;

import com.American.reggie.dto.SetmealDto;
import com.American.reggie.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);

    void deleteWithDish(List<Long> ids);

}
