package com.gromov.service.dataExport;
import jxl.*;
import jxl.format.Colour;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExcelAdapter {
    private static String excelHubPath = "src/main/java/com/gromov/документы Excel/";
    public static void excelExport(JTable table,String docName,String sheetName) {

        WritableWorkbook workbook;
        try {
            File file = new File(excelHubPath + docName + ".xls");
            file.createNewFile();
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet(sheetName,0);
            int columnCount = table.getColumnCount();
            Label label;
            for(int i = 0; i < table.getRowCount(); i++) {
                for(int j = 0; j < columnCount; j++) {
                    label = new Label(j,i+1,table.getValueAt(i,j).toString());
                    sheet.addCell(label);
                }
            }

            WritableCellFormat wcf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL,
                    10,WritableFont.BOLD,false));
            wcf.setBackground(Colour.BRIGHT_GREEN);

            CellView columnView;
            for(int i = 0; i < table.getColumnCount();i++) {
                label = new Label(i,0,table.getColumnName(i),wcf);
                sheet.addCell(label);
                columnView = sheet.getColumnView(i);
                columnView.setAutosize(true);
                sheet.setColumnView(i,columnView);
            }
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (RowsExceededException e) {
            throw new RuntimeException(e);
        } catch (WriteException e) {
            throw new RuntimeException(e);
        }


    }

}