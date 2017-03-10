package com.iamasoldier6.soldiernews;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iamasoldier6.soldiernews.activity.LoginActivity;
import com.iamasoldier6.soldiernews.activity.PersonalActivity;
import com.iamasoldier6.soldiernews.bean.Constant;
import com.iamasoldier6.soldiernews.bean.User;
import com.iamasoldier6.soldiernews.biz.UserProxy;
import com.iamasoldier6.soldiernews.fragment.JoyFragment;
import com.iamasoldier6.soldiernews.fragment.TechFragment;
import com.iamasoldier6.soldiernews.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mView;
    private String[] mDrawerTitles = {"科技", "娱乐", "设置"};
    private List<Fragment> mFragmentList;
    private Class[] mClasses = {TechFragment.class, JoyFragment.class, SettingFragment.class};
    private ImageView mIvPhoto;
    private TextView mTvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mFragmentList.add(null);
        }
        initView();
        selectItem(0);
    }

    private void selectItem(int position) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        for (Fragment fragment : mFragmentList) {
            if (fragment != null) {
                fragmentTransaction.hide(fragment);
            }
        }

        Fragment fragment;

        if (mFragmentList.get(position) == null) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.TITLE, mDrawerTitles[position]);
            fragment = Fragment.instantiate(this, mClasses[position].getName(), bundle);
            mFragmentList.set(position, fragment);
            fragmentTransaction.add(R.id.main, fragment);
        } else {
            fragment = mFragmentList.get(position);
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(mDrawerTitles[position]);
    }

    private void initView() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mView = (NavigationView) findViewById(R.id.navigation_view);
        mLayout = (DrawerLayout) findViewById(R.id.dl_left);

        mToolbar.setTitle("界面");

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.menu);

        mToggle = new ActionBarDrawerToggle(this, mLayout, mToolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mLayout.setDrawerListener(mToggle);
        setupDrawerContent(mView);
    }

    private void setupDrawerContent(NavigationView mNavigationView) {

        View header = LayoutInflater.from(this).inflate(R.layout.navigation_header, null);
        mIvPhoto = (ImageView) header.findViewById(R.id.photo_iv);
        mIvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserProxy.isLogin(MainActivity.this)) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, PersonalActivity.class);
                    startActivity(intent);
                }
            }
        });

        mTvLogin = (TextView) header.findViewById(R.id.login_tv);
        if (UserProxy.isLogin(this)) {
            User user = UserProxy.getCurrentUser(this);
            mTvLogin.setText(user.getUsername());
        }

        mNavigationView.addHeaderView(header);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_tech:
                        selectItem(0);
                        break;
                    case R.id.nav_joy:
                        selectItem(1);
                        break;
                    case R.id.nav_settings:
                        selectItem(2);
                        break;
                    case R.id.nav_personal:
                        startActivity(new Intent(MainActivity.this, PersonalActivity.class));
                        break;
                }
                menuItem.setChecked(true);
                mLayout.closeDrawers();
                return true;
            }
        });
    }
}
