package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.ScoreEntity;
import com.pvirtech.pzpolice.enumeration.RoleEnum;

import java.util.List;


/**
 * Created by youpengda on 2017/2/9.
 */

public class ScoreboardPersonlAdapter extends BaseQuickAdapter<ScoreEntity, BaseViewHolder> {

    private Context mContext = null;

    private List<ScoreEntity> data;

    private OnRecyclerViewListener onRecyclerViewListener;
    int position = 0;

    public interface OnRecyclerViewListener {
        void onItemClick(ScoreEntity entity);

        boolean onItemLongClick(ScoreEntity entity);

    }

    public ScoreboardPersonlAdapter(Context context, List<ScoreEntity> data, OnRecyclerViewListener onRecyclerViewListener) {
        super(R.layout.adapter_scoreboard_personal, data);
        this.data = data;
        mContext = context;
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final ScoreEntity scoreEntity) {
        baseViewHolder.setText(R.id.score_num, scoreEntity.getPosition() + "");
        if (scoreEntity.getPosition() < 4) {
            baseViewHolder.setTextColor(R.id.score_num, mContext.getResources().getColor(R.color.text_orange));
        } else {
            baseViewHolder.setTextColor(R.id.score_num, mContext.getResources().getColor(R.color.text_dark));
        }
        if (scoreEntity.getPosition() % 4 == 0) {
            baseViewHolder.setImageResource(R.id.score_image, R.mipmap.user_1);
        } else if (scoreEntity.getPosition() % 4 == 1) {
            baseViewHolder.setImageResource(R.id.score_image, R.mipmap.user_2);
        } else if (scoreEntity.getPosition() % 4 == 2) {
            baseViewHolder.setImageResource(R.id.score_image, R.mipmap.user_3);
        } else if (scoreEntity.getPosition() % 4 == 3) {
            baseViewHolder.setImageResource(R.id.score_image, R.mipmap.user_4);
        }
        baseViewHolder.setText(R.id.score_police, scoreEntity.getName());

        baseViewHolder.setText(R.id.score_society, scoreEntity.getDept());
        baseViewHolder.setText(R.id.score_total, String.valueOf(scoreEntity.getTotalScore()));

        String position = "";
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleEnum.getValue().getRoleId().equals(scoreEntity.getRole())) {
                position = roleEnum.getValue().getRoleName();
                break;
            }
        }
        baseViewHolder.setText(R.id.score_position, position);
        baseViewHolder.setOnClickListener(R.id.root_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewListener.onItemClick(scoreEntity);
            }
        });
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
