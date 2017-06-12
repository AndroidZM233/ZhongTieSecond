package com.speedata.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.speedata.adapter.SimpleTreeAdapter;
import com.speedata.application.CustomerApplication;
import com.speedata.bean.StorageInfomation;
import com.speedata.dao.StorageInfomationDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.Constant;
import com.speedata.utils.MyLogger;
import com.zhy.bean.FileBean;
import com.zhy.tree.bean.Node;
import com.zhy.tree.bean.TreeListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/16.
 */
public class LocationDialog extends Dialog implements View.OnClickListener {
    private CallBack callBack;
    private Context mContext;
    private MyLogger logger = MyLogger.jLog();
    private CustomerApplication app;

    private Context dialog;

    public LocationDialog(Context context, CallBack callBack) {
        super(context);
        this.callBack = callBack;
        mContext = context;
        app = (CustomerApplication) mContext.getApplicationContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
//        dialog = this;
    }

    private List<FileBean> mDatas = new ArrayList<FileBean>();
    private ListView mTree;
    private TreeListViewAdapter mAdapter;

    private void initUI() {
        setContentView(R.layout.dialog_location);
        InitData();
        mTree = (ListView) findViewById(R.id.id_tree);
        try {
            mAdapter = new SimpleTreeAdapter<FileBean>(mTree, mContext, mDatas, 0);

            mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    if (node.isLeaf()) {
//                        Toast.makeText(mContext, node.getName() + node.getCKNode(),
//                                Toast.LENGTH_SHORT).show();
                        callBack.callBack(node.getCKNode());
                        LocationDialog.this.dismiss();
                    }
                }

            });

            mTree.setAdapter(mAdapter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private StorageInfomationDao storageInfomationDao;

    private int _offset = 0;

    private void InitData() {
        storageInfomationDao = new StorageInfomationDao(mContext);

//        List<StorageInfomation> listEnd = storageInfomationDao.imRawQuery("select * from " +
//                "storageInfomation where IsLeaf=\"true\"", null, StorageInfomation.class);

        //4个长度为一个节点    以projectid为起始

        List<StorageInfomation> list = storageInfomationDao.
                imRawQuery
                        ("select * from storageInfomation where length(CKNodebh) = " + (app
                                        .getSharedPreferences().getString(Constant.FIELD_PROJETC,
                                                "00001").length() + 4), null,
                                StorageInfomation.class);
        logger.d("=======list.size()=" + list.size());
        for (int i = 0; i < list.size(); i++) {
            StorageInfomation storageInfomation = list.get(i);
            mDatas.add(new FileBean(i + 1, 0, list.get(i).getCKName()));
            String node = storageInfomation.getCKNodebh();
            query(node, 1 + i, list.size() + _offset);
            _offset = list.size();
        }
//        StorageInfomation storageInfomation = list.get(0);
//        mDatas.add(new FileBean(1, 0, list.get(0).getCKName(),list.get(0).getCKNodebh()));
//        String node = storageInfomation.getCKNodebh();
//        query(node, 1 + 0);
//
//         storageInfomation = list.get(1);
//        mDatas.add(new FileBean(2, 0, list.get(1).getCKName(),list.get(1).getCKNodebh()));
//         node = storageInfomation.getCKNodebh();
//        query(node, 2 + 0);
    }

    private List<StorageInfomation> query(String data, int pos, int offset) {
        List<StorageInfomation> infor;
        offset += _offset;
        infor = storageInfomationDao.imRawQuery("select * from storageInfomation where length" +
                "(CKNodebh) = " + (data.length() + 4) +
                " and CKNodebh like \"" + data
                + "%\"", null, StorageInfomation.class);
        if (infor.size() == 0) {
            return null;
        }
        logger.d("=======infor.size()=" + infor.size());
        for (int i = 0; i < infor.size(); i++) {
            int id = i + pos + 1 + offset;
            int parentid = pos;
            mDatas.add(new FileBean(id, parentid, infor.get(i).getCKName(), infor
                    .get(i)
                    .getCKNodebh() + ""));
            logger.d("=======id=" + id + "  parentid=" + parentid + " CKNode" + infor.get(i)
                    .getCKNodebh());
            if (!infor.get(i).isLeaf()) {
                query(infor.get(i).getCKNodebh(), id, infor.size());
            }
        }
        return infor;
    }

    @Override
    public void onClick(View v) {
    }

    public interface CallBack {
        public void callBack(String CKNode);
    }
}
