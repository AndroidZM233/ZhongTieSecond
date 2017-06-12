package com.speedata.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.speedata.dreamdemo.R;
import com.speedata.utils.Constant;

import java.util.Calendar;

public class SelectDateDialog extends Dialog implements View.OnClickListener {

    private Button sure;
    private DatePicker m_DatePickerStart;
    private Calendar c;
    private String startTime = "";
    private String endTime = "";
    private int getyear;
    private int getmonth;
    private int getday;
    private ShowDate showDate;
    private String initialDate;

    public SelectDateDialog(Context context, ShowDate showDate, String initialDate) {
        super(context, R.style.dialog);
        // TODO Auto-generated constructor stub
        this.showDate = showDate;
        this.initialDate = initialDate;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == sure) {
            if (startTime.equals("")) {
                startTime = initialDate;
            }
            showDate.showDate(startTime);
            dismiss();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_one_date);
        sure = (Button) findViewById(R.id.date_button);
        sure.setOnClickListener(this);
        m_DatePickerStart = (DatePicker) findViewById(R.id.datePicker_start);
        String[] defaultDate = initialDate.split("-");
        final int year = Integer.parseInt(defaultDate[0]);
        final int month = Integer.parseInt(defaultDate[1]);
        final int day = Integer.parseInt(defaultDate[2]);

        m_DatePickerStart.init(year, month - 1, day,
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                        // 当日期更改时，在这里处理
                        // c.set(year, monthOfYear, dayOfMonth);
                        getyear = year;
                        getmonth = monthOfYear + 1;
                        String temp_date;
                        if (getmonth < 10) {
                            temp_date = "0" + Integer.toString(getmonth);
                        } else {
                            temp_date = Integer.toString(getmonth) + "";
                        }
                        getday = dayOfMonth;
                        // System.out.println(getyear + "/" + getmonth + 1 +
                        // "/" + getday);
//                        if(dayOfMonth< Constant.ACCOUNT_DAY){
//                            startTime = getyear + "-" + (getmonth-1) + "-" + "26";
//                                   // + getday;
//                        }else{
//                            startTime = getyear + "-" + temp_date + "-" + "25";
////                                    + getday;
//                        }
                        if (dayOfMonth < 10) {
                            startTime = getyear + "-" + temp_date + "-" + "0"
                                    + getday;
                        } else {
                            startTime = getyear + "-" + temp_date + "-" + getday;
                        }
                    }
                });

    }
    public interface ShowDate {
        public abstract void showDate(String time);
    }

}
