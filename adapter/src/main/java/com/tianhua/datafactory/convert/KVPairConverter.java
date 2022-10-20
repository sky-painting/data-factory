package com.tianhua.datafactory.convert;

import com.tianhua.datafactory.domain.support.kvpair.KVPairBO;
import com.tianhua.datafactory.vo.datasource.KVPairVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* @Description:
* @Author:
* @CreateTime:2022-05-27 17:45:38
* @version v1.0
*/
@Mapper
public interface KVPairConverter {
	KVPairConverter INSTANCE = Mappers.getMapper(KVPairConverter.class);

	/**
	 * vo列表转换成bo列表
	 * @Description:
	 * @return List<KVPairBO>
	 */
	 List<KVPairBO> VOs2BOs(List<KVPairVO> vOList);

	/**
	 * vo转换成bo
	 * @Description:
	 * @return ParamModelBO
	 */
	KVPairBO vo2bo(KVPairVO KVPairVO);

	/**
	 * bo转换成vo
	 * @Description:
	 * @return KVPairBO
	 */
	KVPairVO bo2VO(KVPairBO KVPairBO);

	/**
	 * bo列表转换成vo列表
	 * @Description:
	 * @return List<KVPairVO>
	 */
	 List<KVPairVO> BOs2VOs(List<KVPairBO> KVPairBOList);
}