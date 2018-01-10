package com.pvirtech.pzpolice.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.ScoreEntity;
import com.pvirtech.pzpolice.entity.ScoreTeamEntity;

import java.util.List;


/**
 * Created by youpengda on 2017/2/9.
 */

public class ScoreboardTeamAdapter extends BaseQuickAdapter<ScoreTeamEntity, BaseViewHolder> {

    private Context mContext = null;

    private List<ScoreTeamEntity> data;

    private OnRecyclerViewListener onRecyclerViewListener;
    int position = 0;

    public interface OnRecyclerViewListener {
        void onItemClick(ScoreEntity entity);

        boolean onItemLongClick(ScoreEntity entity);

    }

    public ScoreboardTeamAdapter(Context context, List<ScoreTeamEntity> data, OnRecyclerViewListener onRecyclerViewListener) {
        super(R.layout.adapter_scoreboard_team, data);
        mContext = context;
        this.data = data;
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ScoreTeamEntity entity) {
        baseViewHolder.setText(R.id.score_num, entity.getPosition() + "");
        if (entity.getPosition() < 4) {
            baseViewHolder.setTextColor(R.id.score_num, mContext.getResources().getColor(R.color.text_orange));
        } else {
            baseViewHolder.setTextColor(R.id.score_num, mContext.getResources().getColor(R.color.text_dark));
        }
        if (entity.getPosition() % 4 == 0) {
            baseViewHolder.setImageResource(R.id.score_image, R.mipmap.user_1);
        } else if (entity.getPosition() % 4 == 1) {
            baseViewHolder.setImageResource(R.id.score_image, R.mipmap.user_2);
        } else if (entity.getPosition() % 4 == 2) {
            baseViewHolder.setImageResource(R.id.score_image, R.mipmap.user_3);
        } else if (entity.getPosition() % 4 == 3) {
            baseViewHolder.setImageResource(R.id.score_image, R.mipmap.user_4);
        }
        baseViewHolder.setText(R.id.score_society, entity.getGroup_NAME());
        baseViewHolder.setText(R.id.score_total, String.valueOf(entity.getTotalScore()));
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
