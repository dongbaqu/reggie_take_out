package com.cwj.reggie.mapper;

import com.cwj.reggie.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author cwj
* @description 针对表【order_detail(订单明细表)】的数据库操作Mapper
* @createDate 2022-09-19 16:32:05
* @Entity com.cwj.reggie.entity.OrderDetail
*/
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}




