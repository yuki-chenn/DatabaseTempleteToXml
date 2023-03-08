package FileUtil;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelFileManager {

    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    private static ExcelFileManager instance = null;

    private ExcelFileManager(){

    }

    public static ExcelFileManager getInstance(){
        if(instance == null) instance = new ExcelFileManager();
        return instance;
    }

    public Workbook readFile(String path) throws IOException {
        String fileType = path.substring(path.lastIndexOf('.') + 1);
        FileInputStream inputStream = new FileInputStream(path);
        Workbook workbook = null;
        if(fileType.equals(XLS)){
            workbook = new HSSFWorkbook(inputStream);
        }else if(fileType.equals(XLSX)){
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

    public void writeExcel(Workbook workbook,String path) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        workbook.write(fileOutputStream);
        System.out.println("文件修改完成：保存位置为：" + path);
        fileOutputStream.close();
    }
}
