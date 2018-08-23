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
                         @RequestParam("edate") String edate,HttpServletResponse response) {
        try {
            List<FundBO> fundBOList=jsoupUtil.getFundBOList(URL.replace("CODE",code).replace("SDATE",sdate).replace("EDATE",edate));
            excelUtil.downloadExcel(fundBOList,response,code);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
