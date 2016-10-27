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

import static com.iamasoldier6.soldiernews.R.id.login_btn;
import static com.iamasoldier6.soldiernews.R.id.register_btn;

/**
 * Created by Iamasoldier6 on 2015/11/22.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private DeletableEditText usernameEt, passwordEt;
    private Button loginBtn, registerBtn;
    private LoadingDialog dialog;
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
        usernameEt = (DeletableEditText) findViewById(R.id.login_username_et);
        passwordEt = (DeletableEditText) findViewById(R.id.login_password_et);
        loginBtn = (Button) findViewById(login_btn);
        registerBtn = (Button) findViewById(register_btn);
        dialog = new LoadingDialog();
        dialog.setParams("请稍等...");

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    private boolean isUserComplete() {
        username = usernameEt.getText().toString();
        password = passwordEt.getText().toString();
        if (username == null) {
            usernameEt.setShakeAnimation();
            Toast.makeText(this, "请填写用户名", Toast.LENGTH_SHORT);
            return false;
        }
        if (password == null) {
            passwordEt.setShakeAnimation();
            Toast.makeText(this, "请填写密码", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case login_btn:
                if (loginOrRegister == LoginOrRegister.LOGIN) {
                    if (!isUserComplete()) return;
                    dialog.show(getFragmentManager(), "loading");
                    UserProxy.login(getApplicationContext(), username, password, new UserProxy.LoginListener() {
                        @Override
                        public void onSuccess() {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            // Constant.isLogin = true;
                            LoginActivity.this.finish();
                        }

                        @Override
                        public void onFailure(String msg) {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (loginOrRegister == LoginOrRegister.REGISTER) {
                    if (!isUserComplete()) return;
                    dialog.show(getFragmentManager(), "loading...");
                    UserProxy.register(getApplicationContext(), username, password, new UserProxy.RegsiterListener() {
                        @Override
                        public void onSuccess() {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            LoginActivity.this.finish();
                        }

                        @Override
                        public void onFailure(String msg) {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case register_btn:
                if (loginOrRegister == LoginOrRegister.LOGIN) {
                    loginOrRegister = LoginOrRegister.REGISTER;
                    registerBtn.setText("登录");
                } else if (loginOrRegister == LoginOrRegister.REGISTER) {
                    loginOrRegister = LoginOrRegister.LOGIN;
                    registerBtn.setText("注册帐号");
                }
                updateLayout(loginOrRegister);
                break;
        }
    }

    private void updateLayout(LoginOrRegister loginOrRegister) {
        if (loginOrRegister == LoginOrRegister.LOGIN) {
            loginBtn.setText("登录");
        } else if (loginOrRegister == LoginOrRegister.REGISTER) {
            loginBtn.setText("注册");
        }
    }
}
