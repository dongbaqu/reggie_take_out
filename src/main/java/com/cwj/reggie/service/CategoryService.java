package com.cwj.reggie.service;

import com.cwj.reggie.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author cwj
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2022-09-16 14:50:54
*/
public interface CategoryService extends IService<Category> {
    void removeById(Long id);
}
