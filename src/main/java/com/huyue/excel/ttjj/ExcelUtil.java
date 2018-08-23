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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huyue on 2017/7/16.
 */

@Service
public class ExcelUtil {

    public void downloadExcel(List<FundBO> fundBOS, HttpServletResponse response, String code) throws Exception {
        Collections.sort(fundBOS, new Comparator<FundBO>() {
            @Override
            public int compare(FundBO o1, FundBO o2) {
                if (o1.getTime().before(o2.getTime()))
                    return -1;
                else
                    return 1;
            }
        });
        List<FundBO> fundBOS1 = new ArrayList<>();
        String dateStr = "2015-06-23";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        int flage = 0;
        for (Calendar calendar = cal; calendar.get(Calendar.YEAR) < 2019; calendar.add(Calendar.MONTH, 1)) {
            flage = 0;
            for (FundBO fundBO : fundBOS) {
                Calendar ca2 = Calendar.getInstance();
                ca2.setTime(fundBO.getTime());
                if (calendar.get(Calendar.YEAR) == ca2.get(Calendar.YEAR) && calendar.get(Calendar.MONTH) == ca2.get(Calendar.MONTH)) {
                    if (ca2.get(Calendar.DATE) == 20) {
                        fundBOS1.add(fundBO);
                        flage = 1;
                    }
                }
            }
            if (flage == 0) {
                for (FundBO fundBO : fundBOS) {
                    Calendar ca2 = Calendar.getInstance();
                    ca2.setTime(fundBO.getTime());
                    if (calendar.get(Calendar.YEAR) == ca2.get(Calendar.YEAR) && calendar.get(Calendar.MONTH) == ca2.get(Calendar.MONTH)) {
                        if (ca2.get(Calendar.DATE) > 20) {
                            fundBOS1.add(fundBO);
                            break;
                        }
                    }
                }
            }
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("基金净值");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        //style.setAlignment(HSSFCellStyle); // 创建一个居中格式

        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("净值日期");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("单位净值");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("累计净值");
        cell.setCellStyle(style);
        for (int i = 0; i < fundBOS1.size(); i++) {
            row = sheet.createRow((int) i + 1);
            FundBO fundBO = fundBOS1.get(i);
            // 第四步，创建单元格，并设置值
            cell = row.createCell((short) 0);
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(fundBO.getTime()));
            row.createCell((short) 1).setCellValue((double) fundBO.getDwjz());
            row.createCell((short) 2).setCellValue((double) fundBO.getLjjz());
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename="+code+".xls");
        response.flushBuffer();
        wb.write(response.getOutputStream());

    }

}
