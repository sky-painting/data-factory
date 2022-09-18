package com.tianhua.datafactory;

import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.client.context.FieldIndex;
import com.tianhua.datafactory.client.context.FileDataSourceContext;
import com.tianhua.datafactory.client.enums.FileTypeEnum;
import com.tianhua.datafactory.client.filedata.CommonParseService;
import com.tianhua.datafactory.demomodel.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description
 * date: 2022/9/4
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,classes = {Application.class})
@Slf4j
public class CommonParseServiceTest {

    @Resource(name = "txtParseServiceImpl")
    private CommonParseService txtParseServiceImpl;


    @Resource(name = "jSONParseServiceImpl")
    private CommonParseService jSONParseServiceImpl;

    @Resource(name = "excelParseServiceImpl")
    private CommonParseService excelParseServiceImpl;



    @Test
    public void testParseTxt(){
        FileDataSourceContext fileDataSourceContext = new FileDataSourceContext();

        String url = "E:\\myspace2\\myspace\\data-factory\\start-local\\src\\test\\resources\\demodata\\userInfo.txt";
        fileDataSourceContext.setFileUrl(url);
        fileDataSourceContext.setFileType(FileTypeEnum.TXT.getType());
        fileDataSourceContext.setSplitTag(",");
        fileDataSourceContext.setDataSourceCode("com.snail.user.basinfo");
        fileDataSourceContext.setDataSourceName("轻蜗牛租房产品用户基本信息");
        fileDataSourceContext.setLoadCount(10);
        fileDataSourceContext.setReadCount(8);


        List<FieldIndex> fieldIndexList = new ArrayList<>();

        fieldIndexList.add(new FieldIndex(1,"userName","String"));
        fieldIndexList.add(new FieldIndex(2,"age","Integer"));
        fieldIndexList.add(new FieldIndex(3,"city","String"));
        fieldIndexList.add(new FieldIndex(4,"company","String"));

        fileDataSourceContext.setFieldIndexBOList(fieldIndexList);

        try {
            List<Map<String,Object>> mapList = txtParseServiceImpl.parseData(fileDataSourceContext);
            mapList.stream().forEach(v-> log.info("map = {}", JSON.toJSONString(v)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }


    @Test
    public void testParseJSON(){
        FileDataSourceContext fileDataSourceContext = new FileDataSourceContext();

        String url = "E:\\myspace2\\myspace\\data-factory\\start-local\\src\\test\\resources\\demodata\\userInfojson.txt";
        fileDataSourceContext.setFileUrl(url);
        fileDataSourceContext.setFileType(FileTypeEnum.JSON.getType());
        fileDataSourceContext.setDataSourceCode("com.snail.user.basinfo");
        fileDataSourceContext.setDataSourceName("轻蜗牛租房产品用户基本信息");
        fileDataSourceContext.setLoadCount(10);
        fileDataSourceContext.setReadCount(8);


        List<FieldIndex> fieldIndexList = new ArrayList<>();

        fieldIndexList.add(new FieldIndex(1,"userName","String"));
        fieldIndexList.add(new FieldIndex(2,"age","Integer"));
        fieldIndexList.add(new FieldIndex(3,"city","String"));
        fieldIndexList.add(new FieldIndex(4,"company","String"));

        fileDataSourceContext.setFieldIndexBOList(fieldIndexList);

        try {
            List<Map<String,Object>> mapList = jSONParseServiceImpl.parseData(fileDataSourceContext);
            mapList.stream().forEach(v-> log.info("map = {}", JSON.toJSONString(v)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }



    @Test
    public void testParseExcel(){
        FileDataSourceContext fileDataSourceContext = new FileDataSourceContext();

        String url = "E:\\myspace2\\myspace\\data-factory\\start-local\\src\\test\\resources\\demodata\\userInfo.xlsx";
        fileDataSourceContext.setFileUrl(url);
        fileDataSourceContext.setFileType(FileTypeEnum.EXCEL.getType());
        fileDataSourceContext.setDataSourceCode("com.snail.user.basinfo");
        fileDataSourceContext.setDataSourceName("轻蜗牛租房产品用户基本信息");
        fileDataSourceContext.setLoadCount(10);
        fileDataSourceContext.setReadCount(8);


        List<FieldIndex> fieldIndexList = new ArrayList<>();

        fieldIndexList.add(new FieldIndex(1,"userName","String"));
        fieldIndexList.add(new FieldIndex(2,"age","Integer"));
        fieldIndexList.add(new FieldIndex(3,"city","String"));
        fieldIndexList.add(new FieldIndex(4,"company","String"));

        fileDataSourceContext.setFieldIndexBOList(fieldIndexList);

        try {
            List<Map<String,Object>> mapList = excelParseServiceImpl.parseData(fileDataSourceContext);
            mapList.stream().forEach(v-> log.info("map = {}", JSON.toJSONString(v)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }




    @Test
    public void generate(){
        for (int i =0;i< 11;i++){
            UserInfo userInfo = UserInfo.builder()
                    .userName("zhangsan"+i)
                    .age(i)
                    .city("北京市"+i)
                    .company("xxx公司"+i)
                    .id((long) i+1)
                    .build();
            log.info(JSON.toJSONString(userInfo));
        }
    }



}
