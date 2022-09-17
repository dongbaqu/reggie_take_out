package com.cwj.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.reggie.entity.DishFlavor;
import com.cwj.reggie.service.DishFlavorService;
import com.cwj.reggie.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author cwj
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2022-09-17 10:21:16
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




