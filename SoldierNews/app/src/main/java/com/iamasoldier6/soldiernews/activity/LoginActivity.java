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

    private DeletableEditText mEtUsername, etPassword;
    private Button btnLogin, btnRegister;
    private LoadingDialog mDialog;
    private String username, password;

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
        etPassword = (DeletableEditText) findViewById(R.id.et_login_password);
        btnLogin = (Button) findViewById(btn_login);
        btnRegister = (Button) findViewById(btn_register);
        mDialog = new LoadingDialog();
        mDialog.setParams("请稍等...");

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private boolean isUserComplete() {
        username = mEtUsername.getText().toString();
        password = etPassword.getText().toString();

        if (username == null) {
            mEtUsername.setShakeAnimation();
            Toast.makeText(this, "请填写用户名", Toast.LENGTH_SHORT);
            return false;
        }
        if (password == null) {
            etPassword.setShakeAnimation();
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
                    UserProxy.login(getApplicationContext(), username, password, new UserProxy.LoginListener() {
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
                    UserProxy.register(getApplicationContext(), username, password, new UserProxy.RegsiterListener() {
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
                    btnRegister.setText("登录");
                } else if (loginOrRegister == LoginOrRegister.REGISTER) {
                    loginOrRegister = LoginOrRegister.LOGIN;
                    btnRegister.setText("注册帐号");
                }
                updateLayout(loginOrRegister);
                break;
        }
    }

    private void updateLayout(LoginOrRegister loginOrRegister) {
        if (loginOrRegister == LoginOrRegister.LOGIN) {
            btnLogin.setText("登录");
        } else if (loginOrRegister == LoginOrRegister.REGISTER) {
            btnLogin.setText("注册");
        }
    }
}
