package com.tianhua.datafactory.domain.bo.project;

import java.util.ArrayList;
import java.util.List;

import com.tianhua.datafactory.domain.bo.BaseBO;
import lombok.Data;
import lombok.ToString;

/**
* @Description:项目基本信息类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Data
@ToString
public class ProjectBO extends BaseBO {

   private Long id;

   /** 项目应用名 **/
   private  String projectCode;

   /** 应用描述 **/
   private  String projectDesc;

   /** 业务领域编码 **/
   private  String domainCode;

   /** 业务领域描述 **/
   private  String domainDesc;

   /** app描述 **/
   private  String appDesc;

   /** 所属业务线 **/
   private  String busLine;

   /** 所属部门 **/
   private  String deptName;

   /** 项目api列表 **/
   private  List<ApiBO> apiList;

   public ProjectBO(){}

   public ProjectBO(String projectCode){
      this.projectCode = projectCode;
   }

   public static ProjectBO getInstance(){
      return new ProjectBO();
   }

   public static ProjectBO getInstance(String projectCode){
      return new ProjectBO(projectCode);
   }


   public void addApiBo(ApiBO apiBO){
      if(this.apiList == null){
         this.apiList = new ArrayList<>();
      }
      this.apiList.add(apiBO);
   }

}