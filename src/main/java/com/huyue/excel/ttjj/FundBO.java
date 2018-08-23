package com.huyue.excel.ttjj;

import java.util.Date;

/**
 * TODO
 *
 * @author huyue
 * @version V1.0
 * @since 2018-08-22 22:24
 */
public class FundBO {

    private Date time;

    private double dwjz;

    private double ljjz;

    private double rzjl;

    private String sgzt;

    private String shzt;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getDwjz() {
        return dwjz;
    }

    public void setDwjz(double dwjz) {
        this.dwjz = dwjz;
    }

    public double getLjjz() {
        return ljjz;
    }

    public void setLjjz(double ljjz) {
        this.ljjz = ljjz;
    }

    public double getRzjl() {
        return rzjl;
    }

    public void setRzjl(double rzjl) {
        this.rzjl = rzjl;
    }

    public String getSgzt() {
        return sgzt;
    }

    public void setSgzt(String sgzt) {
        this.sgzt = sgzt;
    }

    public String getShzt() {
        return shzt;
    }

    public void setShzt(String shzt) {
        this.shzt = shzt;
    }
}
