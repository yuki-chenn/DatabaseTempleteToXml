package Model;

import DataBaseUtil.BaseDb;
import DataBaseUtil.DbFactory;
import DataBaseUtil.IDbOperation;
import DataBaseUtil.MySqlDb;
import XmlUtil.XmlManager;

import java.util.List;

/**
 * @author yuki
 * @version 1.0.0
 * @date 2023-02-20 22:51:48
 * @packageName Model
 * @className DbBean
 * @describe TODO
 */
public class DbBean {

    public static final String[] DBTYPE_ITEMS = {"","MySql"};

    private String[] tableList;

    private BaseDb dataBase ;

    public DbBean(){

    }

    public void setDataBase(BaseDb db){
        this.dataBase = db;
        if(dataBase != null && dataBase.isConnect()) setTableList();
    }

    private void setTableList(){
        List<String> list = dataBase.getAllTableName();
        tableList = new String[list.size() + 1];
        for (int i=0;i<list.size();++i){
            tableList[i+1] = list.get(i);
        }
        tableList[0] = "";
    }

    public String[] getTableList(){
        if(this.tableList == null) tableList = new String[]{""};
        return this.tableList;
    }

    public BaseDb getDataBase() {
        return dataBase;
    }


}
