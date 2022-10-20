package com.tianhua.datafactory;

import com.coderman.utils.page.PageDTO;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.dto.UserDTO;

import java.util.List;

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
     * 进行接口数据mock模拟
     * @param userDTO
     * @return
     */
    ResultDataDto<Boolean> saveUser(UserDTO userDTO);

    /**
     * 分页获取用户信息
     * @return
     */
    ResultDataDto<PageDTO> getUserListPage(PageDTO pageDTO);

    /**
     * 获取全量用户信息
     *
     * 将此接口注册为数据源，
     * 在其他数据模型构建的时候从此数据源中获取用户数据信息
     * @return
     */
    ResultDataDto<List<UserDTO>> getAllUserList();

}
