package com.speedata.activity.print;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.speedata.activity.BaseActivity;
import com.speedata.application.CustomerApplication;
import com.speedata.dreamdemo.R;

public class PrintTestActivity extends BaseActivity{
    private CustomerApplication context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_test);
        context=(CustomerApplication)getApplicationContext();
    }

    public void onClick(View v) {
        PrintTab printTab=new PrintTab();
//        printTab.printOutTabCPCL(context,1);
    }


}
