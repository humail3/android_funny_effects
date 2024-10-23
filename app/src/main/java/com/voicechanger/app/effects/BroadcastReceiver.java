package com.voicechanger.app.effects;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


import java.util.Objects;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context mCtx, Intent classIntent) {
        if (classIntent.getAction() != null) {
            if (Objects.equals(classIntent.getAction(), Intent.ACTION_SCREEN_ON)) {
//                OpenAppAds.isScreenOnOff = true;
            } else if (Objects.equals(classIntent.getAction(), Intent.ACTION_SCREEN_OFF)) {
//                OpenAppAds.isScreenOnOff = true;
            }
        }
    }

    public IntentFilter getDataFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        return intentFilter;
    }
}
