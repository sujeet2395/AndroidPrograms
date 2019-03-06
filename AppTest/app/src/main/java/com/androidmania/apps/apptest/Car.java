package com.androidmania.apps.apptest;

public class Car {
    private String name="nonname";
    public Car()
    {
        name="noname";
    }
    public String getName()
    {
        return name;
    }
    public void setName(String s)
    {
        name=s;
    }
    public String toString()
    {
        String toStr="name:"+name;
        return toStr;
    }
}
