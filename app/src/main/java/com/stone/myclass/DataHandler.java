package com.stone.myclass;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by meamea on 2015/6/10.
 */
public class DataHandler extends DefaultHandler {

    private Job job;//單筆job資料
    private ArrayList<Job> jobs;//最終處理完的Job集合
    private int announceDate;//XML資料版本編號
    private boolean inAnnounceDate,inRow,inOrgName,inPersonKind,inRank,inTitle,inSysnam,
            inNumberOf,inGenderType,inWorkPlaceType,inDateFrom,inDateTo,
            inIsHandicap, inIsOriginal,inIsLocalOriginal,inIsTraning,
            inType,inVitaeEmail,inWorkQuality,inWorkItem,inWorkAddress,
            inContactMethod,inUrlLink,inViewUrl;

    public ArrayList<Job> getJobs(){
        return jobs;
    }

    public int getAnnounceDate(){
        return announceDate;
    }

    public String tempDateFrom,tempDateTo;

    @Override
    public void startDocument() throws SAXException {
        jobs = new ArrayList<Job>();
    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if(localName.equals("ANNOUNCE_DATE")){
            inAnnounceDate = true;
        }else if(localName.equals("ROW") && inRow == false){
            inRow = true;
            job = new Job();//新增一筆空的job資料
        }else if(inRow == true){
            if(localName.equals("ORG_NAME")) {inOrgName = true;}
            if(localName.equals("PERSON_KIND")) {inPersonKind = true;}
            if(localName.equals("RANK")) {inRank = true;}
            if(localName.equals("TITLE")) {inTitle = true;}
            if(localName.equals("SYSNAM")) {inSysnam = true;}
            if(localName.equals("NUMBER_OF")) {inNumberOf = true;}
            if(localName.equals("GENDER_TYPE")) {inGenderType = true;}
            if(localName.equals("WORK_PLACE_TYPE")) {inWorkPlaceType = true;}
            if(localName.equals("DATE_FROM")) {inDateFrom = true;}
            if(localName.equals("DATE_TO")) {inDateTo= true;}
            if(localName.equals("IS_HANDICAP")) {inIsHandicap = true;}
            if(localName.equals("IS_ORIGINAL")) {inIsOriginal = true;}
            if(localName.equals("IS_LOCAL_ORIGINAL")) {inIsLocalOriginal = true;}
            if(localName.equals("IS_TRANING")) {inIsTraning = true;}
            if(localName.equals("TYPE")) {inType = true;}
            if(localName.equals("VITAE_EMAIL")) {inVitaeEmail = true;}
            if(localName.equals("WORK_QUALITY")) {inWorkQuality = true;}
            if(localName.equals("WORK_ITEM")) {inWorkItem = true;}
            if(localName.equals("WORK_ADDRESS")) {inWorkAddress = true;}
            if(localName.equals("CONTACT_METHOD")) {inContactMethod = true;}
            if(localName.equals("URL_LINK")) {inUrlLink = true;}
            if(localName.equals("VIEW_URL")) {inViewUrl = true;}
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if(localName.equals("ANNOUNCE_DATE")){
            inAnnounceDate = false;
        }else if(localName.equals("ROW") && inRow == true){
            inRow = false;
            jobs.add(job);//把一筆job資料放到jobs裡面
            job = null;
        }else if(inRow == true){
            if(localName.equals("ORG_NAME")) {inOrgName = false;}
            if(localName.equals("PERSON_KIND")) {inPersonKind = false;}
            if(localName.equals("RANK")) {inRank = false;}
            if(localName.equals("TITLE")) {inTitle = false;}
            if(localName.equals("SYSNAM")) {inSysnam = false;}
            if(localName.equals("NUMBER_OF")) {inNumberOf = false;}
            if(localName.equals("GENDER_TYPE")) {inGenderType = false;}
            if(localName.equals("WORK_PLACE_TYPE")) {inWorkPlaceType = false;}
            if(localName.equals("DATE_FROM")) {
                Date dateFrom = toDate(tempDateFrom);
                job.setDateFrom(dateFrom);
                tempDateFrom = null;
                inDateFrom = false;
            }
            if(localName.equals("DATE_TO")) {
                Date dateTo = toDate(tempDateTo);
                job.setDateTo(dateTo);
                tempDateTo = null;
                inDateTo= false;
            }
            if(localName.equals("IS_HANDICAP")) {inIsHandicap = false;}
            if(localName.equals("IS_ORIGINAL")) {inIsOriginal = false;}
            if(localName.equals("IS_LOCAL_ORIGINAL")) {inIsLocalOriginal = false;}
            if(localName.equals("IS_TRANING")) {inIsTraning = false;}
            if(localName.equals("TYPE")) {inType = false;}
            if(localName.equals("VITAE_EMAIL")) {inVitaeEmail = false;}
            if(localName.equals("WORK_QUALITY")) {inWorkQuality = false;}
            if(localName.equals("WORK_ITEM")) {inWorkItem = false;}
            if(localName.equals("WORK_ADDRESS")) {inWorkAddress = false;}
            if(localName.equals("CONTACT_METHOD")) {inContactMethod = false;}
            if(localName.equals("URL_LINK")) {inUrlLink = false;}
            if(localName.equals("VIEW_URL")) {
                String tempViewUrl = job.getViewUrl();
                //設定ID
                int sindex = tempViewUrl.length() - 10;
                job.set_id(Integer.parseInt(tempViewUrl.substring(sindex)));
                inViewUrl = false;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String chars = new String(ch, start, length);
        chars = chars.trim();

        if(inAnnounceDate){
            announceDate = Integer.parseInt(chars);
        }else if(inRow) {
            if(inOrgName){job.setOrgName(chars);}
            if(inPersonKind){job.setPersonKind(chars);}
            if(inRank){job.setRank(chars);}
            if(inTitle){job.setTitle(chars);}
            if(inSysnam){job.setSysnam(chars);}
            if(inNumberOf){job.setNumberOf(chars);}
            if(inGenderType){job.setGenderType(chars);}
            if(inWorkPlaceType){job.setWorkPlaceType(chars);}

            if(inDateFrom){
                if(tempDateFrom==null){
                    tempDateFrom = chars;
                }else{
                    tempDateFrom = tempDateFrom + chars;
                }
            }

            if(inDateTo){
                if(tempDateTo==null){
                    tempDateTo = chars;
                }else{
                    tempDateTo = tempDateTo + chars;
                }
            }

            if(inIsHandicap){
                if(chars.equals("Y")){
                    job.setIsHandicap(true);
                }else{
                    job.setIsHandicap(false);
                }
            }

            if(inIsOriginal){
                if(chars.equals("Y")){
                    job.setIsOriginal(true);
                }else{
                    job.setIsOriginal(false);
                }
            }

            if(inIsLocalOriginal){
                if(chars.equals("Y")){
                    job.setIsLocalOriginal(true);
                }else{
                    job.setIsLocalOriginal(false);
                }
            }

            if(inIsTraning){
                if(chars.equals("Y")){
                    job.setIsTraning(true);
                }else{
                    job.setIsTraning(false);
                }
            }

            if(inType){job.setType(chars);}
            if(inVitaeEmail){job.setVitaeEmail(chars);}

            if(inWorkQuality){
                if(job.getWorkQuality()== null){
                    job.setWorkQuality(chars);
                }else{
                    chars = job.getWorkQuality()+chars;
                    job.setWorkQuality(chars);
                }
            }

            if(inWorkItem){
                if(job.getWorkItem()==null) {
                    job.setWorkItem(chars);
                }else{
                    chars = job.getWorkItem()+chars;
                    job.setWorkItem(chars);
                }
            }

            if(inWorkAddress){
                if(job.getWorkAddress()==null){
                    job.setWorkAddress(chars);
                }else{
                    chars = job.getWorkAddress()+chars;
                    job.setWorkAddress(chars);
                }
            }

            if(inContactMethod){
                if(job.getContactMethod()== null){
                    job.setContactMethod(chars);
                }else{
                    chars = job.getContactMethod()+chars;
                    job.setContactMethod(chars);
                }
            }

            if(inUrlLink){job.setUrlLink(chars);}

            if(inViewUrl){
                if(job.getViewUrl()== null){
                    job.setViewUrl(chars);
                }else{
                    chars = job.getViewUrl()+chars;
                    job.setViewUrl(chars);
                }
            }
        }
    }

    private Date toDate(String s){
        Date date = null;

        String year, month, day, dateComb;
        year = Integer.toString(Integer.parseInt(s.substring(0, 3)) + 1911);
        month = s.substring(3, 5);
        day = s.substring(5);

        dateComb = year + "/" + month + "/" + day;
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        try {
            date = df.parse(dateComb);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
