package com.cwj.reggie.mapper;

import com.cwj.reggie.entity.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author cwj
* @description 针对表【shopping_cart(购物车)】的数据库操作Mapper
* @createDate 2022-09-19 12:21:27
* @Entity com.cwj.reggie.entity.ShoppingCart
*/
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}




