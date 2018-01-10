package com.pvirtech.pzpolice.ui.activity.work;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.base.BaseActivity;
import com.pvirtech.pzpolice.ui.contract.DealTaskActivityContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class DealTaskActivity extends BaseActivity implements DealTaskActivityContract.View {
    DealTaskActivityContract.Presenter presenter;
    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    private Button reset;
    private EditText mission_quantity;
    private EditText remarks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_task);
        ButterKnife.bind(this);
        initTitleView("提交任务");
        mContext = DealTaskActivity.this;
        TAG = "DealTaskActivity";

        Spinner spinner = (Spinner)findViewById(R.id.mission_unit);
        data_list = new ArrayList<String>();
        data_list.add("个");
        data_list.add("组");
        data_list.add("项");
        arr_adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,data_list);
        arr_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arr_adapter);

        ImageView add_people = (ImageView)findViewById(R.id.imageButton);
        add_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        remarks = (EditText)findViewById(R.id.remarks);
        mission_quantity = (EditText)findViewById(R.id.mission_quantity);
        reset = (Button)findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mission_quantity.setText("");
                remarks.setText("");
            }
        });

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
}
