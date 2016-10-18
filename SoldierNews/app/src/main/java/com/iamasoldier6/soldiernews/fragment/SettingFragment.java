package com.iamasoldier6.soldiernews.fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iamasoldier6.soldiernews.R;
import com.iamasoldier6.soldiernews.util.ACache;
import com.iamasoldier6.soldiernews.util.DataCleanManager;
import com.iamasoldier6.soldiernews.util.SDCardUtils;
import com.iamasoldier6.soldiernews.view.ConfirmDialog;
import com.iamasoldier6.soldiernews.view.LoadingDialog;

import java.text.DecimalFormat;

/**
 * Created by iamasoldier6 on 2015/11/26.
 */
public class SettingFragment extends Fragment {

    private Button clearBtn;
    private TextView tvClear;
    private DialogHandler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        CacheSize();
    }

    private void CacheSize() {
        // 内置存储 cache
        double internalCacheSize = SDCardUtils.getDirSize(getActivity().getCacheDir());
        // 外置存储 cache
        double externalCacheSize = 0;
        if (SDCardUtils.isSDCardEnable())
            externalCacheSize = SDCardUtils.getDirSize(getActivity().getExternalCacheDir());
        // WebView 的c ache
        String totalCacheSize = new DecimalFormat("#.00").format(internalCacheSize + externalCacheSize);
        if (totalCacheSize.startsWith(".")) totalCacheSize = "0" + totalCacheSize;
        // 若总大小小于 0.1MB ，直接显示 0.00MB
        if (Float.parseFloat(totalCacheSize) < 0.10f) totalCacheSize = "0.00";
        String str = totalCacheSize + "MB";

        tvClear.setText(str);
    }

    private void initViews(View view) {
        clearBtn = (Button) view.findViewById(R.id.clear_btn);
        tvClear = (TextView) view.findViewById(R.id.clear_tv);
        clearBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String title = "确认清除吗？";
                ConfirmDialog confirmDialog = new ConfirmDialog();
                confirmDialog.setParams(title, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoadingDialog loadingDialog = new LoadingDialog();
                        loadingDialog.setParams("请稍后...");
                        loadingDialog.show(getActivity().getFragmentManager(), "loading");
                        clearCache();
                        mHandler = new DialogHandler(loadingDialog);
                        mHandler.sendEmptyMessageDelayed(0, 1000);
                    }
                }, null);
                confirmDialog.show(getActivity().getFragmentManager(), "confirm");
            }
        });
    }

    private void clearCache() {
        ACache.get(getActivity()).clear();
        DataCleanManager.cleanInternalCache(getActivity());
        DataCleanManager.cleanExternalCache(getActivity());
    }

    private class DialogHandler extends Handler {
        LoadingDialog dialog;

        DialogHandler(LoadingDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (dialog != null && dialog.getDialog().isShowing()) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "清除成功", Toast.LENGTH_SHORT).show();
                String str = "0.00MB";
                tvClear.setText(str);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        // 当 fragment 显示时
        if (!hidden) {
            CacheSize();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
