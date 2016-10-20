package com.msj.baseframe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.msj.baseframe.bussiness.loginmvp.LoginActivity_;
import com.msj.core.utils.android.ActivityCollector;


public class RetryLoginReceiver extends BroadcastReceiver {

	public static String RETRYLOGIN_RECEIVER_ACTION = "android.msj.intent.action.RETRYLOGIN_RECEIVER_ACTION";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(RETRYLOGIN_RECEIVER_ACTION)) {
			if (!ActivityCollector.isExistActivity(LoginActivity_.class)) {
				Intent login_intent = new Intent(context, LoginActivity_.class);
				login_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(login_intent);
			}

		}
	}

}
