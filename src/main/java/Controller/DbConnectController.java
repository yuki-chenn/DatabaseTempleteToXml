package Controller;

import DataBaseUtil.BaseDb;
import DataBaseUtil.DbFactory;

/**
 * @author yuki
 * @version 1.0.0
 * @date 2023-02-20 23:38:27
 * @packageName Controller
 * @className DbConnectController
 * @describe TODO
 */
public class DbConnectController {

    public static BaseDb dbConnect(String dbType,String username,String password,String ip,String port,String dbName){
        BaseDb dataBase = null;
        try {
            dataBase = DbFactory.getInstance().getDataBase(dbType,username,password,ip,port,dbName);
            dataBase.loadConnection();
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("【ERROR】 数据库连接失败");
        }
        return dataBase;
    }

}
