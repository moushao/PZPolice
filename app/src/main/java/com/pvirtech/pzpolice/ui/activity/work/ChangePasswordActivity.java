package com.pvirtech.pzpolice.ui.activity.work;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.base.BaseActivity;

import butterknife.ButterKnife;

public class ChangePasswordActivity extends BaseActivity {
    private EditText password = null;
    private EditText verifypassword = null;
    private CheckBox show = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        initTitleView("找回密码");
        mContext = ChangePasswordActivity.this;
        TAG = "ChangePasswordActivity";
        this.password = (EditText) super.findViewById(R.id.get_password);
        this.verifypassword = (EditText) super.findViewById(R.id.verify_password);
        this.show = (CheckBox) super.findViewById(R.id.show_password);
        this.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ChangePasswordActivity.this.show.isChecked()) {
                    ChangePasswordActivity.this.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ChangePasswordActivity.this.verifypassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    ChangePasswordActivity.this.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ChangePasswordActivity.this.verifypassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        final EditText edit_password = (EditText) findViewById(R.id.get_password);
        edit_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edit_password.length() == 0) {
                    edit_password.setError("密码不能为空");
                }
            }
        });
        final EditText edit_password_verify = (EditText) findViewById(R.id.verify_password);
        edit_password_verify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edit_password.getText().toString().equals(edit_password_verify.getText().toString())) {
                } else {
                    edit_password_verify.setError("两次密码不一致");
                }
            }
        });
        final EditText edit_phone_number = (EditText) findViewById(R.id.get_phone_number);
        edit_phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        final EditText edit_verify_code = (EditText)findViewById(R.id.get_verify_code);
        edit_verify_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(edit_verify_code.getText().length()!=4)
                {
                    edit_verify_code.setError("请输入4位验证码");
                }
            }
        });

        Button bt_get_verify_code = (Button) findViewById(R.id.bt_get_verify_code);
        bt_get_verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_phone_number.getText().toString().length() != 11) {
                    edit_phone_number.setError("请输入11位手机号");
                }
            }
        });
    }
}

