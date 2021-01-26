package com.coderman.tianhua.datafactory.api.test;

import com.alibaba.fastjson.JSON;
import com.coderman.tianhua.datafactory.api.Application;
import com.coderman.tianhua.datafactory.api.test.dto.DepartmentDTO;
import com.coderman.tianhua.datafactory.api.test.dto.StaffType;
import com.coderman.tianhua.datafactory.core.enums.DataSourceTypeEnum;
import com.coderman.tianhua.datafactory.core.enums.VisitStrategyEnums;
import com.coderman.tianhua.datafactory.core.vo.DataSourceVO;
import com.coderman.utils.kvpair.KVPair;
import com.coderman.utils.response.ResultDataDto;
import com.coderman.utils.response.ResultDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description: DataSourceControllerTest <br>
 * date: 2020/12/8 23:10 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,classes = {Application.class})
public class DataSourceControllerTest {
    protected Logger logger = LoggerFactory.getLogger(DataSourceControllerTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testRegistDataSource(){
        DataSourceVO dataSourceVO = new DataSourceVO();
        dataSourceVO.setCreateTime(new Date());
        dataSourceVO.setCreateUserId(1L);
        dataSourceVO.setUpdateUserId(1L);
        dataSourceVO.setUpdateTime(new Date());
        dataSourceVO.setSourceType(DataSourceTypeEnum.FROM_NACOS.getCode());
        dataSourceVO.setStatus(0);
        dataSourceVO.setToken("");
        dataSourceVO.setSourceName("数据源类型枚举");
        dataSourceVO.setUrl("");
        dataSourceVO.setVisitStrategy(VisitStrategyEnums.LOCAL_CACHE.getCode());
        dataSourceVO.setProviderSrc("nacos");

        dataSourceVO.setDataId("com.lightsnail.snail.room");
        dataSourceVO.setGroupId("room_source_type");

        List<KVPair<Integer,String>> kvPairList = new ArrayList<>();
        for(DataSourceTypeEnum dataSourceTypeEnum : DataSourceTypeEnum.values()){
            kvPairList.add(KVPair.build(dataSourceTypeEnum.getCode(),dataSourceTypeEnum.getDesc()));
        }
        dataSourceVO.setDataContentJson(JSON.toJSONString(kvPairList));
        ResultDto resultDto = restTemplate.postForEntity("/data/source/regist",dataSourceVO, ResultDto.class).getBody();

    }


    @Test
    public void testGetById(){
        ResultDataDto resultDto = restTemplate.getForEntity("/data/source/get?id=6", ResultDataDto.class).getBody();
        System.out.println(JSON.toJSONString(resultDto));
    }




    @Test
    public void testRegistDepartment(){
        DataSourceVO dataSourceVO = new DataSourceVO();
        dataSourceVO.setCreateTime(new Date());
        dataSourceVO.setCreateUserId(1L);
        dataSourceVO.setUpdateUserId(1L);
        dataSourceVO.setUpdateTime(new Date());
        dataSourceVO.setSourceType(DataSourceTypeEnum.FROM_CUSTOM.getCode());
        dataSourceVO.setStatus(0);
        dataSourceVO.setToken("");
        dataSourceVO.setSourceName("虚拟部门列表");
        dataSourceVO.setUrl("");
        dataSourceVO.setVisitStrategy(VisitStrategyEnums.LOCAL_CACHE.getCode());
        dataSourceVO.setProviderSrc("自定义注册");
        dataSourceVO.setSourceCode("com.lightsnail.infosys.department");
        dataSourceVO.setDataContentJson(JSON.toJSONString(getDepartmentDTO()));
        ResultDto resultDto = restTemplate.postForEntity("/data/source/regist",dataSourceVO, ResultDto.class).getBody();
        logger.info("resultDto = "+JSON.toJSONString(resultDto));
    }


    @Test
    public void testRegistStaffType(){
        DataSourceVO dataSourceVO = new DataSourceVO();
        dataSourceVO.setCreateTime(new Date());
        dataSourceVO.setCreateUserId(1L);
        dataSourceVO.setUpdateUserId(1L);
        dataSourceVO.setUpdateTime(new Date());
        dataSourceVO.setSourceType(DataSourceTypeEnum.FROM_NACOS.getCode());
        dataSourceVO.setStatus(0);
        dataSourceVO.setToken("");
        dataSourceVO.setSourceName("省份数据");
        dataSourceVO.setUrl("");
        dataSourceVO.setVisitStrategy(VisitStrategyEnums.DYNAMIC_ACCESS.getCode());
        dataSourceVO.setProviderSrc("自定义注册");
        dataSourceVO.setSourceCode("com.lightsnail.app.dict.common.province_group");
        dataSourceVO.setDataId("com.lightsnail.app.dict.common");
        dataSourceVO.setGroupId("province_group");

        dataSourceVO.setDataContentJson(JSON.toJSONString(getStaffTypeList()));
        ResultDto resultDto = restTemplate.postForEntity("/data/source/regist",dataSourceVO, ResultDto.class).getBody();
        logger.info("resultDto = "+JSON.toJSONString(resultDto));
    }


    @Test
    public void testRegistMetaAreaService(){
        DataSourceVO dataSourceVO = new DataSourceVO();
        dataSourceVO.setCreateTime(new Date());
        dataSourceVO.setCreateUserId(1L);
        dataSourceVO.setUpdateUserId(1L);
        dataSourceVO.setUpdateTime(new Date());
        dataSourceVO.setSourceType(DataSourceTypeEnum.FROM_SERVICE_API.getCode());
        dataSourceVO.setStatus(0);
        dataSourceVO.setToken("");
        dataSourceVO.setSourceName("省份服务数据");
        dataSourceVO.setUrl("http://lightsnail-meta-area-core/chinaProvince/getall");
        dataSourceVO.setVisitStrategy(VisitStrategyEnums.DYNAMIC_ACCESS.getCode());
        dataSourceVO.setProviderSrc("自定义注册");
        //服务名+数据名标示唯一性
        dataSourceVO.setSourceCode("lightsnail-meta-area-core.province");


        dataSourceVO.setDataContentJson(JSON.toJSONString(getStaffTypeList()));
        ResultDto resultDto = restTemplate.postForEntity("/data/source/regist",dataSourceVO, ResultDto.class).getBody();
        logger.info("resultDto = "+JSON.toJSONString(resultDto));
    }







    /**
     * 构建部门数据
     * @return
     */
    public List<DepartmentDTO> getDepartmentDTO(){
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartName("信息技术部");
        departmentDTO.setId(100L);
        departmentDTO.setManagerId(4005L);
        departmentDTO.setSuperId(50L);
        departmentDTOList.add(departmentDTO);

        DepartmentDTO departmentDTO2 = new DepartmentDTO();
        departmentDTO2.setDepartName("架构技术部");
        departmentDTO2.setId(101L);
        departmentDTO2.setManagerId(4000L);
        departmentDTO2.setSuperId(57L);
        departmentDTOList.add(departmentDTO2);

        DepartmentDTO departmentDTO3 = new DepartmentDTO();
        departmentDTO3.setDepartName("前端技术部");
        departmentDTO3.setId(103L);
        departmentDTO3.setManagerId(4000L);
        departmentDTO3.setSuperId(57L);
        departmentDTOList.add(departmentDTO3);

        DepartmentDTO departmentDTO4 = new DepartmentDTO();
        departmentDTO4.setDepartName("测试技术部");
        departmentDTO4.setId(104L);
        departmentDTO4.setManagerId(40060L);
        departmentDTO4.setSuperId(52L);
        departmentDTOList.add(departmentDTO4);

        DepartmentDTO departmentDTO5 = new DepartmentDTO();
        departmentDTO5.setDepartName("后端技术部");
        departmentDTO5.setId(105L);
        departmentDTO5.setManagerId(40061L);
        departmentDTO5.setSuperId(52L);

        DepartmentDTO departmentDTO6 = new DepartmentDTO();
        departmentDTO6.setDepartName("无线技术部");
        departmentDTO6.setId(106L);
        departmentDTO6.setManagerId(40061L);
        departmentDTO6.setSuperId(52L);

        departmentDTOList.add(departmentDTO4);

        return departmentDTOList;
    }

    /**
     * 构建员工类型数据
     * @return
     */
    public List<StaffType> getStaffTypeList(){
        List<StaffType> staffTypeList = new ArrayList<>();
        StaffType staffType = new StaffType();
        staffType.setCode(1);
        staffType.setName("正式员工");
        staffTypeList.add(staffType);

        StaffType staffType2 = new StaffType();
        staffType2.setCode(2);
        staffType2.setName("非正式员工-实习生");
        staffTypeList.add(staffType2);

        StaffType staffType3 = new StaffType();
        staffType3.setCode(3);
        staffType3.setName("非正式员工-外包");
        staffTypeList.add(staffType3);

        StaffType staffType4 = new StaffType();
        staffType4.setCode(4);
        staffType4.setName("非正式员工-顾问");
        staffTypeList.add(staffType4);
        return staffTypeList;

    }



}
