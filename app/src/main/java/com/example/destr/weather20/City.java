package com.example.destr.weather20;

public class City {
    public String name;
    public String aaname;
    public Integer key;

    public void init(String string1, String string2, Integer integer){
        name = string1;
        aaname = string2;
        key = integer;
    }

    public String getName() {
        return name;
    }

    public String getAaname() {
        return aaname;
    }

    public Integer getKey() {
        return key;
    }

}
