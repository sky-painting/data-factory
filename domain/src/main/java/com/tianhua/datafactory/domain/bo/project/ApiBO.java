package com.tianhua.datafactory.domain.bo.project;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.domain.enums.ReturnWrapClassEnum;
import com.tianhua.datafactory.domain.bo.BaseBO;
import com.tianhua.datafactory.domain.bo.model.ParamModelBO;

import com.tianhua.datafactory.domain.enums.ApiTypeEnum;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * @version v1.0
 * @Description:api模型信息类
 * @Author:shenshuai
 * @CreateTime:2022-05-27 16:05:26
 */
@Data
@ToString
public class ApiBO extends BaseBO {

    private Long id;

    /**
     * api类型
     **/
    private String apiType;

    /**
     * api信息
     **/
    private String apiUrl;

    /**
     * api签名
     **/
    private String apiSign;

    /**
     * api返回类型
     **/
    private String returnType;

    /**
     * 请求参数元信息
     **/
    private String requestParam;


    /**
     * api描述
     **/
    private String apiDoc;


    /**
     * api请求方式
     **/
    private String methodType;

    /**
     * api返回值集合
     **/
    private String returnValue;

    /**
     * api参数列表
     **/
    private List<ParamModelBO> paramList;

    /**
     * api参数可选值列表
     **/
    private Map<Integer, List<Object>> paramDefaultValueList;

    /**
     * api状态
     **/
    private Integer status;

    /**
     * api所属项目编码
     **/
    private String projectCode;
    /**
     * api返回的对象信息
     */
    private ParamModelBO returnParamModel;

    /**
     * api返回的对象信息
     */
    private String returnParam;


    /**
     * api返回参数类名
     */
    private String returnParamClass;
    /**
     * 请求参数列表
     */
    private String requestParamClasses;

    /**
     * 所属模块
     */
    private String moduleCode;

    /**
     * 所属模块名称
     */
    private String moduleName;

    /**
     * 进行接口mock调用的时候接口返回的条数
     * 1条或者多条
     * 模拟后端数据返回
     */
    private Integer mockCount;


    /**
     * api返回包装类型
     * @see ReturnWrapClassEnum
     */
    private Integer apiReturnWrapType;





    public ApiBO() {
    }

    public ApiBO(String projectCode, String apiUrl) {
        this.projectCode = projectCode;
        this.apiUrl = apiUrl;
    }


    public static ApiBO getInstance(String projectCode, String apiUrl) {
        return new ApiBO(projectCode, apiUrl);
    }


    public void disable() {
        this.setStatus(0);
    }

    public void enable() {
        this.setStatus(1);
    }

    /**
     * 构建api签名
     */
    public void buildApiSign() {
        if (ApiTypeEnum.HTTP_API.getType().equals(apiType)) {
            this.apiSign = projectCode + ":" + apiUrl + "." + methodType;
        } else {
            this.apiSign = projectCode + ":" + apiUrl;
        }

        if (this.paramList != null && !this.paramList.isEmpty()) {
            this.apiSign = this.apiSign + "." + this.paramList.size();
        }
    }


    /**
     * 构建返回参数模型
     */
    public void buildReturnParamModel() {
        if (StringUtils.isNotEmpty(returnParam)) {
            this.returnParamModel = JSON.parseObject(returnParam,ParamModelBO.class);
            if(this.returnParamModel != null){
                this.returnParamClass = returnParamModel.getParamClassName();
            }
        }
    }

    /**
     * 构建请求参数模型
     */
    public void buildRequestParam() {
        if (StringUtils.isNotEmpty(requestParam)) {
            this.paramList = JSON.parseArray(requestParam,ParamModelBO.class);
            if(this.paramList == null || this.paramList.isEmpty()){
                return;
            }
            List<String> requestParamClassList =  this.paramList.stream().map(ParamModelBO::getParamClassName).collect(Collectors.toList());
            this.requestParamClasses = StringUtils.join(requestParamClassList,",");
        }
    }
}