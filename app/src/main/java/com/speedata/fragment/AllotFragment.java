package com.speedata.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.speedata.activity.allot.AllotAgainBarCodeActivity;
import com.speedata.activity.allot.AllotDownLoadActivity;
import com.speedata.activity.allot.AllotRegisterActivity;
import com.speedata.activity.allot.AllotUploadActivity;
import com.speedata.adapter.UploadGridViewAdapter;
import com.speedata.dao.InRegisterDao;
import com.speedata.dao.OutRegisterDao;
import com.speedata.dao.StoreDao;
import com.speedata.dreamdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/7.
 */
public class AllotFragment extends BaseFragment {
    private GridView mGridView;
    private ImageView mBack;
    private UploadGridViewAdapter adapter;
    private List<String> list = new ArrayList<String>();
    private List<Integer> imgList = new ArrayList<Integer>();
    private InRegisterDao inRegisterDao;
    private StoreDao storeDao;
    private OutRegisterDao outRegisterDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initList();
        inRegisterDao = new InRegisterDao(mActivity);
        storeDao = new StoreDao(mActivity);
        outRegisterDao = new OutRegisterDao(mActivity);
    }
    public void initList() {
        list.add("");//发料登记
        list.add("");//上传
        list.add("");//下载
        list.add("");
        imgList.add(R.drawable.regist);
        imgList.add(R.drawable.icon_arrow_up);
        imgList.add(R.drawable.icon_down);
        imgList.add(R.drawable.icon_computer);
    }
    @Override
    public int setFragmentLayout() {
        return R.layout.fragment_out;
    }

    @Override
    public void findById(View view) {
        mGridView = (GridView) view.findViewById(R.id.upload_grid);
        adapter = new UploadGridViewAdapter(list, mActivity, imgList);
        mGridView.setAdapter(adapter);
        mBack = mActivity.getBackBtn();
        mActivity.setTitle("调拨");
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
                        // 进入发料登记界面：
                        openFragment(new AllotRegisterFragment());
                        break;
                    case 1:
                        // 弹出提示框
                        startActivity(new Intent(mActivity, AllotUploadActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(mActivity, AllotDownLoadActivity.class));
                        break;
                    case 3:
                        openFragment(new AllotAgainBarCodeFragment());
                        break;
                }
            }
        });
    }
}
