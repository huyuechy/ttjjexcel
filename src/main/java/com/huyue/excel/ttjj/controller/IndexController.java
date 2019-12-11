package com.huyue.excel.ttjj.controller;

import com.huyue.excel.ttjj.ExcelUtil;
import com.huyue.excel.ttjj.FundBO;
import com.huyue.excel.ttjj.JsoupUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/index")

public class IndexController {
    @Resource
    private JsoupUtil jsoupUtil;

    @Resource
    private ExcelUtil  excelUtil;

    public static final String URL="http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code=CODE&sdate=SDATE&edate=EDATE&per=5000";


    @RequestMapping(value = "/downloadExecl", method = RequestMethod.GET)
    public void getExecl (@RequestParam("code") String code,
                         @RequestParam("sdate")String sdate,
                          @RequestParam("num")Integer num,
                         @RequestParam("edate") String edate,HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        try {
            if(num>31||num<0){
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write("num不能大于31或小于1");
                throw new Exception("num不能大于31或小于1");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date startDate = sdf.parse(sdate);
                Date endDate = sdf.parse(edate);
            } catch (ParseException e) {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write("sdate或edate格式错误");
                throw new Exception("sdate或edate格式错误");
            }
            List<FundBO> fundBOList=jsoupUtil.getFundBOList(URL.replace("CODE",code).replace("SDATE",sdate).replace("EDATE",edate));
            excelUtil.downloadExcel(fundBOList,response,code,num,sdate,edate);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
