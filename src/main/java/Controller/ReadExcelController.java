package Controller;

import FileUtil.ExcelFileManager;
import Model.ReplaceItem;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadExcelController {

    public static Workbook readExcel(String path){
        Workbook workbook = null;
        System.out.println("读取文件：" + path);
        try {
            workbook = ExcelFileManager.getInstance().readFile(path);
        } catch (IOException e) {
            System.out.println("文件" + path + "读取错误...");
            e.printStackTrace();
        }

        return workbook;

    }

    public static void writeExcel(Workbook workbook,String path){
        try {
            ExcelFileManager.getInstance().writeExcel(workbook,path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSheetName(Workbook workbook,int sheetIdx){
        if(workbook == null) return "";
        return workbook.getSheetName(sheetIdx);
    }

    public static String getColumnName(Workbook workbook,int sheetIdx,int columnIdx){
        if(workbook == null) return "";
        return workbook.getSheetAt(sheetIdx).getRow(0).getCell(columnIdx).toString();
    }

    public static boolean replace(Workbook workbook,ReplaceItem item){
        System.out.println("正在处理" + item.toString());
        Sheet tar = workbook.getSheetAt(item.getTarSheet());
        Sheet fore = workbook.getSheetAt(item.getForeignSheet());
        Map<String,Integer> foreMap = new HashMap<>();
        for(int i=1;i <= fore.getLastRowNum();++i){
            Row row = fore.getRow(i);
            Cell cell = row.getCell(item.getForeignColumn());
            Cell idCell = row.getCell(0);
            if(cell == null) continue;
            if(idCell == null){
                System.out.println("id列不完整或id列不为第一列");
                return false;
            }
            cell.setCellType(CellType.STRING);
            idCell.setCellType(CellType.STRING);
            foreMap.put(
                    cell.getStringCellValue(),
                    Integer.parseInt(idCell.getStringCellValue())
            );
        }

        for(int i=1;i<=tar.getLastRowNum();++i){
            Row row = tar.getRow(i);
            Cell cell = row.getCell(item.getTarColumn());
            if(cell == null) continue;
            cell.setCellType(CellType.STRING);
            String cellValue = cell.getStringCellValue();
            String newCellValue = replaceStr(cellValue,foreMap,workbook,item);
            if(newCellValue == null){
                return false;
            }
            cell.setCellValue(newCellValue);
        }
        return true;
    }

    private static String replaceStr(String ori,Map map,Workbook workbook,ReplaceItem item){
        String[] split = ori.split("\\|");
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<split.length;++i){
            if(i != 0) sb.append("|");
            if(!map.containsKey(split[i])){
                System.out.println("外键 " + getSheetName(workbook,item.getForeignSheet()) + "." +
                        getColumnName(workbook,item.getForeignSheet(),item.getForeignColumn()) +
                        " 不存在值 " + split[i]);
                return null;
            }
            sb.append(map.get(split[i]));
        }
        return sb.toString();
    }

}
