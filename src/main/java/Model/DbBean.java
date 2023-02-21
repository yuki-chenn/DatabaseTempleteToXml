package Model;

import DataBaseUtil.BaseDb;
import DataBaseUtil.DbFactory;
import DataBaseUtil.IDbOperation;
import DataBaseUtil.MySqlDb;

/**
 * @author yuki
 * @version 1.0.0
 * @date 2023-02-20 22:51:48
 * @packageName Model
 * @className DbBean
 * @describe TODO
 */
public class DbBean {

    public static final String[] DBTYPE_ITEMS = {"MySql"};

    private BaseDb dataBase ;

    public DbBean(){

    }

    public void setDataBase(BaseDb db){
        this.dataBase = db;
    }

    public BaseDb getDataBase() {
        return dataBase;
    }

}
