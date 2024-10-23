package com.voicechanger.app.effects.custUi.constatnt;

import android.view.View;

import com.voicechanger.app.effects.tapListener.CustomTapListener;

import kotlin.jvm.functions.Function1;

public final class TapClick {
    public static void tap(View view, Function1 function1) {
        view.setOnClickListener(new CustomTapListener() {
            @Override
            public void onTap(View view) {
                function1.invoke(view);
            }
        });
    }

}
