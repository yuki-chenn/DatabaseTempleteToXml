package FileUtil;

import Controller.WriteXmlController;
import Model.UserDataBean;
import XmlUtil.XmlManager;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class FileChooserManager {

    private static FileChooserManager instance;

    JFileChooser fileChooser;

    UserDataBean userCache;

    private FileChooserManager(){

    }

    public static FileChooserManager getInstance(){
        if(instance == null) instance = new FileChooserManager();
        return instance;
    }

    public String chooseFile(String path, Component parent){
        if(path == null || path.length() == 0) path = "./";

        fileChooser = new JFileChooser(path);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("excel","xls","xlsx");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(parent);
        if(result == JFileChooser.APPROVE_OPTION){
            // 记录本次选择的路径
            String absolutePath = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println("选择文件：" + absolutePath);
            userCache.userData.put("excelPath",absolutePath);
            WriteXmlController.writeUserData(userCache);
            return absolutePath;
        }

        return "";
    }

}
