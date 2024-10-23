package com.voicechanger.app.effects.allDialogs;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import com.voicechanger.app.effects.R;
import com.voicechanger.app.effects.allBaseAct.BaseDialog;
import com.voicechanger.app.effects.custUi.SetLanguage;
import com.voicechanger.app.effects.custUi.constatnt.AppDataException;
import com.voicechanger.app.effects.custUi.constatnt.TapClick;
import com.voicechanger.app.effects.databinding.AudioNotSaveDialogBinding;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public final class NotRecordedDialog extends BaseDialog<AudioNotSaveDialogBinding> {
    private final Activity act;
    private String strContent;
    private final Function0 function0;

    private String strPos;

    public int getDialogView() {
        return R.layout.audio_not_save_dialog;
    }

    public Function0 getFunction0() {
        return function0;
    }

    public NotRecordedDialog(String str, String str2, Activity activity2, boolean z, Function0<Unit> function0) {
        super(activity2, z);
        this.strContent = str;
        this.strPos = str2;
        this.act = activity2;
        this.function0 = function0;
    }

    public void setLanguage() {
        SetLanguage.setLocale(this.act);
    }

    public void initViews() {
        Window getWin = getWindow();
        WindowManager.LayoutParams layoutParams = getWin == null ? null : getWin.getAttributes();
        if (layoutParams != null) {
            layoutParams.width = (int) (((double) AppDataException.getWithMetrics(this.act)) * 0.9d);
        }
        getDataBinding().tvContent.setText(this.strContent);
        getDataBinding().tvExit.setText(strPos);
    }

    public void bindId() {
        TapClick.tap(getDataBinding().tvCancel, view -> {
            dismiss();
            return null;
        });
        TapClick.tap(getDataBinding().tvExit, view -> {
            getFunction0().invoke();
            dismiss();
            return null;
        });
    }
}
