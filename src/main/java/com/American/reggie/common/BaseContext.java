package com.American.reggie.common;

public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();
    public static void setThreadLocal(long id){
        threadLocal.set(id);
    }
    public static long getThreadLocal(){
        return threadLocal.get();
    }
    public static Long getCurrentId() {
       return threadLocal.get();
    }
}
