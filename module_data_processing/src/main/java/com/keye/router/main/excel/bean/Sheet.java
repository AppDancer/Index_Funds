package com.keye.router.main.excel.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

/**
 * Entity mapped to table "Sheet".
 */
@Entity(indexes = {
        @Index(value = " date DESC", unique = true)
})
public class Sheet {

    @Id(autoincrement = true)
    private Long id;

    private java.util.Date date;

    private Float closingPrice;//收盘价

    private Float PE;//市盈率

    private Float PB;//市净率

    private Float PCF;//市现率

    private Float PS;//市销率

    private Float EPS;//每股收益[元]

    private Float netAssets;//每股净资产[元]

    private Float ROE;//净资产收益率[%]

    private Float GMV;//总市值[亿]

    private Float CMV;//流通市值[亿]]

    @Generated(hash = 448565035)
    public Sheet(Long id, java.util.Date date, Float closingPrice, Float PE,
                 Float PB, Float PCF, Float PS, Float EPS, Float netAssets, Float ROE,
                 Float GMV, Float CMV) {
        this.id = id;
        this.date = date;
        this.closingPrice = closingPrice;
        this.PE = PE;
        this.PB = PB;
        this.PCF = PCF;
        this.PS = PS;
        this.EPS = EPS;
        this.netAssets = netAssets;
        this.ROE = ROE;
        this.GMV = GMV;
        this.CMV = CMV;
    }

    @Generated(hash = 1314343206)
    public Sheet() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.util.Date getDate() {
        return this.date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public Float getClosingPrice() {
        return this.closingPrice;
    }

    public void setClosingPrice(Float closingPrice) {
        this.closingPrice = closingPrice;
    }

    public Float getPE() {
        return this.PE;
    }

    public void setPE(Float PE) {
        this.PE = PE;
    }

    public Float getPB() {
        return this.PB;
    }

    public void setPB(Float PB) {
        this.PB = PB;
    }

    public Float getPCF() {
        return this.PCF;
    }

    public void setPCF(Float PCF) {
        this.PCF = PCF;
    }

    public Float getPS() {
        return this.PS;
    }

    public void setPS(Float PS) {
        this.PS = PS;
    }

    public Float getEPS() {
        return this.EPS;
    }

    public void setEPS(Float EPS) {
        this.EPS = EPS;
    }

    public Float getNetAssets() {
        return this.netAssets;
    }

    public void setNetAssets(Float netAssets) {
        this.netAssets = netAssets;
    }

    public Float getROE() {
        return this.ROE;
    }

    public void setROE(Float ROE) {
        this.ROE = ROE;
    }

    public Float getGMV() {
        return this.GMV;
    }

    public void setGMV(Float GMV) {
        this.GMV = GMV;
    }

    public Float getCMV() {
        return this.CMV;
    }

    public void setCMV(Float CMV) {
        this.CMV = CMV;
    }
}
