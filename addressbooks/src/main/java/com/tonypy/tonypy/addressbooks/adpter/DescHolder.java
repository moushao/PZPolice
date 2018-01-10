package com.tonypy.tonypy.addressbooks.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.tonypy.tonypy.addressbooks.R;

/**
 * Created by lyd10892 on 2016/8/23.
 */

public class DescHolder extends RecyclerView.ViewHolder {
    public TextView descView;
    public RoundedImageView roundedImageView;
    public ImageView imageView;

    public DescHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        descView = (TextView) itemView.findViewById(R.id.tv_desc);
        roundedImageView = (RoundedImageView) itemView.findViewById(R.id.user_icon);
        imageView = (ImageView) itemView.findViewById(R.id.desc_checked_img);
    }
}
