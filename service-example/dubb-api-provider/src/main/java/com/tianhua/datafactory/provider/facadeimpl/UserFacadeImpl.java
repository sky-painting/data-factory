package com.tianhua.datafactory.provider.facadeimpl;

import com.alibaba.fastjson.JSON;
import com.coderman.utils.page.PageDTO;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.UserFacade;
import com.tianhua.datafactory.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description
 * date: 2022/9/14
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@DubboService
@Slf4j
public class UserFacadeImpl implements UserFacade {
    @Override
    public ResultDataDto<Boolean> saveUser(UserDTO userDTO) {
        log.info("userDTO = {}", JSON.toJSONString(userDTO));
        return ResultDataDto.success(true);
    }

    @Override
    public ResultDataDto<PageDTO> getUserListPage(PageDTO pageDTO) {
        log.info("pageDTO = {}", JSON.toJSONString(pageDTO));
        pageDTO.setTotal(1000);
        pageDTO.setItems(getUserList());
        return ResultDataDto.success(pageDTO);
    }

    @Override
    public ResultDataDto<List<UserDTO>> getAllUserList() {
        return ResultDataDto.success(getUserList());
    }


    /**
     * demo 数据
     * @return
     */
    private List<UserDTO> getUserList(){
        List<UserDTO> list = new ArrayList<>();
        for (int i = 0;i < 10 ;i++){
            UserDTO userDTO = new UserDTO();
            userDTO.setAge(10+i);
            userDTO.setChineseName("神帅"+i);
            userDTO.setUserName("神帅"+i);
            userDTO.setChineseName("shenshuai"+i);
            userDTO.setBankCount("2324099749204"+i);
            userDTO.setBankMoney(3000.23+i);
            userDTO.setCardNumber("3483985395898989"+i);
            userDTO.setDepartId(0L + i);
            userDTO.setCompanyName("xxx科技有限公司"+i);
            userDTO.setEmail("shenshai"+i+"@email.com");
            userDTO.setBankName("xxx银行"+i);
            userDTO.setHight(170.00d + i);
            userDTO.setId(1L + i);
            userDTO.setPassword("sdf23kdsjdn132dasdf"+i);
            userDTO.setUpdateTime(new Date());
            userDTO.setCreateTime(new Date());
            userDTO.setJoinDate(new Date());
            userDTO.setSchoolName("xxx大学"+i);
            userDTO.setStatus((short)i);


            list.add(userDTO);
        }

        return list;
    }



}
