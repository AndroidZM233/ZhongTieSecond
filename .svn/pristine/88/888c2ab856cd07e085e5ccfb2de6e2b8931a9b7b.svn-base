package com.speedata.activity.in;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.speedata.activity.BaseActivity;
import com.speedata.adapter.UploadGridViewAdapter;
import com.speedata.bean.StoreBean;
import com.speedata.dao.InRegisterDao;
import com.speedata.dao.OutRegisterDao;
import com.speedata.dao.StoreDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.MyLogger;

import java.util.ArrayList;
import java.util.List;

public class InActivity extends BaseActivity {

    private GridView mGridView;
    private ImageView mBack;
    private UploadGridViewAdapter adapter;
    private List<String> list = new ArrayList<String>();
    private List<Integer> imgList = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in);
        initList();
        initView();
        inRegisterDao = new InRegisterDao(mContext);
        storeDao = new StoreDao(mContext);
        outRegisterDao = new OutRegisterDao(mContext);
    }

    public void initList() {
        list.add("");//进料登记
        list.add("");//供应商退料
        list.add("");///上传
        list.add("");//下载
        list.add("");//条码补打
        imgList.add(R.drawable.regist);
        imgList.add(R.drawable.icon_reject);
        imgList.add(R.drawable.icon_arrow_up);
        imgList.add(R.drawable.icon_down);
        imgList.add(R.drawable.icon_computer);

    }

    private MyLogger logger = MyLogger.jLog();

    private InRegisterDao inRegisterDao;
    private StoreDao storeDao;
    private OutRegisterDao outRegisterDao;

    public void initView() {
        mGridView = (GridView) findViewById(R.id.upload_grid);
        adapter = new UploadGridViewAdapter(list, InActivity.this, imgList);
        mGridView.setAdapter(adapter);
//        mBack = (ImageView) findViewById(R.id.upload_back_btn);
        mBack = getBackBtn();
        setTitle("进料");
        setListener();
    }

    private void setListener() {
        mBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 跳转到metrailsActivity
                // Intent intent = new Intent()
                finish();
            }
        });
        mGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                switch (position) {
                    case 0:
                        // 进入进料登记界面：
                        startActivity(new Intent(InActivity.this, InRegisterActivity.class));
                        break;
                    case 1:
                        // 进入退料界面
                        startActivity(new Intent(InActivity.this, SupplierRejectActivity.class));
                        break;
                    case 2:
                        // 弹出提示框
                        startActivity(new Intent(InActivity.this, InUpLoadActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(InActivity.this, InDownLoadActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(InActivity.this, AgainBarCodeActivity.class));
                        break;
                }
            }
        });
    }


    private List<StoreBean> storeBeanList = new ArrayList<StoreBean>();

//    @Override
//    public void onFragmentInteraction(Uri uri) {
//
//    }
}
