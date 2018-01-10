package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PersonalFileFamilyFragmentEntity;

import java.util.List;


/**
 * Created by youpengda on 2017/2/9.
 */

public class PersonalFileFamilyMemberAdapter extends RecyclerView.Adapter {


    public interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private Context mContext = null;



    private List<PersonalFileFamilyFragmentEntity> mData;

    private OnRecyclerViewListener onRecyclerViewListener;


    public PersonalFileFamilyMemberAdapter(Context context, List<PersonalFileFamilyFragmentEntity> mData, OnRecyclerViewListener onRecyclerViewListener) {
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.mData = mData;
    }
    public List<PersonalFileFamilyFragmentEntity> getmData() {
        return mData;
    }

    public void setmData(List<PersonalFileFamilyFragmentEntity> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_personal_file_family_member, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.family_member_who.setText(mData.get(i).getREALATIONS());
        holder.family_member_name.setText(mData.get(i).getNAME());
        holder.family_member_age.setText(mData.get(i).getAGE()+"");
        holder.family_member_address.setText(mData.get(i).getADDRESS());
        holder.family_member_work_place.setText(mData.get(i).getWORK_COMPANY_NAME());
        holder.family_member_phone_number.setText(mData.get(i).getPHONE());
//        if (mData.get(i).isChecked()) {
//            holder.rootView.setBackgroundResource(R.drawable.time_declare_pressed);
//        } else {
//            holder.rootView.setBackgroundResource(R.drawable.time_declare_normal);
//        }
        holder.position = i;
//        showItemAnim(holder.cardView, i);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView family_member;
        TextView family_member_who;
        TextView family_member_name;
        TextView family_member_age;
        TextView family_member_address;
        TextView family_member_work_place;
        TextView family_member_phone_number;
        LinearLayout rootView;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.root_view);
            family_member = (TextView) itemView.findViewById(R.id.family_member);
            family_member_who = (TextView) itemView.findViewById(R.id.tv_family_member_who);
            family_member_name = (TextView) itemView.findViewById(R.id.tv_family_member_name);
            family_member_age = (TextView) itemView.findViewById(R.id.tv_family_member_age);
            family_member_address = (TextView) itemView.findViewById(R.id.tv_family_member_address);
            family_member_work_place = (TextView) itemView.findViewById(R.id.tv_family_member_work_place);
            family_member_phone_number = (TextView) itemView.findViewById(R.id.tv_family_member_phone_number);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (null != onRecyclerViewListener) {
                return onRecyclerViewListener.onItemLongClick(position);
            }
            return false;
        }
    }


//    private String dealDay(double day) {
//        try {
//            String data = String.valueOf(day);
//            if (data.endsWith(".0")) {
//                data = data.substring(0, data.length() - 2);
//            }
//            return data;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
}
