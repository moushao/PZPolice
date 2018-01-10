package com.pvirtech.pzpolice.ui.base;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cjt2325.cameralibrary.CameraActivity;
import com.google.gson.JsonObject;
import com.pvirtech.multiimageselectorlibrary.MultiImageSelector;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.MyTasksEntity;
import com.pvirtech.pzpolice.entity.SecurityCasePointEntity;
import com.pvirtech.pzpolice.entity.TaskAndIntegration;
import com.pvirtech.pzpolice.enumeration.CaseComeEnum;
import com.pvirtech.pzpolice.enumeration.FileEnum;
import com.pvirtech.pzpolice.enumeration.ScoreDistributionEnum;
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
import com.pvirtech.pzpolice.third.SublimePickerFragmentUtils;
import com.pvirtech.pzpolice.ui.activity.work.SelectPersonActivity;
import com.pvirtech.pzpolice.ui.adapter.CasePointAdapter;
import com.pvirtech.pzpolice.ui.adapter.PictureAdapter;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.ListUtils;
import com.tonypy.tonypy.addressbooks.entity.Group;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by pd on 2016/9/19.
 */
public abstract class BaseCaseActivity extends BaseActivity {
    @BindView(R.id.iv_upload_picture)
    ImageView ivUploadPicture;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    @BindView(R.id.ll_upload_picture)
    LinearLayout llUploadPicture;
    @BindView(R.id.recycleview_select_picture)
    RecyclerView recycleviewSelectPicture;
    @BindView(R.id.tv_join_person)
    TextView tvJoinPerson;
    @BindView(R.id.ll_join_person)
    LinearLayout llJoinPerson;
    @BindView(R.id.tv_join_persons)
    TextView tvJoinPersons;

    @BindView(R.id.tv_case_point)
    public TextView tvCasePoint;
    @BindView(R.id.case_point_recyclerview)
    RecyclerView casePointRecyclerview;
    @BindView(R.id.tv_mp)
    TextView tvMp;
    @BindView(R.id.ll_mp)
    LinearLayout llMp;

    public ArrayList<String> mSelectPicturePath = new ArrayList<>();//选择文件的路径
    public PictureAdapter pictureAdapter;//文件adapter
    public ArrayList<Group.GroupUSERBean> listPersons = new ArrayList<>();//选择的人员数据
    public String taskTypeID = "";//选择任务的id
    public TaskAndIntegration taskAndIntegration=new TaskAndIntegration();//请求到的案件和积分规则
    List<TaskAndIntegration.SCOREWAYLISTBean> scorewaylistBeanList = new ArrayList<>();
    public TaskAndIntegration.TYPELISTBean mTypelistBean = new TaskAndIntegration.TYPELISTBean();
    public CasePointAdapter casePointAdapter;//分值分配adpter
    public List<SecurityCasePointEntity> securityCasePointList = new ArrayList<>();//分值分配的详情数据
    public int caseComeType = 0;
    public MyTasksEntity myTasksEntity;
    public String id = "0";
    public SublimePickerFragmentUtils sublimePickerFragmentUtils = new SublimePickerFragmentUtils();
    public String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setBaseContentView(int layout) {
        setContentView(layout);
    }

