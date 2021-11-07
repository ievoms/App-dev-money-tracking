package com.example.app_dev_money_tracking;

import com.example.app_dev_money_tracking.RecordTypeModel.RecordTypeKey;
import java.util.Date;

public class RecordsModel {

    private int id;
    private int amount;
    private String date;
    private int categoryId;
    private RecordTypeKey recordType;

    public RecordsModel(int id, int amount, String date, int categoryId, RecordTypeKey recordType) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.categoryId = categoryId;
        this.recordType = recordType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public RecordTypeKey getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordTypeKey recordType) {
        this.recordType = recordType;
    }

}
