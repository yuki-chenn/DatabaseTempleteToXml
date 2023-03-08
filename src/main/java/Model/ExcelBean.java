package Model;

import Controller.ReadExcelController;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelBean {

    public static Workbook workbook = null;

    public static String[] sheetList = null;

    public static Map<Integer,String[]> columns = null;

    public static List<ReplaceItem> replaceList = null;

    public static String[] getSheetList(){
        if(workbook == null) return new String[]{""};
        if(sheetList == null){
            int sheetNum = workbook.getNumberOfSheets();
            sheetList = new String[sheetNum];
            for(int i=0;i<sheetNum;++i) sheetList[i] = workbook.getSheetName(i);
        }
        return sheetList;
    }

    public static String[] getSheetColumns(int sheetIdx){
        if(workbook == null) return new String[]{""};
        if(columns == null) columns = new HashMap<>();
        if(!columns.containsKey(sheetIdx)){
            Sheet sheet = workbook.getSheetAt(sheetIdx);
            Row columnRow = sheet.getRow(0);
            int num = columnRow.getLastCellNum();
            String[] column = new String[num];
            for(int i=0;i<num;++i) column[i] = columnRow.getCell(i).toString();
            columns.put(sheetIdx,column);
        }
        return columns.get(sheetIdx);
    }

    public static String[] getReplaceList(){
        if(replaceList == null) return new String[]{""};
        String[] list = new String[replaceList.size()];
        for(int i=0;i<replaceList.size();++i) list[i] = replaceList.get(i).toString();
        return list;
    }

    public static void addReplaceItem(ReplaceItem item){
        if(replaceList == null){
            replaceList = new ArrayList<>();
        }
        for(ReplaceItem iter : replaceList){
            if(item.equals(iter)){
                System.out.println("该外键已添加");
                return ;
            }
        }

        replaceList.add(item);
        System.out.println("添加外键修改：" + item.toString());

    }

    public static void removeRepalceItem(int idx){
        if(replaceList == null){
            replaceList = new ArrayList<>();
            return ;
        }

        if(idx < 0) return ;

        System.out.println("移除外键修改：" + replaceList.get(idx).toString());
        replaceList.remove(idx);

    }

    public static void removeAllRepalceItem(){
        replaceList.clear();
    }

}
