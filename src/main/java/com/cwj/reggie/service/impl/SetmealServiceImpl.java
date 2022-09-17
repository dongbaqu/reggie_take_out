package com.cwj.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.reggie.common.CustomException;
import com.cwj.reggie.dto.SetmealDto;
import com.cwj.reggie.entity.Setmeal;
import com.cwj.reggie.entity.SetmealDish;
import com.cwj.reggie.mapper.SetmealMapper;
import com.cwj.reggie.service.SetmealDishService;
import com.cwj.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
* @author cwj
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
* @createDate 2022-09-16 16:23:52
*/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{
    @Autowired
    private SetmealDishService setmealDishService;
    /**
     * 添加套餐
     * @param setmealDto
     */
    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);

        Long id = setmealDto.getId();
        //添加id
        List<SetmealDish> list = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : list) {
            setmealDish.setSetmealId(id);
        }
        //保存菜单和菜品的关联关系，操作setmeal_dish表
        setmealDishService.saveBatch(list);

    }

    @Transactional
    @Override
    public void removeWithDish(Long[] ids) {
        //查询套餐状态，确定是否可用删除
        //select count(*) from setmeal where id in () and status = 1;
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);
        //如果不能删除，抛出一个异常
        if (count > 0){
            throw new CustomException("改套餐正在售卖中,不能删除");
        }

        //如果可以删除，先删除套餐表中的数据
        this.removeByIds(Arrays.asList(ids));
        //删除关系表中的数据
        LambdaQueryWrapper<SetmealDish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(dishLambdaQueryWrapper);
    }
}




