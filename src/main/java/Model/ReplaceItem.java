package Model;

import Controller.ReadExcelController;
import org.apache.poi.ss.usermodel.Workbook;

public class ReplaceItem {

    Workbook workbook = ExcelBean.workbook;

    private int tarSheet;

    private int tarColumn;

    private int foreignSheet;

    private int foreignColumn;

    public ReplaceItem(int ts,int tc,int fs,int fc){
        tarSheet = ts;
        tarColumn = tc;
        foreignSheet = fs;
        foreignColumn = fc;
    }

    public int getTarSheet() {
        return tarSheet;
    }

    public void setTarSheet(int tarSheet) {
        this.tarSheet = tarSheet;
    }

    public int getTarColumn() {
        return tarColumn;
    }

    public void setTarColumn(int tarColumn) {
        this.tarColumn = tarColumn;
    }

    public int getForeignSheet() {
        return foreignSheet;
    }

    public void setForeignSheet(int foreignSheet) {
        this.foreignSheet = foreignSheet;
    }

    public int getForeignColumn() {
        return foreignColumn;
    }

    public void setForeignColumn(int foreignColumn) {
        this.foreignColumn = foreignColumn;
    }

    @Override
    public String toString() {
        String str = ReadExcelController.getSheetName(workbook,tarSheet) + "." + ReadExcelController.getColumnName(workbook,tarSheet,tarColumn)
                 + " —> "
                + ReadExcelController.getSheetName(workbook,foreignSheet) + "." + ReadExcelController.getColumnName(workbook,foreignSheet,foreignColumn);
        return str;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ReplaceItem item = (ReplaceItem) obj;
        return tarSheet == item.tarSheet && tarColumn == item.tarColumn && foreignSheet == item.foreignSheet && foreignColumn == item.foreignColumn;
    }
}
