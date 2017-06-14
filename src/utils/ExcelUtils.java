package utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Chenli
 * @time 2017/5/20 19:19
 */
public class ExcelUtils {

    public static String makeSampleFile(String[] cellWords,String path){
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        HSSFRow row = sheet.createRow(0);
        for (int i=0;i<cellWords.length;i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(cellWords[i]);
        }
        String uuidHex = UUIDUtils.getUUIDHex();
        FileOutputStream excelFile = null;
        try {
            File file = new File(path + uuidHex + ".xlsx");

            excelFile = new FileOutputStream(file);
            wb.write(excelFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uuidHex+".xlsx";
    }
}
