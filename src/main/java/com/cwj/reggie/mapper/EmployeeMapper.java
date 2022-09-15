package com.cwj.reggie.mapper;

import com.cwj.reggie.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author cwj
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2022-09-12 12:20:46
* @Entity com.cwj.reggie.entity.Employee
*/
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}




