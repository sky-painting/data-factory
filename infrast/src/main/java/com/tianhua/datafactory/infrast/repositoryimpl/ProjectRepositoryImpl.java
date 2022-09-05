package com.tianhua.datafactory.infrast.repositoryimpl;

import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.domain.bo.project.ApiBO;
import com.tianhua.datafactory.domain.repository.ModelRepository;
import com.tianhua.datafactory.domain.repository.ProjectRepository;
import com.tianhua.datafactory.domain.bo.project.ProjectBO;

import com.tianhua.datafactory.infrast.dao.dataobject.ApiModelDO;
import com.tianhua.datafactory.infrast.dao.dataobject.ProjectConfigDO;
import com.tianhua.datafactory.infrast.dao.mapper.ApiModelMapper;
import com.tianhua.datafactory.infrast.dao.mapper.ProjectConfigMapper;
import com.tianhua.datafactory.infrast.dataconvert.ApiConvert;
import com.tianhua.datafactory.infrast.dataconvert.ProjectConvert;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * @Description:接口实现类
 * @Author：
 * @CreateTime：2022-05-27 17:45:38
 * @version v1.0
 */
@Service
public class ProjectRepositoryImpl  implements ProjectRepository{

	private  Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectConfigMapper projectConfigMapper;

    @Autowired
    private ApiModelMapper apiModelMapper;

    @Autowired
    private ModelRepository modelRepository;


    @Override
	public boolean saveProject(ProjectBO projectBO){
        projectBO.init();
        if(StringUtils.isNotEmpty(projectBO.getProjectCode())){
            ProjectConfigDO projectConfigDO = ProjectConvert.INSTANCE.bo2do(projectBO);
            projectConfigMapper.insert(projectConfigDO);
        }

        List<ApiBO> apiBOList = projectBO.getApiList();
        if(CollectionUtils.isNotEmpty(apiBOList)){
            for (ApiBO apiBO : apiBOList){
                apiBO.using();
                apiBO.buildApiSign();
                ApiModelDO apiModelDO = ApiConvert.INSTANCE.bo2do(apiBO);
                apiModelDO.setRequestParam(JSON.toJSONString(apiBO.getParamList()));
                apiModelDO.setReturnParam(JSON.toJSONString(apiBO.getReturnParamModel()));
                apiModelMapper.insert(apiModelDO);
            }
        }
        return true;
    }

    @Override
	public boolean updateProject(ProjectBO projectBO){
        if(StringUtils.isNotEmpty(projectBO.getProjectCode())){
            ProjectConfigDO projectConfigDO = ProjectConvert.INSTANCE.bo2do(projectBO);
            projectConfigMapper.update(projectConfigDO);
        }

        List<ApiBO> apiBOList = projectBO.getApiList();
        if(CollectionUtils.isNotEmpty(apiBOList)){
            for (ApiBO apiBO : apiBOList){
                if(apiBO.getId() == null) {
                    apiModelMapper.insert(ApiConvert.INSTANCE.bo2do(apiBO));
                }else {
                    apiModelMapper.update(ApiConvert.INSTANCE.bo2do(apiBO));
                }
            }
        }

        return true;
    }

    @Override
	public ProjectBO getByCode(String projectCode){
        ProjectBO projectBO = ProjectConvert.INSTANCE.do2bo(projectConfigMapper.getByCode(projectCode));
        if(projectBO == null){
            //todo throw exception
        }

        List<ApiModelDO> apiModelDOList = apiModelMapper.getByProjectCode(projectCode);
        projectBO.setApiList(ApiConvert.INSTANCE.doList2boList(apiModelDOList));

        return projectBO;
    }

}