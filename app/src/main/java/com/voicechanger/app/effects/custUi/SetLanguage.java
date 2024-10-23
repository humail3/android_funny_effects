package com.voicechanger.app.effects.custUi;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;
import com.voicechanger.app.effects.MainApplication;

public class SetLanguage {

    public static void setLocale(Context mCtx) {
        String defLang = MainApplication.Companion.getPrefManager().getDefaultLanguage();
        if (defLang.length() != 0) {
            setLanguagesString((Activity) mCtx, defLang);
        }
    }

    public static void setLanguagesString(Activity act, String strLangCode) {
        Locale locales = new Locale(strLangCode);
        Locale.setDefault(locales);
        Resources actResources = act.getResources();
        Configuration actResourcesConfiguration = actResources.getConfiguration();
        actResourcesConfiguration.setLocale(locales);
        actResources.updateConfiguration(actResourcesConfiguration, actResources.getDisplayMetrics());
    }
}
