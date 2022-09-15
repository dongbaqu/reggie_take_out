package com.cwj.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.reggie.entity.Category;
import com.cwj.reggie.service.CategoryService;
import com.cwj.reggie.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author cwj
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2022-09-16 14:50:54
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}




