package com.tianhua.datafactory.domain.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.tianhua.datafactory.domain.enums.VisibilityEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Description:
 * date: 2021/6/28
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class MethodBean {

    public MethodBean(){}
    public MethodBean(String methodName, String returnClass){
        this.methodName = methodName;
        this.returnClass = returnClass;
    }

    public MethodBean(String methodName, String returnClass, String desc){
        this.methodName = methodName;
        this.returnClass = returnClass;
        this.desc = desc;
    }
    /**
     * 方法名称，
     * 包括参数，括号
     */
    private String methodName;
    /**
     * 访问权限
     */
    private String visibility;

    /**
     * 方法描述
     */
    private String desc;

    /**
     * 是否是静态属性
     */
    private boolean isStatic;

    /**
     * 方法返回值
     */
    private String returnClass;

    /**
     * 方法返回对象所在包
     */
    private String returnClassPackage;


    /**
     * 方法内容
     */
    private String  methodContent;

    /**
     * 方法返回体
     */
    private String returnBody;

    /**
     * controller方法的请求路径
     */
    private String pathValue;


    /**
     * 方法调用内容，根据调用流程图-plantUML解析而来
     */
    private LinkedList<String> invokeMethodList = new LinkedList<>();


    /**
     * 方法文档
     */
    private String doc;

    /**
     * 解析出来的方法参数列表
     */
    private String [] paramArr;

    /**
     * 参数列表
     */
    private List<ParamBean> paramBeanList;

    /**
     * list 扩展性比较强
     * 对于mapper和controller需要增加参数注解的进行动态构建参数注解
     */
    private List<String> paramAnnotationList = new ArrayList<>(5);

    /**
     * 所属类名
     */
    private String className;

    /**
     * 方法上的注解
     */
    private String annotation;

    /**
     * 方法的范型返回值
     */
    private String genericReturnType;

    /**
     * 方法的范型参数
     */
    private List<String> genericParamList;

    /**
     * 是否是bo模型定义的扩展接口
     */
    private boolean modelExtendMethod;

    /**
     * plantUML的扩展注解标示
     */
    private List<String> annotationTagList;



    private String simpleMethodNameInfo;

    /**
     * 优化方法接口注释
     * @param desc
     */
    public void buildDesc(String desc){
        if(desc.startsWith(VisibilityEnum.PUBLIC.getTag())
          || desc.startsWith(VisibilityEnum.PRIVATE.getTag())
          || desc.startsWith(VisibilityEnum.PROTECT.getTag())){
            String newDesc = desc.substring(1);
            this.setDesc(newDesc);
        }else {
            this.setDesc(desc);
        }
    }


    /**
     * 从方法名称中解析到方法参数
     */
    public void buildParamArr(){
        if(this.getParamArr() != null){
            return;
        }
        if(!this.getMethodName().contains("(") && !this.getMethodName().contains(")")){
            return;
        }
        if (!this.getMethodName().contains("()")){
            String [] paramArr = this.getMethodName().replace(")","").split("\\(")[1].split(",");

            if(paramArr != null && paramArr.length>=1){
                this.setParamArr(paramArr);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodBean that = (MethodBean) o;
        return Objects.equals(methodName, that.methodName) &&
                Objects.equals(returnClass, that.returnClass) &&
                Objects.equals(className, that.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodName, returnClass, className);
    }

    /**
     * 刷新方法声明，注入方法参数注解，也可能是方法注解，后续可扩展
     * @return
     */
    public String refreshMethodName(){
        if(CollectionUtils.isEmpty(this.paramAnnotationList)){
            return this.getMethodName();
        }
        String preMethod = this.getMethodName().split("\\(")[0];
        StringBuilder builder = new StringBuilder(preMethod);
        List<String> paramList = new ArrayList<>();
        for (int i = 0;i < this.getParamArr().length;i ++){
            String annotation = this.paramAnnotationList.get(i);
            String param = this.getParamArr()[i];
            paramList.add(annotation + " " + param);
        }
        builder.append("(");
        builder.append(StringUtils.join(paramList,", "));
        builder.append(")");

        return builder.toString();
    }

    /**
     * 将方法中的参数信息去掉，仅仅获取方法名
     * @return
     */
    public String getSimplMethodName(){
        if(this.getMethodName().contains("()")){
            this.simpleMethodNameInfo = this.getMethodName().replace("()","");
            return this.simpleMethodNameInfo;
        }
        else {
            this.simpleMethodNameInfo = this.getMethodName().split("\\(")[0];
            return this.simpleMethodNameInfo;
        }
    }


}
