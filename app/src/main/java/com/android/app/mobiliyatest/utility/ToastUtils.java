package com.android.app.mobiliyatest.utility;

import android.widget.Toast;

import com.android.app.mobiliyatest.MobiliyaTestApplication;

public class ToastUtils {


	public static void showToast(String str) {

		Toast.makeText(
				MobiliyaTestApplication.getAppContext(),
				str,
				Toast.LENGTH_SHORT).show();
	}
}
