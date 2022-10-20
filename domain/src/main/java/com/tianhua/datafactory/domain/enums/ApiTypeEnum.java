package com.tianhua.datafactory.domain.enums;

import com.tianhua.datafactory.domain.support.OptionsBO;
import lombok.Getter;

/**
* @Description:api类型类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Getter
public enum ApiTypeEnum{

   /**
    *
    */
   HTTP_API("httpApi","对外开放的http协议接口"),
   RPC_API("rpcApi","对外开放的rpc协议的接口"),
   SERVICE_API("serviceApi","服务内部的Service接口,包括所有方法"),
   ;

   /** api类型code **/
   private String type;
   /** api类型描述 **/
   private String desc;


   ApiTypeEnum(String type,String desc){
       this.type = type;
       this.desc = desc;
   }

   public static OptionsBO getOptionList(){
      OptionsBO optionsBO = new OptionsBO();
      for (ApiTypeEnum apiTypeEnum : ApiTypeEnum.values()){
         optionsBO.addOptionItem(apiTypeEnum.getDesc(), apiTypeEnum.getType());
      }
      return optionsBO;
   }

   /**
    * 数据枚举路由
    * @param enumCode
    * @return
    */
   public static boolean isApiType(String enumCode){
      return "apiType".equals(enumCode);
   }

}