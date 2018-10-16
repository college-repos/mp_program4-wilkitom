package com.example.thomaswilkinson.program4;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;

@Entity
public class Cont {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;

    public String category;

    public String date;

    public Float amount;

    public String note;


    public long getId(){ return id;}
    public void setId(long id){this.id = id;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public String getCategory(){return category;}
    public void setCategory(String category){this.category = category;}
    public String getDate(){return date;}
    public void setDate(String date){this.date = date;}
    public Float getAmount(){return amount;}
    public void setAmount(Float amount){this.amount = amount;}
    public String getNote(){return note;}
    public void setNote(String note){this.note = note;}

    public Cont(){}
    public Cont(String name, String category, String date, Float amount, String note)
    {
        this.name = name;
        this.category = category;
        this.date = date;
        this. amount = amount;
        this.note = note;
    }
}
