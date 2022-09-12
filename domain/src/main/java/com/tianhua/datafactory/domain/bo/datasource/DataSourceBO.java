package com.tianhua.datafactory.domain.bo.datasource;

import com.tianhua.datafactory.domain.bo.BaseBO;
import com.tianhua.datafactory.domain.enums.DataSourceTypeEnum;
import com.tianhua.datafactory.domain.support.kvpair.KVPairBO;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
* @Description:数据源管理表VO类
* @Author:fanchunshuai
* @CreateTime:2020-12-02 23:07:13
* @version v1.0
*/
@Data
@ToString
public class DataSourceBO extends BaseBO {


	/** 主键 **/
	private Long id;

	/** 数据源名称 **/
	private String sourceName;

	/** 数据源code,全局唯一 **/
	/**
	 * 格式如下:
	 * 服务名:com.xxx.xxx.服务名.模块名.模型名
	 */
	private String sourceCode;

	/**
	 *
	 * 数据源类型
	 * @See DataSourceTypeEnum
	 */
	private Integer sourceType;

	/**
	 * 服务提供者域名
	 */
	private String providerDomainUrl;

	/**
	 * 注册中心类型
	 */
	private Integer registServer;


	/**
	 * 数据提供来源服务
	 */
	private String providerService;

	/** 数据源访问url **/
	private String url;

	/** 访问策略 **/
	private Integer visitStrategy;

	/**
	 * 数据源返回格式
	 */
	private String structType;

	/**
	 * 数据源请求配置参数
	 */
	private List<DataSourceReqConfigBO> dataSourceReqConfigList;

	/**
	 * 数据源响应配置参数
	 */
	private List<DataSourceRespConfigBO> dataSourceRespConfigList;


	private List<KVPairBO> kvPairList;


	public boolean isLocalEnum(){
		return sourceType == DataSourceTypeEnum.FROM_SERVICE_ENUM.getCode();

	}

}