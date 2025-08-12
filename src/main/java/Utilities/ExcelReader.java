package Utilities;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.util.*;

public class ExcelReader {

    public static List<Map<String, String>> getData(String filePath, String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {  // Enforce XSSF for .xlsx files

            Sheet sheet = workbook.getSheet(sheetName);
            Row headerRow = sheet.getRow(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Map<String, String> rowMap = new HashMap<>();
                Row currentRow = sheet.getRow(i);

                for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                    String key = headerRow.getCell(j).getStringCellValue();
                    Cell cell = currentRow.getCell(j);

                    String value = cell != null ? cell.toString() : "";
                    rowMap.put(key, value);
                }
                dataList.add(rowMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataList;
    }
}
