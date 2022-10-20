package com.tianhua.datafactory.vo;

import java.util.Date;

/**
 * Description:
 * date: 2022/4/24
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public class BaseVO {
    /**  创建时间 **/
    public Date dateCreate;
    /**  修改时间 **/
    public Date dateUpdate;
    /**  修改人 **/
    public Long updateUserId;
    /**  创建人 **/
    public Long createUserId;

    /**
     * 状态
     * @See com.tianhua.datafactory.domain.enums.ApiModelFieldStatusEnum
     */
    private Integer status;
    public String statusDesc;



    public void init(){
       this.createUserId = 1L;
       this.updateUserId = 1L;
       this.dateCreate = new Date();
       this.dateUpdate = new Date();
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
