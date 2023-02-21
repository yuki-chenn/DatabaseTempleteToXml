package Frame;

import Controller.DbConnectController;
import Controller.WriteXmlController;
import Model.DbBean;
import Model.UserDataBean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author yuki
 * @version 1.0.0
 * @date 2023-02-19 09:08:58
 * @packageName Frame
 * @className MainFrame
 * @describe TODO
 */
public class MainFrame extends JFrame {

    // label
    JLabel label_dbType;
    JLabel label_ip;
    JLabel label_port;
    JLabel label_dbName;
    JLabel label_username;
    JLabel label_password;

    JLabel label_tableName;
    JLabel label_path;


    //下拉框
    JComboBox<String> comboBox_dbtype;

    // 输入文本
    JTextField textField_ip;
    JTextField textField_port;
    JTextField textField_dbName;
    JTextField textField_username;
    JPasswordField psdField_password;

    JComboBox<String> comboBox_tableName;
    JTextField textField_path;

    //按钮
    JButton btn_connect;
    JButton btn_export;
    JButton btn_clear;


    // 提示信息
    JScrollPane scrollPane_sout;
    SystemOutTextArea textArea_sout;

    DbBean bean;


    public MainFrame(){
        bean = new DbBean();
        initGUI();
        loadCache();
        refresh();
    }

    private void initGUI(){

        this.setTitle("模板表工具");
        this.setSize(600,420);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);


        //数据库类型
        label_dbType = new JLabel("数据库类型");
        label_dbType.setBounds(20,20,100,20);

        comboBox_dbtype = new JComboBox<>(bean.DBTYPE_ITEMS);
        comboBox_dbtype.setBounds(120,20,100,20);

        //数据库IP
        label_ip = new JLabel("主机名或IP地址");
        label_ip.setBounds(20,50,100,20);
        textField_ip = new JTextField("localhost");
        textField_ip.setBounds(120,50,100,20);

        //数据库端口号
        label_port = new JLabel("端口");
        label_port.setBounds(20,80,100,20);
        textField_port = new JTextField();
        textField_port.setBounds(120,80,100,20);

        //数据库名称
        label_dbName = new JLabel("数据库名称");
        label_dbName.setBounds(20,110,100,20);
        textField_dbName = new JTextField();
        textField_dbName.setBounds(120,110,100,20);

        //用户名、密码
        label_username = new JLabel("用户名");
        label_username.setBounds(20,140,100,20);
        textField_username = new JTextField();
        textField_username.setBounds(120,140,100,20);
        label_password = new JLabel("密码");
        label_password.setBounds(20,170,100,20);
        psdField_password = new JPasswordField();
        psdField_password.setBounds(120,170,100,20);

        //连接按钮
        btn_connect = new JButton("连接");
        btn_connect.setBounds(70,200,100,20);

        //模板表名称
        label_tableName = new JLabel("模板表名称");
        label_tableName.setBounds(20,250,100,20);
        comboBox_tableName = new JComboBox<>();
        comboBox_tableName.setBounds(120,250,120,20);

        // 文件路径
        label_path = new JLabel("文件路径");
        label_path.setBounds(20,280,100,20);
        textField_path = new JTextField();
        textField_path.setBounds(20,305,220,20);

        // 导出按钮
        btn_export = new JButton("导出");
        btn_export.setBounds(70,335,100,20);

        // 信息显示
        textArea_sout = new SystemOutTextArea();
        textArea_sout.setBounds(270,20,280,320);
        textArea_sout.setEditable(false);
        scrollPane_sout = new JScrollPane(textArea_sout);
        scrollPane_sout.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_sout.setBounds(270,20,280,320);
        btn_clear = new JButton("清除");
        btn_clear.setBounds(470,345,80,20);

        addGUIComponent();
        addListener();

        this.setVisible(true);
    }

    private void addGUIComponent(){
        this.add(label_dbType);
        this.add(comboBox_dbtype);
        this.add(label_ip);
        this.add(textField_ip);
        this.add(label_port);
        this.add(textField_port);
        this.add(label_dbName);
        this.add(textField_dbName);
        this.add(label_username);
        this.add(textField_username);
        this.add(label_password);
        this.add(psdField_password);
        this.add(btn_connect);
        this.add(label_tableName);
        this.add(comboBox_tableName);
        this.add(label_path);
        this.add(textField_path);
        this.add(btn_export);
        this.add(scrollPane_sout);
        this.add(btn_clear);
    }

    private void addListener(){
        btn_connect.addActionListener(e -> {
            if(bean.getDataBase() != null && bean.getDataBase().isConnect()){
                bean.getDataBase().close();
            }else{
                String dbType = comboBox_dbtype.getSelectedItem().toString();
                String username = textField_username.getText().toString();
                String password = new String(psdField_password.getPassword());
                String ip = textField_ip.getText().toString();
                String port = textField_port.getText().toString();
                String dbName = textField_dbName.getText().toString();
                bean.setDataBase(DbConnectController.dbConnect(dbType,username,password,ip,port,dbName));
                if(bean.getDataBase().isConnect()){
                    UserDataBean cache = bean.getUserCache();
                    cache.userData.put("dbType",dbType);
                    cache.userData.put("username",username);
                    cache.userData.put("password",password);
                    cache.userData.put("ip",ip);
                    cache.userData.put("port",port);
                    cache.userData.put("dbName",dbName);
                    WriteXmlController.writeUserData(cache);
                }
            }
            refresh();
        });
        btn_clear.addActionListener(e -> {
            textArea_sout.clearText();
            refresh();
        });
    }

    private void loadCache(){
        UserDataBean cache = bean.getUserCache();
        comboBox_dbtype.setSelectedItem(cache.userData.get("dbType"));
        textField_ip.setText(cache.userData.get("ip"));
        textField_port.setText(cache.userData.get("port"));
        textField_username.setText(cache.userData.get("username"));
        psdField_password.setText(cache.userData.get("password"));
        textField_dbName.setText(cache.userData.get("dbName"));
        comboBox_tableName.setSelectedItem(cache.userData.get("tableName"));
    }

    public void refresh(){
        boolean isConnect = bean.getDataBase() != null && bean.getDataBase().isConnect();

        label_dbType.setEnabled(!isConnect);
        label_ip.setEnabled(!isConnect);
        label_port.setEnabled(!isConnect);
        label_dbName.setEnabled(!isConnect);
        label_username.setEnabled(!isConnect);
        label_password.setEnabled(!isConnect);

        label_tableName.setEnabled(isConnect);
        label_path.setEnabled(isConnect);


        //下拉框
        comboBox_dbtype.setEnabled(!isConnect);

        // 输入文本
        textField_ip.setEnabled(!isConnect);
        textField_port.setEnabled(!isConnect);
        textField_dbName.setEnabled(!isConnect);
        textField_username.setEnabled(!isConnect);
        psdField_password.setEnabled(!isConnect);

        comboBox_tableName.setEnabled(isConnect);
        textField_path.setEnabled(isConnect);
//
        //按钮
        btn_connect.setText(isConnect ? "断开" : "连接");
        btn_export.setEnabled(isConnect);



    }

}
