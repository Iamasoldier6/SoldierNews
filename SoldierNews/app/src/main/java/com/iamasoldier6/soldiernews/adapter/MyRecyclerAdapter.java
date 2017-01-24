package com.iamasoldier6.soldiernews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.iamasoldier6.soldiernews.MyApplication;
import com.iamasoldier6.soldiernews.R;
import com.iamasoldier6.soldiernews.bean.NewsItem;

import java.util.List;

import static com.iamasoldier6.soldiernews.R.id.tv_title;

/**
 * Created by iamasoldier6 on 2015/11/16.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<NewsItem> mNewsList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public MyRecyclerAdapter(Context context, List newsList) {
        mContext = context;
        mNewsList = newsList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void updateData(List<NewsItem> data) {
        this.mNewsList = data;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTvTitle.setText(mNewsList.get(position).getTitle());
        holder.mTvDate.setText(mNewsList.get(position).getDate());
        ImageLoader.getInstance().displayImage(mNewsList.get(position).getImageurl(), holder.mIvPicture, MyApplication.getInstance().getOptionsWithRoundedCorner());

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTvTitle;
        ImageView mIvPicture;
        TextView mTvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(tv_title);
            mIvPicture = (ImageView) itemView.findViewById(R.id.iv_image);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
