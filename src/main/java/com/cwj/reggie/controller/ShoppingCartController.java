package com.cwj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cwj.reggie.common.BaseContext;
import com.cwj.reggie.common.CustomException;
import com.cwj.reggie.common.R;
import com.cwj.reggie.entity.ShoppingCart;
import com.cwj.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author cwj
 * @version 1.0.0
 * @date 2022/9/19 14:12
 * @description:
 */
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("shoppingCart" + shoppingCart);
        //设置用户id,指定当前用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        //查询当前菜品或者套餐是否在购物车中
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();
        ShoppingCart cartServiceOne = null;
            LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
            //queryWrapper.and(wrapper -> wrapper.eq("batch_name", prodBatch.getBatchName()).or().eq("batch_code", prodBatch.getBatchCode()));
            queryWrapper.and(wrapper -> wrapper.eq(dishId != null, ShoppingCart::getDishId, dishId)
                    .or().eq(setmealId != null, ShoppingCart::getSetmealId, setmealId));
            queryWrapper.eq(currentId != null, ShoppingCart::getUserId, currentId);
            cartServiceOne = shoppingCartService.getOne(queryWrapper);
        if (cartServiceOne != null) {
            //如果以经存在，就在原来的数量基础上加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            cartServiceOne.setCreateTime(LocalDateTime.now());
            shoppingCartService.updateById(cartServiceOne);
        } else {
            //如果不存在，则添加到购物车，数量默认就是一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }

        return R.success(cartServiceOne);
    }



    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {
        log.info("shoppingCart  ==== >" + shoppingCart);
        ShoppingCart cartServiceOne = null;
        if (shoppingCart != null) {
            //设置用户id,指定当前用户的购物车数据
            Long currentId = BaseContext.getCurrentId();
            shoppingCart.setUserId(currentId);

            //查询当前菜品或者套餐是否在购物车中
            Long dishId = shoppingCart.getDishId();
            Long setmealId = shoppingCart.getSetmealId();
            LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(dishId != null, ShoppingCart::getDishId, dishId).
                    or().eq(setmealId != null, ShoppingCart::getSetmealId, setmealId);
            queryWrapper.eq(currentId != null, ShoppingCart::getUserId, currentId);
            cartServiceOne = shoppingCartService.getOne(queryWrapper);
            Integer number = cartServiceOne.getNumber();
            //根据菜品数量判断是否相减
            if (number > 1) {
                cartServiceOne.setNumber(number - 1);
                shoppingCartService.updateById(cartServiceOne);
            } else if (number == 1) {
                shoppingCartService.removeById(cartServiceOne);
                cartServiceOne.setNumber(number - 1);
            } else {
                throw new CustomException("系统异常，请稍后再试");
            }

        }
        return R.success(cartServiceOne);
    }


    /**
     * 查询购物车
     *
     * @return
     */
    //shoppingCart/list
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(HttpSession session) {
        //根据用户id查询购物车
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        Long currentId = BaseContext.getCurrentId();
        Long user = (Long) session.getAttribute("user");
        log.info("currentId == {},,,user === {}", currentId, user);
        queryWrapper.eq(ShoppingCart::getUserId, currentId);
        queryWrapper.orderByDesc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 清空购物车
     *
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean() {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartService.remove(queryWrapper);
        return R.success("清空购物车成功");
    }

}
