package com.example.huangjiahao.qianjiangweather.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.huangjiahao.qianjiangweather.R;

/**
 * Created by JH.H on 2017/4/5.
 */

public class CustomDialog {

    private Dialog mDialog;
    private Activity mActivity;

    public CustomDialog(Context context) {
        this.mActivity = (Activity) context;
    }

    /**
     * 创建对话框
     *
     * @return
     */
    private CustomDialog create() {
        create(R.style.Theme_CustomDialog);
        return this;
    }

    /**
     * 根据对话框主题创建对话框
     *
     * @param theme
     */
    public CustomDialog create(int theme) {
        if (mDialog == null && mActivity.isFinishing() == false) {
            mDialog = new Dialog(mActivity, theme);
        }
        return this;
    }

    /**
     * 设置对话框布局
     *
     * @param layoutResID 对话框布局ID
     * @return
     */
    public CustomDialog setContentView(int layoutResID) {
        if (mDialog == null) {
            create();
        }
        mDialog.setContentView(layoutResID);
        return this;
    }

    /**
     * 设置对话框布局，大小自适应，居中显示
     *
     * @param view 对话框布局
     * @return
     */
    public CustomDialog setContentView(View view) {
        setContentView(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        return this;
    }

    /**
     * 设置对话框布局，大小自适应，指定位置显示
     *
     * @param view    对话框布局
     * @param gravity 指定对话框显示位置
     * @return
     */
    public CustomDialog setContentView(View view, int gravity) {
        setContentView(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, gravity);
        return this;
    }

    /**
     * 设置对话框布局，大小，显示位置
     *
     * @param view    对话框布局
     * @param width   对话框宽度
     * @param height  对话框高度
     * @param gravity 对话框显示位置
     */
    public CustomDialog setContentView(View view, int width, int height, int gravity) {
        if (mDialog == null) {
            create();
        }
        mDialog.setContentView(view);
        Window winDlg = mDialog.getWindow();
        WindowManager.LayoutParams lp = winDlg.getAttributes();
        lp.width = width;
        lp.height = height;
        winDlg.setGravity(gravity);
        winDlg.setAttributes(lp);
        return this;
    }

    /**
     * 设置对话框是否允许外部点击取消
     *
     * @param cancel
     * @return
     */
    public CustomDialog setCanceledOnTouchOutside(boolean cancel) {
        if (mDialog == null)
            create();
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * 设置对话框是否允许取消
     *
     * @param isCancel
     * @return
     */
    public CustomDialog setCancelable(boolean isCancel) {
        if (mDialog == null)
            create();
        mDialog.setCancelable(isCancel);
        return this;
    }

    /**
     * 设置对话框动画功能
     *
     * @param resId
     * @return
     */
    public CustomDialog setAnimations(int resId) {
        if (mDialog == null)
            create();
        mDialog.getWindow().setWindowAnimations(resId);
        return this;
    }

    /**
     * 设置对话框显示监听
     *
     * @param listener
     * @return
     */
    public CustomDialog setOnShowListener(DialogInterface.OnShowListener listener) {
        if (mDialog == null)
            create();
        if (listener != null)
            mDialog.setOnShowListener(listener);
        return this;
    }

    /**
     * 设置对话框取消监听
     *
     * @param listener
     * @return
     */
    public CustomDialog setOnDismissListener(DialogInterface.OnDismissListener listener) {
        if (mDialog == null)
            create();
        if (listener != null)
            mDialog.setOnDismissListener(listener);
        return this;
    }

    /**
     * 对话框显示
     */
    public void show() {
        //重要！！一定要加上mActivity.isFinishing()判断条件，不然三星手机会挂掉
        if (mDialog != null && !mActivity.isFinishing() && mDialog.isShowing()==false)
            mDialog.show();
    }

    /**
     * 对话框取消
     */
    public void dismiss() {
        //重要！！一定要加上mActivity.isFinishing()判断条件，不然三星手机会挂掉
        if (mDialog != null && !mActivity.isFinishing() && mDialog.isShowing())
            mDialog.dismiss();
    }

    /**
     * 判断对话框是否已经显示
     *
     * @return
     */
    public boolean isShowing() {
        return mDialog == null ? false : mDialog.isShowing();
    }
}
