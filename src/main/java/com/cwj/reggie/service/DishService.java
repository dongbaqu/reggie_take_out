package com.cwj.reggie.service;

import com.cwj.reggie.dto.DishDto;
import com.cwj.reggie.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author cwj
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2022-09-16 16:16:25
*/
public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);
}
