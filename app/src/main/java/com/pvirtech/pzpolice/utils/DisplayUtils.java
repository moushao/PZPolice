package com.pvirtech.pzpolice.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 
 * dp,sp,px转换工具类,保证尺寸大小不变
 * 
 * @author Justin Z
 * 
 */
public class DisplayUtils {

	/**
	 * px转换成dip
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		// 获取屏幕的密度
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * dip转换成px
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		// 获取屏幕的密度
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * px转换成sp
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		// 获取屏幕的密度
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * sp转换成px
	 * @param context
	 * @param spValue
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		// 获取屏幕的密度
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (spValue * scale + 0.5f);
	}
	
	/**
	 * 动态计算ListView的高度
	 * @param listView ListView对象的引用
	 * @return 返回ListView的高度
	 */
	public static int getListViewHeightBasedOnChildren(ListView listView) {

		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return 0;
		}
		// 总高度
		int totalHeight = 0;
		// 循环计算ListView Item的总高度
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		// 计算得到ListView的总高度=所有Item高度的和+所有分割线高度的和
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));

//		((MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除
//		listView.setLayoutParams(params);
		return params.height;
	}

}
