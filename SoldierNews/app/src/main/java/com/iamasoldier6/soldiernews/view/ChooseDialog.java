package com.iamasoldier6.soldiernews.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by iamasoldier6 on 2015/11/26.
 */
public class ChooseDialog extends DialogFragment {

    private String mTitle;
    private String[] mItem;
    private DialogInterface.OnClickListener mOnClickListener;

    public void setParams(String title, String[] item, DialogInterface.OnClickListener chooseOnClickListener) {
        this.mTitle = title;
        this.mItem = item;
        this.mOnClickListener = chooseOnClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (mTitle != null) builder.setTitle(mTitle);
        if (mItem != null) builder.setItems(mItem, mOnClickListener);
        builder.setCancelable(true);
        return builder.create();
    }
}
