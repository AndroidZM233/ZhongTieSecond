package com.speedata.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.speedata.activity.LoginActivity;
import com.speedata.activity.MainActivity;
import com.speedata.fragment.LoginFragment;

public class BootBroadcastReceiver extends BroadcastReceiver {
	static final String action_boot = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(action_boot)) {
			Intent ootStartIntent = new Intent(context, MainActivity.class);
			ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(ootStartIntent);
		}

	}
}
