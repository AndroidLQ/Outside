package com.yunwang.ui.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yunwang.R;
import com.yunwang.base.BaseActivty;
import com.yunwang.manager.PreferenceManager;
import com.yunwang.manager.ToastManager;
import com.yunwang.utils.Constants;
import com.yunwang.utils.Md5MessageDigest;
import com.yunwang.utils.Util;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/17.
 */

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivty implements View.OnClickListener, TextWatcher {

    @ViewInject(R.id.et_usernme)
    private EditText etUserName;//登录名

    @ViewInject(R.id.et_user_password)
    private EditText etUserPassword;//密码

    @ViewInject(R.id.btn_login)
    private Button btnLogin;//登陆

    @ViewInject(R.id.iv_account_error)
    private ImageView ivAccountError;

    @ViewInject(R.id.iv_eye_state)
    private ImageView ivEyeState;//密码的输入状态（明文或者密文）

    @ViewInject(R.id.cb_save_password)
    private CheckBox savePassword;//记住密码

    @ViewInject(R.id.ll_set_url)
    private LinearLayout llSetUrl;//设置端口

    //用户名
    private String userName;
    //用户密码
    private String userPassword;
    //密码状态标志
    private boolean passwordState;

    @Override
    protected void initView() {
        btnLogin.setOnClickListener(this);
        llSetUrl.setOnClickListener(this);
        ivAccountError.setOnClickListener(this);
        ivEyeState.setOnClickListener(this);
        etUserName.addTextChangedListener(this);

        //默认记住密码
        savePassword.setChecked(true);

        //判断用户是否保存账号
        boolean isMemory = PreferenceManager.getBoolean(mActivity, Constants.IS_SAVE_USER_ACCOUNT);
        if (isMemory) {
            //用户账号
            String userAccount = PreferenceManager.getString(mActivity, Constants.USER_ACCOUNT);
            etUserName.setText(userAccount);
            etUserName.setSelection(userAccount.length());
            //用户密码信息
            String userPassword = PreferenceManager.getString(mActivity, Constants.USER_PASSWORD);
            etUserPassword.setText(userPassword);
            etUserPassword.setSelection(userPassword.length());
        }
        //登录页禁止滑动删除
        setSwipeBackEnable(false);
    }

    @Override
    protected void loadData() {

    }

    /**
     * 判断登陆信息
     */
    private void verdictLoginInfo() {
        //用户名
        userName = etUserName.getText().toString();
        //用户密码
        userPassword = etUserPassword.getText().toString();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword)) {
            ToastManager.createToast(mActivity).showToast(R.string.please_input_login_info,
                    ToastManager.GRAVITY_CENTER);
            return;
        }
        //进行登陆
        doLogin();
    }

    /**
     * 清除用户的账号信息
     */
    private void clearAccountInfo() {
        //用户名
        userName = etUserName.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            return;
        }
        String hintAccount = mActivity.getResources().getString(R.string.et_user_login_account);
        etUserName.setHint(hintAccount);
        //一定要设置显示文字为空才能显示hint的文字
        etUserName.setText("");
    }

    /**
     * 登陆密码明文或者密文的显示
     */
    private void controlPasswordState(boolean passwordState) {
        if (passwordState) {
            //设置成明文
            //This transformation method causes any carriage return characters (\r)
            //to be hidden by displaying them as zero-width non-breaking space characters (﻿).
            etUserPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ivEyeState.setImageResource(R.mipmap.icon_eye_open);
        } else {
            //设置成密文
            etUserPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ivEyeState.setImageResource(R.mipmap.icon_eye_closed);
        }
        userPassword = etUserPassword.getText().toString();
        if (!TextUtils.isEmpty(userPassword)) {
            //将光标移至最后
            etUserPassword.setSelection(userPassword.length());
        }
    }

    /**
     * 点击事件
     *
     * @param v 点击的view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_account_error:
                clearAccountInfo();
                break;
            case R.id.iv_eye_state:
                //密码状态进行切换
                passwordState = !passwordState;
                controlPasswordState(passwordState);
                break;
            case R.id.btn_login:
                //隐藏软键盘
                Util.hideInputMethodManagerKeyStore(mActivity);
                startActivity(mActivity,CarApplyActivity.class,null,0,false);
                //登陆信息进行判断
//              verdictLoginInfo();
                break;
            case R.id.ll_set_url://跳转到设置URL界面
                startActivity(mActivity,SetUrlActivity.class,null,0,false);
                break;
        }
    }

    private void doLogin() {
        Map<String, String> params = new HashMap<>();
        String userName = etUserName.getText().toString();
        String password = etUserPassword.getText().toString();
        params.put("username", userName);
        //使用Md5加密两次（都需要转小写）
        String md5Str = Md5MessageDigest.getMD5Str(password).toLowerCase();
        //转小写
        String result = Md5MessageDigest.getMD5Str(md5Str).toLowerCase();
        params.put("password", result);
        //设置URL
        String url = PreferenceManager.getString(mActivity,Constants.URL);
        if(TextUtils.isEmpty(url)){
            ToastManager.createToast(mActivity).showToast(R.string.tv_please_input_url,ToastManager.GRAVITY_CENTER);
            return;
        }
        String lineNumber = PreferenceManager.getString(mActivity, Constants.LINE_NUMBER);
        if(TextUtils.isEmpty(lineNumber)){
            ToastManager.createToast(mActivity).showToast(R.string.tv_please_input_line_number,ToastManager.GRAVITY_CENTER);
            return;
        }
        params.put("url",url);
        params.put("line_number",lineNumber);
        dialog.setDialogMessage(mActivity, R.string.login_message);
        //设置dialog触摸边缘不消失
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        //TODO 进行网络请求

    }

    /**
     * 保存用户的账号信息
     */
    private void remenberUserAccount() {
        if (savePassword.isChecked()) {
            //保存用户账号信息
            PreferenceManager.setString(mActivity, Constants.USER_ACCOUNT, etUserName.getText().toString());
            //保存用户密码信息
            PreferenceManager.setString(mActivity, Constants.USER_PASSWORD, etUserPassword.getText().toString());
            //保存用户账号信息
            PreferenceManager.setBoolean(mActivity, Constants.IS_SAVE_USER_ACCOUNT, true);
        } else {
            //未保存用户账号信息
            PreferenceManager.setBoolean(mActivity, Constants.IS_SAVE_USER_ACCOUNT, false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //当有账号的时候就显示删除按钮
        if (!TextUtils.isEmpty(s)) {
            ivAccountError.setVisibility(View.VISIBLE);
        } else {
            ivAccountError.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
