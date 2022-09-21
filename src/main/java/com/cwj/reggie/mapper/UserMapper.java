package com.cwj.reggie.mapper;

import com.cwj.reggie.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author cwj
* @description 针对表【user(用户信息)】的数据库操作Mapper
* @createDate 2022-09-18 21:18:41
* @Entity com.cwj.reggie.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




