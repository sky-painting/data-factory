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
public class DataFactoryRequestVo {
    /**
     * 项目编码
     */
    private String projectCode;

    /**
     * 模型类名
     */
    private String paramModelCode;

    /**
     * 数据规则
     */
    private List<FieldVO> fieldVOList;

    /**
     * 生成条数
     */
    private Integer buildCount;

}
