package com.stone.myclass;

/**
 * Created by Entrace on 2015/6/22.
 */
public class GovernmentOfficer {

    private int serialNumber ;
    private String name;
    private String level;
    private String  registerDate;
    private String testDate;
    private String note;

    public GovernmentOfficer() {

    }

    public GovernmentOfficer(int serialNumber, String name, String level, String registerDate,
                             String testDate, String note) {

        this.serialNumber = serialNumber;
        this.name=name;
        this.level=level;
        this.registerDate=registerDate;
        this.testDate=testDate;
        this.note=note;


    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void showAllItem(GovernmentOfficer g){

        System.out.println("serial nuber"+g.getSerialNumber());
        System.out.println("name" + g.getName());
        System.out.println("level"+g.getLevel());
        System.out.println("Rday"+g.getRegisterDate());
        System.out.println("Tday"+g.getTestDate());




    }



}
