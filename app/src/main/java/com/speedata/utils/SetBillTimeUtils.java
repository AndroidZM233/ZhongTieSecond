package com.speedata.utils;

import com.speedata.bean.MyDateAndTime;

/**
 * Created by Administrator on 2016/3/10.
 */
public class SetBillTimeUtils {
    MyDateAndTime myDateAndTime = MyDateAndTime.praseDateAndTime(MyDateAndTime.getTimeString
            ("yyyy-MM-dd " +
                    "HH:mm:ss"));
    int getMonth = myDateAndTime.getMonth();
    String startTime = "";
    String endTime = "";
    private int mouth;

    public String billtimeAdd1() {
        mouth = getMonth + 1;
        if (mouth > 12) {
            return startTime = myDateAndTime.getYear() + 1 + "-01-26";
        } else if (mouth < 10) {
            return startTime = myDateAndTime.getYear() + "-0" + mouth + "-26";
        }
        return startTime = myDateAndTime.getYear() + "-" + mouth + "-26";
    }

    public String billtimeAdd2() {
        mouth = getMonth + 2;
        if (mouth > 13) {
            return endTime = myDateAndTime.getYear() + 1 + "-02-25";
        } else if (mouth > 12) {
            return endTime = myDateAndTime.getYear() + 1 + "-01-25";
        } else if (mouth < 10) {
            return endTime = myDateAndTime.getYear() + "-0" + mouth + "-25";
        }
        return endTime = myDateAndTime.getYear() + "-" + mouth + "-25";
    }

    public String billtimeSub1() {
        mouth = getMonth - 1;
        if (mouth == 0) {
            return startTime = myDateAndTime.getYear() - 1 + "-12-26";
        } else if (mouth > 0 && mouth < 10) {
            return startTime = myDateAndTime.getYear() + "-0" + mouth + "-26";
        }
        return startTime = myDateAndTime.getYear() + "-" + mouth + "-26";
    }
    public String billtime(){
        mouth=getMonth;
        if (mouth<10){
            return endTime = myDateAndTime.getYear() + "-0" + mouth + "-25";
        }
        return endTime = myDateAndTime.getYear() + "-" + mouth + "-25";
    }

}
