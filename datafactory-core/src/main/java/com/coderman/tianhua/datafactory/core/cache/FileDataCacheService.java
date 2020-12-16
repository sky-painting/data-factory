package com.coderman.tianhua.datafactory.core.cache;

import java.io.IOException;

/**
 * description: FileDataCacheService <br>
 * date: 2020/12/16 0:04 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
public interface FileDataCacheService {
    /**
     * 获取缓存的文件内容数据
     * @param fileName
     * @return
     */
    String getCacheSource(String fileName) throws IOException;

}
