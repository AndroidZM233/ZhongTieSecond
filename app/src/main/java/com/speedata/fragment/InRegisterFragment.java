package com.speedata.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.speedata.activity.base.IBaseScanFragment;
import com.speedata.activity.print.PrintTab;
import com.speedata.adapter.CommonAdapter;
import com.speedata.adapter.ViewHolder;
import com.speedata.application.CustomerApplication;
import com.speedata.bean.CommonMaterialInfo;
import com.speedata.bean.CommonSupplier;
import com.speedata.bean.InRegister;
import com.speedata.bean.MyDateAndTime;
import com.speedata.bean.StoreBean;
import com.speedata.dao.CommonFormDao;
import com.speedata.dao.CommonMaterialInfoDao;
import com.speedata.dao.CommonSupplierDao;
import com.speedata.dao.InRegisterDao;
import com.speedata.dao.StoreDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.MyLogger;
import com.speedata.utils.ScanUtil;
import com.speedata.view.MetrailTypeDialog;
import com.speedata.view.SelectDateDialog;
import com.zhy.bean.FileBean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2016/4/6.
 */
public class InRegisterFragment extends BaseFragment implements IBaseScanFragment, View.OnClickListener, View
        .OnTouchListener {
    private ImageView imageBack;
    private CommonSupplierDao commonSupplierDao;
    InRegister inRegister = new InRegister();
    private CustomerApplication app;
    private CommonMaterialInfoDao commonMaterialInfoDao;
    private String quantity;
    private String bookPrice;
//    private Spinner spinnerSupper;
//    private SearchView supperSV,materialSV;
    private AutoCompleteTextView actv1,actv2;
    private ArrayAdapter<String> supperAdapter;
    private String[] suppers;
    private EditText edv_Quantity, edvBookPrice, edvPrintNumber, edv_Remark;
    private TextView edv_CKNodebh, edv_Metrail, tv_RequisitionDate,
            tvProjectID, tvInfoCode, tvInfoModel, tvInfoUnit;
    private Button btnRegister;
    private List<CommonSupplier> supperList;
    private StoreDao storeDao;
    private CommonFormDao commonFormDao;
    private InRegisterDao inRegisterDao;
    private CommonMaterialInfo commonMaterialInfo;
//    private final int SUCCESS=0;
//
//    Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case SUCCESS:
//                    break;
//            }
//        }
//    };
    private Boolean finish=false;
    private AlertDialog alertDialog;
    private static final int NEWDIALOG=0;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case NEWDIALOG:
                    Object obj = msg.obj;
                    edv_Metrail.setText(obj.toString());
                    logger.d("===materialList=" + materialList.size());
                    if (materialList.size() <= 0) {
                        Toast.makeText(mActivity, "ERROR", Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }
                    if (materialList.size()>1){
                        ListView listView=new ListView(mActivity);
                        AlertDialog.Builder builder=new AlertDialog.Builder(mActivity);
                        builder.setTitle("规格型号选择");
                        builder.setView(listView);
                        builder.setCancelable(false);
                        final AlertDialog alertDialog=builder.create();
                        alertDialog.show();
                        CommonAdapter commonAdapter=new CommonAdapter(mActivity,materialList,
                                R.layout.act_in_lv_content) {
                            @Override
                            public void convert(ViewHolder helper, Object item, int position) {
//                                helper.setText(R.id.lv_content_tvInfoName,
//                                        materialList.get(position).getInfoName());
                                helper.setText(R.id.lv_content_tv,materialList.get(position).getInfoModel());
                            }
                        };
                        listView.setAdapter(commonAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                commonMaterialInfo = materialList.get(position);
                                printInfoName = commonMaterialInfo.getInfoName();
                                infoClassName=commonMaterialInfo.getInfoClassName();
                                tvInfoUnit.setText(commonMaterialInfo.getInfoUnit());
                                tvInfoCode.setText(commonMaterialInfo.getInfoCode());
                                tvInfoModel.setText(commonMaterialInfo.getInfoModel());
                                tvProjectID.setText(newProjectID);
                                alertDialog.cancel();
                            }
                        });
                    }
                    if (materialList.size()==1){
                        commonMaterialInfo = materialList.get(0);
                        printInfoName = commonMaterialInfo.getInfoName();
                        infoClassName=commonMaterialInfo.getInfoClassName();
                        tvInfoUnit.setText(commonMaterialInfo.getInfoUnit());
                        tvInfoCode.setText(commonMaterialInfo.getInfoCode());
                        tvInfoModel.setText(commonMaterialInfo.getInfoModel());
                        tvProjectID.setText(newProjectID);
                    }
                    edv_Quantity.setFocusable(true);
                    edv_Quantity.setFocusableInTouchMode(true);
                    edv_Quantity.requestFocus();
                    break;
            }
        }
    };
    private List<CommonMaterialInfo> materialList;
    private String printInfoName;
    private String infoClassName;
    private SQLiteDatabase sqlite;
    private String[] trainColumns;
    private String[] trainColumns1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        storeDao = new StoreDao(mActivity);
        commonFormDao = new CommonFormDao(mActivity);
        app = (CustomerApplication) mActivity.getApplication();
        storeBean = new StoreBean();

        AlertDialog.Builder builder=new AlertDialog.Builder(mActivity);
        builder.setMessage("数据过大，正在拼命加载中...");
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
        InitData();

    }

    @Override
    public int setFragmentLayout() {
        return R.layout.fragment_in_register;
    }

    @Override
    public void findById(View view) {
        if (commonSupplierDao == null) {
            commonSupplierDao = new CommonSupplierDao(mActivity);
        }
        tvInfoCode = (TextView) view.findViewById(R.id.tv_InfoCode);
        tvInfoModel = (TextView) view.findViewById(R.id.tv_InfoModel);
        tvInfoUnit = (TextView) view.findViewById(R.id.tv_InfoUnit);
        tvProjectID = (TextView) view.findViewById(R.id.tv_ProjectID);
        edv_Metrail = (TextView) view.findViewById(R.id.edv_Metrail);
        edvBookPrice = (EditText) view.findViewById(R.id.edv_BookPrice);
        edvPrintNumber = (EditText) view.findViewById(R.id.edv_printNumber);
        edv_Metrail.setOnClickListener(this);
        edv_Quantity = (EditText) view.findViewById(R.id.edv_Quantity);
        edv_Quantity.setFocusable(true);//这个和下面的这个命令必须要设置了，才能监听back事件。
        edv_Quantity.setFocusableInTouchMode(true);
        edv_Quantity.setOnKeyListener(onKeyListener);
        tv_RequisitionDate = (TextView) view.findViewById(R.id.edv_RequisitionDate);
        edv_Remark = (EditText) view.findViewById(R.id.edv_Remark);
        edv_Remark.setFocusable(true);
        edv_Remark.setFocusableInTouchMode(true);
        edv_Remark.setOnKeyListener(onKeyListener);
        btnRegister = (Button) view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

//        supperList = commonSupplierDao.imQueryList("ProjectID=?", new
//                String[]{mActivity.ProjectID});//查询所有供应商
        supperList = commonSupplierDao.imQueryList();
        if (supperList == null || supperList.size() == 0) {
            Toast.makeText(mActivity, "无供应商信息，请先进行下载!", Toast.LENGTH_SHORT).show();
        }
        suppers = new String[supperList.size()];
        for (int i = 0; i < supperList.size(); i++) {
            suppers[i] = supperList.get(i).getSupplierName();
        }

        imageBack = mActivity.getBackBtn();
        imageBack.setOnClickListener(this);
//        spinnerSupper = (Spinner) view.findViewById(R.id.sp_supplier);
        supperAdapter = new ArrayAdapter<String>(mActivity, android.R.layout
                .simple_dropdown_item_1line, suppers);
//        spinnerSupper.setAdapter(supperAdapter);
//        spinnerSupper.setSelection(mActivity.sharedPreferences.getInt("inSelection", 0));
//        spinnerSupper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mActivity.sharedPreferences.edit().putInt("inSelection", position).commit();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        supperSV= (SearchView) view.findViewById(R.id.sv_Supper);
//        supperSV.setIconifiedByDefault(false);
//        supperSV.setSubmitButtonEnabled(false);
//        supperSV.setQueryRefinementEnabled(true);
//        supperSV.setQueryHint("供应商");
//
//        materialSV= (SearchView) view.findViewById(R.id.sv_Metrail);
//        materialSV.setIconifiedByDefault(false);
//        materialSV.setSubmitButtonEnabled(false);
//        materialSV.setQueryHint("材料");
//        final Cursor[] cursor = {null};
//        SQLiteDatabase db = SQLiteDatabase.openDatabase(
//                "data/data/com.speedata.zhongtiesecond/databases/zhongtiesecond.db", null,2);
//        cursor[0] =db.rawQuery("select SupplierName AS _id from commonSupplier",null);
//        final SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(mActivity,
//                R.layout.mytextview, cursor[0], new String[] { "_id" },
//                new int[] { R.id.textview });
//        supperSV.setSuggestionsAdapter(cursorAdapter);
//        supperSV.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
//            @Override
//            public boolean onSuggestionSelect(int position) {
//                return false;
//            }
//
//            @Override
//            public boolean onSuggestionClick(int position) {
////                supperSV.setQuery(supperList.get(position).getSupplierName(),false);
//                spinnerSupper.setAdapter(cursorAdapter);
//                spinnerSupper.setSelection(position);
//
//                supperSV.setFocusable(false);
//                supperSV.setFocusableInTouchMode(false);
//                supperSV.requestFocus();
//                return false;
//            }
//        });
//        supperSV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                SQLiteDatabase db = SQLiteDatabase.openDatabase(
//                        "data/data/com.speedata.zhongtiesecond/databases/zhongtiesecond.db", null,2);
//                cursor[0] =db.rawQuery("select SupplierName AS _id from commonSupplier where _id like ?%"
//                        ,new String[]{newText});
//                cursorAdapter.notifyDataSetChanged();
//                return false;
//            }
//        });
//
//        Cursor cursorM=null;
//        cursorM=db.rawQuery("select distinct InfoClassName AS _id from commonMaterialInfo",null);
//        SimpleCursorAdapter cursorAdapterM = new SimpleCursorAdapter(mActivity,
//                R.layout.mytextview, cursorM, new String[] { "_id" },
//                new int[] { R.id.textview });
//        materialSV.setSuggestionsAdapter(cursorAdapterM);
//        materialSV.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
//            @Override
//            public boolean onSuggestionSelect(int position) {
//                return false;
//            }
//
//            @Override
//            public boolean onSuggestionClick(int position) {
//                return false;
//            }
//        });
        actv1= (AutoCompleteTextView) view.findViewById(R.id.actv1);
        actv2= (AutoCompleteTextView) view.findViewById(R.id.actv2);
        sqlite = mActivity.openOrCreateDatabase("zhongtiesecond.db", 0, null);
        List<CommonSupplier> commonSuppliers = commonSupplierDao
                .imRawQuery("select SupplierName from commonSupplier"
                , null, CommonSupplier.class);
        String[] supperStr=new String[commonSuppliers.size()];
        for (int i = 0; i < commonSuppliers.size(); i++) {
            supperStr[i]=commonSuppliers.get(i).getSupplierName();
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(mActivity,android.R.layout.simple_list_item_1,
                supperStr);
//        trainColumns1 = new String[] {"distinct SupplierName", "SupplierNM as _id" };
//        trainAdpter1 trainadpter1 = new trainAdpter1(mActivity, null, 0);
        actv1.setAdapter(arrayAdapter);
        actv1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actv1.showDropDown();
                return false;
            }
        });

