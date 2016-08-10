package cn.memedai.base.javassist;

import javassist.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by admin on 2016/8/9.
 * 使用javassist来创建[cn.memedai.base.javassist.JavassistClass]类的代码
 */
public class JavassistDemo01 {

    public static void main(String[] args) throws Exception {
        ClassPool cp = ClassPool.getDefault();
        CtClass ctClass = cp.makeClass("cn.memedai.base.javassist.JavassistClass");

        StringBuilder body = null;
        //参数 1：属性类型 2：属性名称 3：所属类CtClass
        CtField ctField = new CtField(cp.get("java.lang.String"), "name", ctClass);
        ctField.setModifiers(Modifier.PRIVATE);
        //设置name属性的get set方法
        ctClass.addMethod(CtNewMethod.setter("setName", ctField));
        ctClass.addMethod(CtNewMethod.getter("getName", ctField));
        ctClass.addField(ctField, CtField.Initializer.constant("default"));

        //设置构造函数 参数 1：参数类型 2：所属类CtClass
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, ctClass);
        body = new StringBuilder();
        body.append("{\n name=\"me\";\n}");
        ctConstructor.setBody(body.toString());
        ctClass.addConstructor(ctConstructor);

        //设置execute方法 参数： 1：返回类型 2：方法名称 2：传人参数类型 4：所属类CtClass
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "execute", new CtClass[]{}, ctClass);
        ctMethod.setModifiers(Modifier.PUBLIC);
        body = new StringBuilder();
        body.append("{\n System.out.println(name);");
        body.append("\n System.out.println(\"execute ok\");");
        body.append("\n return ;");
        body.append("\n}");
        ctMethod.setBody(body.toString());
        ctClass.addMethod(ctMethod);
        Class<?> c = ctClass.toClass();
        Object o = c.newInstance();
        Method method = o.getClass().getMethod("execute", new Class[]{});
        //调用字节码生产类的execute方法
        method.invoke(o, new Object[]{});

    }
}





















































































