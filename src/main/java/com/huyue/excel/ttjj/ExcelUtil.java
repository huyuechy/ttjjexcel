package com.huyue.excel.ttjj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;

/**
 * Created by huyue on 2017/7/16.
 */
public class ExcelUtil {
    public static void main(String[] args) throws Exception
    {
    File f = new File("E:\\old.xls");
    InputStream inputStream = new FileInputStream(f);
    HSSFWorkbook xssfWorkbook = new HSSFWorkbook(inputStream);
        List<FundBO> fundBOS=new ArrayList<>();
    //XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0); //如果是.xlsx文件使用这个
    HSSFSheet sheet1 = xssfWorkbook.getSheetAt(0);
        for (org.apache.poi.ss.usermodel.Row row : sheet1) {
            FundBO fundBO=new FundBO();
            fundBO.setTime(row.getCell(0).getDateCellValue());
            fundBO.setDwjz(row.getCell(1).getNumericCellValue());
            fundBO.setLjjz(row.getCell(2).getNumericCellValue());
            //row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
            fundBO.setRzjl(row.getCell(3).getNumericCellValue());
            fundBO.setSgzt(row.getCell(4).getStringCellValue());
            fundBO.setShzt(row.getCell(5).getStringCellValue());
            fundBOS.add(fundBO);
        }
        Collections.sort(fundBOS, new Comparator<FundBO>() {

            @Override
            public int compare(FundBO o1, FundBO o2) {
                if(o1.getTime().before(o2.getTime()))
                return -1;
                else
                    return 1;
            }
        });
        List<FundBO> fundBOS1=new ArrayList<>();
        String dateStr = "2015-06-23";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate=sdf.parse(dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        int flage=0;
        for(Calendar calendar=cal;calendar.get(Calendar.YEAR)<2019;calendar.add(Calendar.MONTH,1)){
            flage=0;
            for (FundBO fundBO : fundBOS) {
                Calendar ca2 = Calendar.getInstance();
                ca2.setTime(fundBO.getTime());
                if(calendar.get(Calendar.YEAR)==ca2.get(Calendar.YEAR)&&calendar.get(Calendar.MONTH)==ca2.get(Calendar.MONTH)){
                    if(ca2.get(Calendar.DATE)==20){
                        fundBOS1.add(fundBO);
                        flage=1;
                    }
                }
            }
            if(flage==0){
                for (FundBO fundBO : fundBOS) {
                    Calendar ca2 = Calendar.getInstance();
                    ca2.setTime(fundBO.getTime());
                    if(calendar.get(Calendar.YEAR)==ca2.get(Calendar.YEAR)&&calendar.get(Calendar.MONTH)==ca2.get(Calendar.MONTH)){
                        if(ca2.get(Calendar.DATE)>20){
                            fundBOS1.add(fundBO);
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("1");

        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("学生表一");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("净值日期");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("单位净值");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("累计净值");
        cell.setCellStyle(style);
        cell = row.createCell((short) 3);
        cell.setCellValue("日增长率");
        cell.setCellStyle(style);
        cell = row.createCell((short) 4);
        cell.setCellValue("申购状态");
        cell.setCellStyle(style);
        cell = row.createCell((short) 5);
        cell.setCellValue("赎回状态");
        cell.setCellStyle(style);

        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        for (int i = 0; i < fundBOS1.size(); i++)
        {
            row = sheet.createRow((int) i + 1);
            FundBO fundBO= fundBOS1.get(i);
            // 第四步，创建单元格，并设置值
            cell = row.createCell((short) 0);
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(fundBO.getTime()));
            row.createCell((short)1).setCellValue((double) fundBO.getDwjz());
            row.createCell((short)2).setCellValue((double) fundBO.getLjjz());
            row.createCell((short)3).setCellValue((double)fundBO.getRzjl());
            row.createCell((short)4).setCellValue(fundBO.getSgzt());
            row.createCell((short)4).setCellValue(fundBO.getShzt());
        }
        // 第六步，将文件存到指定位置
        try
        {
            FileOutputStream fout = new FileOutputStream("E:/Members2.xls");
            wb.write(fout);
            fout.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
