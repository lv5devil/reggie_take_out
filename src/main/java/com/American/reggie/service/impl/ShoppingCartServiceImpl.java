package com.American.reggie.service.impl;

import com.American.reggie.entity.ShoppingCart;
import com.American.reggie.mapper.ShoppingCartMapper;
import com.American.reggie.service.ShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>implements ShoppingCartService {
}
