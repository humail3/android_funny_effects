package com.voicechanger.app.effects.allDialogs;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.voicechanger.app.effects.R;
import com.voicechanger.app.effects.allBaseAct.BaseDialog;
import com.voicechanger.app.effects.custUi.MobileState;
import com.voicechanger.app.effects.custUi.SetLanguage;
import com.voicechanger.app.effects.custUi.constatnt.AppDataException;
import com.voicechanger.app.effects.custUi.constatnt.TapClick;
import com.voicechanger.app.effects.databinding.RingSetDialogBinding;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public final class SetRingtoneDialog extends BaseDialog<RingSetDialogBinding> {
    private Activity act;
    private final Function1 onSetClick;
    private MobileState mobileState = MobileState.STATE_RINGTONE;

    public int getDialogView() {
        return R.layout.ring_set_dialog;
    }

    public Activity getAct() {
        return this.act;
    }

    public void setAct(Activity activity2) {
        Intrinsics.checkNotNullParameter(activity2, "<set-?>");
        this.act = activity2;
    }

    public Function1<MobileState, Unit> getOnSetClick() {
        return (Function1<MobileState, Unit>) this.onSetClick;
    }


    public SetRingtoneDialog(Activity activity2, boolean z, Function1<? super MobileState, Unit> function1) {
        super(activity2, z);
        this.act = activity2;
        this.onSetClick = function1;
    }

    public MobileState getMobileState() {
        return this.mobileState;
    }

    public void setMobileState(MobileState mobileState) {
        Intrinsics.checkNotNullParameter(mobileState, "<set-?>");
        this.mobileState = mobileState;
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
    }

    public void bindId() {
        assert getDataBinding() != null;
        TapClick.tap(getDataBinding().tvCancel, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                dismiss();
                return null;
            }
        });
        TapClick.tap(getDataBinding().rbRingtones, view -> {
            setMobileState(MobileState.STATE_RINGTONE);
            getDataBinding().rbRingtones.setChecked(true);
            getDataBinding().rbAlarm.setChecked(false);
            getDataBinding().rbNotification.setChecked(false);
            return null;
        });
        TapClick.tap(getDataBinding().rbAlarm, view -> {
            setMobileState(MobileState.STATE_ALARM);
            getDataBinding().rbRingtones.setChecked(false);
            getDataBinding().rbAlarm.setChecked(true);
            getDataBinding().rbNotification.setChecked(false);
            return null;
        });
        TapClick.tap(getDataBinding().rbNotification, view -> {
            setMobileState(MobileState.STATE_NOTIFICATION);
            getDataBinding().rbRingtones.setChecked(false);
            getDataBinding().rbAlarm.setChecked(false);
            getDataBinding().rbNotification.setChecked(true);
            return null;
        });
        TapClick.tap(getDataBinding().tvSet, view -> {
            getOnSetClick().invoke(getMobileState());
            dismiss();
            return null;
        });
        TapClick.tap(getDataBinding().tvCancel, view -> {
            dismiss();
            return null;
        });
    }
}
