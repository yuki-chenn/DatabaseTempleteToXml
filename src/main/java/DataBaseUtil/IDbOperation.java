package DataBaseUtil;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @author yuki
 * @version 1.0.0
 * @date 2023-02-19 11:15:49
 * @packageName DataBaseUtil
 * @className IDataBase
 * @describe 数据库操作接口
 */
public interface IDbOperation {

    public Connection loadConnection();

    public List<String> getAllTableName();

    public List<Map<String,String>> getAllDataByTableName(String tableName);

    public List<String> getAllColumsByTableName(String tableName);

    public void close();

    public void generateXml(String[] paths,String fileName,List<Map<String,String>> datas,List<String> columnList);

}
