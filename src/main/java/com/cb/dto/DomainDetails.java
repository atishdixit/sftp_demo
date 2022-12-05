package com.cb.dto;


import com.opencsv.bean.CsvBindByPosition;

public class DomainDetails {
    @CsvBindByPosition(position = 0)
    private String povoID;

    @CsvBindByPosition(position = 1)
    private String povoTel;

    @CsvBindByPosition(position = 2)
    private String povoEmailAddr;

    private String providerCd;
    private String ctCd;
    private String providerAbbr;
    private String ctAbbr;
    private String payInfoNo;
    private String dateOfUse;
    private String plusMinusSign;
    private String useAmt;
    private String adjustClass;
    private String appropriatedPtAmt;
    private String billPtnClass;

    public String getPovoID() {
        return povoID;
    }

    public void setPovoID(String povoID) {
        this.povoID = povoID;
    }

    public String getPovoTel() {
        return povoTel;
    }

    public void setPovoTel(String povoTel) {
        this.povoTel = povoTel;
    }

    public String getPovoEmailAddr() {
        return povoEmailAddr;
    }

    public void setPovoEmailAddr(String povoEmailAddr) {
        this.povoEmailAddr = povoEmailAddr;
    }

    public String getProviderCd() {
        return providerCd;
    }

    public void setProviderCd(String providerCd) {
        this.providerCd = providerCd;
    }

    public String getCtCd() {
        return ctCd;
    }

    public void setCtCd(String ctCd) {
        this.ctCd = ctCd;
    }

    public String getProviderAbbr() {
        return providerAbbr;
    }

    public void setProviderAbbr(String providerAbbr) {
        this.providerAbbr = providerAbbr;
    }

    public String getCtAbbr() {
        return ctAbbr;
    }

    public void setCtAbbr(String ctAbbr) {
        this.ctAbbr = ctAbbr;
    }

    public String getPayInfoNo() {
        return payInfoNo;
    }

    public void setPayInfoNo(String payInfoNo) {
        this.payInfoNo = payInfoNo;
    }

    public String getDateOfUse() {
        return dateOfUse;
    }

    public void setDateOfUse(String dateOfUse) {
        this.dateOfUse = dateOfUse;
    }

    public String getPlusMinusSign() {
        return plusMinusSign;
    }

    public void setPlusMinusSign(String plusMinusSign) {
        this.plusMinusSign = plusMinusSign;
    }

    public String getUseAmt() {
        return useAmt;
    }

    public void setUseAmt(String useAmt) {
        this.useAmt = useAmt;
    }

    public String getAdjustClass() {
        return adjustClass;
    }

    public void setAdjustClass(String adjustClass) {
        this.adjustClass = adjustClass;
    }

    public String getAppropriatedPtAmt() {
        return appropriatedPtAmt;
    }

    public void setAppropriatedPtAmt(String appropriatedPtAmt) {
        this.appropriatedPtAmt = appropriatedPtAmt;
    }

    public String getBillPtnClass() {
        return billPtnClass;
    }

    public void setBillPtnClass(String billPtnClass) {
        this.billPtnClass = billPtnClass;
    }

    @Override
    public String toString() {
        return "CBDetails{" +
                "povoID='" + povoID + '\'' +
                ", povoTel='" + povoTel + '\'' +
                ", povoEmailAddr='" + povoEmailAddr + '\'' +
                '}';
    }
}
