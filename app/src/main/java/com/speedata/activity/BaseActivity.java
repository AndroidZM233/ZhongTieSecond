package com.speedata.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.speedata.application.CustomerApplication;
import com.speedata.dreamdemo.R;
import com.speedata.utils.Constant;

public class BaseActivity extends Activity {
    private TextView tvTitle;
    private ImageView imageBack;
    public Context mContext;
    public CustomerApplication app;
    public String ProjectID = "";
    public String ProjectName;
    public String PhoneID;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.title_layout);
        mContext = this;
        sharedPreferences=getSharedPreferences("remember",MODE_PRIVATE);
        app = (CustomerApplication) getApplication();
        ProjectID = app.getSharedPreferences().getString(Constant.FIELD_PROJETC, Constant
                .FIELD_DEFAULT_PROJETC);
        ProjectName = app.getSharedPreferences().getString(Constant.PROJECT_NAME, "中铁一局集团公司");
        PhoneID = app.getSharedPreferences().getString(Constant.PHONE_ID, "1");
        Log.d("===pname", ProjectName);

    }

    public void updateProject(){
        ProjectID = app.getSharedPreferences().getString(Constant.FIELD_PROJETC, Constant
                .FIELD_DEFAULT_PROJETC);
        ProjectName = app.getSharedPreferences().getString(Constant.PROJECT_NAME, "中铁一局集团公司");
        PhoneID = app.getSharedPreferences().getString(Constant.PHONE_ID, "1");
    }

    public void setTitle(String title) {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    public ImageView getBackBtn() {
        imageBack = (ImageView) findViewById(R.id.img_back_btn);
        return imageBack;
    }

    public void alertMessage(String msg) {
        new AlertDialog.Builder(mContext).setMessage(msg).show();
    }

    public void showMessage(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
