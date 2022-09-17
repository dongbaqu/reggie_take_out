package com.cwj.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.reggie.common.CustomException;
import com.cwj.reggie.dto.DishDto;
import com.cwj.reggie.entity.Dish;
import com.cwj.reggie.entity.DishFlavor;
import com.cwj.reggie.mapper.DishMapper;
import com.cwj.reggie.service.DishFlavorService;
import com.cwj.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        //拷贝
        BeanUtils.copyProperties(dish,dishDto);

        //根据菜品id，查询菜品口味
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(id != null,DishFlavor::getDishId,id);

        List<DishFlavor> list = dishFlavorService.list(queryWrapper);

        dishDto.setFlavors(list);
        return dishDto;
    }

    @Transactional
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //更新菜品
        this.updateById(dishDto);

        Long id = dishDto.getId();

        //先删除原口味
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(id != null,DishFlavor::getDishId,id);
        dishFlavorService.remove(queryWrapper);

        //添加新口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor dishFlavor : flavors) {
            dishFlavor.setDishId(id);

        }
        dishFlavorService.saveBatch(flavors);

    }


    @Transactional
    @Override
    public void deleteByIds(Long[] ids) {
        //查询套餐状态，确定是否可用删除
        //select count(*) from setmeal where id in () and status = 1;
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId,ids);
        queryWrapper.eq(Dish::getStatus,1);
        int count = this.count(queryWrapper);
        //如果不能删除，抛出一个异常
        if (count > 0){
            throw new CustomException("改菜品正在售卖中,不能删除");
        }

        //批量删除菜品
        this.removeByIds(Arrays.asList(ids));

        for (Long id : ids) {
            LambdaUpdateWrapper<DishFlavor> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(id != null,DishFlavor::getDishId,id);
            dishFlavorService.remove(updateWrapper);
        }
    }


}




