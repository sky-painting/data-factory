package com.tianhua.datafactory.vo.datasource;

import com.tianhua.datafactory.vo.BaseVO;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
* @Description:数据源管理表VO类
* @Author:fanchunshuai
* @CreateTime:2020-12-02 23:07:13
* @version v1.0
*/
@Data
@ToString
public class DataSourceVO extends BaseVO {


	/** 主键 **/
	private Long id;

	/** 数据源名称 **/
	private String sourceName;

	/** 数据源code,唯一 **/
	private String sourceCode;

	/** 数据源类型/(regist,api,enum,constant) **/
	private Integer sourceType;
	/**
	 * 数据提供来源服务
	 */
	private String providerService;

	/** 数据源访问url **/
	private String url;


	/** 访问策略 **/
	private Integer visitStrategy;

	private String visitStrategyDesc;
	private String sourceTypeDesc;


	/**
	 * 服务提供者域名
	 */
	private String providerDomainUrl;

	/**
	 * 注册中心类型
	 */
	private Integer registServer;

	/**
	 * 数据源格式
	 */
	private String structType;

    /**
     * 数据源请求配置参数
	 */
	private List<DataSourceReqConfigVO> dataSourceReqConfigList;

    /**
     * 数据源响应配置参数
	 */
	private List<DataSourceRespConfigVO> dataSourceRespConfigList;


	/**
	 * 数据列表
	 */
	private List<KVPairVO> kvPairList;


}