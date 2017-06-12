package com.speedata.activity.UpAndDown;

import java.util.Calendar;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.speedata.dreamdemo.R;

public class SetDateDialog extends Dialog implements View.OnClickListener {

    private Button sure;
    private DatePicker m_DatePickerStart, mDatePickerEnd;
    private Calendar c;
    private String startTime = "";
    private String endTime = "";
    private int getyear;
    private int getmonth;
    private int getday;
    private ShowDate showDate;
    private String initialDate;

    public SetDateDialog(Context context, ShowDate showDate, String initialDate) {
        super(context, R.style.dialog);
        // TODO Auto-generated constructor stub
        this.showDate = showDate;
        this.initialDate = initialDate;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == sure) {
            // System.out.println(getyear + getmonth + getday);
            // startTime.setText(getyear + getmonth + getday);
            if (endTime.equals("")) {
                endTime = initialDate;
            }
            showDate.showDate(startTime, endTime);
            // finish();
            dismiss();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_dialog);
        sure = (Button) findViewById(R.id.date_button);
        sure.setOnClickListener(this);
        m_DatePickerStart = (DatePicker) findViewById(R.id.datePicker_start);
        mDatePickerEnd = (DatePicker) findViewById(R.id.datePicker_end);

        String[] defaultDate = initialDate.split("-");
        final int year = Integer.parseInt(defaultDate[0]);
        final int month = Integer.parseInt(defaultDate[1]);
        final int day = Integer.parseInt(defaultDate[2]);
        if (month - 2 < 10) {
            startTime = year + "-0" + (month - 2) + "-" + day;
        } else {
            startTime = year + "-" + (month - 2) + "-" + day;
        }

        m_DatePickerStart.init(year, month - 2, day,
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
                        if (dayOfMonth < 10) {
                            startTime = getyear + "-" + temp_date + "-" + "0"
                                    + getday;
                        } else {
                            startTime = getyear + "-" + temp_date + "-" + getday;
                        }
                    }
                });

        mDatePickerEnd.init(year, month - 1, day,
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
                        if (dayOfMonth < 10) {
                            endTime = getyear + "-" + temp_date + "-" + "0"
                                    + getday;
                        } else {
                            endTime = getyear + "-" + temp_date + "-" + getday;
                        }
                    }
                });
    }

}
