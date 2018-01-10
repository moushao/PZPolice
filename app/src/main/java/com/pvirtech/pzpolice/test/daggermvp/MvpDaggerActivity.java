package com.pvirtech.pzpolice.test.daggermvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.test.daggermvp.contract.MyContract;
import com.pvirtech.pzpolice.test.daggermvp.module.MyModule;
import com.pvirtech.pzpolice.test.daggermvp.presenter.MyPresenterImpl;
import com.pvirtech.pzpolice.utils.L;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MvpDaggerActivity extends AppCompatActivity implements MyContract.View, View.OnClickListener {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;

    @Inject
    MyPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMyComponent.builder().myModule(new MyModule(this)).build().inject(this);
        setContentView(R.layout.activity_mvp);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClearText() {
        button.setText("cleard");
        button2.setText("cleard222");
    }

    @Override
    public void onLoginResult(Boolean result, int code) {
        button.setText("登录成功");
        L.d("登录成功");
    }

    @OnClick({R.id.button, R.id.button2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
               /* Button button=null;
                button.setText("error");*/
                presenter.clear();
                break;
            case R.id.button2:
                presenter.doLogin("userName", "PassWord");
                break;
        }
    }
}
