package com.pvirtech.pzpolice.test.other;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.UI;
import com.pvirtech.pzpolice.utils.L;

import java.util.ArrayList;
import java.util.List;

public class XMLTestActivity extends AppCompatActivity {

    private TableLayout pnlContent;
    private Button btnSave;
    private  List<TextView> myUI = new ArrayList<>();
    private  List<UI> spinnerUI = new ArrayList<>();
    private String TAG = "XMLTestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        UI ui01 = new UI("TextView", "TextView01");
//        UI ui02 = new UI("EditText", "EditText01");
//        myUI.add(ui01);
//        myUI.add(ui02);
        setContentView(R.layout.activity_xmltest);

        pnlContent = (TableLayout) this.findViewById(R.id.pnlContent);
        btnSave = (Button) this.findViewById(R.id.button1);

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < pnlContent.getChildCount(); i++) {
                    TableRow tr = (TableRow) pnlContent.getChildAt(i);

                    TextView txt1 = (TextView) tr.getChildAt(0);
                    EditText et1 = (EditText) tr.getChildAt(1);
                    sb.append(String.format("{%s}={%s}\n", txt1.getTag().toString(),
                            et1.getText()));
                }
            }

        });

        //下面是生成表单的操作
        for (int i = 0; i < 5; i++) {
            TableRow row = new TableRow(this);
            TextView txtView1 = new TextView(this);
            EditText edit1 = new EditText(this);
            txtView1.setText("lable" + i + ":");
            txtView1.setTag("lable" + i);
            txtView1.setPadding(3, 3, 3, 3);
            myUI.add(txtView1);
            edit1.setTag("text" + i);
            edit1.setPadding(3, 3, 3, 3);
            //LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            row.addView(txtView1);
            row.addView(edit1);


            ViewGroup.LayoutParams layoutParams2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams
                    .WRAP_CONTENT);
            pnlContent.addView(row, layoutParams2);
        }
        pnlContent.setColumnStretchable(1, true);
        //stretchColumns

        for (int i = 0; i < 2; i++) {
            final UI ui = new UI();
            Spinner spinner = new Spinner(this);
            ui.spinner = spinner;
            ui.value = "";
            spinnerUI.add(ui);
            final String[] mItems = {"aaa" + i, "bbb" + i, "ccc" + i, "ddd" + i};
            // 建立Adapter并且绑定数据源
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
            //绑定 Adapter到控件
            spinner.setTag("spinner" + i);
            spinner.setAdapter(adapter);
            pnlContent.addView(spinner);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    ui.value = mItems[i];
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }

        //
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (UI sp : spinnerUI) {
                    Spinner spinner = sp.spinner;
                    int postion = spinner.getFirstVisiblePosition();
                    String s = sp.value;
                    L.d(TAG, s);
                }
//                for (TextView te : myUI) {
//                    String s = (String) te.getTag();
////                    String s = te.getText().toString();
//                    L.d(TAG, s);
//                }
            }
        });
    }


}


