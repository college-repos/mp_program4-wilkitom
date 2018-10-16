package com.example.thomaswilkinson.program4;

//container to hold entrys
class container{
    public container(String name, String category, String date, float amount, String note, long id)
    {
        cName = name;
        cCategory = category;
        cDate = date;
        cAmount = amount;
        cNote = note;
        _id = id;
    }
    String cName;
    String cCategory;
    String cDate;
    Float cAmount;
    String cNote;
    long _id;
}
