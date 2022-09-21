package com.cwj.reggie.mapper;

import com.cwj.reggie.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author cwj
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2022-09-19 16:32:01
* @Entity com.cwj.reggie.entity.Orders
*/
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}




