package com.pvirtech.pzpolice.ui.activity.bottomnavigationbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.cjt2325.cameralibrary.CameraActivity;
import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.file.upload.UploadService;
import com.pvirtech.pzpolice.main.AppLoginActivity;
import com.pvirtech.pzpolice.main.AppValue;
import com.pvirtech.pzpolice.ui.activity.personalfile.PersonalFileActivity;
import com.pvirtech.pzpolice.ui.activity.setting.versionUpdate.DownloadAppUtils;
import com.pvirtech.pzpolice.ui.activity.setting.versionUpdate.UpdateAppUtil;
import com.pvirtech.pzpolice.ui.activity.work.SettingActivity;
import com.pvirtech.pzpolice.utils.AppManager;
import com.pvirtech.pzpolice.utils.PreferenceUtils;
import com.pvirtech.pzpolice.utils.appupdate.AppUpdateManager;

import java.util.ArrayList;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class HomeMainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener, 
        NavigationView
        .OnNavigationItemSelectedListener {
    private ArrayList<Fragment> fragments;
    TaskFragment taskFragment = new TaskFragment();
    ScoreboardFragment scoreboardFragment = new ScoreboardFragment();
    WorkFragment workFragment = new WorkFragment();
    PersonFragment personFragment = new PersonFragment();

    /**
     * 设置显示主页
     */
    String[] titles = {"任务", "排行榜", "工作台", "人员薄"};
    Toolbar toolbar;
    private Context mContext = null;
    BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        Intent intent = new Intent(this, UploadService.class);
        startService(intent);
        setContentView(R.layout.activity_home_main);
        mContext = this;

        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(titles[0]);//设置主标题
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string
                .app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /**
         * NavigationView  里设置我的信息
         */
        TextView tv_name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_name);
        tv_name.setText("姓名：" + AppValue.getInstance().getmUserInfo().getNAME());
        TextView tv_no = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_no);
        tv_no.setText("警号：" + AppValue.getInstance().getmUserInfo().getUSERNAME());


        /**
         * 底部导航栏
         */
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //        BadgeItem numberBadgeItem = new BadgeItem().setBorderWidth(4).setBackgroundColor(Color.RED).setText
        // ("5").setHideOnSelect(true);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.task, mContext.getResources().getString(R
                .string.task))
                .setActiveColorResource(R.color.colorPrimary)/*.setBadgeItem(numberBadgeItem)*/).addItem(new 
                BottomNavigationItem(R.mipmap.scoreboard,
                mContext.getResources().getString(R.string.scoreboard)).setActiveColorResource(R.color.colorPrimary))
                .addItem(new
                BottomNavigationItem(R.mipmap.work_home, mContext.getResources().getString(R.string.work))
                        .setActiveColorResource(R.color
                .colorPrimary)).addItem(new BottomNavigationItem(R.mipmap.mail_list, mContext.getResources()
                .getString(R.string.person))
                .setActiveColorResource(R.color.colorPrimary)).addItem(new BottomNavigationItem(R.mipmap.camera, 
                "照相机").setActiveColorResource(R
                .color.colorPrimary)).setFirstSelectedPosition(0).setFirstSelectedPosition(2).initialise();
        fragments = getFragments();

        if (savedInstanceState == null) {
            // 正常情况下去 加载根Fragment
            setDefaultFragment();
        }

        bottomNavigationBar.setTabSelectedListener(this);

        /**
         * 添加到activity 的列表中
         */
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        switchFragment(2);
    }


    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(taskFragment);
        fragments.add(scoreboardFragment);
        fragments.add(workFragment);
        fragments.add(personFragment);
        return fragments;
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                switchFragment(position);
                toolbar.setTitle(titles[position]);
            } else {
                Intent intent = new Intent(mContext, CameraActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("imgPath", AppValue.getInstance().getImgPath());//应用图片的路径
                bundle.putString("USERNAME", AppValue.getInstance().getmUserInfo().getUSERNAME());//登录的用户名
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        }

    }

    @Override
    public void onTabUnselected(int position) {
    }

    @Override
    public void onTabReselected(int position) {

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //        if (id == R.id.action_settings) {
        //            return true;
        //        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle menu_navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_information) {
            /**
             * 查看我的个人信息
             */
            Intent intent = new Intent(mContext, PersonalFileActivity.class);
            mContext.startActivity(intent);
        } else if (id == R.id.new_message) {//新消息提醒

        } else if (id == R.id.cellular_data) {//流量统计


        } else if (id == R.id.wipe_cache) {//清空缓存

        } else if (id == R.id.account_and_security) {//账号与安全

        } else if (id == R.id.check_for_updates) {//检查更新
//                        UpdateAppUtil.updateApp(mContext);
            DownloadAppUtils.downloadForAutoInstall(mContext, "https://raw.githubusercontent" +
                    ".com/moushao/FFmpeg4Android/master/ffmpeg.apk", "demo.apk", "光明路派出所");
//            AppUpdateManager.getInstance(mContext, "https://raw.githubusercontent" +
//                    ".com/moushao/FFmpeg4Android/master/ffmpeg.apk", "").downloadAPK();
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(mContext, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.cancellation) {
            /**
             * 注销
             */
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("你确定注销吗?").setContentText
                    ("你将要注销该账户").setConfirmText("确定")
                    .setCancelText("取消").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    sDialog.dismissWithAnimation();
                    PreferenceUtils.setPrefBoolean(mContext, "IsAutoLogin", false);
                    PreferenceUtils.removePrefString(mContext, "IsAutoLoginTime");
                    Intent intent = new Intent(mContext, AppLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).show();
        } else if (id == R.id.nav_out) {//退出

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void needToUpdate() {

    }

    android.support.v4.app.FragmentTransaction ft;
    android.support.v4.app.FragmentManager fm = HomeMainActivity.this.getSupportFragmentManager();

    private void switchFragment(int index) {

        ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        for (int i = 0; i < fragments.size(); i++) {
            if (index == i) {
                if (!fragments.get(index).isAdded()) {
                    ft.add(R.id.layFrame, fragments.get(index));
                }
                ft.show(fragments.get(index));
            } else {
                ft.hide(fragments.get(i));
            }
        }
        ft.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == com.cjt2325.cameralibrary.Constant.TAKE_PHOTO || resultCode == 0) {
            bottomNavigationBar.selectTab(2);
        }
    }

}
