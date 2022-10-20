package com.tianhua.datafactory.client.factory;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Description
 * 接口返回值包装工厂配置类
 * 可能需要根据不同公司进行二次改造
 * date: 2022/9/4
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class ReturnWrapClassFactory {

    /**
     * 构建成功的返回包装类
     * @return
     */
    public Map<String, Object> buildSuccessResultDTO(Object data){
        Map<String, Object> resultDataMap = new HashMap<>();
        resultDataMap.put("code", "200");
        resultDataMap.put("msg", "成功");
        resultDataMap.put("data", data);

        return resultDataMap;
    }

    /**
     * 构建失败的返回包装类
     * @return
     */
    public Map<String, Object> buildFairResultDTO(){
        Map<String, Object> resultDataMap = new HashMap<>();
        resultDataMap.put("code", "500");
        resultDataMap.put("msg", "失败");
        resultDataMap.put("data", null);
        return resultDataMap;
    }

    /**
     * 构建通用resultdto返回对象包装上下文
     * @return
     */
    public Map<String, Object> buildSuccessResultPage(Object data){
        Map<String, Object> pageResultMap = new HashMap<>();
        Random random = new Random();
        pageResultMap.put("currentPageNum",random.nextInt(10)+1);
        pageResultMap.put("nextPageNum",Integer.valueOf(pageResultMap.get("currentPageNum").toString()) + 1);
        pageResultMap.put("prePageNum",Integer.valueOf(pageResultMap.get("currentPageNum").toString()) - 1);
        pageResultMap.put("pageSize", 10);
        pageResultMap.put("startRow", Integer.valueOf(pageResultMap.get("currentPageNum").toString()) *  Integer.valueOf(pageResultMap.get("pageSize").toString()));
        pageResultMap.put("endRow", Integer.valueOf(pageResultMap.get("startRow").toString()) + Integer.valueOf(pageResultMap.get("pageSize").toString()) );

        pageResultMap.put("totalRows", Integer.valueOf(pageResultMap.get("currentPageNum").toString())*(10+2));
        pageResultMap.put("totalPages", Integer.valueOf(pageResultMap.get("currentPageNum").toString()) + 2);
        pageResultMap.put("Items", data);

        return buildSuccessResultDTO(pageResultMap);
    }
    
    
}
