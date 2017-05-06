package com.rideread.rideread;


import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {


        List<String> strs=new ArrayList<>();
        strs.add("aaaa");
        strs.add("aaaa1");
        strs.add("aaaa2");
        String testStr=strs.toString();
        System.out.println(testStr.substring(1,testStr.length()-1));


    }


}
