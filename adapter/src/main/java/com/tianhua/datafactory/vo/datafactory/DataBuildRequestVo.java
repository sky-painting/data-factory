package com.tianhua.datafactory.vo.datafactory;

import com.tianhua.datafactory.vo.model.FieldVO;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * description: DataFactoryVo <br>
 * date: 2020/12/2 23:48 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Data
@ToString
public class DataBuildRequestVo {
    /**
     * 项目编码
     */
    private String projectCode;

    /**
     * 模型类名
     */
    private String paramModelCode;

    /**
     * 模型属性名称
     */
    private List<FieldVO> fieldVOList;

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
     * mock数据结果
     */
    private String mockResultData;

}
