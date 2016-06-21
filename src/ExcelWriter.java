import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelWriter {

    private Map<Sheet, Integer> columnSizes = new HashMap<>();

    private Workbook wb;

    public ExcelWriter() {
        rest();
    }

    public void rest() {
        wb = new HSSFWorkbook();
    }

    public void addVectorList(String sheetName, List<Vector> list) {
        List<Double> listX = new ArrayList<>();
        List<Double> listY = new ArrayList<>();
        List<Double> listZ = new ArrayList<>();

        for (Vector vector : list) {
            listX.add(vector.getX());
            listY.add(vector.getY());
            listZ.add(vector.getZ());
        }
        addFewColumns(sheetName, listX, listY, listZ);

    }


    public void addFewColumns(String sheetName, List<Double> ... lists) {
        for (List<Double> list : lists)
            addColumn(sheetName, list);
    }

    public void addFewColumns(String sheetName,  ArrayList<ArrayList<Double>> lists) {
        for (List<Double> list : lists)
            addColumn(sheetName, list);
    }

    public void addColumn(String sheetName, List<Double> list) {
        int column = 0;
        Sheet sheet = null;
        for (Sheet iterSheet : columnSizes.keySet())
            if (iterSheet.getSheetName().equals(sheetName)) {
                column = columnSizes.get(iterSheet) + 1;
                sheet = iterSheet;
                break;
            }
        if (sheet == null)
            sheet = wb.createSheet(sheetName);
        columnSizes.put(sheet, column);


        int i = 0;

        if (column != 0)
            for (double value : list) {
                Row row = sheet.getRow(i++);
                row.createCell(column).setCellValue(value);
            }
        else
            for (double value : list) {
                Row row = sheet.createRow(i++);
                row.createCell(column).setCellValue(value);
            }
    }

    public void write(String excelName) {
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(excelName + ".xls");
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
