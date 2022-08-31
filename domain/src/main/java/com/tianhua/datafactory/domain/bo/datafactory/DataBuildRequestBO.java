package com.tianhua.datafactory.domain.bo.datafactory;

import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.domain.bo.BaseBO;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@ToString
public class DataBuildRequestBO extends BaseBO {

    /**
     * 项目编码
     */
    private String projectCode;

    /**
     * 模型类名
     */
    private String paramModelCode;

    /**
     * 数据构建规则
     */
    private List<DataBuildRequestFieldBO> fieldBOList;

    /**
     * 生成条数
     */
    private Integer buildCount;

    /**
     * insert.sql
     * json.json(default)
     * excel.xlsx
     *
     */
    private String dataUseType;


    /**
     * 模型对应的api签名
     */
    private String apiSign;

    /**
     * 需要用到的function
     */
    private Map<String, Function> functionMap;

    /**
     * 是否使用ResultDTO进行api 参数结果包装
     */
    private Boolean useResultDtoWrapper;

    /**
     * api返回值对应的数据源编码
     * 适用场景:非业务模型返回值如
     * String
     * Long
     * Integer
     * List<String>
     * Set<Long>
     * Boolean
     * Map<String,String>
     *
     *
     */
    private String apiRespDataSourceCode;

    /**
     * api返回值对应的数据规则
     */
    private DataBuildRequestFieldRuleBO dataBuildRequestFieldRuleBO;
}
