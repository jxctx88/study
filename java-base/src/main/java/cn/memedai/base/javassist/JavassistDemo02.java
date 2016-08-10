package cn.memedai.base.javassist;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Method;

/**
 * Created by admin on 2016/8/9.
 * 使用javassist来进行AOP拦截处理
 * 对JavassistClass类的getName()方法进行拦截前置处理
 */
public class JavassistDemo02 {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        ProxyFactory factory = new ProxyFactory();
        //设置父类，ProxyFactory将会动态生产一个类，继承该父类
        factory.setSuperclass(JavassistClass.class);
        //设置过滤器，判断哪些方法调用需要别拦截
        factory.setFilter(new MethodFilter() {
            @Override
            public boolean isHandled(Method method) {
                if(method.getName().equals("getName")){
                    return true;
                }
                return false;
            }
        });

        //设置拦截处理
        factory.setHandler(new MethodHandler() {
            @Override
            public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
                //拦截后置处理，改写name属性的内容
                //实际情况可根据需求修改
                JavassistClass o = (JavassistClass)self;
                o.setName("haha");

                return proceed.invoke(self,args);
            }
        });

        Class<?> c = factory.createClass();
        JavassistClass object = (JavassistClass) c.newInstance();
        System.out.println(object.getName());

    }


}


























































