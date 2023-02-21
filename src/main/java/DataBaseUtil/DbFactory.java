package DataBaseUtil;

/**
 * @author yuki
 * @version 1.0.0
 * @date 2023-02-20 23:19:45
 * @packageName DataBaseUtil
 * @className DbFactory
 * @describe TODO
 */
public class DbFactory {

    private static DbFactory instance = null;

    private DbFactory(){

    }

    public static DbFactory getInstance() {
        if(instance == null) instance = new DbFactory();
        return instance;
    }

    public BaseDb getDataBase(String dbType,String username,String password,String ip,String port,String dbName) throws Exception {
        BaseDb db = null;
        if(dbType.equals(MySqlDb.DB_TYPE)){
            db = new MySqlDb(username,password,ip,port,dbName);
        }else{
            throw new Exception("不存在该类数据库");
        }
        return db;
    }
}
