package cn.cdqf.dmsjportal.util;

import cn.cdqf.dmsjportal.entity.DmsjUser;

public class ThreadLocalUtil {
    //用来放 ip与用户名
    //threadLocal可以放入很多个元素
    //A线程放入一个map   A线程永远都会取到map
    //B线程放入一个string B线程取到string
    private static ThreadLocal<Object> threadLocal = new  ThreadLocal<Object>();
    //可以看成一个map  key是线程id
    private static ThreadLocal<DmsjUser> dmsjUserThreadLocal = new ThreadLocal<>();


    public static DmsjUser getDmsjUser() {
        return dmsjUserThreadLocal.get();
    }
    public static void removeDmsjUser(){
        dmsjUserThreadLocal.remove();
    }
    public static void setDmsjUser(DmsjUser dmsjUser) {
        dmsjUserThreadLocal.set(dmsjUser);
    }

    public static ThreadLocal<DmsjUser> getDmsjUserThreadLocal() {
        return dmsjUserThreadLocal;
    }

    public static void setDmsjUserThreadLocal(ThreadLocal<DmsjUser> dmsjUserThreadLocal) {
        ThreadLocalUtil.dmsjUserThreadLocal = dmsjUserThreadLocal;
    }

    //放入的方法
    public static void set(Object object){
        threadLocal.set(object);
    }
    //每个线程都会取出自己线程线程放的 不会取到别的线程
    public static Object get(){
        return threadLocal.get();
    }
    //只会移除当前线程的
    public static void remove(){
        threadLocal.remove();
    }
}
