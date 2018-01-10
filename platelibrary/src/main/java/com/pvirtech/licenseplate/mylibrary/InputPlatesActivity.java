package com.pvirtech.licenseplate.mylibrary;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 使用方法
 * <p>
 * Intent intent = new Intent(MainActivity.this, InputPlateActivity.class);
 * startActivityForResult(intent, 1);
 *
 * @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 * super.onActivityResult(requestCode, resultCode, data);
 * if (resultCode == RESULT_OK) {
 * String str = data.getExtras().getString("data");
 * System.out.println("");
 * tv.setText(str);
 * }
 * }
 */

public class InputPlatesActivity extends AppCompatActivity {
    private KeyboardView keyboardView;
    private Keyboard k1;// 省份简称键盘
    private Keyboard k2;// 数字字母键盘

    private String provinceShort[];
    private String letterAndDigit[];
    private int currentEditText = 0;//默认当前光标在第一个EditText
    private TextView tv_numbers;
    ImageView iv_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_plates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("车牌输入");//设置主标题
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 4.0以上Navigation默认false
            actionBar.setDisplayHomeAsUpEnabled(true);
            // Title默认true
            actionBar.setDisplayShowTitleEnabled(true);
            // Logo默认true
            actionBar.setDisplayUseLogoEnabled(true);
        }


        tv_numbers = (TextView) findViewById(R.id.tv_numbers);
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        if (!TextUtils.isEmpty(data)) {
            tv_numbers.setText(data);
        }

        currentEditText = tv_numbers.getText().length();
        k1 = new Keyboard(this, R.xml.province_short_keyboard_big_delete);
        k2 = new Keyboard(this, R.xml.lettersanddigit_keyboard_big);
        keyboardView = (KeyboardView) findViewById(R.id.keyboard_view);
        keyboardView.setKeyboard(k1);
        keyboardView.setEnabled(true);
        //设置为true时,当按下一个按键时会有一个popup来显示<key>元素设置的android:popupCharacters=""
        keyboardView.setPreviewEnabled(true);
        //设置键盘按键监听器
        keyboardView.setOnKeyboardActionListener(listener);
        provinceShort = new String[]{"京", "津", "冀", "鲁", "晋", "蒙", "辽", "吉", "黑", "沪", "苏", "浙", "皖", "闽", "赣", "豫", "鄂", "湘", "粤", "桂", "渝", "川",
                "贵", "云", "藏", "陕", "甘", "青", "琼", "新", "港", "澳", "台", "宁"};

        letterAndDigit = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S",
                "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};
        showKeyboard();

        iv_ok = (ImageView) findViewById(R.id.iv_ok);
        iv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnBack();
            }
        });
    }


    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            currentEditText = tv_numbers.getText().length();
            if (primaryCode == 112) { //xml中定义的删除键值为112
                currentEditText--;
//                tvList[currentEditText].setText("");//将当前EditText置为""并currentEditText-1
                String content = tv_numbers.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    content = content.substring(0, content.length() - 1);
                }
                tv_numbers.setText(content);
                if (currentEditText % 8 == 7) {
                    String content1 = tv_numbers.getText().toString();
                    if (!TextUtils.isEmpty(content1)) {
                        content1 = content1.substring(0, content1.length() - 1);
                    }
                    tv_numbers.setText(content1);
                    currentEditText--;
                    keyboardView.setKeyboard(k2);
                }
                if (currentEditText % 8 == 0) {
                    //切换为省份简称键盘
                    keyboardView.setKeyboard(k1);
                }

                if (currentEditText <= 0) {
                    currentEditText = 0;
                }
            } else { //其它字符按键
                currentEditText++;

                if (currentEditText % 8 == 1) {
                    String content = tv_numbers.getText().toString();
                    tv_numbers.setText(content + provinceShort[primaryCode]);
                    keyboardView.setKeyboard(k2);
                } else {
                    //第二位必须大写字母
                    if (currentEditText % 8 == 2 && !letterAndDigit[primaryCode].matches("[A-Z]{1}")) {
                        return;
                    }
                    String content = tv_numbers.getText().toString();
                    tv_numbers.setText(content + letterAndDigit[primaryCode]);
                    if (currentEditText % 8 == 7) {
                        String content1 = tv_numbers.getText().toString();
                        tv_numbers.setText(content1 + ",");
                        currentEditText++;
                        keyboardView.setKeyboard(k1);
                    }
                }
            }
        }
    };

    private void returnBack() {
        Intent intent = new Intent();
        String license = "";
        license = tv_numbers.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("data", license);
        intent.putExtras(bundle);
        setResult(Constant.RESULT_CAR_PLATE, intent);
        finish();
    }

    /**
     * 显示键盘
     */
    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            keyboardView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
