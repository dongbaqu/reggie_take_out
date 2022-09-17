package com.cwj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cwj.reggie.common.R;
import com.cwj.reggie.dto.DishDto;
import com.cwj.reggie.entity.Category;
import com.cwj.reggie.entity.Dish;
import com.cwj.reggie.entity.DishFlavor;
import com.cwj.reggie.service.CategoryService;
import com.cwj.reggie.service.DishFlavorService;
import com.cwj.reggie.service.DishService;
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
 * @date 2022/9/17 10:41
 * @description:
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishFlavorService dishFlavorService;
    /**
     * 添加菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info("dishDto  ==== > " + dishDto);
        dishService.saveWithFlavor(dishDto);
        return R.success("添加成功");
    }


    /**
     * 模糊查询+分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        //设置查询条件
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(Strings.isNotBlank(name),Dish::getName,name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        //执行排序条件
        dishService.page(pageInfo,queryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = new ArrayList<>();
        for (Dish dish :records) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish,dishDto);

            Long categoryId = dish.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            list.add(dishDto);
        }
        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }


    /**
     * 修改回显
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public R<DishDto> update(@PathVariable Long id){
        log.info("id ===== > {}" + id);
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 根据id修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> updateWithFlavor(@RequestBody DishDto dishDto){
        log.info("dishDto ==== >" + dishDto);
        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功");
    }

    /**
     * 根据ids批量修改售卖状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<Dish> updateStatusByIds(@PathVariable int status,Long[] ids){
        log.info("status ==== >" + status);
        log.info("ids ==== >" + Arrays.toString(ids));
        //设置状态
        Dish dish = new Dish();
        dish.setStatus(status);
        //循环遍历菜品id
        for (Long id : ids) {
            LambdaUpdateWrapper<Dish> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(id != null,Dish::getId,id);
            dishService.update(dish,updateWrapper);
        }
        return R.success(dish);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteByIds(Long[] ids){
        log.info("ids ==== >" + Arrays.toString(ids));
        dishService.deleteByIds(ids);
        return R.success("删除成功");
    }


    //  dish/list?categoryId=1413341197421846529
//    @GetMapping("/list")
//    public R<List<Dish>> getListByCategoryId(Long categoryId){
//        log.info("categoryId ====== >" + categoryId);
//
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Dish::getStatus,1);
//        queryWrapper.eq(Dish::getCategoryId,categoryId);
//        queryWrapper.orderByDesc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//
//        List<Dish> list = dishService.list(queryWrapper);
//        return R.success(list);
//    }


    @GetMapping("/list")
    public R<List<DishDto>> getListByCategoryId(Long categoryId){
        log.info("categoryId ====== >" + categoryId);

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.eq(Dish::getCategoryId,categoryId);
        queryWrapper.orderByDesc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);
        List<DishDto> dishDtoArrayList = new ArrayList<>();
        for (Dish dish : list) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish,dishDto);
            Long id = dish.getId();
            LambdaQueryWrapper<DishFlavor> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(id != null,DishFlavor::getDishId,id);
            List<DishFlavor> listDF = dishFlavorService.list(queryWrapper1);
            dishDto.setFlavors(listDF);
            dishDtoArrayList.add(dishDto);
        }

        return R.success(dishDtoArrayList);
    }
}
