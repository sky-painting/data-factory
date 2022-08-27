package com.tianhua.datafactory.domain.factory;

import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldRuleBO;
import com.tianhua.datafactory.domain.bo.datafactory.FieldDSLKeyConstant;
import org.springframework.stereotype.Service;

/**
 * Description
 *
 * 属性规则dsl解析工厂
 *
 * date: 2022/8/12
 * orderCode:
 *   prefix=BRAND_;
 *   subfix=000x;
 *
 * orderDetailBOList:
 *   orderCode.relyField=orderCode
 *   itemId.relySourceCode=com.item.skuFacade#skuid (依赖商品服务的skuid)
 *   count.relySourceCode=com.datafactory.random(10) (随机数函数,10以内)
 *   price.relySourceCode=com.datafactory.random(1000) (随机数函数,1000以内)
 *   relyCount=100 (随机生成100条orderDetailBO使用)
 *   funcVar
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class FieldRuleDslFactory {

    /**
     * 根据
     * @param fieldBuildRuleDSL
     * @return
     */
    public DataBuildRequestFieldRuleBO buildRuleBO(String fieldBuildRuleDSL){
        DataBuildRequestFieldRuleBO dataBuildRequestFieldRuleBO = new DataBuildRequestFieldRuleBO();
        String [] array = fieldBuildRuleDSL.trim().split(";");
        for (String dslStr : array){
            String [] kvArr = dslStr.trim().split("=");
            if(kvArr[0].trim().equals(FieldDSLKeyConstant.PREFIX)){
                dataBuildRequestFieldRuleBO.setPrefix(kvArr[1].trim());
            }
            if(kvArr[0].trim().equals(FieldDSLKeyConstant.SUBFIX)){
                dataBuildRequestFieldRuleBO.setSubfix(kvArr[1].trim());
            }
            if(kvArr[0].trim().equals(FieldDSLKeyConstant.RELY_FIELD)){
                dataBuildRequestFieldRuleBO.setRelyField(kvArr[1].trim());
            }

            if(kvArr[0].trim().equals(FieldDSLKeyConstant.RELY_LIST_FIELD)){
                dataBuildRequestFieldRuleBO.setRelyListField(kvArr[1].trim());
            }


            if(kvArr[0].trim().equals(FieldDSLKeyConstant.RELY_SET_FIELD)){
                dataBuildRequestFieldRuleBO.setRelySetField(kvArr[1].trim());
            }

            if(kvArr[0].trim().equals(FieldDSLKeyConstant.RELY_COUNT)){
                dataBuildRequestFieldRuleBO.setRelyCount(Integer.valueOf(kvArr[1].trim()));
            }

            if(kvArr[0].trim().equals(FieldDSLKeyConstant.RELY_MAP_KEY_FIELD)){
                dataBuildRequestFieldRuleBO.setRelyMapKeyField(kvArr[1].trim());
            }

            if(kvArr[0].trim().equals(FieldDSLKeyConstant.RELY_MAP_VALUE_FIELD)){
                dataBuildRequestFieldRuleBO.setRelyMapValueField(kvArr[1].trim());
            }

            if(kvArr[0].trim().equals(FieldDSLKeyConstant.FUNC_VAR)){
                dataBuildRequestFieldRuleBO.setFuncVar(kvArr[1].trim());
            }
        }
        return dataBuildRequestFieldRuleBO;
    }

}