//        List<CommonMaterialInfo> commonMaterialInfos = commonMaterialInfoDao
//                .imRawQuery("select distinct InfoName from commonMaterialInfo"
//                , null, CommonMaterialInfo.class);
//        String[] materialStr=new String[commonMaterialInfos.size()];
//        for (int i = 0; i < commonMaterialInfos.size(); i++) {
//            materialStr[i]=commonMaterialInfos.get(i).getInfoName();
//        }
//        ArrayAdapter arrayAdapter2=new ArrayAdapter(mActivity,android.R.layout.simple_list_item_1,
//                materialStr);
//        actv2.setAdapter(arrayAdapter2);

        trainColumns = new String[] {"distinct InfoName", "InfoClassNodebh as _id" };
        trainAdpter trainadpter = new trainAdpter(mActivity, null, 0);
        actv2.setAdapter(trainadpter);
        mActivity.setTitle("进料登记");

        actv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ClassNodebh = actv2.getText().toString();
                materialList = commonMaterialInfoDao
                        .imRawQuery("select * " +
                                        "from commonMaterialInfo where InfoName = ?"
                                , new String[]{ClassNodebh}, CommonMaterialInfo.class);
                Message message=new Message();
                message.what=NEWDIALOG;
                message.obj=ClassNodebh;
                handler.sendMessage(message);
            }
        });
