package com.American.reggie.controller;

import com.American.reggie.common.R;
import com.American.reggie.entity.Employee;
import com.American.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/login")
    public R<Employee>login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(lqw);
        if(emp==null){
            return R.error("用户名不存在！");
        }
        if(!emp.getPassword().equals(password)){
            return R.error("密码错误！");
        }
        if(emp.getStatus()==0){
            return R.error("该用户已锁定！");
        }
        request.getSession().setAttribute("employee",emp.getId());

        return R.success(emp);
    }
    @PostMapping("/logout")
    public R<String>logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
    @PostMapping
    public R<String>save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工信息:{}",employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employeeService.save(employee);
        return R.success("新增用户成功");
    }
    @GetMapping("/page")
    public R<Page>page(int page,int pageSize,String name){

        Page pageInfo=new Page(page,pageSize);
        LambdaQueryWrapper<Employee> lqw=new LambdaQueryWrapper<>();
        lqw.like(name!=null,Employee::getName,name);
        lqw.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo,lqw);
        return R.success(pageInfo);
    }
    @PutMapping
    private R<String>update(HttpServletRequest request,@RequestBody Employee employee){
        employeeService.updateById(employee);
        return R.success("修改成功");
    }
    @GetMapping("/{id}")
    private R<Employee>getById(@PathVariable long id){
        Employee employee = employeeService.getById(id);
        if(employee!=null){
            return R.success(employee);
        }
        return R.error("查询失败");
    }

}
