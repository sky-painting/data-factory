package com.tianhua.datafactory.domain.bo;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tianhua.datafactory.domain.bo.model.FieldBO;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Description:
 * 抽象plantuml 类
 * date: 2021/6/28
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public abstract class AbstractClassBean {


    /**
     * 类名
     */
    protected String className;

    /**
     * 所在包
     */
    protected String packageName;
    /**
     * 所属模块
     */
    protected String belongModel;

    /**
     * 类描述
     */
    protected String classDesc;

    /**
     * 方法
     */
    private List<MethodBean> methodBeanList;


    /**
     * 父类方法列表
     */
    private List<MethodBean> superMethodBeanList;

    /**
     * 父类属性列表
     *
     */
    private List<FieldBO> superFieldBeanList;

    /**
     * 需要引入的class包名
     */
    private List<String> importClassList;
    /**
     * 属性列表
     *
     */
    private List<FieldBO> fieldBeanList;



    /**
     * 实现接口
     */
    private InterfaceBean implInterfaceBean;

    /**
     * 继承类列表
     */
    private String relationClassStr;
    /**
     * 项目作者
     */
    private String author;

    /**
     * 所在plantUML的包名
     */
    private String plantUMLPackage;

    /**
     * 是否是派生类
     */
    private boolean isDerived;

    /**
     * 组件名称
     * 根据该标示判断是否是plantUML类图中的还是组件扫描引入的
     */
    private String compName;


    private String bodtoConvertInterface;

    private String bodoConvertInterface;

    private String bovoConvertInterface;

    /**
     * 在动态调用中产生的引用包
     */
    private List<String> dynamicImportPackageList = new ArrayList<>();


    private List<String> thisClassImportPackageList = new ArrayList<>();

    /**
     * 子类需要导入的包
     */
    private List<String> childClassImportPackageList = new ArrayList<>();


    /**
     * 枚举值列表
     */
    private List<Map<String,String>> enumValueList;

    /**
     * 派生类链路
     * 如bo派生dto
     * dto的派生链路里就有boclass
     * 用来记录派生过程中的关联关系
     */
    private List<String> derivedChainClassList;


    /**
     * 类上的注释
     */
    private String annotation;

    /**
     * 所属上下文
     */
    private String context = "";

    /**
     * plantUML的扩展注解标示
     */
    private List<String> annotationTagList;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractClassBean that = (AbstractClassBean) o;
        return Objects.equals(className, that.className) &&
                Objects.equals(packageName, that.packageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, packageName);
    }


    public void buildPlantUMLPackage(String plantUMLPackage){
        if(plantUMLPackage.contains("\"")){
            String content = plantUMLPackage.split("\"")[1];
            if(content.contains("-")){
                String [] arr = content.split("-");
                if(arr[1].contains(".")){
                    this.setPlantUMLPackage(arr[1].trim());
                    return;
                }
                if(arr[0].contains(".")){
                    this.setPlantUMLPackage(arr[0].trim());
                    return;
                }

            }
        }

        this.setPlantUMLPackage(plantUMLPackage.trim().trim());
    }

    /**
     * 合并引用包，
     * 接口的引用包与实现的引用包合并
     * @param importClassList
     */
    public void mergeImportClass(List<String> importClassList){
        if(CollectionUtils.isEmpty(importClassList)){
            return;
        }
        if(CollectionUtils.isEmpty(this.getImportClassList())){
            this.setImportClassList(importClassList);
        }else {
            Set<String> newHashSet = Sets.newHashSet(importClassList);
            for (String importClassName : this.getImportClassList()){
                newHashSet.remove(importClassName);
            }
            this.getImportClassList().addAll(Lists.newArrayList(newHashSet));
        }
    }


}
