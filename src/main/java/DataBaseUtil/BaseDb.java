package DataBaseUtil;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author yuki
 * @version 1.0.0
 * @date 2023-02-19 11:21:10
 * @packageName DataBaseUtil
 * @className BaseDb
 * @describe 基类
 */
public abstract class BaseDb implements IDbOperation{

    protected String driver;
    protected String url;
    protected String username;
    protected String password;
    protected String ip;
    protected String port;
    protected String dbName;

    protected Connection conn = null;
    protected PreparedStatement pstatement = null;
    protected ResultSet resultSet = null;

    public BaseDb(){

    }

    public BaseDb(String ip,String port,String username,String password,String dbName){
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.dbName = dbName;
    }

    /**
     * 是否已连接
     * @return 连接
     */
    public boolean isConnect(){
        boolean isConnect = false;
        try {
            isConnect = !conn.isClosed();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn != null && isConnect;
    }


    /**
     * 关闭数据库连接
     */
    @Override
    public void close(){
        try {
            if(resultSet != null) resultSet.close();
            if(pstatement != null) pstatement.close();
            if(conn != null) conn.close();
            System.out.println("数据库连接断开");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * 生成xml文件
     * @param paths 文件路径
     * @param fileName 文件名
     * @param datas 文件数据
     * @param columnList 文件列数据
     */
    @Override
    public void generateXml(String[] paths,String fileName,List<Map<String, String>> datas,List<String> columnList) {
        for(String path:paths){

            // 创建dom对象
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("root");

            // 添加节点
            for(Map<String,String> data:datas){
                Element element = root.addElement("data");
                for(String column:columnList){
                    Element subElement = element.addElement(column);
                    subElement.setText(data.get(column));
                }
            }

            // 格式化模板
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");

            // 生成xml文件
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                XMLWriter writer = new XMLWriter(out, format);
                writer.write(document);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("生成xml文件失败。文件名【" + fileName + "】");
            }

            // 利用文件输出流输出到文件
            try (FileOutputStream fos = new FileOutputStream(path + "/" + fileName + ".xml")) {
                fos.write(out.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("xml文件写入失败。文件名【" + fileName + "】");
            }

            System.out.println("文件导出成功，位置：" + path + "/" + fileName + ".xml");

        }
    }
}
