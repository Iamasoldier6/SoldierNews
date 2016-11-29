package com.infiniteviewpager;

import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * A PagerAdapter that wraps around another PagerAdapter to handle paging wrap-around.
 */
public class InfinitePagerAdapter extends PagerAdapter {

    private static final String TAG = "InfinitePagerAdapter";
    private static final boolean DEBUG = true;

    private PagerAdapter mAdapter;

    public InfinitePagerAdapter(PagerAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public int getCount() {
        if (getRealCount() == 0) {
            return 0;
        }
        // warning: scrolling to very high values (1,000,000+) results in
        // strange drawing behaviour
        return Integer.MAX_VALUE;
    }

    /**
     * @return the {@link #getCount()} result of the wrapped adapter
     */
    public int getRealCount() {
        return mAdapter.getCount();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int virtualPosition = position % getRealCount();
        debug("instantiateItem: real position: " + position);
        debug("instantiateItem: virtual position: " + virtualPosition);

        // only expose virtual position to the inner adapter
        return mAdapter.instantiateItem(container, virtualPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int virtualPosition = position % getRealCount();
        debug("destroyItem: real position: " + position);
        debug("destroyItem: virtual position: " + virtualPosition);

        // only expose virtual position to the inner adapter
        mAdapter.destroyItem(container, virtualPosition, object);
    }

    /*
     * Delegate rest of methods directly to the inner adapter.
     */
    @Override
    public void finishUpdate(ViewGroup container) {
        mAdapter.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return mAdapter.isViewFromObject(view, object);
    }

    @Override
    public void restoreState(Parcelable bundle, ClassLoader classLoader) {
        mAdapter.restoreState(bundle, classLoader);
    }

    @Override
    public Parcelable saveState() {
        return mAdapter.saveState();
    }

    @Override
    public void startUpdate(ViewGroup container) {
        mAdapter.startUpdate(container);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int virtualPosition = position % getRealCount();
        return mAdapter.getPageTitle(virtualPosition);
    }

    @Override
    public float getPageWidth(int position) {
        return mAdapter.getPageWidth(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mAdapter.setPrimaryItem(container, position, object);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mAdapter.unregisterDataSetObserver(observer);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mAdapter.registerDataSetObserver(observer);
    }

    @Override
    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return mAdapter.getItemPosition(object);
    }

    /*
     * End delegation
     */
    private void debug(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }
}
