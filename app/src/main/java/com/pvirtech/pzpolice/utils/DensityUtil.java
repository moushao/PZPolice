package com.pvirtech.pzpolice.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.content.Context;

import java.util.List;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;

/**
 * 密度转换工具类
 */
public class DensityUtil {

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(final float density, final float dpValue) {
		return (int) (dpValue * density + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(final float density, final float pxValue) {
		return (int) (pxValue / density + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, final float dpValue) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * density + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, final float pxValue) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / density + 0.5f);
	}

	// 判断程序是否在后台运行
	public static boolean isBackgroundRunning(Context ctx) {
		String processName = "org.metroclient.two";
		ActivityManager activityManager = (ActivityManager) ctx
				.getSystemService(Context.ACTIVITY_SERVICE);
		KeyguardManager keyguardManager = (KeyguardManager) ctx
				.getSystemService(Context.KEYGUARD_SERVICE);

		if (activityManager == null)
			return false;
		// get running application processes
		List<ActivityManager.RunningAppProcessInfo> processList = activityManager
				.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo process : processList) {
			if (process.processName.startsWith(processName)) {
				boolean isBackground = process.importance != IMPORTANCE_FOREGROUND
						&& process.importance != IMPORTANCE_VISIBLE;
				boolean isLockedState = keyguardManager
						.inKeyguardRestrictedInputMode();
				if (isBackground || isLockedState)
					return true;
				else
					return false;
			}
		}
		return false;
	}

	// android 如何判断程序是否在前台运行
	public static boolean isTopActivity(Context ctx) {
		ActivityManager activityManager = (ActivityManager) ctx
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
		if (tasksInfo.size() > 0) {
			// 应用程序位于堆栈的顶层
			if ("org.metroclient.two".equals(tasksInfo.get(0).topActivity
					.getPackageName())) {
				return true;
			}
		}
		return false;
	}
	// public static AlertDialog getNoTitleDialog(Activity context, String info,
	// String rightText, final OnClickListener onClickListener) {
	//
	// final AlertDialog dialog = new MyDialog.Builder(context).create();
	// dialog.show();
	// dialog.setCanceledOnTouchOutside(false);
	// Window view = dialog.getWindow();
	// view.setContentView(R.layout.dialog_no_title);
	// view.setGravity(Gravity.BOTTOM|Gravity.LEFT);
	// TextView tv_msg_info = (TextView) view.findViewById(R.id.tv_msg);
	// tv_msg_info.setText(info);
	// Button bt_back = (Button) view.findViewById(R.id.bt_ok);
	// dialog.setCancelable(false);
	// if (!TextUtils.isEmpty(rightText)) {
	// bt_back.setText(rightText);
	// }
	// bt_back.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// if (onClickListener != null)
	// onClickListener.onClick(v);
	// dialog.dismiss();
	// }
	// });
	// View bt_cancel = view.findViewById(R.id.bt_cancel);
	// bt_cancel.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// dialog.dismiss();
	// }
	// });
	// dialog.setCancelable(false);
	// return dialog;
	// }

}