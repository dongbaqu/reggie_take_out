package com.cwj.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cwj.reggie.common.BaseContext;
import com.cwj.reggie.common.PageS;
import com.cwj.reggie.common.R;
import com.cwj.reggie.dto.OrdersDto;
import com.cwj.reggie.entity.Orders;
import com.cwj.reggie.entity.User;
import com.cwj.reggie.service.OrderDetailService;
import com.cwj.reggie.service.OrdersService;
import com.cwj.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private UserService userService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        ordersService.submit(orders);
        return R.success("下单成功");
    }

    /**
     * 分页操作
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page> page(int page, int pageSize){
        log.info("page = {},pageSize = {},name = {}",page,pageSize);
        //构造分页的构造器
        Page<Orders> pageInfo = new Page(page,pageSize);
        //构建条件构建器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());
        //执行查询
        ordersService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 分页查询
     * @param pageS
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(PageS pageS){
        log.info("page ====== > " + pageS.toString());
        //构造分页的构造器
        Page<Orders> pageInfo = new Page(pageS.getPage(), pageS.getPageSize());
        Page<OrdersDto> ordersDtoPage = new Page();
        //构建条件构建器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(pageS.getNumber() != null, Orders::getNumber,pageS.getNumber());
        //添加排序条件
        queryWrapper.between(StringUtils.isNotEmpty(pageS.getBeginTime())
                || StringUtils.isNotEmpty(pageS.getEndTime()),Orders::getOrderTime,pageS.getBeginTime(),pageS.getEndTime());
        queryWrapper.orderByAsc(Orders::getStatus);
        queryWrapper.orderByAsc(Orders::getOrderTime);

        //执行查询
        ordersService.page(pageInfo,queryWrapper);

        BeanUtils.copyProperties(pageInfo,ordersDtoPage,"records");
        List<Orders> records = pageInfo.getRecords();
        List<OrdersDto> ordersDtoList = new ArrayList<>();
        for (Orders record : records) {
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(record,ordersDto);
            Long userId = record.getUserId();

            User user = userService.getById(userId);

            if (user != null){
                String name = user.getName();
                ordersDto.setUserName(name);
            }
            ordersDtoList.add(ordersDto);
        }
        ordersDtoPage.setRecords(ordersDtoList);
        return R.success(ordersDtoPage);
    }

    @PutMapping
    public R<String> update(@RequestBody Orders orders){
        log.info("orders ===== >" + orders.toString());
        boolean update = ordersService.updateById(orders);
        return R.success("修改成功");
    }
}