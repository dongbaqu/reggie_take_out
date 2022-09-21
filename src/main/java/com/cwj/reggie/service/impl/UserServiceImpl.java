package com.cwj.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.reggie.entity.User;
import com.cwj.reggie.service.UserService;
import com.cwj.reggie.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author cwj
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2022-09-18 21:18:41
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




