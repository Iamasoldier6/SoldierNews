package com.iamasoldier6.soldiernews.biz;

import com.iamasoldier6.soldiernews.bean.NewsItem;

import java.util.List;

/**
 * Created by iamasoldier6 on 2015/11/16.
 */
public interface OnparseListener {
    void onParseSuccess(List<NewsItem> list);

    void onParseFailed();
}
