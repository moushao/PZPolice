package com.pvirtech.pzpolice.test.other;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pvirtech.pzpolice.R;

import java.util.concurrent.Callable;

public class TestUtilsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_utils);
    }
    Callable d=new Callable() {
        @Override
        public String call() throws Exception {
            return "aaaaaa";
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * test
     */
    private void add() {
    }


}
