package com.cwj.reggie.service;

import com.cwj.reggie.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author cwj
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2022-09-19 16:32:01
*/
public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
