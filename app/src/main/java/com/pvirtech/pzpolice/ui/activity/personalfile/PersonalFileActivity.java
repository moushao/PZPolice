package com.pvirtech.pzpolice.ui.activity.personalfile;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.adapter.TitleFragmentPagerAdapter;
import com.pvirtech.pzpolice.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * 个人档案主界面
 */
public class PersonalFileActivity extends BaseActivity {

    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_file);
        ButterKnife.bind(this);
        initTitleView("个人档案");
        mContext = PersonalFileActivity.this;
        TAG = "PersonalFileActivity";
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new PersonalFileBasicFragment());
        fragments.add(new PersonalFileWorkFragment());
        fragments.add(new PersonalFileEducationFragment());
        fragments.add(new PersonalFileFamilyFragment());

        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"基本信息", "工作", "教育",
                "家庭"});
        viewpager.setAdapter(adapter);

        tab.setupWithViewPager(viewpager);


    }

}
