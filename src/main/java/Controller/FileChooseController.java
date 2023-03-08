package Controller;

import FileUtil.FileChooserManager;

import java.awt.*;

public class FileChooseController {

    public static String chooseFile(String path, Component parent){
        return FileChooserManager.getInstance().chooseFile(path,parent);
    }
}
