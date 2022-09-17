package com.cwj.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.reggie.common.CustomException;
import com.cwj.reggie.entity.Category;
import com.cwj.reggie.entity.Dish;
import com.cwj.reggie.entity.Setmeal;
import com.cwj.reggie.mapper.CategoryMapper;
import com.cwj.reggie.service.CategoryService;
import com.cwj.reggie.service.DishService;
import com.cwj.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author cwj
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2022-09-16 14:50:54
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void removeById(Long id){
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if (count > 0){
            throw new CustomException("当前类关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        if (count > 0){
            throw new CustomException("当前类关联了套餐，不能删除");
        }

        super.removeById(id);

    }
}




