package com.cwj.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.reggie.entity.Employee;
import com.cwj.reggie.service.EmployeeService;
import com.cwj.reggie.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
* @author cwj
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2022-09-12 12:20:46
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

}




