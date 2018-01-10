package com.pvirtech.pzpolice.ui.activity.log;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cjt2325.cameralibrary.CameraActivity;
import com.dd.CircularProgressButton;
import com.google.gson.JsonObject;
import com.pvirtech.multiimageselectorlibrary.MultiImageSelector;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.WorkLogEntity;
import com.pvirtech.pzpolice.enumeration.FileEnum;
import com.pvirtech.pzpolice.file.upload.UploadInfo;
import com.pvirtech.pzpolice.file.upload.UploadInfoDao;
import com.pvirtech.pzpolice.http.HttpResult;
import com.pvirtech.pzpolice.http.HttpSubmit;
import com.pvirtech.pzpolice.http.HttpSubmitUtil;
import com.pvirtech.pzpolice.http.ResultCode;
import com.pvirtech.pzpolice.http.RetrofitHttp;
import com.pvirtech.pzpolice.main.AppValue;
import com.pvirtech.pzpolice.main.Constant;
import com.pvirtech.pzpolice.main.MyApplication;
import com.pvirtech.pzpolice.ui.adapter.PictureAdapter;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ReviewWorkLogActivity extends BaseActivity {
    WorkLogEntity workLogEntity;
    ArrayList<String> mSelectPicturePath = new ArrayList<>();
    PictureAdapter adapter;
    @BindView(R.id.et_work_log)
    EditText etWorkLog;
    @BindView(R.id.iv_upload_picture)
    ImageView ivUploadPicture;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    @BindView(R.id.ll_upload_picture)
    LinearLayout llUploadPicture;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.submit)
    CircularProgressButton submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_work_log);
        initTitleView("查看日志");
        TAG = "ReviewWorkLogActivity";
        initData();
        initView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void initData() {
        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            workLogEntity = bundle.getParcelable(Constant.INTENT_BUNDLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        adapter = new PictureAdapter(mContext, mSelectPicturePath, new PictureAdapter
                .OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                if (position < adapter.getList().size()) {
                    adapter.getList().remove(position);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        recycleview.setAdapter(adapter);
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 4));

        etWorkLog.setText(workLogEntity.getCONTENT());
    }


    @OnClick({R.id.iv_upload_picture, R.id.iv_camera, R.id.submit})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_upload_picture:
                pickImage();
                break;
            case R.id.submit:
                String content = etWorkLog.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toasty.warning(mContext, "请输入日志内容！").show();
                    return;
                }

                String RESOURCE_ID = "";
                for (String data : mSelectPicturePath) {
                    String[] splitString = data.split("/");
                    RESOURCE_ID = RESOURCE_ID + splitString[splitString.length - 1] + ",";
                }
                RESOURCE_ID = dealEnd(RESOURCE_ID);

                submit(mSelectPicturePath, workLogEntity.getID(), content, "104.078402", "30.542631", getString(R
                        .string.police_station), RESOURCE_ID);
                break;
            case R.id.iv_camera:
                intent = new Intent(mContext, CameraActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("imgPath", AppValue.getInstance().getImgPath());//应用图片的路径
                bundle.putString("USERNAME", AppValue.getInstance().getmUserInfo().getUSERNAME());//登录的用户名
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                break;
        }
    }

    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    com.pvirtech.multiimageselectorlibrary.Constant.REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            boolean showCamera = true;
            int maxNum = 9;
            MultiImageSelector selector = MultiImageSelector.create(mContext);
            selector.showCamera(showCamera);
            selector.count(maxNum);
            selector.multi();
            selector.origin(mSelectPicturePath);
            selector.start(ReviewWorkLogActivity.this, com.pvirtech.multiimageselectorlibrary.Constant.REQUEST_IMAGE);
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(ReviewWorkLogActivity.this, permission)) {
            new AlertDialog.Builder(ReviewWorkLogActivity.this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ReviewWorkLogActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(ReviewWorkLogActivity.this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == com.pvirtech.multiimageselectorlibrary.Constant.REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void submit(final List<String> mSelectPicturePath, String id, String content, String latitude, String longitude, String position, String
            resourceid) {
        if (submit.getProgress() != 0) {
            return;
        }
        /**
         * 处理提交数据
         */
        JsonObject data = new JsonObject();
        data.addProperty("id", id);
        data.addProperty("content", content);
        data.addProperty("latitude", latitude);
        data.addProperty("longitude", longitude);
        data.addProperty("position", position);
        data.addProperty("resourceid", resourceid);

        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);
        submit.setProgress(50);
        submit.setIndeterminateProgressMode(true);

        getCompositeDisposable().add(RetrofitHttp.provideClientApi().appWorkLogUpdateWorkLog(httpSubmit).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new io.reactivex.functions.Consumer<HttpResult>() {
            @Override
            public void accept(@NonNull HttpResult response) throws Exception {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    submit.setProgress(0);
                    insertToData(mSelectPicturePath);
                    new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE).setContentText("日志保存成功！")
                            .setConfirmText("确定")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    finish();
                                }
                            }).show();
                } else {
                    Toasty.error(mContext, response.getResultMessage()).show();
                    submit.setProgress(0);
                }
                L.d("sucess");
            }
        }, new io.reactivex.functions.Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                L.d("sucess");
                Toasty.error(mContext, mContext.getResources().getString(R.string.submint_failed)
                ).show();
                submit.setProgress(0);

            }
        }));


    }

    private void insertToData(List<String> mSelectPath) {
        /**
         * 保存需要上传的文件到数据库
         */
        UploadInfoDao dao = MyApplication.getInstance().getDaoSession().getUploadInfoDao();
        List<UploadInfo> uploadInfoList = new ArrayList<>();
        for (String data : mSelectPath) {
            UploadInfo uploadInfo = new UploadInfo(null, data, data, 0, 0, 0, FileEnum.WAITING.getValue());
            uploadInfoList.add(uploadInfo);
        }
        dao.insertInTx(uploadInfoList);
    }

    public String dealEnd(String person) {
        if (!TextUtils.isEmpty(person) && person.endsWith(",")) {
            person = person.substring(0, person.length() - 1);
            return person;
        }
        return "";
    }
}
