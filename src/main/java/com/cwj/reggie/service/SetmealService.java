package com.cwj.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cwj.reggie.dto.SetmealDto;
import com.cwj.reggie.entity.Setmeal;

/**
* @author cwj
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2022-09-16 16:23:52
*/
public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);

    void removeWithDish(Long[] ids);
}
