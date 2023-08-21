package com.hb.poihandleexcel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 读写Excel的工具类
 * 注意：如数据量较大，请在子线程中使用download和readExcel方法！
 * 工具方法：
 * <p> download(Context, List, String)，把集合里的数据导出到Excel表格。
 * <p> readExcel(String)，从Excel中读取不带表头的数据返回List集合。
 * <p> getNowTime()，返回"yyyy-MM-dd HH:mm:ss"形式的当前时间
 */
public class ExcelFileUtils {

    private static final String TAG = "ExcelFileUtils测试";
    private static final String sheetName = "Sheet1";

    /**
     * 把集合里的数据导出到Excel表格
     *
     * @param context  context，用于显示toast提示
     * @param list     数据集合
     * @param fileName 文件名（不需要加扩展名，默认.xlsx）
     */
    public static void download(Context context, @NonNull List<MyBean> list, String fileName) {

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet(sheetName);

        //设置列宽
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 7000);

        //=================================定义表头属性===============================================
        Font font = wb.createFont(); // 生成字体格式设置对象
        font.setFontName("黑体"); // 设置字体黑体
        font.setBold(true); // 字体加粗
        font.setFontHeightInPoints((short) 16); // 设置字体大小
        font.setColor(Font.COLOR_NORMAL);//字体颜色

        CellStyle cellStyle = wb.createCellStyle(); // 生成行格式设置对象
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);// 下边框
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);// 右边框
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);// 上边框
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 横向居中对齐
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 纵向居中对齐
        cellStyle.setFont(font);

        //=================================定义内容属性===============================================
        Font txtContent = wb.createFont(); // 生成字体格式设置对象
        txtContent.setFontName("黑体"); // 设置字体黑体
        txtContent.setBold(false); // 字体加粗
        txtContent.setFontHeightInPoints((short) 12); // 设置字体大小
        txtContent.setColor(Font.COLOR_RED); //字体颜色

        CellStyle cellStyleContent = wb.createCellStyle(); // 生成行格式设置对象
        cellStyleContent.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
        cellStyleContent.setBorderLeft(CellStyle.BORDER_THIN); // 左边框
        cellStyleContent.setBorderRight(CellStyle.BORDER_THIN); // 右边框
        cellStyleContent.setBorderTop(CellStyle.BORDER_THIN); // 上边框
        cellStyleContent.setAlignment(CellStyle.ALIGN_CENTER); // 横向居中对齐
        cellStyleContent.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 纵向居中对齐
        cellStyleContent.setFont(txtContent);

        //====================================写入数据===============================================
        for (int k = 0; k <= list.size(); k++) {
            Row row = sheet.createRow(k);
            if (k == 0) {
                // 表头
                Cell cell0 = row.createCell(0);
                Cell cell1 = row.createCell(1);
                Cell cell2 = row.createCell(2);
                cell0.setCellStyle(cellStyle);
                cell1.setCellStyle(cellStyle);
                cell2.setCellStyle(cellStyle);

                row.setHeight((short) 500); // 行高
                cell0.setCellValue("名称");
                cell1.setCellValue("数值");
                cell2.setCellValue("时间");
            } else {
                // 内容
                Cell cell0 = row.createCell(0);
                Cell cell1 = row.createCell(1);
                Cell cell2 = row.createCell(2);
                cell0.setCellStyle(cellStyleContent);
                cell1.setCellStyle(cellStyleContent);
                cell2.setCellStyle(cellStyleContent);

                row.setHeight((short) 500); // 行高
                cell0.setCellValue(list.get(k - 1).getName());
                cell1.setCellValue(list.get(k - 1).getValue());
                cell2.setCellValue(list.get(k - 1).getTime());
            }
        }

        String filePath = Environment.getExternalStorageDirectory().getPath();
        String fileResultPath = filePath + File.separator + fileName + ".xlsx";
        Log.d(TAG, "download: fileResultPath = " + fileResultPath);

        File file = new File(fileResultPath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "Create Fail IOException" + e);
                e.printStackTrace();
            }
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileResultPath);
            wb.write(fileOutputStream);
            Toast.makeText(context, "导出成功：" + fileResultPath, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Download Fail FileNotFoundException" + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "Download Fail IOException" + e);
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                    fileOutputStream = null;
                }
                if (wb != null) {
                    wb.close();
                    wb = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从Excel中读取数据，返回不带表头的数据List集合
     *
     * @param fileResultPath 文件的绝对路径
     * @return 数据集合
     */
    public static List readExcel(String fileResultPath) {
        List list = new ArrayList();
        Workbook workbook = null;
        Sheet sheet = null;
        try {
            workbook = WorkbookFactory.create(new File(fileResultPath));
            sheet = workbook.getSheet(sheetName);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                int j = 0;
                MyBean myRowBean = new MyBean(getCellValueToString(row.getCell(j)), getCellValueToString(row.getCell(++j)), getCellValueToString(row.getCell(++j)));
                list.add(myRowBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                    workbook = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 把从Excel中读出来的各类型的数据转换为string类型
     *
     * @param cell 单元格
     * @return string类型的单元格的数据
     */
    private static String getCellValueToString(Cell cell) {
        // 获取单元格值，并根据单元格的位置或特定条件，将值设置到MyRowBean对象的相应属性中
        String cellValue = "";
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            cellValue = cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            cellValue = String.valueOf(cell.getNumericCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            cellValue = String.valueOf(cell.getBooleanCellValue());
        }
        return cellValue;
    }

    /**
     * 获取时间
     *
     * @return 返回"yyyy-MM-dd HH:mm:ss"形式的当前时间
     */
    public static String getNowTime() {
        Date time = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time);
    }

}
