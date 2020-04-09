package com.kaiyun.io.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @program: trafficdataserver
 * @description:
 * @author: Chonghao Zhong
 * @create: 2020-03-30 10:06
 **/
public class VehicleLocationUtils {
    public static String getImei() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String gpsNum = "" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1);
        Random r = new Random();
        gpsNum += r.nextInt(100000);
        return gpsNum;
    }

    public static String getUid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
