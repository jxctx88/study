package cn.memedai.base.javassist;

/**
 * Created by admin on 2016/8/9.
 */
public class JavassistClass {
    private String name ="default";
    public JavassistClass(){
        name = "me";
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void  execute(){
        System.out.println(name);
        System.out.println("execute ok");
    }

}
































