package com.cwj.reggie.controller;

import com.cwj.reggie.common.R;
import com.cwj.reggie.dto.DishDto;
import com.cwj.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
