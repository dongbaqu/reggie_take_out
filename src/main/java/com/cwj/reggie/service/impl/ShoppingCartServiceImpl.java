package com.cwj.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.reggie.entity.ShoppingCart;
import com.cwj.reggie.service.ShoppingCartService;
import com.cwj.reggie.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
* @author cwj
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2022-09-19 12:21:27
*/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService{

}




