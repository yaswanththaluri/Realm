package com.maya.yaswanththaluri.relm.model;


import io.realm.RealmObject;

public class User extends RealmObject
{
  private String name;
  private String termid;
  private String tgpa;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTermid() {
        return termid;
    }

    public void setTermid(String termid) {
        this.termid = termid;
    }

    public String getTgpa() {
        return tgpa;
    }

    public void setTgpa(String tgpa) {
        this.tgpa = tgpa;
    }
}
