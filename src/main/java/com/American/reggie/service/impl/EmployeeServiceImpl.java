package com.American.reggie.service.impl;

import com.American.reggie.entity.Employee;
import com.American.reggie.mapper.EmployeeMapper;
import com.American.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
