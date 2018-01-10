package com.tonypy.tonypy.addressbooks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tonypy.tonypy.addressbooks.adpter.HotelEntityAdapter;
import com.tonypy.tonypy.addressbooks.adpter.SectionedSpanSizeLookup;
import com.tonypy.tonypy.addressbooks.entity.HotelEntity;
import com.tonypy.tonypy.addressbooks.utils.JsonUtils;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private HotelEntityAdapter mAdapter;
    private ImageView back_img, search_img;
    private TextView confirm_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_person);
        initView();
       /* CustomDialog dailog=new CustomDialog(this,0);
        dailog.show();*/
    }


    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.selct_recyclerView);
        mAdapter = new HotelEntityAdapter(this);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        //设置header
        manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter, manager));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        HotelEntity entity = JsonUtils.analysisJsonFile(this, "json");


    }


}
