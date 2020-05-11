package com.simpleexample.kafka_prod_cons.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Constants {

    public static final String Collect_Statistic="statistics";

    public static final Map<String,String> map = new ConcurrentHashMap<String,String>();

    public static final Map<String, String> codeComp = new HashMap<>();
    public static final String[] types = {"CRITICAL","ERROR","WARNING","INFO","DEBUG"};
    public static final Map<String, Integer> alarmCode = new HashMap<>();
    public static final String[] kipStatus={"NONE","INFO","MAINTENANCE_RECOMMENDED","MAINTENANCE_REQUIRED","ERROR"};

    static {
        //映射对应的数据库
        map.put("Door closing","doorClose");
        map.put("Accelerate","acceleration");
        map.put("Constant ride","constantRun");
        map.put("Decelerate","deceleration");
        map.put("Door opening","doorOpen");

//        map.put("Door closing freq","doorOpen");
//        map.put("Door opening freq","doorOpen");
//        map.put("Constant ride freq","doorOpen");
        map.put("Door closing freq","doorCloseFreq");
        map.put("Door opening freq","doorOpenFreq");
        map.put("Constant ride freq","constantRunFreq");

        codeComp.put("0x00","Unknown");
        codeComp.put("0x01","Car door"); // Car Door
        codeComp.put("0x02","Landing door"); // Landing Door(不确定,猜的)
        codeComp.put("0x03","Driving unit");
        codeComp.put("0x04","Motor");
        codeComp.put("0x05","Gearbox"); // Gearbox(不确定,猜的)
        codeComp.put("0x06","Guides");
        codeComp.put("0x07","Car");
        codeComp.put("0x08","WEARwatcher device itself"); // WearWatcher device itself
        codeComp.put("0x09","Suspension means"); // Suspensions means

        //1 表示OK,无告警
        alarmCode.put("CRITICAL",6);
        alarmCode.put("ERROR",5);
        alarmCode.put("WARNING",4);
        alarmCode.put("INFO",3);
        alarmCode.put("DEBUG",2);
    }
    public static final String[] raisedbys= {"limit exceedance at daily average calculation",
            "curve shape change at daily average",
            "limit exceedance at monthly average calculation ",
            "curve shape change at monthly average",
            "limit exceedance at single ride"
    };

}
