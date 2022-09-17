package com.cwj.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.reggie.dto.DishDto;
import com.cwj.reggie.entity.Dish;
import com.cwj.reggie.entity.DishFlavor;
import com.cwj.reggie.service.DishFlavorService;
import com.cwj.reggie.service.DishService;
import com.cwj.reggie.mapper.DishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author cwj
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2022-09-16 16:16:25
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{
    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新添菜品，同时保存对应的口味数据
     * @param dishDto
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        Long id = dishDto.getId();//菜品id
        //保存菜品口味数据到菜品口味表dish_flavor
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor dishFlavor : flavors) {
            dishFlavor.setDishId(id);

        }
        dishFlavorService.saveBatch(flavors);
    }
}




