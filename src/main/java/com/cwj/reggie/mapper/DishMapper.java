package com.cwj.reggie.mapper;

import com.cwj.reggie.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author cwj
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2022-09-16 16:16:25
* @Entity com.cwj.reggie.entity.Dish
*/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}