    public void initData() {
        Intent intent = getIntent();
        caseComeType = intent.getIntExtra(Constant.CASE_COME_TYPE, 0);
        if (caseComeType == CaseComeEnum.AUTONOMY.getValue()) {
            try {
                id = intent.getStringExtra("id");
                title = intent.getStringExtra(Constant.TITLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (caseComeType == CaseComeEnum.TASK.getValue()) {
            try {
                myTasksEntity = intent.getParcelableExtra("data");
                id = myTasksEntity.getCASE_TYPE() + "";
                title = myTasksEntity.getCASE_NAME();
                taskTypeID = myTasksEntity.getTASK_CHILDE_TYPE() + "";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initCommonView() {


        casePointAdapter = new CasePointAdapter(mContext, securityCasePointList, new CasePointAdapter
                .OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {

                casePointAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        casePointRecyclerview.setAdapter(casePointAdapter);
        casePointRecyclerview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        casePointRecyclerview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
        casePointRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 1));

        pictureAdapter = new PictureAdapter(mContext, mSelectPicturePath, new PictureAdapter
                .OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                if (position < pictureAdapter.getList().size()) {
                    pictureAdapter.getList().remove(position);
                    pictureAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        recycleviewSelectPicture.setAdapter(pictureAdapter);
        recycleviewSelectPicture.setLayoutManager(new GridLayoutManager(mContext, 4));
        ivUploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });


        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CameraActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("imgPath", AppValue.getInstance().getImgPath());//应用图片的路径
                bundle.putString("USERNAME", AppValue.getInstance().getmUserInfo().getUSERNAME());//登录的用户名
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
        llJoinPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(mContext, SelectPersonActivity.class);
                intent1.putExtra("type", Constant.RESULT_SELECT_PERSON);
                startActivityForResult(intent1, 1);
            }
        });


        if (caseComeType == CaseComeEnum.TASK.getValue()) {
            Double totalScore = myTasksEntity.getTOTAL_SCORE() / (double) myTasksEntity.getTOTAL_COUNT();
            tvCasePoint.setText(totalScore + "");
            initsSecurityCasePoint(totalScore, myTasksEntity.getSCORE_WAY());
        }
        tvMp.setText(AppValue.getInstance().getmUserInfo().getNAME());
    }


    public void initsSecurityCasePoint(double totalScore, int SCORE_WAY) {
        securityCasePointList.clear();
        List<TaskAndIntegration.SCOREWAYLISTBean> scoreway = new ArrayList<>();
        if (caseComeType == CaseComeEnum.TASK.getValue()) {
            if (!ListUtils.isEmpty(scorewaylistBeanList)) {
                scoreway = scorewaylistBeanList;
            }

        } else if (caseComeType == CaseComeEnum.AUTONOMY.getValue()) {
            if (!ListUtils.isEmpty(taskAndIntegration.getSCOREWAYLIST())) {
                scoreway = taskAndIntegration.getSCOREWAYLIST();
            }
        }

        if (null != AppValue.getInstance().getmUserInfo().getNAME()) {
            SecurityCasePointEntity temp = new SecurityCasePointEntity(AppValue.getInstance().getmUserInfo().getNAME(), AppValue.getInstance()
                    .getmUserInfo().getUSER_ID());
            securityCasePointList.add(temp);
        }
        for (Group.GroupUSERBean groupUSERBean : listPersons) {
            SecurityCasePointEntity temp = new SecurityCasePointEntity(groupUSERBean.getName(), groupUSERBean.getUser_ID());
            securityCasePointList.add(temp);
        }

        /**
         * 平均分配分数
         */
        if (SCORE_WAY == ScoreDistributionEnum.AVERAGE.getValue()) {
            double doubleScore = totalScore / (double) securityCasePointList.size();
            doubleScore = dealNumber(doubleScore);
            for (SecurityCasePointEntity securityCasePointEntity : securityCasePointList) {
                securityCasePointEntity.setSCORE(doubleScore);
            }
        }

        /**
         * 固定分配分数
         */
        else if ((SCORE_WAY == ScoreDistributionEnum.FIXED.getValue() || SCORE_WAY == ScoreDistributionEnum
                .CUSTOM.getValue()) && null != scoreway && !ListUtils.isEmpty
                (scoreway)) {
            TaskAndIntegration.SCOREWAYLISTBean mScorewaylistBean = null;
            for (TaskAndIntegration.SCOREWAYLISTBean scorewaylistBean : scoreway) {
                if (scorewaylistBean.getPOLICE_COUNT() == securityCasePointList.size()) {
                    mScorewaylistBean = scorewaylistBean;
                    break;
                }
            }
            if (!ListUtils.isEmpty(scoreway)) {
                if (null == mScorewaylistBean) {
                    mScorewaylistBean = scoreway.get(scoreway.size() - 1);
                }
                if (securityCasePointList.size() >= 1) {
                    //主办民警的分数
                    double mainPolice = mScorewaylistBean.getMASTER_POLICE_SCALE() * totalScore;
                    securityCasePointList.get(0).setSCORE(mainPolice);
                    //协办民警的分数
                    if (securityCasePointList.size() >= 2) {
                        double doubleScore = (totalScore - mainPolice) / (double) (securityCasePointList
                                .size() - 1);
                        doubleScore = dealNumber(doubleScore);
                        for (int i = 1; i < securityCasePointList.size(); i++) {
                            SecurityCasePointEntity securityCasePointEntity = securityCasePointList.get(i);
                            securityCasePointEntity.setSCORE(doubleScore);
                        }
                    }
                }
            }
        }
        /**
         * 如果是自定义分配分值
         */
        if (SCORE_WAY == ScoreDistributionEnum.CUSTOM.getValue()) {

        }

        casePointAdapter.setList(securityCasePointList);
    }

    private double dealNumber(double data) {
        BigDecimal b = new BigDecimal(data);
        double result = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (result < 0) {
            result = 0;
        }
        return result;
    }

    /**
     * 查询任务的类型和分值分配方式
     */
    public void apptaskTaskTypeList(String id) {
        showLoading("");
        JsonObject data = new JsonObject();
        data.addProperty("id", id);
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);

        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apptaskTaskTypeList(httpSubmit).subscribeOn(Schedulers.newThread()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<TaskAndIntegration>>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull HttpResult<TaskAndIntegration> response) {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    if (null != response.getData()) {
                        taskAndIntegration = response.getData();
                        initTaskName();
                        initsSecurityCasePoint(mTypelistBean.getSIGLE_SCORE(), mTypelistBean.getSCORE_WAY());
                    }
                    hideLoading();
                } else {
                    showError(response.getResultMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                L.d("Error");
                showError("");
            }
        }));

    }


