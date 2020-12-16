package com.coderman.tianhua.datafactory.core.defaultfactory;
import java.io.IOException;
import java.util.List;

/**
 * description: FileDataService <br>
 * date: 2020/12/15 23:50 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 * 数据文件解析服务
 */
public interface FileDataParseService {
    /**
     * 解析姓氏
     * @return
     */
    List<String> parseFirstName() throws IOException;

    /**
     * 解析名称
     * @return
     */
    List<String> parseLastName() throws IOException;

}
