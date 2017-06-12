package com.speedata.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.speedata.activity.MainActivity;
import com.speedata.dreamdemo.R;
import com.speedata.utils.Constant;
import com.speedata.utils.MyExceptionHandler;

import java.lang.reflect.Method;



public abstract class BaseFragment extends Fragment implements IBaseFragment {
    private static final int containerViewId = R.id.container;
    private static Toast mToast;

    /**
     * 基础主界面
     **/
    public MainActivity mActivity;
    public String newProjectID;


    @Override
    public void onAttach(Activity activity) {
        mActivity = (MainActivity) getActivity();
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        MyExceptionHandler.getInstanceMyExceptionHandler()
                .init(mActivity);
        newProjectID = mActivity.app
                .getSharedPreferences().getString(Constant.FIELD_PROJETC, Constant
                        .FIELD_DEFAULT_PROJETC);
        return inflater.inflate(setFragmentLayout(), container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findById(this.getView());
    }

    /**
     * 打开新的Fragment
     * @param fragment
     */
    public void openFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(containerViewId, fragment);
        transaction.addToBackStack(null);
        // 提交事物
        transaction.commit();
    }

    /**
     * 关闭Fragment
     */
    public void closeFragment() {
        FragmentManager fm = getFragmentManager();
        fm.popBackStack();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        if (mToast != null) {
            mToast.cancel();
        }
        super.onDestroyView();
    }

    /**
     * 显示Toast
     *
     * @param msg
     */
    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT);// getApplicationContext()
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }

        mToast.show();
    }

    /**
     * 隐藏隐藏输入框焦点
     *
     * @param editText
     */
    public void HideEdInput(EditText editText) {
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(editText, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取当前应用程序的版本号
     */
    public String getVersion() {
        PackageManager pm = mActivity.getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(mActivity.getPackageName(), 0);
            String version = packinfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "版本号错误";
        }
    }
}
