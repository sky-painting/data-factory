package com.tianhua.datafactory.infrast.repositoryimpl;

import java.util.List;

import com.tianhua.datafactory.domain.bo.PageBean;
import com.tianhua.datafactory.domain.repository.ProjectQueryRepository;
import com.tianhua.datafactory.domain.bo.PageBO;
import com.tianhua.datafactory.domain.bo.project.ApiBO;
import com.tianhua.datafactory.domain.bo.project.ProjectBO;

import com.tianhua.datafactory.infrast.dao.dataobject.ApiModelDO;
import com.tianhua.datafactory.infrast.dao.dataobject.ProjectConfigDO;
import com.tianhua.datafactory.infrast.dao.mapper.ApiModelMapper;
import com.tianhua.datafactory.infrast.dao.mapper.ProjectConfigMapper;
import com.tianhua.datafactory.infrast.dataconvert.ApiConvert;
import com.tianhua.datafactory.infrast.dataconvert.ProjectConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Description:接口实现类
 * @Author：
 * @CreateTime：2022-05-27 17:45:38
 * @version v1.0
 */
@Service
public class ProjectQueryRepositoryImpl  implements ProjectQueryRepository{

	private  Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectConfigMapper projectConfigMapper;

    @Autowired
    private ApiModelMapper apiModelMapper;


    @Override
	public List<ApiBO> getApiListByCode(String projectCode){
        List<ApiModelDO> apiModelDOList = apiModelMapper.getByProjectCode(projectCode);
        List<ApiBO> apiBOList = ApiConvert.INSTANCE.doList2boList(apiModelDOList);
        apiBOList.stream().forEach(apiBO -> {
            apiBO.buildReturnParamModel();
            apiBO.buildRequestParam();
        });
        return apiBOList;
    }

    @Override
	public List<ApiBO> searchApi(String projectCode){
        return null;
    }

    @Override
	public PageBean queryApiPage(PageBean pageBean ){
        List<ApiModelDO> apiModelDOList = apiModelMapper.getPageList(pageBean);
        List<ApiBO> apiBOList = ApiConvert.INSTANCE.doList2boList(apiModelDOList);
        apiBOList.stream().forEach(apiBO -> {
            apiBO.buildReturnParamModel();
            apiBO.buildRequestParam();
        });
        pageBean.setRows(apiBOList);
        pageBean.setCount(apiModelMapper.getPageCount(pageBean));
        return pageBean;
    }

    @Override
	public ApiBO getBySign(String methodSign){
        ApiModelDO apiModelDO = apiModelMapper.getByApiSign(methodSign);
        ApiBO apiBO = ApiConvert.INSTANCE.do2bo(apiModelDO);
        if(apiBO == null){
            return null;
        }
        apiBO.buildRequestParam();
        apiBO.buildReturnParamModel();
        return apiBO;
    }

    @Override
	public ProjectBO getProjectByCode(String projectCode){
       return ProjectConvert.INSTANCE.do2bo(projectConfigMapper.getByCode(projectCode));
    }

    @Override
	public List<ProjectBO> searchProject(String content){
        List<ProjectConfigDO> projectConfigDOList = projectConfigMapper.search(content);
        return ProjectConvert.INSTANCE.doList2boList(projectConfigDOList);
    }

    @Override
	public PageBean queryProjectPage(PageBean pageBean ){
        List<ProjectConfigDO> projectConfigDOList = projectConfigMapper.getPageList(pageBean);
        pageBean.setRows(ProjectConvert.INSTANCE.doList2boList(projectConfigDOList));
        pageBean.setCount(projectConfigMapper.getPageCount(pageBean));
        return pageBean;
    }

    @Override
    public ApiBO getApiById(Long id) {
        ApiModelDO apiModelDO = apiModelMapper.getById(id);
        ApiBO apiBO = ApiConvert.INSTANCE.do2bo(apiModelDO);
        apiBO.buildReturnParamModel();
        apiBO.buildRequestParam();
        return apiBO;
    }

}