package com.iamasoldier6.soldiernews.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by iamasoldier6 on 2015/11/24.
 */
public class User extends BmobUser {

    private String mNickName;
    private String mSex;
    private String signature;

    public String getSex() {
        return mSex;
    }

    public void setSex(String sex) {
        this.mSex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String nickName) {
        this.mNickName = nickName;
    }
}