//        actv2.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                actv2.showDropDown();
//                return false;
//            }
//        });

        tv_RequisitionDate.setOnClickListener(this);
        tv_RequisitionDate.setText(MyDateAndTime.getTimeString("yyyy-MM-dd"));
    }
    private class trainAdpter extends CursorAdapter {
        private int columnIndex;

        public trainAdpter(Context context, Cursor c, int col) {
            super(context, c);
            this.columnIndex = col;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            final TextView view = (TextView) inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ((TextView) view).setText(cursor.getString(columnIndex));
        }

        @Override
        public String convertToString(Cursor cursor) {
            Log.i("info", " convertToString ");
            return cursor.getString(columnIndex);
        }

        @Override
        public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
            if (constraint != null) {
                String selection = "InfoName like \'%" + constraint.toString() + "%\' limit 100";
                System.out.println(selection);
                return sqlite.query("commonMaterialInfo", trainColumns, selection, null, null, null, null);
                //从表train查询
            } else {
                return null;
            }
        }
    }

//    private class trainAdpter1 extends CursorAdapter {
//        private int columnIndex;
//
//        public trainAdpter1(Context context, Cursor c, int col) {
//            super(context, c);
//            this.columnIndex = col;
//        }
//
//        @Override
//        public View newView(Context context, Cursor cursor, ViewGroup parent) {
//            final LayoutInflater inflater = LayoutInflater.from(context);
//            final TextView view = (TextView) inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
//            return view;
//        }
//
//        @Override
//        public void bindView(View view, Context context, Cursor cursor) {
//            ((TextView) view).setText(cursor.getString(columnIndex));
//        }
//
//        @Override
//        public String convertToString(Cursor cursor) {
//            Log.i("info", " convertToString ");
//            return cursor.getString(columnIndex);
//        }
//
//        @Override
//        public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
//            if (constraint != null) {
//                String selection = "SupplierName like \'%" + constraint.toString() + "%\' limit 100";
//                System.out.println(selection);
//                return sqlite.query("commonSupplier", trainColumns1, selection, null, null, null, null);
//                //从表train查询
//            } else {
//                return null;
//            }
//        }
//    }


    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction()==KeyEvent.ACTION_DOWN){
                if (keyCode==KeyEvent.KEYCODE_F4){
                    edv_Quantity.setText("");
                    edv_Remark.setText("");
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    public void onClick(View v) {
        if (v == edv_Metrail) {
            MetrailTypeDialog metrailTypeDialog = new MetrailTypeDialog(mActivity, mDatas,new
                    MetrailTypeDialog.CallBack() {
                        @Override
                        public void callBack(final String ClassNodebh) {

                            logger.d("===mater=" + ClassNodebh);
                            //TODO 查询条件应改为InfoClassNodebh
                            commonMaterialInfoDao = new CommonMaterialInfoDao(mActivity);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    materialList = commonMaterialInfoDao
                                            .imRawQuery("select * " +
                                                            "from commonMaterialInfo where InfoName = ?"
                                                    , new String[]{ClassNodebh}, CommonMaterialInfo.class);
                                    Message message=new Message();
                                    message.what=NEWDIALOG;
                                    message.obj=ClassNodebh;
                                    handler.sendMessage(message);
                                }
                            }).start();
                        }
                    });
            metrailTypeDialog.setTitle("请选择材料类别");
            metrailTypeDialog.show();
        }

        if (tv_RequisitionDate == v) {
            SelectDateDialog dateDialog = new SelectDateDialog(mActivity, new SelectDateDialog
                    .ShowDate() {
                @Override
                public void showDate(String time) {
                    tv_RequisitionDate.setText(time);
                }
            }, tv_RequisitionDate.getText().toString());
            dateDialog.show();
        } else if (v == imageBack) {
            openFragment(new InFragment());
        } else if (v == btnRegister) {
            inRegister();
        }
    }
    private List<CommonMaterialInfo> list2;
    private List<CommonMaterialInfo> list3;
    private List<CommonMaterialInfo> list4;
    private List<FileBean> mDatas = new ArrayList<FileBean>();
    private void InitData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                commonMaterialInfoDao = new CommonMaterialInfoDao(mActivity.context);
                List<CommonMaterialInfo> list1 = commonMaterialInfoDao.imRawQuery("select distinct " +
                                "FirstClassName from commonMaterialInfo"
                        , null, CommonMaterialInfo.class);
                logger.d("=======list.size()=" + list1.size());
                //循环遍历一、二、三级类别名称
                int count = 0;
                int second = 0;
                int third = 0;
                int fourth;
                for (int i = 0; i < list1.size(); i++) {
                    count++;
                    mDatas.add(new FileBean(count, 0, list1.get(i).getFirstClassName(),list1.get(i).getInfoClassNodebh()));
//            mDatas.add(new FileBean(count, 0, list1.get(i).getFirstClassName()));
                    list2 = commonMaterialInfoDao.imRawQuery("select distinct SecondClassName from " +
                                    "commonMaterialInfo where FirstClassName like ?"
                            , new String[]{list1.get(i).getFirstClassName()}, CommonMaterialInfo.class);
                    second = count;
                    for (int j = 0; j < list2.size(); j++) {
                        count++;
                        mDatas.add(new FileBean(count, second, list2.get(j).getSecondClassName(),list2.get(j).getInfoClassNodebh()));
//                mDatas.add(new FileBean(count, second, list2.get(j).getSecondClassName()));
                        list3 = commonMaterialInfoDao.imRawQuery("select distinct InfoClassName from " +
                                        "commonMaterialInfo where SecondClassName like ?"
                                , new String[]{list2.get(j).getSecondClassName()}, CommonMaterialInfo
                                        .class);
                        logger.d("=======list3.size()=" + list3.size());
                        third = count;
                        for (int k = 0; k < list3.size(); k++) {
                            count++;
                            mDatas.add(new FileBean(count, third, list3.get(k).getInfoClassName()
                                    ,list3.get(k).getInfoClassNodebh()));
                            list4 = commonMaterialInfoDao.imRawQuery("select distinct InfoName from " +
                                            "commonMaterialInfo where InfoClassName like ?"
                                    , new String[]{list3.get(k).getInfoClassName()}, CommonMaterialInfo
                                            .class);
                            if (list4.size()==1&&list4.get(0).getInfoName().equals(list3.get(k).getInfoClassName())){

                            }else {
                                fourth = count;
                                for (int m = 0; m < list4.size(); m++) {
                                    count++;
                                    mDatas.add(new FileBean(count, fourth, list4.get(m)
                                            .getInfoName(),list4.get(m).getInfoClassNodebh()));
                                }
                            }

                        }
                    }
                }
                alertDialog.cancel();

            }
        }).start();


    }
    private void inRegister() {
        String price = edvBookPrice.getText().toString();
        if (!TextUtils.isEmpty(price)) {
            bookPrice = String.format("%.8f", Double.parseDouble(price));
        }

        if (edv_Metrail == null) {
            Toast.makeText(mActivity, "请选择登记物资", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edv_Quantity.getText().toString().equals("")) {
            Toast.makeText(mActivity, "请输入数量", Toast.LENGTH_SHORT).show();
            return;
        }
        if (supperList == null || supperList.size() == 0) {
            Toast.makeText(mActivity, "无供应商信息，请先进行下载!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (commonMaterialInfo == null) {
            commonMaterialInfo = new CommonMaterialInfo();
        }
        if (inRegisterDao == null) {
            inRegisterDao = new InRegisterDao(mActivity);
        }

        String barcode = MyDateAndTime.getBatchCode(mActivity);
        UUID uuid = UUID.randomUUID();
        inRegister.setItemNM(String.valueOf(uuid));
        inRegister.setInfoModel(tvInfoModel.getText().toString());
        inRegister.setInfoUnit(tvInfoUnit.getText().toString());
        inRegister.setInfoName(printInfoName);
        inRegister.setProjectID(tvProjectID.getText().toString());
        inRegister.setRemark(edv_Remark.getText().toString());
        inRegister.setInfoCode(tvInfoCode.getText().toString());
        inRegister.setInfoClassName(commonMaterialInfo.getInfoClassName());
        inRegister.setInfoClassNodebh(commonMaterialInfo.getInfoClassNodebh());
        inRegister.setFirstClassName(commonMaterialInfo.getFirstClassName());
        inRegister.setSecondClassName(commonMaterialInfo.getSecondClassName());
        if (!TextUtils.isEmpty(bookPrice)) {
            inRegister.setBookPrice(Double.parseDouble(bookPrice));
        }
        inRegister.setBarCode(barcode);//批次码  时间戳
        inRegister.setRequisitionDate(tv_RequisitionDate.getText().toString());//TODO 进料日期
//        inRegister.setSupplierName(supperList.get(spinnerSupper
//                .getSelectedItemPosition()).getSupplierName());//供应商名称

        String supplierName = actv1.getText().toString();
        if (TextUtils.isEmpty(supplierName)){
            showToast("请选择供应商");
            return;
        }
        inRegister.setSupplierName(supplierName);
        List<CommonSupplier> commonSuppliers = commonSupplierDao
                .imRawQuery("select SupplierNM from commonSupplier where SupplierName = ?"
                , new String[]{supplierName}, CommonSupplier.class);
        String supplierNM = null;
        if (commonSuppliers.size()==0){
            showToast("供应商信息有误");
            return;
        }
        try {
            supplierNM = commonSuppliers.get(0).getSupplierNM();
            inRegister.setSupplierNM(supplierNM);//供应商编码
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String SupplierNM = supperList.get
//                (spinnerSupper
//                        .getSelectedItemPosition()).getSupplierNM();
        logger.d("====IMEI====" + app.getIMEI());
        quantity = String.format("%.4f", Double.parseDouble(edv_Quantity.getText().toString()));
        double count = Double.parseDouble(quantity);
        inRegister.setQuantity(count);
        logger.d("===inRegister=" + inRegister);

        storeBean.setMakerDate(MyDateAndTime.getTimeStringYMD());
        storeBean.setBarCode(barcode);//批次码  时间戳
        storeBean.setRequisitionDate(tv_RequisitionDate.getText().toString());//TODO 进料日期
        storeBean.setSupplierName(supplierName);//供应商名称
        storeBean.setQuantity(count);
        storeBean.setSupplierNM(supplierNM);
        storeBean.setInfoCode(tvInfoCode.getText().toString());
        storeBean.setInfoModel(tvInfoModel.getText().toString());
        storeBean.setInfoUnit(tvInfoUnit.getText().toString());
        storeBean.setInfoName(printInfoName);
        storeBean.setProjectID(tvProjectID.getText().toString());
        storeBean.setBookPrice(inRegister.getBookPrice());
        storeBean.setInfoClassNodebh(commonMaterialInfo.getInfoClassNodebh());
        storeBean.setInfoClassName(commonMaterialInfo.getInfoClassName());
        storeBean.setFirstClassName(commonMaterialInfo.getFirstClassName());
        storeBean.setSecondClassName(commonMaterialInfo.getSecondClassName());
        storeBean.setRemark(inRegister.getRemark());
        Toast.makeText(mActivity, "入库成功", Toast.LENGTH_LONG).show();
        inRegisterDao.imInsert(inRegister, false);
        storeDao.imInsert(storeBean);
        printLable(barcode);
        clearUI();

    }

    private void clearUI() {
        tvInfoCode.setText("");
        tvInfoUnit.setText("");
        tvInfoModel.setText("");
        tvProjectID.setText("");
        edv_Remark.setText("");
        edvBookPrice.setText("");
        edv_Metrail.setText("");
        edv_Quantity.setText("");
    }

    private void printLable(String barcode) {
        //TODO 打印
        String temp = edvPrintNumber.getText().toString();
        int printCount = 1;
        if ("".equalsIgnoreCase(temp)) {
            return;
        } else {
            printCount = Integer.parseInt
                    (temp);
            if (printCount <= 0) {
                return;
            }
        }

        String name = printInfoName;
        String spec = tvInfoModel.getText().toString();
        String unit=tvInfoUnit.getText().toString();
        String count = quantity+" 单位："+unit;
        resetView();
//        mActivity.printInLable(mActivity.ProjectName, spinnerSupper.getSelectedItem()
//                .toString(), name, spec, count, barcode, printCount);
        PrintTab printTab=new PrintTab();
        printTab.printInTabCPCL(mActivity.context,mActivity.ProjectName, actv1.getText().toString()
                , name, spec, count, barcode, printCount);

    }

    private void resetView() {
        edv_Quantity.setText("");
        edv_Remark.setText("");
    }

    private MyLogger logger = MyLogger.jLog();

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }


    private ScanUtil scanUtil;

    @Override
    public void onResume() {
        super.onResume();
        scanUtil = new ScanUtil(mActivity);
        scanUtil.setOnScanListener(new ScanUtil.OnScanListener() {

            @Override
            public void getBarcode(String barcode) {
                onGetBarcode(barcode);
            }
        });
    }

    @Override
    public void onPause() {
        scanUtil.stopScan();
        super.onPause();
    }


    //TODO 获取条码
    private String mBarcode;
    private StoreBean storeBean;
    private List<StoreBean> tempStore;

    @Override
    public void onGetBarcode(String barcode) {
        mBarcode = barcode.replace("\n", "");
        mBarcode = mBarcode.replace("\r", "");
        mBarcode = mBarcode.replace("\u0000","");
        try {
            tempStore = storeDao.imQueryList("BarCode=?", new String[]{mBarcode});
            if (tempStore.size() > 0) {
                storeBean = tempStore.get(0);
                setUI(storeBean);
            }
        } catch (Exception e) {
            Toast.makeText(mActivity, "条码不匹配", Toast.LENGTH_SHORT).show();
        }


    }

    private Double Quantity = 0.0;

    private void setUI(StoreBean storeBean) {
        edv_Metrail.setText(storeBean.getInfoName());
        tvInfoCode.setText(storeBean.getInfoCode());
        tvInfoModel.setText(storeBean.getInfoModel());
        tvInfoUnit.setText(storeBean.getInfoUnit());
        tvProjectID.setText(storeBean.getProjectID());
        edvBookPrice.setText(storeBean.getBookPrice() + "");

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        edvPrintNumber.setText("1");
//        edv_Quantity.setText("");
//        edv_Remark.setText("");
//        return super.onKeyDown(keyCode, event);
//    }


}
