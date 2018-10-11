package com.missfresh.metadata.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Auther Cgl
 * @Date 2018/10/10 21:08
 * @Desc
 **/
public class Demo {

    public static void main(String[] args) {

        ArrayList<HashMap<String ,String>> list=new ArrayList<>();
        HashMap<String,String> m=new HashMap<>();
        m.put("1","2");
        list.add(m);
        list.get(0).put("1","3");
        System.out.println(list);

    }

    public static void  add(ArrayList<Integer> list,Integer n){

        n=n-8;
        list.add(n);
        if(n<10){
            return;
        }else {
            add(list,n);
        }

    }
}
