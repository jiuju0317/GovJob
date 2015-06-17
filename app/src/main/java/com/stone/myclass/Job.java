package com.stone.myclass;

import java.util.Date;

/**
 * Created by meamea on 2015/6/10.
 */
public class Job implements java.io.Serializable {

    //資料宣告
    private int _id;//該筆資料序號，可在viewUrl的最後找到
    private String orgName;//徵才機關
    private String personKind;//人員區分
    private String rank;//官職等
    private String title;//職稱
    private String sysnam;//職系
    private String numberOf;//名額
    private String genderType;//性別
    private String workPlaceType;//工作地
    private Date dateFrom;//刊登起始日期
    private Date dateTo;//刊登截止日期
    private boolean isHandicap;//歡迎身心障礙者參加甄選之職務
    private boolean isOriginal;//歡迎原住民族參加甄選之職務
    private boolean isLocalOriginal;//原住民族地區之職務
    private boolean isTraning;//通過「專員級人事人員進階職能培訓專班」人員專區
    private String type;//官等類別
    private String vitaeEmail;//聯絡Email
    private String workQuality;//資格條件
    private String workItem;//工作項目
    private String workAddress;//工作地址
    private String contactMethod;//聯絡方式
    private String urlLink;//線上報名URL
    private String viewUrl;//職缺資訊顯示URL

    public Job() {
    }

    public Job(int _id, String orgName, String personKind, String rank, String title,
               String sysnam, String numberOf, String genderType, String workPlaceType, Date dateFrom,
               Date dateTo, boolean isHandicap, boolean isOriginal, boolean isLocalOriginal, boolean isTraning,
               String type, String vitaeEmail, String workQuality, String workItem, String workAddress,
               String contactMethod, String urlLink, String viewUrl) {
        this._id = _id;
        this.orgName = orgName;
        this.personKind = personKind;
        this.rank = rank;
        this.title = title;
        this.sysnam = sysnam;
        this.numberOf = numberOf;
        this.genderType = genderType;
        this.workPlaceType = workPlaceType;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.isHandicap = isHandicap;
        this.isOriginal = isOriginal;
        this.isLocalOriginal = isLocalOriginal;
        this.isTraning = isTraning;
        this.type = type;
        this.vitaeEmail = vitaeEmail;
        this.workQuality = workQuality;
        this.workItem = workItem;
        this.workAddress = workAddress;
        this.contactMethod = contactMethod;
        this.urlLink = urlLink;
        this.viewUrl = viewUrl;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPersonKind() {
        return personKind;
    }

    public void setPersonKind(String personKind) {
        this.personKind = personKind;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSysnam() {
        return sysnam;
    }

    public void setSysnam(String sysnam) {
        this.sysnam = sysnam;
    }

    public String getNumberOf() {
        return numberOf;
    }

    public void setNumberOf(String numberOf) {
        this.numberOf = numberOf;
    }

    public String getGenderType() {
        return genderType;
    }

    public void setGenderType(String genderType) {
        this.genderType = genderType;
    }

    public String getWorkPlaceType() {
        return workPlaceType;
    }

    public void setWorkPlaceType(String workPlaceType) {
        this.workPlaceType = workPlaceType;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public boolean isHandicap() {
        return isHandicap;
    }

    public void setIsHandicap(boolean isHandicap) {
        this.isHandicap = isHandicap;
    }

    public boolean isOriginal() {
        return isOriginal;
    }

    public void setIsOriginal(boolean isOriginal) {
        this.isOriginal = isOriginal;
    }

    public boolean isLocalOriginal() {
        return isLocalOriginal;
    }

    public void setIsLocalOriginal(boolean isLocalOriginal) {
        this.isLocalOriginal = isLocalOriginal;
    }

    public boolean isTraning() {
        return isTraning;
    }

    public void setIsTraning(boolean isTraning) {
        this.isTraning = isTraning;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVitaeEmail() {
        return vitaeEmail;
    }

    public void setVitaeEmail(String vitaeEmail) {
        this.vitaeEmail = vitaeEmail;
    }

    public String getWorkQuality() {
        return workQuality;
    }

    public void setWorkQuality(String workQuality) {
        this.workQuality = workQuality;
    }

    public String getWorkItem() {
        return workItem;
    }

    public void setWorkItem(String workItem) {
        this.workItem = workItem;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(String contactMethod) {
        this.contactMethod = contactMethod;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    @Override
    public String toString() {
        return  "ID="+_id +"\n"
                +"--ORG_NAME="+orgName  +"\n"
                +"--PERSON_KIND="+personKind +"\n"
                +"--RANK="+rank +"\n"
                +"--TITLE="+title+"\n"
                +"--SYSNAM="+sysnam+"\n"
                +"--NUMBER_OF="+numberOf+"\n"
                +"--GENDER_TYPE="+genderType+"\n"
                +"--WORK_PLACE_TYPE="+workPlaceType+"\n"
                +"--DATE_FROM="+dateFrom+"\n"
                +"--DATE_TO="+dateTo+"\n"
                +"--isHandicap="+isHandicap+"\n"
                +"--isOriginal="+isOriginal+"\n"
                +"--isLocalOriginal="+isLocalOriginal+"\n"
                +"--isTraning="+isTraning+"\n"
                +"--type="+type+"\n"
                +"--vitaeEmail="+vitaeEmail+"\n"
                +"--workQuality="+workQuality+"\n"
                +"--workItem="+workItem+"\n"
                +"--workAddress="+workAddress+"\n"
                +"--contactMethod="+contactMethod+"\n"
                +"--urlLink="+urlLink+"\n"
                +"--viewUrl="+viewUrl+"\n"
                +"\n==========\n";
    }

}
