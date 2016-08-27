package com.rj.subscription.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rj.subscription.R;
import com.rj.subscription.bean.PubsubNode;
import com.rj.subscription.view.CustomTextView;

import java.util.List;

/**
 * Created by lijunyan on 2016/8/23.
 */
public class SubscriptionGridViewAdapter extends BaseAdapter {

    private List<PubsubNode> subInfoList;
    private Activity activity;
    public final static String TAG = "SubGridViewAdapter";

    public SubscriptionGridViewAdapter(List<PubsubNode> subInfoList, Activity activity) {
        this.subInfoList = subInfoList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        if (subInfoList == null){
            return 0;
        }else{
            return subInfoList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return subInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        PubsubNode subInfo = subInfoList.get(position);
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.subscription_num_grid_view_item, null);
            ImageView icon = (ImageView) convertView.findViewById(R.id.subscription_icon_iv);
            TextView name = (TextView) convertView.findViewById(R.id.subscription_name_tv);
            ImageView label = (ImageView)convertView.findViewById(R.id.label_triangle_subpub_iv);
            CustomTextView mTextView = (CustomTextView)convertView.findViewById(R.id.sub_custom_tv);
            viewHolder = new ViewHolder();
            viewHolder.icon = icon;
            viewHolder.name = name;
            viewHolder.label = label;
            viewHolder.mTextView = mTextView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.app_add_icon_normal)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        ImageLoader.getInstance().displayImage(subInfo.getLogo(),  viewHolder.icon, options);
        viewHolder.name.setText(subInfo.getName());
        if (subInfo.isPub() == 1){
            viewHolder.label.setVisibility(View.VISIBLE);
            viewHolder.mTextView.setVisibility(View.VISIBLE);
        }else{
            viewHolder.label.setVisibility(View.GONE);
            viewHolder.mTextView.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView name;
        ImageView label;
        CustomTextView mTextView;
    }
}
