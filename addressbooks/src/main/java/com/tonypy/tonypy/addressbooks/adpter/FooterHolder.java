package com.tonypy.tonypy.addressbooks.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.tonypy.tonypy.addressbooks.R;

/**
 * Created by tonypy on 2017/04/01.
 */

public class FooterHolder extends RecyclerView.ViewHolder {
    public Button cancleBtn;
    public Button confirmBtn;
    public FooterHolder(View itemView) {
        super(itemView);
        initView();
    }
    private void initView(){
        cancleBtn=(Button)itemView.findViewById(R.id.all_selected);
        confirmBtn=(Button)itemView.findViewById(R.id.confirm);

    }
}
