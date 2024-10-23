package com.voicechanger.app.effects.custUi.constatnt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.voicechanger.app.effects.allDialogs.RingtonesPermissionDialog;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public final class RingtonePermission {
    public static boolean checkSystemWritePermission(Context context) {
        return Settings.System.canWrite(context);
    }

    public static void openAndroidPermissionsMenu(Activity activity) {
        new RingtonesPermissionDialog(activity, true, new Function0<Unit>() {
            @Override
            public Unit invoke() {
                Intent settingIntent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
                settingIntent.setData(Uri.parse("package:com.soundeffect.voiceavatar.changer"));
                activity.startActivity(settingIntent);
                return null;
            }
        }).show();
    }
}
