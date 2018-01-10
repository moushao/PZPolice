package com.tonypy.tonypy.addressbooks.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tonypy.tonypy.addressbooks.R;
import com.tonypy.tonypy.addressbooks.entity.Group;
import com.tonypy.tonypy.addressbooks.utils.HotelUtils;

import java.util.List;

/**
 * Created by lyd10892 on 2016/8/23.
 */

public class HotelEntityAdapter extends SectionedRecyclerViewAdapter<HeaderHolder, DescHolder, RecyclerView.ViewHolder> {

    private static final String TAG = "HoteEntityAdapter";

    public List<Group> allTagList;
    private Context mContext;
    private LayoutInflater mInflater;

    private SparseBooleanArray mBooleanMap;
    private SparseBooleanArray sparseBooleanMap;

    public HotelEntityAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mBooleanMap = new SparseBooleanArray();
        sparseBooleanMap = new SparseBooleanArray();
    }

    public void setData(List<Group> allTagList) {
        this.allTagList = allTagList;
        notifyDataSetChanged();
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    public void setTitleFlag(boolean flag) {
        for (int i = 0; i < allTagList.size(); i++) {
            sparseBooleanMap.put(i, flag);
        }
    }

    @Override
    protected int getSectionCount() {
        return HotelUtils.isEmpty(allTagList) ? 0 : allTagList.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        int count = allTagList.get(section).getGroup_USER().size();
        if (count >= 1 && !mBooleanMap.get(section)) {
            count = 0;
        }

        return HotelUtils.isEmpty(allTagList.get(section).getGroup_USER()) ? 0 : count;
    }

    //是否有footer布局
    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected HeaderHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        return new HeaderHolder(mInflater.inflate(R.layout.address_title_item, parent, false));
    }


    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return new FooterHolder(mInflater.inflate(R.layout.address_footer_item, parent, false));
    }

    @Override
    protected DescHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new DescHolder(mInflater.inflate(R.layout.address_desc_item, parent, false));
    }

    @Override
    protected void onBindSectionHeaderViewHolder(final HeaderHolder holder, final int section) {
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = sparseBooleanMap.get(section);
                sparseBooleanMap.put(section, !isChecked);
                if (!isChecked) {
                    for (Group.GroupUSERBean tinfo : allTagList.get(section).getGroup_USER()) {
                        tinfo.setFlag(1);
                    }
                } else {
                    for (Group.GroupUSERBean tinfo : allTagList.get(section).getGroup_USER()) {
                        tinfo.setFlag(0);
                    }
                }
                notifyDataSetChanged();
            }

        });
        if (sparseBooleanMap.get(section)) {
            holder.imageView.setImageResource(R.mipmap.xuanze);

        } else {
            holder.imageView.setImageResource(R.mipmap.quanxuan);

        }
        holder.openView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOpen = mBooleanMap.get(section);
                String text = isOpen ? "展开" : "关闭";
                mBooleanMap.put(section, !isOpen);
                holder.openView.setText(text);
                notifyDataSetChanged();
            }
        });
        holder.titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isOpen = mBooleanMap.get(section);
                String text = isOpen ? "展开" : "关闭";
                mBooleanMap.put(section, !isOpen);
                holder.openView.setText(text);
                notifyDataSetChanged();
            }
        });
        holder.titleView.setText(allTagList.get(section).getGroup_NAME());
        holder.openView.setText(mBooleanMap.get(section) ? "关闭" : "展开");

    }


    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }


    @Override
    protected void onBindItemViewHolder(final DescHolder holder, final int section, final int position) {
        final String data = allTagList.get(section).getGroup_USER().get(position).getName();
        int selectedflag = allTagList.get(section).getGroup_USER().get(position).getFlag();
        if (selectedflag == 1) {
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }
        holder.descView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, data);
                if (holder.imageView.getVisibility() == View.GONE) {
                    holder.imageView.setVisibility(View.VISIBLE);
                    allTagList.get(section).getGroup_USER().get(position).setFlag(1);
                } else {
                    holder.imageView.setVisibility(View.GONE);
                    allTagList.get(section).getGroup_USER().get(position).setFlag(2);
                }
            }
        });
        holder.roundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.imageView.getVisibility() == View.GONE) {
                    holder.imageView.setVisibility(View.VISIBLE);
                    allTagList.get(section).getGroup_USER().get(position).setFlag(1);
                } else {
                    holder.imageView.setVisibility(View.GONE);
                    allTagList.get(section).getGroup_USER().get(position).setFlag(2);
                }
            }
        });
        holder.descView.setText(allTagList.get(section).getGroup_USER().get(position).getName());
        final int flag = allTagList.get(section).getGroup_USER().get(position).getState();
        if (flag == 2) {
            holder.roundedImageView.setImageResource(R.mipmap.kaihui);
        } else if (flag == 1) {
            holder.roundedImageView.setImageResource(R.mipmap.zaigang);
        } else if (flag == 3) {
            holder.roundedImageView.setImageResource(R.mipmap.gongwu);
        } else {
            holder.roundedImageView.setImageResource(R.mipmap.qingjia);
        }
    }
}
