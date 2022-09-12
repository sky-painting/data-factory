package com.tianhua.datafactory;

import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.dto.UserDTO;

/**
 * Description
 * date: 2022/9/12
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public interface UserFacade {

    /**
     * 保存用户信息
     * @param userDTO
     * @return
     */
    ResultDataDto<Boolean> saveUser(UserDTO userDTO);

}
