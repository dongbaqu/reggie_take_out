package com.cwj.reggie.mapper;

import com.cwj.reggie.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author cwj
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2022-09-16 14:50:54
* @Entity com.cwj.reggie.entity.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




