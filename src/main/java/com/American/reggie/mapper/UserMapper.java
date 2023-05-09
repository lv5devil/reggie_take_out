package com.American.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.American.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User>{
}
