package com.tonypy.tonypy.addressbooks;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.tonypy.tonypy.addressbooks.adpter.HotelEntityAdapter;
import com.tonypy.tonypy.addressbooks.adpter.SectionedSpanSizeLookup;
import com.tonypy.tonypy.addressbooks.entity.HotelEntity;
import com.tonypy.tonypy.addressbooks.utils.JsonUtils;

/**
 * Created by tonypy on 2017/04/10.
 */

public class CustomDialog extends Dialog {
    private Context context;
    private RecyclerView mRecyclerView;
    private HotelEntityAdapter mAdapter;
    public CustomDialog(Context context, int themeResId) {
        super(context, R.style.CustomDialog);
        this.context = context;
        initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (CustomDialog.this.isShowing())
                    CustomDialog.this.dismiss();
                break;
        }
        return true;
    }

    private void initView() {

        setContentView(R.layout.dialog_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new HotelEntityAdapter(context);
        GridLayoutManager manager = new GridLayoutManager(context,4);
        //设置header
        manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter,manager));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        HotelEntity entity = JsonUtils.analysisJsonFile(context,"json");
//        mAdapter.setData(entity.allTagsList);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = 1.0f;
        getWindow().setAttributes(attributes);
        setCancelable(true);
    }
}
