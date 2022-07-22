package com.tianhua.datafactory.infrast.servicepiml;

import com.tianhua.datafactory.domain.ability.KVPairService;
import com.tianhua.datafactory.domain.support.kvpair.KVPairBO;
import com.tianhua.datafactory.infrast.dao.dataobject.KVPairDO;
import com.tianhua.datafactory.infrast.dao.mapper.KVMapper;
import com.tianhua.datafactory.infrast.dataconvert.KVPairModelConvert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:走数据库的配置服务
 * date: 2022/1/11
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Component(value = "kVDBPersistService")
public class KVDBPersistServiceImpl implements KVPairService {

    @Resource
    private KVMapper kvMapper;

    @Override
    public List<KVPairBO> getAll() {
        List<KVPairDO> kvPairDOList = kvMapper.selectAll();
        if(kvPairDOList == null || kvPairDOList.isEmpty()){
            return new ArrayList<>();
        }
        return KVPairModelConvert.INSTANCE.doList2boList(kvPairDOList);
    }

    @Override
    public KVPairBO get(KVPairBO kv) {
        return null;
    }

    @Override
    public List<KVPairBO> getList(KVPairBO kv) {
        List<KVPairDO> kvPairDOList = kvMapper.selectList(KVPairModelConvert.INSTANCE.bo2do(kv));
        return KVPairModelConvert.INSTANCE.doList2boList(kvPairDOList);
    }

    @Override
    public boolean insert(KVPairBO kv) {
        int count = kvMapper.insert(KVPairModelConvert.INSTANCE.bo2do(kv));
        if(count > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean batchInsert(List<KVPairBO> kvList) {
        List<KVPairDO> kvDOList = KVPairModelConvert.INSTANCE.boList2doList(kvList);
        kvMapper.insertBatch(kvDOList);
        return true;
    }

    @Override
    public List<KVPairBO> getByKey(List<KVPairBO> kvList) {
        return null;
    }

    @Override
    public boolean update(KVPairBO kv) {
        return false;
    }

    @Override
    public boolean delete(KVPairBO kv) {
        return false;
    }

    @Override
    public boolean contains(KVPairBO kv) {
        return false;
    }
}
