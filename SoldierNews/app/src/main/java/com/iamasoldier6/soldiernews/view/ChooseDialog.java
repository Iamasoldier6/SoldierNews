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
    private String[] item;
    private DialogInterface.OnClickListener chooseOnClickListener;

    public void setParams(String title, String[] item, DialogInterface.OnClickListener chooseOnClickListener) {
        this.mTitle = title;
        this.item = item;
        this.chooseOnClickListener = chooseOnClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (mTitle != null) builder.setTitle(mTitle);
        if (item != null) builder.setItems(item, chooseOnClickListener);
        builder.setCancelable(true);
        return builder.create();
    }
}
