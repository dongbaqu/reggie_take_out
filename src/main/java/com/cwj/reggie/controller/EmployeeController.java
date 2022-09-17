package com.cwj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cwj.reggie.common.Constants;
import com.cwj.reggie.common.R;
import com.cwj.reggie.entity.Employee;
import com.cwj.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author cwj
 * @version 1.0.0
 * @description TODO
 * @date 2022/9/12 12:39
 */
@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 登录验证
     * @param employee
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpSession session){
        //1.将密码进行MD5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2.根据页面提交的用户username，查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //3.如果没有查询到则返回登录失败
        if (emp == null){
            return R.error("该用户不存在");
        }

        //4.密码对比，如果不一致则返回登录失败
        if (!emp.getPassword().equals(password)){
            return R.error("密码错误");
        }

        //5.查看员工状态，如果为已禁用状态，则返回员工禁用
        if (emp.getStatus() == Constants.STATE_NO){
            return R.error("账号已被禁用");
        }

        //6.登录成功，将员工存入Session并返回登录成功
        session.setAttribute(Constants.EMPLOYEE_SESSION,emp.getId());

        return R.success(emp);
    }

    /**
     * 退出操作
     * @param session
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpSession session){
        session.removeAttribute(Constants.EMPLOYEE_SESSION);
        return R.success("退出成功");
    }

    /**
     * 添加用户
     * @param employee
     * @param session
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Employee employee,HttpSession session){
       // Employee(id=null, name=张三, username=zhangsan, password=null, phone=18312312312, sex=1, idNumber=123123123123123, status=null, createTime=null, updateTime=null, createUser=null, updateUser=null)
        //设置MD5加密密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());
        //获取当前登录用户id
        //Long id = (Long)session.getAttribute(Constants.EMPLOYEE_SESSION);
        //employee.setCreateUser(id);
        //employee.setUpdateUser(id);

        employeeService.save(employee);
    return R.success("添加成功");
    }

    /**
     * 分页操作
     * @param page
     * @param pageSize
     * @param name 模糊查询
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page = {},pageSize = {},name = {}",page,pageSize,name);
        //构造分页的构造器
        Page<Employee> pageInfo = new Page(page,pageSize);
        //构建条件构建器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 修改用户信息
     * @param employee
     * @param session
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Employee employee,HttpSession session){
        long id = Thread.currentThread().getId();
        log.info("线程id为： "+ id);
        log.info(employee.toString());
        //封装数据
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser((Long) session.getAttribute(Constants.EMPLOYEE_SESSION));
        employeeService.updateById(employee);
        return R.success("修改成功");
    }

    /**
     *回显用户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getEmployeeById(@PathVariable Long id){
        log.info("id === > {}",id);
        Employee employee = employeeService.getById(id);
        return R.success(employee);
    }

}
