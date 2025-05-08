package com.erling.utilJ.path;

import java.util.ArrayList;
import java.util.List;

public class GetPath {
    public static List<String> getPath(){ //获取路径
        List<String> list = new ArrayList<String>();
        list.add(System.getProperty("user.dir"));
        list.add(System.getProperty("user.home"));
        list.add(System.getProperty("java.home"));
        return list;
    }
}
