package com.American.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
         log.info("执行公共插入，id为:{}",BaseContext.getThreadLocal());
        metaObject.setValue("createTime",LocalDateTime.now());
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("createUser",BaseContext.getThreadLocal());
        metaObject.setValue("updateUser",BaseContext.getThreadLocal());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("执行公共修改，id为:{}",BaseContext.getThreadLocal());

        metaObject.setValue("updateTime",LocalDateTime.now());

        metaObject.setValue("updateUser",BaseContext.getThreadLocal());

    }
}
