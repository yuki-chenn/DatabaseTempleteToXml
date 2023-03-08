package Frame;

import Controller.DbConnectController;
import Controller.FileChooseController;
import Controller.ReadExcelController;
import Controller.WriteXmlController;
import DataBaseUtil.BaseDb;
import Model.DbBean;
import Model.ExcelBean;
import Model.ReplaceItem;
import Model.UserDataBean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
    JCheckBox checkBox_exportAll;
    JButton btn_export;
    JButton btn_clear;

    // 分割线
    JLabel sepLine;


    // 读取excel
    JLabel label_excelFilePath;
    JTextField textField_excelFilePath;
    JButton btn_chooseExcelFilePath;
    JButton btn_readFile;

    // 一键替换
    JLabel label_arrow;
    JComboBox<String> comboBox_tarSheetName;
    JComboBox<String> comboBox_tarColumnName;
    JComboBox<String> comboBox_foreignSheetName;
    JComboBox<String> comboBox_foreignColumnName;
    JComboBox<String> comboBox_replaceItem;
    JButton btn_addReplace;
    JButton btn_delReplace;
    JButton btn_modify;
    JCheckBox checkBox_overwrite;

    // 提示信息
    JScrollPane scrollPane_sout;
    SystemOutTextArea textArea_sout;

    DbBean bean;

    ExcelBean excelBean;

    UserDataBean userCache;


    public MainFrame(){
        bean = new DbBean();
        initGUI();
        loadCache();
        refreshDbPanel();
        refreshExcelPanel();
    }

    private void initGUI(){

        this.setTitle("模板表工具");
        this.setSize(650,620);
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
        label_tableName.setBounds(20,230,100,20);
        comboBox_tableName = new JComboBox<>();
        comboBox_tableName.setBounds(20,255,240,20);

        // 文件路径
        label_path = new JLabel("文件路径");
        label_path.setBounds(20,280,100,20);
        textField_path = new JTextField();
        textField_path.setBounds(20,305,240,20);

        // 导出按钮
        btn_export = new JButton("导出");
        btn_export.setBounds(20,340,100,20);
        checkBox_exportAll = new JCheckBox("全选所有表");
        checkBox_exportAll.setBounds(140,340,100,20);

        //分割线
        sepLine = new JLabel("");
        sepLine.setBounds(10, 380, 315, 1);
        sepLine.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // excel文件路径
        label_excelFilePath = new JLabel("excel文件路径");
        label_excelFilePath.setBounds(20,390,100,20);
        btn_chooseExcelFilePath = new JButton("选择");
        btn_chooseExcelFilePath.setBounds(130,390,60,20);
        btn_readFile = new JButton("读取");
        btn_readFile.setBounds(200,390,60,20);
        textField_excelFilePath = new JTextField();
        textField_excelFilePath.setBounds(20,420,305,20);

        // 一键替换
        comboBox_tarSheetName = new JComboBox<String>();
        comboBox_tarSheetName.setBounds(20,450,100,20);
        comboBox_tarColumnName = new JComboBox<String>();
        comboBox_tarColumnName.setBounds(20,480,100,20);
        comboBox_foreignSheetName = new JComboBox<String>();
        comboBox_foreignSheetName.setBounds(160,450,100,20);
        comboBox_foreignColumnName = new JComboBox<String>();
        comboBox_foreignColumnName.setBounds(160,480,100,20);
        label_arrow = new JLabel("—>");
        label_arrow.setBounds(130,465,50,20);
        btn_addReplace = new JButton("增加");
        btn_addReplace.setBounds(265,465,60,20);
        comboBox_replaceItem = new JComboBox<String>();
        comboBox_replaceItem.setBounds(20,510,240,20);
        btn_delReplace = new JButton("删除");
        btn_delReplace.setBounds(265,510,60,20);
        btn_modify = new JButton("修改");
        btn_modify.setBounds(20,540,100,20);
        checkBox_overwrite = new JCheckBox("覆盖源文件");
        checkBox_overwrite.setBounds(130,540,100,20);

        // 信息显示
        textArea_sout = new SystemOutTextArea();
        textArea_sout.setBounds(330,20,300,520);
        textArea_sout.setEditable(false);
        scrollPane_sout = new JScrollPane(textArea_sout);
        scrollPane_sout.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_sout.setBounds(330,20,300,520);
        btn_clear = new JButton("清除");
        btn_clear.setBounds(550,550,80,20);

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
        this.add(checkBox_exportAll);
        this.add(scrollPane_sout);
        this.add(btn_clear);
        this.add(sepLine);
        this.add(label_excelFilePath);
        this.add(btn_chooseExcelFilePath);
        this.add(btn_readFile);
        this.add(textField_excelFilePath);
        this.add(comboBox_tarSheetName);
        this.add(comboBox_tarColumnName);
        this.add(comboBox_foreignSheetName);
        this.add(comboBox_foreignColumnName);
        this.add(label_arrow);
        this.add(comboBox_replaceItem);
        this.add(btn_addReplace);
        this.add(btn_delReplace);
        this.add(btn_modify);
        this.add(checkBox_overwrite);
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
                if(bean.getDataBase() != null && bean.getDataBase().isConnect()){
                    userCache.userData.put("dbType",dbType);
                    userCache.userData.put("username",username);
                    userCache.userData.put("password",password);
                    userCache.userData.put("ip",ip);
                    userCache.userData.put("port",port);
                    userCache.userData.put("dbName",dbName);
                    WriteXmlController.writeUserData(userCache);
                }
            }
            refreshDbPanel();
        });
        btn_export.addActionListener(e -> {
            String path = textField_path.getText().toString();
            String tableName = comboBox_tableName.getSelectedItem().toString();
            BaseDb db = bean.getDataBase();

            boolean exportAll = checkBox_exportAll.isSelected();
            if(exportAll){
                System.out.println("导出所有表");
                for(String table:bean.getTableList()){
                    if(table == null || table.length() == 0) continue;
                    db.generateXml(new String[]{path},table,db.getAllDataByTableName(table),db.getAllColumsByTableName(table));
                }
            }else{
                if(tableName == null || tableName.length() == 0){
                    System.out.println("【ERROR】 未选择模板表");
                    return ;
                }
                db.generateXml(new String[]{path},tableName,db.getAllDataByTableName(tableName),db.getAllColumsByTableName(tableName));
            }

            userCache.userData.put("tableName",tableName);
            userCache.userData.put("path",path);
            WriteXmlController.writeUserData(userCache);
        });
        btn_clear.addActionListener(e -> {
            textArea_sout.clearText();
            refreshDbPanel();
        });
        btn_chooseExcelFilePath.addActionListener(e -> {
            textField_excelFilePath.setText(
                    FileChooseController.chooseFile(userCache.userData.get("excelPath"),this)
            );
        });
        btn_readFile.addActionListener(e -> {
            String filePath = textField_excelFilePath.getText().toString();
            excelBean.workbook = ReadExcelController.readExcel(filePath);
            if(excelBean.workbook != null){
                userCache.userData.put("excelPath",filePath);
                WriteXmlController.writeUserData(userCache);
            }
            refreshExcelPanel();
        });
        comboBox_tarSheetName.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                comboBox_tarColumnName.removeAllItems();
                for(String str:excelBean.getSheetColumns(comboBox_tarSheetName.getSelectedIndex())) comboBox_tarColumnName.addItem(str);
            }
        });
        comboBox_foreignSheetName.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                comboBox_foreignColumnName.removeAllItems();
                for(String str:excelBean.getSheetColumns(comboBox_foreignSheetName.getSelectedIndex())) comboBox_foreignColumnName.addItem(str);
            }
        });
        btn_addReplace.addActionListener(e -> {
            excelBean.addReplaceItem(new ReplaceItem(
                    comboBox_tarSheetName.getSelectedIndex(),
                    comboBox_tarColumnName.getSelectedIndex(),
                    comboBox_foreignSheetName.getSelectedIndex(),
                    comboBox_foreignColumnName.getSelectedIndex()
            ));
            comboBox_replaceItem.removeAllItems();
            for(String str:excelBean.getReplaceList()) comboBox_replaceItem.addItem(str);

        });
        btn_delReplace.addActionListener(e -> {
            excelBean.removeRepalceItem(comboBox_replaceItem.getSelectedIndex());
            comboBox_replaceItem.removeAllItems();
            for(String str:excelBean.getReplaceList()) comboBox_replaceItem.addItem(str);
        });
        btn_modify.addActionListener(e -> {
            for(ReplaceItem item:excelBean.replaceList){
                if(!ReadExcelController.replace(excelBean.workbook,item)){
                    System.out.println("外键" + item.toString() + "替换失败");
                    return;
                }
            }
            String filePath = textField_excelFilePath.getText().toString();
            String newFilePath = filePath.substring(0,filePath.lastIndexOf('.')) + "_replace" + filePath.substring(filePath.lastIndexOf('.'));
            ReadExcelController.writeExcel(excelBean.workbook,checkBox_overwrite.isSelected() ? filePath : newFilePath);
            excelBean.removeAllRepalceItem();
            comboBox_replaceItem.removeAllItems();
            for(String str:excelBean.getReplaceList()) comboBox_replaceItem.addItem(str);
        });
    }

    private void loadCache(){
        comboBox_dbtype.setSelectedItem(userCache.userData.get("dbType"));
        textField_ip.setText(userCache.userData.get("ip"));
        textField_port.setText(userCache.userData.get("port"));
        textField_username.setText(userCache.userData.get("username"));
        psdField_password.setText(userCache.userData.get("password"));
        textField_dbName.setText(userCache.userData.get("dbName"));
        comboBox_tableName.setSelectedItem(userCache.userData.get("tableName"));
        textField_path.setText(userCache.userData.get("path"));
        textField_excelFilePath.setText(userCache.userData.get("excelPath"));
    }

    public void refreshDbPanel(){
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
        comboBox_tableName.removeAllItems();
        for(String str:bean.getTableList()) comboBox_tableName.addItem(str);
        comboBox_tableName.setSelectedItem(userCache.userData.get("tableName"));
        textField_path.setEnabled(isConnect);
//
        //按钮
        btn_connect.setText(isConnect ? "断开" : "连接");
        btn_export.setEnabled(isConnect);
        checkBox_exportAll.setEnabled(isConnect);



    }


    public void refreshExcelPanel(){
        boolean isRead = excelBean.workbook != null;

        label_excelFilePath.setEnabled(!isRead);
        btn_chooseExcelFilePath.setEnabled(!isRead);
        btn_readFile.setEnabled(!isRead);
        textField_excelFilePath.setEnabled(!isRead);

        comboBox_tarSheetName.setEnabled(isRead);
        comboBox_tarSheetName.removeAllItems();
        for(String str:excelBean.getSheetList()) comboBox_tarSheetName.addItem(str);
        comboBox_tarColumnName.setEnabled(isRead);
        comboBox_foreignSheetName.setEnabled(isRead);
        comboBox_foreignSheetName.removeAllItems();
        for(String str:excelBean.getSheetList()) comboBox_foreignSheetName.addItem(str);
        comboBox_foreignColumnName.setEnabled(isRead);
        label_arrow.setEnabled(isRead);
        btn_addReplace.setEnabled(isRead);
        comboBox_replaceItem.setEnabled(isRead);
        btn_delReplace.setEnabled(isRead);
        btn_modify.setEnabled(isRead);
    }


}
