package com.iamasoldier6.soldiernews.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iamasoldier6.soldiernews.R;
import com.iamasoldier6.soldiernews.bean.Constant;
import com.iamasoldier6.soldiernews.biz.UserProxy;
import com.iamasoldier6.soldiernews.view.DeletableEditText;
import com.iamasoldier6.soldiernews.view.LoadingDialog;

import cn.bmob.v3.Bmob;

import static com.iamasoldier6.soldiernews.R.id.btn_login;
import static com.iamasoldier6.soldiernews.R.id.btn_register;

/**
 * Created by Iamasoldier6 on 2015/11/22.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private DeletableEditText mEtUsername, mEtPassword;
    private Button mBtnLogin, mBtnRegister;
    private LoadingDialog mDialog;
    private String mUsername, mPassword;

    private enum LoginOrRegister {
        LOGIN, REGISTER
    }

    private LoginOrRegister loginOrRegister = LoginOrRegister.LOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, Constant.BMOB_APP_ID);

        initView();
    }

    private void initView() {
        mEtUsername = (DeletableEditText) findViewById(R.id.et_login_username);
        mEtPassword = (DeletableEditText) findViewById(R.id.et_login_password);
        mBtnLogin = (Button) findViewById(btn_login);
        mBtnRegister = (Button) findViewById(btn_register);
        mDialog = new LoadingDialog();
        mDialog.setParams("请稍等...");

        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
    }

    private boolean isUserComplete() {

        mUsername = mEtUsername.getText().toString();
        mPassword = mEtPassword.getText().toString();

        if (mUsername == null) {
            mEtUsername.setShakeAnimation();
            Toast.makeText(this, "请填写用户名", Toast.LENGTH_SHORT);
            return false;
        }
        if (mPassword == null) {
            mEtPassword.setShakeAnimation();
            Toast.makeText(this, "请填写密码", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case btn_login:
                if (loginOrRegister == LoginOrRegister.LOGIN) {
                    if (!isUserComplete()) return;
                    mDialog.show(getFragmentManager(), "loading");
                    UserProxy.login(getApplicationContext(), mUsername, mPassword, new UserProxy.LoginListener() {
                        @Override
                        public void onSuccess() {
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            // Constant.isLogin = true;
                            LoginActivity.this.finish();
                        }

                        @Override
                        public void onFailure(String msg) {
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (loginOrRegister == LoginOrRegister.REGISTER) {
                    if (!isUserComplete()) return;
                    mDialog.show(getFragmentManager(), "loading...");
                    UserProxy.register(getApplicationContext(), mUsername, mPassword, new UserProxy.RegsiterListener() {
                        @Override
                        public void onSuccess() {
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            LoginActivity.this.finish();
                        }

                        @Override
                        public void onFailure(String msg) {
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case btn_register:
                if (loginOrRegister == LoginOrRegister.LOGIN) {
                    loginOrRegister = LoginOrRegister.REGISTER;
                    mBtnRegister.setText("登录");
                } else if (loginOrRegister == LoginOrRegister.REGISTER) {
                    loginOrRegister = LoginOrRegister.LOGIN;
                    mBtnRegister.setText("注册帐号");
                }
                updateLayout(loginOrRegister);
                break;
        }
    }

    private void updateLayout(LoginOrRegister loginOrRegister) {
        if (loginOrRegister == LoginOrRegister.LOGIN) {
            mBtnLogin.setText("登录");
        } else if (loginOrRegister == LoginOrRegister.REGISTER) {
            mBtnLogin.setText("注册");
        }
    }
}
