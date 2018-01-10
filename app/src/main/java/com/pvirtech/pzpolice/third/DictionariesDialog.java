package com.pvirtech.pzpolice.third;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.GroupEntity;
import com.pvirtech.pzpolice.entity.PerformanceAppraisalEntity;
import com.pvirtech.pzpolice.entity.TaskAndIntegration;
import com.pvirtech.pzpolice.entity.TaskDistributeEntity;
import com.pvirtech.pzpolice.entity.TaskDistributeTaskEntity;
import com.pvirtech.pzpolice.entity.TeamAssessmentReasonEntity;
import com.pvirtech.pzpolice.ui.appInterfaces.OnItemSelectedListener;

import java.util.List;

import cn.pedant.SweetAlert.DictionariesEntity;

/**
 * Created by Administrator on 2017/3/28.
 */

public class DictionariesDialog {

    public void showDialog(Context context, String title, String defaultValue, final List<DictionariesEntity> data, final OnItemSelectedListener
            onItemSelectedListener) {
        int selectPostion = 0;
        String items[] = new String[data.size()];
        int i = 0;
        for (DictionariesEntity dictionariesEntity : data) {
            items[i] = dictionariesEntity.getNAME();
            if (defaultValue.equals(dictionariesEntity.getNAME())) {
                selectPostion = i;
            }
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);  //先得到构造器
        View mTitleView = LayoutInflater.from(context).inflate(R.layout.dialog_title, null);
        TextView tvTitle = (TextView) mTitleView.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        } else {
            tvTitle.setText("请选择");
        }
        ImageView iv_Title = (ImageView) mTitleView.findViewById(R.id.iv_title);
        iv_Title.setImageResource(R.mipmap.type_white);
        builder.setCustomTitle(mTitleView);
        builder.setSingleChoiceItems(items, selectPostion, new DialogInterface.OnClickListener()

        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onItemSelectedListener.onSelected(data.get(which));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    /**
     * 队伍建设考核
     */
    public interface OnTeamAssessmentItemSelectedListener {
        void onSelected(TeamAssessmentReasonEntity entity);
    }

    public void showTeamAssessmentDialog(Context context, String title, String defaultValue, final List<TeamAssessmentReasonEntity> data, final
    OnTeamAssessmentItemSelectedListener
            onTeamAssessmentItemSelectedListener) {
        int selectPostion = 0;
        String items[] = new String[data.size()];
        int i = 0;
        for (TeamAssessmentReasonEntity entity : data) {
            items[i] = entity.getREASON();
            if (defaultValue.equals(entity.getREASON())) {
                selectPostion = i;
            }
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);  //先得到构造器
        View mTitleView = LayoutInflater.from(context).inflate(R.layout.dialog_title, null);
        TextView tvTitle = (TextView) mTitleView.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        } else {
            tvTitle.setText("请选择");
        }
        ImageView iv_Title = (ImageView) mTitleView.findViewById(R.id.iv_title);
        iv_Title.setImageResource(R.mipmap.type_white);
        builder.setCustomTitle(mTitleView);
        builder.setSingleChoiceItems(items, selectPostion, new DialogInterface.OnClickListener()

        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onTeamAssessmentItemSelectedListener.onSelected(data.get(which));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    /**
     * 组选择
     */
    public interface OnItemSelectedListenerGroup {
        void onSelected(GroupEntity entity);
    }

    public void showDialogGruop(Context context, String title, String defaultValue, final List<GroupEntity> data, final OnItemSelectedListenerGroup
            onItemSelectedListenerGroup) {
        int selectPostion = 0;
        String items[] = new String[data.size()];
        int i = 0;
        for (GroupEntity entity : data) {
            items[i] = entity.getGroup_NAME();
            if (defaultValue.equals(entity.getGroup_NAME())) {
                selectPostion = i;
            }
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);  //先得到构造器
        View mTitleView = LayoutInflater.from(context).inflate(R.layout.dialog_title, null);
        TextView tvTitle = (TextView) mTitleView.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        } else {
            tvTitle.setText("请选择");
        }
        ImageView iv_Title = (ImageView) mTitleView.findViewById(R.id.iv_title);
        iv_Title.setImageResource(R.mipmap.type_white);
        builder.setCustomTitle(mTitleView);
        builder.setSingleChoiceItems(items, selectPostion, new DialogInterface.OnClickListener()

        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onItemSelectedListenerGroup.onSelected(data.get(which));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    /**
     *
     */
    public interface OnItemSelectedListenerTaskType {
        void onSelected(TaskAndIntegration.TYPELISTBean typelistBean);
    }

    public void showTaskTypeDialog(Context context, String title, String defaultValue, final List<TaskAndIntegration.TYPELISTBean> data, final
    OnItemSelectedListenerTaskType
            onItemSelectedListenerTaskType) {
        int selectPostion = 0;
        String items[] = new String[data.size()];
        int i = 0;
        for (TaskAndIntegration.TYPELISTBean typelistBean : data) {
            items[i] = typelistBean.getNAME();
            if (defaultValue.equals(typelistBean.getNAME())) {
                selectPostion = i;
            }
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);  //先得到构造器
        View mTitleView = LayoutInflater.from(context).inflate(R.layout.dialog_title, null);
        TextView tvTitle = (TextView) mTitleView.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        } else {
            tvTitle.setText("请选择");
        }
        ImageView iv_Title = (ImageView) mTitleView.findViewById(R.id.iv_title);
        iv_Title.setImageResource(R.mipmap.type_white);
        builder.setCustomTitle(mTitleView);
        builder.setSingleChoiceItems(items, selectPostion, new DialogInterface.OnClickListener()

        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onItemSelectedListenerTaskType.onSelected(data.get(which));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    /**
     * 治安案件
     */

    public interface OnItemSelectedListenerSecurityCase {
        void onSelected(PerformanceAppraisalEntity entity);
    }

    public void showDialogSecurityCase(Context context, String title, String defaultValue, final List<PerformanceAppraisalEntity> data, final
    OnItemSelectedListenerSecurityCase onItemSelectedListenerSecurityCase) {
        int selectPostion = 0;
        String items[] = new String[data.size()];
        int i = 0;
        for (PerformanceAppraisalEntity entity : data) {
            items[i] = entity.getContent();
            if (defaultValue.equals(entity.getContent())) {
                selectPostion = i;
            }
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);  //先得到构造器
        View mTitleView = LayoutInflater.from(context).inflate(R.layout.dialog_title, null);
        TextView tvTitle = (TextView) mTitleView.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        } else {
            tvTitle.setText("请选择");
        }
        ImageView iv_Title = (ImageView) mTitleView.findViewById(R.id.iv_title);
        iv_Title.setImageResource(R.mipmap.type_white);
        builder.setCustomTitle(mTitleView);
        builder.setSingleChoiceItems(items, selectPostion, new DialogInterface.OnClickListener()

        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onItemSelectedListenerSecurityCase.onSelected(data.get(which));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 一级选择
     */
    public interface OnItemSelectedListenerTaskDistributeEntity {
        void onSelected(TaskDistributeEntity taskDistributeEntity);
    }

    public void showDialogTaskDistributeEntity(Context context, String title, String defaultValue, final List<TaskDistributeEntity> data, final
    OnItemSelectedListenerTaskDistributeEntity onItemSelectedListenerTaskDistributeEntity) {
        int selectPostion = 0;
        String items[] = new String[data.size()];
        int i = 0;
        for (TaskDistributeEntity taskDistributeEntity : data) {
            items[i] = taskDistributeEntity.getName();
            if (defaultValue.equals(taskDistributeEntity.getName())) {
                selectPostion = i;
            }
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);  //先得到构造器
        View mTitleView = LayoutInflater.from(context).inflate(R.layout.dialog_title, null);
        TextView tvTitle = (TextView) mTitleView.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        } else {
            tvTitle.setText("请选择");
        }
        ImageView iv_Title = (ImageView) mTitleView.findViewById(R.id.iv_title);
        iv_Title.setImageResource(R.mipmap.type_white);
        builder.setCustomTitle(mTitleView);
        builder.setSingleChoiceItems(items, selectPostion, new DialogInterface.OnClickListener()

        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onItemSelectedListenerTaskDistributeEntity.onSelected(data.get(which));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    /**
     * 任务选择选择
     */
    public interface OnItemSelectedListenerTaskDistributeTask {
        void onSelected(TaskDistributeTaskEntity entity);
    }

    public void showDialogTaskDistributeTaskEntity(Context context, String title, String defaultValue, final List<TaskDistributeTaskEntity> data,
                                                   final
                                                   OnItemSelectedListenerTaskDistributeTask onItemSelectedListenerTaskDistributeTask) {
        int selectPostion = 0;
        String items[] = new String[data.size()];
        int i = 0;
        for (TaskDistributeTaskEntity entity : data) {
            items[i] = entity.getName();
            if (defaultValue.equals(entity.getName())) {
                selectPostion = i;
            }
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);  //先得到构造器
        View mTitleView = LayoutInflater.from(context).inflate(R.layout.dialog_title, null);
        TextView tvTitle = (TextView) mTitleView.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        } else {
            tvTitle.setText("请选择");
        }
        ImageView iv_Title = (ImageView) mTitleView.findViewById(R.id.iv_title);
        iv_Title.setImageResource(R.mipmap.type_white);
        builder.setCustomTitle(mTitleView);
        builder.setSingleChoiceItems(items, selectPostion, new DialogInterface.OnClickListener()

        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onItemSelectedListenerTaskDistributeTask.onSelected(data.get(which));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


}
