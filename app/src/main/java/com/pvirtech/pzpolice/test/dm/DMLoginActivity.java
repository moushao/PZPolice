package com.pvirtech.pzpolice.test.dm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.test.dm.contract.DMContract;
import com.pvirtech.pzpolice.test.dm.presenter.DMPresenterImpl;

import javax.inject.Inject;

public class DMLoginActivity extends AppCompatActivity implements DMContract.View, View.OnClickListener {

    private Button mLogin;
    private Button mClear;
    private EditText mName;
    private EditText mPassWord;

    @Inject
    DMPresenterImpl loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger_mvp);

        mLogin = (Button) findViewById(R.id.btn_login);
        mClear = (Button) findViewById(R.id.btn_clear);
        mName = (EditText) findViewById(R.id.et_name);
        mPassWord = (EditText) findViewById(R.id.et_password);

        mLogin.setOnClickListener(this);
        mClear.setOnClickListener(this);
        /*DaggerDMLoginActivityComponent.builder().dMLoginModule(new DMLoginModule(this)).build().inject(this);*/
    }

    @Override
    public void onClearText() {
        mName.setText("");
        mPassWord.setText("");
        Toast.makeText(this, "clear", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginResult(Boolean result, int code) {
        mLogin.setEnabled(true);
        mClear.setEnabled(true);

        if (result) {
            mLogin.setText("登陆成功: ");
            Toast.makeText(this, "登陆成功: " + code, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "登陆失败：" + code, Toast.LENGTH_SHORT).show();
            mLogin.setText("登陆失败: ");
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        String name = mName.getText().toString();
        String password = mPassWord.getText().toString();

        switch (id) {
            case R.id.btn_login:
                loginPresenter.doLogin(name, password);
                break;
            case R.id.btn_clear:
                loginPresenter.clear();
                break;
        }
    }
}
