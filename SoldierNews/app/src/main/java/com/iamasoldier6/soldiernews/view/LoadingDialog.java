package com.iamasoldier6.soldiernews.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * Created by iamasoldier6 on 2015/11/24.
 */
public class LoadingDialog extends DialogFragment {

    private String mTitle;
    private String mMessage;

    public void setParams(String message) {
        setParams(null, message);
    }

    public void setParams(String title, String message) {
        this.mTitle = title;
        this.mMessage = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);

        if (mTitle != null) progressDialog.setTitle(mTitle);
        if (mMessage != null) progressDialog.setMessage(mMessage);
        progressDialog.setCanceledOnTouchOutside(false);

        return progressDialog;
    }
}
