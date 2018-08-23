package com.huyue.excel.ttjj;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class JsoupUtil {

    public List<FundBO> getFundBOList(String url) throws Exception{
        List<FundBO> fundBOList=new ArrayList<>();
        Document document= null;
       document = Jsoup.connect(url)
                    //模拟火狐浏览器
                    .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                    .get();

        Elements main=document.select("body > table > tbody");
        Elements sub=main.select("tr");
        for(Element element:sub){
            FundBO fundBO=new FundBO();
            //日期
            Elements data=element.select("td:nth-of-type(1)");

            fundBO.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(data.text()));

            //单位净值
            Elements dwjz=element.select("td:nth-of-type(2)");

            fundBO.setDwjz(Double.valueOf(dwjz.text()));

            //累计净值
            Elements ljjz=element.select("td:nth-of-type(3)");

            fundBO.setLjjz(Double.valueOf(ljjz.text()));
            fundBOList.add(fundBO);
        }
        return fundBOList;
    }


    }

