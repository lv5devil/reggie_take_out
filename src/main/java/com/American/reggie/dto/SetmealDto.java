package com.American.reggie.dto;

import com.American.reggie.entity.Setmeal;
import com.American.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
