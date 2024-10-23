package com.voicechanger.app.effects.allDialogs;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import com.voicechanger.app.effects.R;
import com.voicechanger.app.effects.allBaseAct.BaseDialog;
import com.voicechanger.app.effects.custUi.SetLanguage;
import com.voicechanger.app.effects.custUi.constatnt.AppDataException;
import com.voicechanger.app.effects.custUi.constatnt.TapClick;
import com.voicechanger.app.effects.databinding.DeleteDialogBinding;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

public final class DeleteDialog extends BaseDialog<DeleteDialogBinding> {
    private Activity act;
    private final Function0 function0;

    public int getDialogView() {
        return R.layout.delete_dialog;
    }

    public Activity getAct() {
        return this.act;
    }

    public void setAct(Activity activity2) {
        Intrinsics.checkNotNullParameter(activity2, "<set-?>");
        this.act = activity2;
    }

    public Function0 getFunction0() {
        return this.function0;
    }

    public DeleteDialog(Activity activity2, boolean z, Function0<Unit> function0) {
        super(activity2, z);
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
            layoutParams.width = (int) (AppDataException.getWithMetrics(this.act) * 0.9d);
        }
    }

    public void bindId() {
        TapClick.tap(getDataBinding().tvCancel, view -> {
            dismiss();
            return null;
        });
        TapClick.tap(getDataBinding().tvDelete, view -> {
            getFunction0().invoke();
            dismiss();
            return null;
        });
    }
}
