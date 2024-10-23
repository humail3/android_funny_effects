package com.voicechanger.app.effects.allDialogs;

import android.app.Activity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.voicechanger.app.effects.FilenameUtils;
import com.voicechanger.app.effects.R;
import com.voicechanger.app.effects.allBaseAct.BaseDialog;
import com.voicechanger.app.effects.custUi.SetLanguage;
import com.voicechanger.app.effects.custUi.constatnt.AppDataException;
import com.voicechanger.app.effects.custUi.constatnt.TapClick;
import com.voicechanger.app.effects.databinding.RenameDialogBinding;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.text.StringsKt;

public final class RenameDialog extends BaseDialog<RenameDialogBinding> {
    private final Activity act;
    private final String strName;
    private final Function2 function2;
    private String strTypeEffect;

    @Override
    public int getDialogView() {
        return R.layout.rename_dialog;
    }

    public RenameDialog(Activity act, boolean z, String strName, Function2 function2) {
        super(act, z);
        this.act = act;
        this.strName = strName;
        this.function2 = function2;
        this.strTypeEffect = "";
    }

    @Override
    public void setLanguage() {
        SetLanguage.setLocale(this.act);
    }

    @Override
    public void initViews() {
        Window getWin = getWindow();
        WindowManager.LayoutParams params = getWin == null ? null : getWin.getAttributes();
        if (params != null) {
            params.width = (int) (AppDataException.getWithMetrics(this.act) * 0.9d);
        }

        String name = FilenameUtils.getBaseName(strName);

        String[] s1 = name.split("_");
        String s2 = s1[0];
        strTypeEffect = s1[1];

        getDataBinding().input.setText(s2);
    }

    @Override
    public void bindId() {
        TapClick.tap(getDataBinding().tvCancel, view -> {
            Log.e("rename---", "invoke: tvCancel :: ");
            dismiss();
            return Unit.INSTANCE;
        });
        TapClick.tap(getDataBinding().tvSet, view -> {
            String strNewName = StringsKt.trim(getDataBinding().input.getText().toString()).toString();
            if (strNewName.equals("")) {
                Toast.makeText(act, act.getResources().getText(R.string.please_enter_text), Toast.LENGTH_SHORT).show();
            } else if (AppDataException.getSpecialChar(strNewName)) {
                Toast.makeText(act, act.getResources().getText(R.string.there_is_a_special_character), Toast.LENGTH_SHORT).show();
            } else {
                function2.invoke(strNewName, strTypeEffect);
            }
            return null;
        });
        TapClick.tap(getDataBinding().ivDel, view -> {
            getDataBinding().input.setText("");
            return Unit.INSTANCE;
        });
    }
}
