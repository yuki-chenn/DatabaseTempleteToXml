package DataBaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuki
 * @version 1.0.0
 * @date 2023-02-19 11:27:43
 * @packageName DataBaseUtil
 * @className MySqlDb
 * @describe TODO
 */
public class MySqlDb extends BaseDb implements IDbOperation{

    public final static String DB_TYPE = "MySql";

    private final static String MYSQL_URL = "jdbc:mysql://%s:%s/%s?userUnicode=true&characterEnconding=UTF-8&serverTimezone=UTC";
    private final static String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String MYSQL_DRIVER_PRE = "com.mysql.jdbc.Driver";

    private final static String SQL_SELECT_ALL_TABLES = "select table_name from information_schema.tables where table_schema='%s'";
    private final static String SQL_SELECT_ALL_FROM_TABLE = "select * from %s";

    public MySqlDb(){
        super();
    }

    public MySqlDb(String username,String password,String ip,String port,String dbName){
        super(ip,port,username,password,dbName);
//        this.driver = MYSQL_DRIVER;
        this.driver = MYSQL_DRIVER_PRE;
        this.url = String.format(MYSQL_URL,ip,port,dbName);
    }


    /**
     * 加载连接
     * @return 连接对象
     */
    @Override
    public Connection loadConnection() {
        System.out.println(
                String.format("正在连接至数据库服务器[%s] %s:%s\nusername:%s\n数据库名称：%s",DB_TYPE,ip,port,username,dbName)
        );

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(conn == null) {
            //通过访问数据库的URL来获取数据库连接对象
            try {
                conn = DriverManager.getConnection(url, username, password);//获得连接

            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.err.println("数据库连接失败...");
            }
        }

        if(conn != null) System.out.println("数据库连接成功！");

        return conn;
    }

    /**
     * 获取数据库中的所有表名
     * @return
     */
    @Override
    public List<String> getAllTableName() {
        List<String> tableList = new ArrayList();

        try {
            pstatement = conn.prepareStatement(String.format(SQL_SELECT_ALL_TABLES,dbName));
            resultSet = pstatement.executeQuery();
            while(resultSet.next()){
                tableList.add(resultSet.getString("TABLE_NAME"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return tableList;
    }


    /**
     * 获取表的数据
     * @param tableName 数据库表名
     * @return 数据
     */
    @Override
    public List<Map<String,String>> getAllDataByTableName(String tableName) {
        List<Map<String,String>> dataList = new ArrayList<Map<String, String>>();

        try {
            pstatement = conn.prepareStatement(String.format(SQL_SELECT_ALL_FROM_TABLE,tableName));
            resultSet = pstatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            while(resultSet.next()){
                Map<String,String> data = new HashMap<String, String>();
                for(int i=1;i<=metaData.getColumnCount();++i){
                    String str = resultSet.getString(i);
                    data.put(metaData.getColumnName(i),str == null ? "" : str);
                }
                dataList.add(data);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return dataList;
    }

    @Override
    public List<String> getAllColumsByTableName(String tableName) {
        List<String> columnList = new ArrayList<String>();

        try {
            pstatement = conn.prepareStatement(String.format(SQL_SELECT_ALL_FROM_TABLE,tableName));
            resultSet = pstatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for(int i=1;i<=metaData.getColumnCount();++i){
                columnList.add(metaData.getColumnName(i));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return columnList;
    }


}
