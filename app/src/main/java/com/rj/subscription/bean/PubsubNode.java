package com.rj.subscription.bean;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

/**
 * Created by lijunyan on 2016/8/23.
 */
public class PubsubNode implements Serializable{

    private String id;
    private String name;
    private String logo;
    private String description;
    private int isPub;
    private int isNotice; //订阅号新消息是否通知

    public int isPub() {
        return isPub;
    }

    public void setPub(int pub) {
        isPub = pub;
    }

    public int getIsNotice() {
        return isNotice;
    }

    public void setIsNotice(int isNotice) {
        this.isNotice = isNotice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "PubsubNode{" +
                "description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", isPub=" + isPub +
                ", isNotice=" + isNotice +
                '}';
    }
}
