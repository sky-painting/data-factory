package com.tianhua.datafactory.domain.bo.datafactory;

import com.tianhua.datafactory.domain.bo.BaseBO;
import com.tianhua.datafactory.domain.bo.model.FieldBO;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class DataFactoryBuildBO extends BaseBO {

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
    private List<FieldBO> fieldBOList;

    /**
     * 生成条数
     */
    private Integer buildCount;

}
