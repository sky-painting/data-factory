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
    public ResultDataDto<PageDTO<UserDTO>> getUserListPage(PageDTO pageDTO) {
        return null;
    }

    @Override
    public ResultDataDto<List<UserDTO>> getAllUserList() {
        return null;
    }


    private List<UserDTO> getUserList(){
        List<UserDTO> list = new ArrayList<>();


        return list;
    }



}