    /**
     * 只查询分值分配方式
     */
    public void apptaskScoreway() {
        showLoading("");
        JsonObject data = new JsonObject();
        HttpSubmit httpSubmit = new HttpSubmit();
        httpSubmit.setData(data);
        httpSubmit = HttpSubmitUtil.dealData(httpSubmit);

        getCompositeDisposable().add(RetrofitHttp.provideClientApi().apptaskScoreway(httpSubmit).subscribeOn(Schedulers.newThread()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<TaskAndIntegration.SCOREWAYLISTBean>>>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull HttpResult<List<TaskAndIntegration.SCOREWAYLISTBean>> response) {
                if (response.getResultCode().equals(ResultCode.SUCCESSED.getValue())) {
                    if (!ListUtils.isEmpty(response.getData())) {
                        scorewaylistBeanList = response.getData();
                        initsSecurityCasePoint(myTasksEntity.getTOTAL_SCORE() / (double) myTasksEntity.getTOTAL_COUNT(), myTasksEntity.getSCORE_WAY
                                ());
                    }
                    hideLoading();
                } else {
                    showError(response.getResultMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                L.d("Error");
                showError("");
            }
        }));

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
            MultiImageSelector selector = MultiImageSelector.create(this);
            selector.showCamera(showCamera);
            selector.count(maxNum);
            selector.multi();
            selector.origin(mSelectPicturePath);
            selector.start(this, com.pvirtech.multiimageselectorlibrary.Constant.REQUEST_IMAGE);
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BaseCaseActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
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

    /**
     * 计算分值分配
     */
    private void initPersonScore() {

        String person = "";
        /**
         * 去除自己
         */
        for (Group.GroupUSERBean groupUSERBean : listPersons) {
            if (AppValue.getInstance().getmUserInfo().getUSER_ID().equals(groupUSERBean.getUser_ID())) {
                listPersons.remove(groupUSERBean);
                break;
            }
        }

        for (Group.GroupUSERBean groupUSERBean : listPersons) {
            person = person + groupUSERBean.getName() + ",";
        }
        if (!TextUtils.isEmpty(person)) {
            person = person.substring(0, person.length() - 1);
        }
        tvJoinPerson.setText("(" + listPersons.size() + ")人");
        tvJoinPersons.setText(person);
        System.out.println("");
        if (caseComeType == CaseComeEnum.TASK.getValue()) {
            initsSecurityCasePoint(myTasksEntity.getTOTAL_SCORE() / (double) myTasksEntity.getTOTAL_COUNT(), myTasksEntity.getSCORE_WAY());
        } else if (caseComeType == CaseComeEnum.AUTONOMY.getValue()) {
            initsSecurityCasePoint(mTypelistBean.getSIGLE_SCORE(), mTypelistBean.getSCORE_WAY());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.RESULT_SELECT_PERSON) {//参与人员
            listPersons = data.getParcelableArrayListExtra(Constant.PERSON_SELECTED);
            initPersonScore();
        }
        if (resultCode == com.pvirtech.multiimageselectorlibrary.Constant.RESULT_IMAGE) {
            ArrayList<String> list = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
            mSelectPicturePath.addAll(list);
            removeDuplicate(mSelectPicturePath);
            pictureAdapter.setList(mSelectPicturePath);
        }
        if (resultCode == com.cjt2325.cameralibrary.Constant.TAKE_PHOTO) {
            ArrayList<String> list = data.getStringArrayListExtra(com.cjt2325.cameralibrary.Constant.PHOTOS);
            mSelectPicturePath.addAll(list);
            removeDuplicate(mSelectPicturePath);
            pictureAdapter.setList(mSelectPicturePath);
        }
    }

    public void removeDuplicate(ArrayList arlList) {
        HashSet h = new HashSet(arlList);
        arlList.clear();
        arlList.addAll(h);
    }

    public String dealEnd(String person) {
        if (!TextUtils.isEmpty(person) && person.endsWith(",")) {
            person = person.substring(0, person.length() - 1);
            return person;
        }
        return "";
    }

    public String dealTime(int time) {
        if (time <= 9) {
            return "0" + time;
        }
        return String.valueOf(time);
    }

    public void initTaskName() {
    }

    public void insertToData(List<String> mSelectPath) {
        /**
         * 保存需要上传的文件到数据库
         */
        UploadInfoDao dao = MyApplication.getInstance().getDaoSession().getUploadInfoDao();
        List<UploadInfo> uploadInfoList = new ArrayList<>();
        for (String data : mSelectPath) {
            String[] names = data.split(File.separator);
            UploadInfo uploadInfo = new UploadInfo(null, data, names[names.length - 1], 0, 0, 0, FileEnum.WAITING.getValue());
            uploadInfoList.add(uploadInfo);
        }
        dao.insertInTx(uploadInfoList);
    }

    public String getPosition() {
        return "104.078402,30.542631";
    }

    public String getLATITUDE() {
        return "104.078402";
    }


    public String getLONGITUDE() {
        return "30.542631";
    }
}
