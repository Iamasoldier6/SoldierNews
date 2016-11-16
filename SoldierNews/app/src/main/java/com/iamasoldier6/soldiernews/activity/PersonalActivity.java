package com.iamasoldier6.soldiernews.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iamasoldier6.soldiernews.R;
import com.iamasoldier6.soldiernews.bean.User;
import com.iamasoldier6.soldiernews.biz.UserProxy;
import com.iamasoldier6.soldiernews.view.ChooseDialog;
import com.iamasoldier6.soldiernews.view.ConfirmDialog;
import com.iamasoldier6.soldiernews.view.EditTextDialog;
import com.iamasoldier6.soldiernews.view.LoadingDialog;

import static com.iamasoldier6.soldiernews.R.id.tv_nickname;
import static com.iamasoldier6.soldiernews.R.id.tv_sex;

/**
 * Created by Iamasoldier6 on 2015/11/25.
 */

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvUsername;
    private Button btnNickname;
    private Button btnSex;
    private Button btnSignature;
    private Button btnLogout;
    private TextView tvNickname;
    private TextView sexTxt;
    private LoadingDialog loadingDialog;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initViews();

        user = UserProxy.getCurrentUser(this);
        if (user != null) {
            tvUsername.setText(user.getUsername());
        }
        setListener();
    }

    private void setListener() {
        btnNickname.setOnClickListener(this);
        btnSex.setOnClickListener(this);
        btnSignature.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("个人信息");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadingDialog = new LoadingDialog();

        tvUsername = (TextView) findViewById(R.id.username);
        tvNickname = (TextView) findViewById(tv_nickname);
        sexTxt = (TextView) findViewById(tv_sex);
        btnNickname = (Button) findViewById(R.id.nickname_btn);
        btnSex = (Button) findViewById(R.id.sex_btn);
        btnSignature = (Button) findViewById(R.id.signature_btn);
        btnLogout = (Button) findViewById(R.id.logout_btn);
    }

    @Override
    public void onClick(View v) {
        EditTextDialog editTextDialog = new EditTextDialog();
        switch (v.getId()) {
            case R.id.nickname_btn:
                editTextDialog.setParams(tvNickname.getText().toString(), true, 20);
                editTextDialog.setMyOnClickListener(new EditTextDialog.MyOnClickListener() {
                    @Override
                    public void onClick(String str) {
                        if (str == null) return;
                        loadingDialog.show(getFragmentManager(), "loading");
                        UserProxy.upDataInfo(PersonalActivity.this, user, str, null, null, new UserProxy.UpdataInfo() {
                            @Override
                            public void onSuccess() {
                                loadingDialog.dismiss();
                                Toast.makeText(PersonalActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                                tvNickname.setText(user.getNickName());
                            }

                            @Override
                            public void onFailure(String msg) {
                                loadingDialog.dismiss();
                                Toast.makeText(PersonalActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                editTextDialog.show(getFragmentManager(), "set_nickname_list_dialog");
                break;
            case R.id.sex_btn:
                final String[] str = {"男", "女"};
                ChooseDialog chooseDialog = new ChooseDialog();
                chooseDialog.setParams(null, str, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadingDialog.show(getFragmentManager(), "loading");
                        UserProxy.upDataInfo(PersonalActivity.this, user, null, str[which], null, new UserProxy.UpdataInfo() {
                            @Override
                            public void onSuccess() {
                                loadingDialog.dismiss();
                                sexTxt.setText(user.getSex());
                                Toast.makeText(PersonalActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(String msg) {
                                loadingDialog.dismiss();
                                Toast.makeText(PersonalActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                chooseDialog.show(getFragmentManager(), "choose");
                break;
            case R.id.signature_btn:
                break;
            case R.id.logout_btn:
                ConfirmDialog confirmDialog = new ConfirmDialog();
                confirmDialog.setParams("确认退出登录吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserProxy.logout(getApplicationContext());
                        PersonalActivity.this.finish();
                    }
                }, null);
                confirmDialog.show(getFragmentManager(), "confirm");
                break;
        }
    }
}
