package com.speedata.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.speedata.activity.in.AgainBarCodeActivity;
import com.speedata.activity.in.InDownLoadActivity;
import com.speedata.activity.in.InRegisterActivity;
import com.speedata.activity.in.InUpLoadActivity;
import com.speedata.activity.in.SupplierRejectActivity;
import com.speedata.adapter.UploadGridViewAdapter;
import com.speedata.dao.InRegisterDao;
import com.speedata.dao.OutRegisterDao;
import com.speedata.dao.StoreDao;
import com.speedata.dreamdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class InFragment extends BaseFragment {
    private GridView mGridView;
    private ImageView mBack;
    private UploadGridViewAdapter adapter;
    private List<String> list = new ArrayList<String>();
    private List<Integer> imgList = new ArrayList<Integer>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initList();
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

    @Override
    public int setFragmentLayout() {
        return R.layout.fragment_in;
    }

    @Override
    public void findById(View view) {
        mGridView = (GridView) view.findViewById(R.id.upload_grid);
        adapter = new UploadGridViewAdapter(list, mActivity, imgList);
        mGridView.setAdapter(adapter);
        mBack = mActivity.getBackBtn();
        mActivity.setTitle("进料");
        setListener();
    }
    private void setListener() {
        mBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               openFragment(new OperatorsHomeFragment());
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                switch (position) {
                    case 0:
                        // 进入进料登记界面：
                        openFragment(new InRegisterFragment());
                        break;
                    case 1:
                        // 进入退料界面
                        openFragment(new SupplierRejectFragment());
                        break;
                    case 2:
                        // 弹出提示框
                        startActivity(new Intent(mActivity, InUpLoadActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(mActivity, InDownLoadActivity.class));
                        break;
                    case 4:
                        openFragment(new AgainBarCodeFragment());
                        break;
                }
            }
        });
    }
}
