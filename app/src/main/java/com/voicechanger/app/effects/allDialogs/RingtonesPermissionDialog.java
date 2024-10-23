package com.voicechanger.app.effects.allDialogs;

import android.app.Activity;

import com.voicechanger.app.effects.R;
import com.voicechanger.app.effects.allBaseAct.BaseDialog;
import com.voicechanger.app.effects.custUi.SetLanguage;
import com.voicechanger.app.effects.custUi.constatnt.TapClick;
import com.voicechanger.app.effects.databinding.PremissionRingtoneDialogBinding;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public final class RingtonesPermissionDialog extends BaseDialog<PremissionRingtoneDialogBinding> {
    private Activity acts;
    private final Function0 allowClick;

    public int getDialogView() {
        return R.layout.premission_ringtone_dialog;
    }

    public Activity getActs() {
        return this.acts;
    }

    public void setActs(Activity activity2) {
        this.acts = activity2;
    }

    public Function0 getAllowClick() {
        return this.allowClick;
    }

    public RingtonesPermissionDialog(Activity activity2, boolean z, Function0<Unit> function0) {
        super(activity2, z);
        this.acts = activity2;
        this.allowClick = function0;
    }

    public void setLanguage() {
        SetLanguage.setLocale(this.acts);
    }

    public void initViews() {

    }

    public void bindId() {
        assert getDataBinding() != null;
        TapClick.tap(getDataBinding().tvCancel, view -> {
            dismiss();
            return null;
        });
        TapClick.tap(getDataBinding().tvAllow, view -> {

            getAllowClick().invoke();
            dismiss();
            return null;
        });
    }
}
