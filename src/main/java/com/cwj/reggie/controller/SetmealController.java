package com.cwj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cwj.reggie.common.R;
import com.cwj.reggie.dto.SetmealDto;
import com.cwj.reggie.entity.Category;
import com.cwj.reggie.entity.Setmeal;
import com.cwj.reggie.service.CategoryService;
import com.cwj.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author cwj
 * @version 1.0.0
 * @date 2022/9/18 12:27
 * @description:
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("setmealDto ===== >" + setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("添加成功");
    }


    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();
        //设置查询条件
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(Strings.isNotBlank(name),Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        //执行排序条件
        setmealService.page(pageInfo,queryWrapper);

        /**
         * 目的：在"records"中添加一个字段,用来显示“套餐分类”（CategoryName）
         * 思路
         * 1.先把pageInfo中的"records"(里面是查询出的数据集合)去除，然后把pageInfo中的其他值拷贝到setmealDtoPage
         * 2.创建新的集合list
         * 3.遍历pageInfo中的"records"，把records集合中Setmeal的数据拷贝到setmealDto
         * 4.通过setmeal.getCategoryId()得到分类id，再查询分类对象，得到"套餐分类名"（name）
         * 5.给setmealDto的CategoryName属性赋值
         * 6.添加到新的集合list
         * 7.setmealDtoPage.setRecords(list);最后把list添加到setmealDtoPage的records属性中
         */
        //对象拷贝
        BeanUtils.copyProperties(pageInfo,setmealDtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();
        ArrayList<SetmealDto> list = new ArrayList<>();
        for (Setmeal setmeal :records) {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal,setmealDto);

            Long categoryId = setmeal.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null){
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }

            list.add(setmealDto);
        }
        setmealDtoPage.setRecords(list);

        return R.success(setmealDtoPage);
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteByIds(Long[] ids){
        log.info("ids ==== >" + Arrays.toString(ids));
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }


    /**
     * 根据ids批量修改售卖状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<Setmeal> updateStatusByIds(@PathVariable int status, Long[] ids){
        log.info("status ==== >" + status);
        log.info("ids ==== >" + Arrays.toString(ids));
        //设置状态
        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(status);
        //循环遍历菜品id
        for (Long id : ids) {
            LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(id != null,Setmeal::getId,id);
            setmealService.update(setmeal,updateWrapper);
        }
        return R.success(setmeal);
    }

    //categoryId=1570672935329406977&status=1
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,Setmeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }



}
