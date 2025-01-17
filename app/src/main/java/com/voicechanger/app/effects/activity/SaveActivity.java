package com.voicechanger.app.effects.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.reactlibrary.ChangeEffectsModule;
import com.voicechanger.app.effects.R;

import com.voicechanger.app.effects.allBaseAct.BaseActivity;
import com.voicechanger.app.effects.allBaseAct.BaseCallback;
import com.voicechanger.app.effects.allBaseAct.BaseFragment;
import com.voicechanger.app.effects.custUi.AppConstant;
import com.voicechanger.app.effects.custUi.FileMethods;
import com.voicechanger.app.effects.custUi.constatnt.TapClick;
import com.voicechanger.app.effects.databinding.ActivitySaveBinding;
import com.voicechanger.app.effects.viewModel.SaveViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

public final class SaveActivity extends BaseActivity<SaveViewModel, ActivitySaveBinding>  {
    public String strDuration = "";
    public String strFileName = "";
    private int pos;

    public String strSize = "";
    private ChangeEffectsModule module;

    public Class<SaveViewModel> createViewModel() {
        return SaveViewModel.class;
    }

    public int getContent() {
        return R.layout.activity_save;
    }

    public void navigate(int i, Bundle bundle, boolean z) {
    }

    public void navigateUp() {
    }

    public void onBackPressed() {
        Log.e("VoiceChanger", "SaveAct_onBack");
    }

    public void onFragmentResumed(BaseFragment<?, ?> baseFragment) {
        Intrinsics.checkNotNullParameter(baseFragment, "fragment");
    }

    public void switchFragment(KClass<?> kClass, Bundle bundle, boolean z) {
        Intrinsics.checkNotNullParameter(kClass, "fragment");
    }

    public void mainView() {

        Log.e("VoiceChanger", "SaveAct_onCreate");



        int i = 0;
        try {
            String str = "";
            if (getIntent().getStringExtra(AppConstant.APP_CONSTANT.getKEY_PATH_VOICE()) != null) {
                ChangeEffectsModule changerModule = new ChangeEffectsModule(this);
                this.module = changerModule;
                changerModule.createOutputDir(SaveActivity.this);
                ChangeEffectsModule module1 = this.module;
                if (module1 != null) {
                    module1.setPath(getIntent().getStringExtra(AppConstant.APP_CONSTANT.getKEY_PATH_VOICE()));
                }
                ChangeEffectsModule module2 = this.module;
                if (module2 != null) {
                    module2.createDBMedia();
                }
                try {
                    JSONArray jSONArray = new JSONArray(AppConstant.APP_CONSTANT.getVoiceEffect(this));
                    int length = jSONArray.length();
                    int i2 = 0;
                    while (i2 < length) {
                        int i3 = i2 + 1;
                        JSONObject jSONObject = jSONArray.getJSONObject(i2);
                        ChangeEffectsModule changeEffectsModule4 = this.module;
                        if (changeEffectsModule4 != null) {
                            changeEffectsModule4.insertEffect(jSONObject.toString());
                        }
                        i2 = i3;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            }
            if (getIntent().getIntExtra(AppConstant.APP_CONSTANT.getKEY_POSITION_EFFECT(), 0) != -1) {
                i = getIntent().getIntExtra(AppConstant.APP_CONSTANT.getKEY_POSITION_EFFECT(), 0);
            }
            pos = i;
            strFileName = getIntent().getStringExtra(AppConstant.APP_CONSTANT.getKEY_FILENAME_EFFECT()) != null ? String.valueOf(getIntent().getStringExtra(AppConstant.APP_CONSTANT.getKEY_FILENAME_EFFECT())) : str;
            strDuration = getIntent().getStringExtra(AppConstant.APP_CONSTANT.getKEY_DURATION_VOICE()) != null ? String.valueOf(getIntent().getStringExtra(AppConstant.APP_CONSTANT.getKEY_DURATION_VOICE())) : str;
            if (getIntent().getStringExtra(AppConstant.APP_CONSTANT.getKEY_SIZE_VOICE()) != null) {
                str = String.valueOf(getIntent().getStringExtra(AppConstant.APP_CONSTANT.getKEY_SIZE_VOICE()));
            }
            this.strSize = str;
        } catch (Exception e2) {
            e2.printStackTrace();
        }


    }



    public void initViews() {
        ChangeEffectsModule module1 = this.module;
        if (module1 != null) {
            module1.saveTheEffects(this.pos, this.strFileName, new BaseCallback() {

                @Override
                public void onSuccess() {
                    getBindingData().preview.setVisibility(View.VISIBLE);
                    getBindingData().tvStatus.setText(getString(R.string.successfully_saved_the_audio_file));
                }
            });
        }
        TapClick.tap(getBindingData().preview, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(AppConstant.APP_CONSTANT.getKEY_PATH_VOICE(), new File(FileMethods.getDirectory(SaveActivity.this), Intrinsics.stringPlus(strFileName, ".wav")).getPath());
                bundle.putString(AppConstant.APP_CONSTANT.getKEY_FILENAME_EFFECT(), strFileName);
                bundle.putString(AppConstant.APP_CONSTANT.getKEY_DURATION_VOICE(), strDuration);
                bundle.putString(AppConstant.APP_CONSTANT.getKEY_SIZE_VOICE(), strSize);
                nextActivity(MusicPlayerActivity.class, bundle);
                finish();

                return null;
            }
        });
    }


}
