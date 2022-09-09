package com.tianhua.datafactory.vo.amis;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * date: 2022/9/8
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class ContainerVO {

    private String type;

    private List<ComponentVO> body = new ArrayList<>();


    public void addComponent(ComponentVO componentVO){
        body.add(componentVO);
    }
}
