package com.pvirtech.pzpolice.ui.activity.work;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.main.MyApplication;
import com.pvirtech.pzpolice.third.DatePickerDialog;
import com.pvirtech.pzpolice.third.baidu.LocationService;
import com.pvirtech.pzpolice.third.view.DaKaView;
import com.pvirtech.pzpolice.ui.appInterfaces.OnSelectedListener;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.ui.contract.AttendanceContract;
import com.pvirtech.pzpolice.utils.L;
import com.pvirtech.pzpolice.utils.TimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pvirtech.pzpolice.R.id.tv_date;


/**
 * 考勤打卡
 */
public class AttendanceActivity extends BaseActivity implements AttendanceContract.View {
    AttendanceContract.Presenter presenter;
    @BindView(tv_date)
    TextView tvDate;
    @BindView(R.id.daka_view)
    DaKaView dakaView;
    @BindView(R.id.tv_daka_time)
    TextView tvDakaTime;

    boolean blnIsUpTime = true;
    Handler mHandler = new Handler();
    Runnable runnableRefresh;
    private LocationService locationService;
    String latitude;
    String lontitude;
    String addr;
    String locationdescribe;
    boolean blnIsLocation = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        ButterKnife.bind(this);
        initTitleView("考勤打卡");
        mContext = AttendanceActivity.this;
        TAG = "LeaveActivity";
        initView();
        presenter.initData("", "");


    }

    @Override
    public void onStart() {
        // -----------location config ------------
        locationService = ((MyApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();// 定位SDK

        super.onStart();
    }

    @Override
    public void onStop() {
        //结束mHandler调用
        mHandler.removeCallbacks(runnableRefresh);
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    public void onInitData() {
        /*初始化今天的考勤数据*/
        runnableRefresh = new Runnable() {
            @Override
            public void run() {
                tvDakaTime.setText(TimeUtil.getHMS());
                if (blnIsUpTime) {
                    mHandler.postDelayed(this, 1000);
                }
            }
        };
        mHandler.post(runnableRefresh);

    }

    @Override
    public void initViewASignOut(String time, String address, String status) {

    }

    @Override
    public void initViewASign(String time, String address, String status) {

    }

    @Override
    public void submitting() {

    }

    @Override
    public void submitSuccess() {

    }

    @Override
    public void submitFailed() {

    }

    @Override
    public void showWarning(String data) {

    }

    @Override
    public void viewShowLoading(String msg) {

    }

    @Override
    public void viewHideLoading() {

    }

    @Override
    public void viewShowError(String msg) {

    }


    private void initView() {
        tvDate.setText(TimeUtil.getYMD());
    }


    @OnClick({R.id.lin_chose_date, R.id.daka_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_chose_date:
                DatePickerDialog datePickerDialog = new DatePickerDialog();
                datePickerDialog.show(AttendanceActivity.this, tvDate.getText().toString(), new OnSelectedListener() {
                    @Override
                    public void onSelected(String data) {
                        tvDate.setText(data);
                    }
                });
                break;
            case R.id.daka_view:
                dakaView.setSearching(true);
                break;
        }
    }


    /*****
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());

                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                latitude = String.valueOf(location.getLatitude());

                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                lontitude = String.valueOf(location.getLongitude());

                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                addr = location.getAddrStr();
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                locationdescribe = location.getLocationDescribe();
                blnIsLocation = true;
                if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    blnIsLocation = false;
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    blnIsLocation = false;
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    blnIsLocation = false;
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                L.d(sb.toString());
            }
        }

        public void onConnectHotSpotMessage(String s, int i) {
        }
    };

}
