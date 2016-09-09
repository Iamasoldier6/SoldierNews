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

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private String[] drawerTitles = {"科技", "娱乐", "设置"};
    private List<Fragment> fragmentList;
    private Class[] classes = {TechFragment.class, JoyFragment.class, SettingFragment.class};
    private ImageView photoImg;
    private TextView loginTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            fragmentList.add(null);
        }
        initView();
        selectItem(0);
    }

    private void selectItem(int position) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        for (Fragment fragment : fragmentList) {
            if (fragment != null) {
                fragmentTransaction.hide(fragment);
            }
        }

        Fragment fragment;
        if (fragmentList.get(position) == null) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.TITLE, drawerTitles[position]);
            fragment = Fragment.instantiate(this, classes[position].getName(), bundle);
            fragmentList.set(position, fragment);
            fragmentTransaction.add(R.id.main, fragment);
        } else {
            fragment = fragmentList.get(position);
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(drawerTitles[position]);
    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_left);

        toolbar.setTitle("界面");

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.menu);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerContent(mNavigationView);
    }

    private void setupDrawerContent(NavigationView mNavigationView) {

        View header = LayoutInflater.from(this).inflate(R.layout.navigation_header, null);
        photoImg = (ImageView) header.findViewById(R.id.photo_iv);
        photoImg.setOnClickListener(new View.OnClickListener() {
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
        loginTxt = (TextView) header.findViewById(R.id.login_tv);
        if (UserProxy.isLogin(this)) {
            User user = UserProxy.getCurrentUser(this);
            loginTxt.setText(user.getUsername());
        }

        mNavigationView.addHeaderView(header);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_keji:
                        selectItem(0);
                        break;
                    case R.id.nav_yule:
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
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }
}
