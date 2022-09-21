package com.cwj.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.reggie.entity.OrderDetail;
import com.cwj.reggie.service.OrderDetailService;
import com.cwj.reggie.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author cwj
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2022-09-19 16:32:05
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




