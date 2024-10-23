package com.voicechanger.app.effects.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.Observable;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.Observer;

import com.voicechanger.app.effects.R;

import com.voicechanger.app.effects.allBaseAct.BaseActivity;
import com.voicechanger.app.effects.allBaseAct.BaseFragment;
import com.voicechanger.app.effects.allDialogs.NotRecordedDialog;
import com.voicechanger.app.effects.custUi.AppConstant;
import com.voicechanger.app.effects.custUi.LiveEvents;
import com.voicechanger.app.effects.custUi.MobileState;
import com.voicechanger.app.effects.custUi.RecordAudioType;
import com.voicechanger.app.effects.custUi.SetLanguage;
import com.voicechanger.app.effects.custUi.constatnt.TapClick;
import com.voicechanger.app.effects.databinding.ActivityRecordingBinding;
import com.voicechanger.app.effects.getApiData.allModel.RecordingModel;
import com.voicechanger.app.effects.recordingServices.ServiceRecordingVoice;
import com.voicechanger.app.effects.viewModel.RecordingViewModel;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;


public final class RecordingActivity extends BaseActivity<RecordingViewModel, ActivityRecordingBinding>  {
    private int recorderSecondsElapsed = -1;
    private int playerSecondsElapsed;
    private Timer timer;
    private boolean isRecording;



//    __________________________________

    public boolean isFirstCallBack = true;
    public boolean isFirst = true;
    public Observable.OnPropertyChangedCallback callback;
    public RecordAudioType stateAudio = RecordAudioType.STATE_PREPARE;
    private RecordingActivity recordingActivity;

    public Class<RecordingViewModel> createViewModel() {
        return RecordingViewModel.class;
    }

    public int getContent() {
        Log.e("VoiceChanger", "RecordingAct_onCreate");
        recordingActivity = this;
        return R.layout.activity_recording;
    }

    public void navigate(int i, Bundle bundle, boolean z) {
    }

    public void navigateUp() {
    }

    public void onFragmentResumed(BaseFragment<?, ?> baseFragment) {
        Intrinsics.checkNotNullParameter(baseFragment, "fragment");
    }

    public void switchFragment(KClass<?> kClass, Bundle bundle, boolean z) {
        Intrinsics.checkNotNullParameter(kClass, "fragment");
    }

    public void mainView() {
        Context context = this;
        SetLanguage.setLocale(context);
        getBindingData().toolbar.tvTitle.setText(R.string.record_voice);
        showHideExRecord();
        ((RecordingViewModel) getMViewModel()).connectService(ServiceRecordingVoice.makeIntent(context, true));
        this.isFirst = true;





    }



    public void initViews() {
        TapClick.tap(getBindingData().toolbar.ivBack, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                onBackPressed();
                return null;
            }
        });
        TapClick.tap(getBindingData().ivReset, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                String string = recordingActivity.getResources().getString(R.string.audio_has_not_been_saved_reset);
                String string2 = getResources().getString(R.string.reset);
                showDialogNotSaved(true, false, string, string2);
                return null;
            }
        });

        getBindingData().icStart.setOnClickListener(v -> {
            getBindingData().txtStartRecord.setText("Recording...");
            startRecordAudio();
            getBindingData().icStart.setClickable(false);
            getBindingData().icStart.setImageResource(R.drawable.ic);
            getBindingData().rlyBottom.setVisibility(View.VISIBLE);
            getBindingData().imgRecord.setVisibility(View.VISIBLE);
            getBindingData().txtExtra.setVisibility(View.GONE);
            Log.e("VoiceChanger", "Click_on_Start");

        });

        TapClick.tap(getBindingData().imgRecord, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {

                int[] iArr = new int[RecordAudioType.values().length];
                iArr[RecordAudioType.STATE_PREPARE.ordinal()] = 1;
                iArr[RecordAudioType.STATE_START.ordinal()] = 2;
                iArr[RecordAudioType.STATE_PAUSE.ordinal()] = 3;
                iArr[RecordAudioType.STATE_STOP.ordinal()] = 4;
                int TimerArray[] = iArr;

                int i = TimerArray[stateAudio.ordinal()];
                if (i == 1) {
                    Log.e("re---", "invoke: 1111");
                    startRecordAudio();
                    getBindingData().rlyBottom.setVisibility(View.VISIBLE);
                    getBindingData().imgRecord.setVisibility(View.VISIBLE);
                } else if (i == 2) {
                    Log.e("re---", "invoke: 222");
                    pauseRecordAudio();
                    startStopRecording();
                    getBindingData().rlyBottom.setVisibility(View.VISIBLE);
                    getBindingData().imgRecord.setVisibility(View.VISIBLE);
                } else {
                    Log.e("re---", "invoke: 3333");
                    startRecordAudio();
                    getBindingData().rlyBottom.setVisibility(View.VISIBLE);
                    getBindingData().imgRecord.setVisibility(View.VISIBLE);
                }
                return null;
            }
        });

        TapClick.tap(getBindingData().imgStop, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                isFirst = false;
                ((RecordingViewModel) getMViewModel()).recStop();
                stopAnim();
                stopTimer();
                ObservableInt secondsElapsed = ((RecordingViewModel) getMViewModel()).getObservableInt();
                Observable.OnPropertyChangedCallback access$getSecsCallback$p = callback;
                Intrinsics.checkNotNull(access$getSecsCallback$p);
                secondsElapsed.removeOnPropertyChangedCallback(access$getSecsCallback$p);
                getWindow().clearFlags(128);
                Observable.OnPropertyChangedCallback secsCallback1 = callback;
                if (secsCallback1 != null) {
                    ((RecordingViewModel) getMViewModel()).getObservableInt().removeOnPropertyChangedCallback(secsCallback1);
                }
                LiveEvents<RecordingModel> recording = ((RecordingViewModel) getMViewModel()).getRecording();
                recording.observe(recordingActivity, new Observer<RecordingModel>() {
                    @Override
                    public void onChanged(RecordingModel recordingModel) {
                        if (recordingModel != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString(AppConstant.APP_CONSTANT.getKEY_PATH_VOICE(), recordingModel.getPath());
                            bundle.putString(AppConstant.APP_CONSTANT.getKEY_SCREEN_INTO_VOICE_EFFECTS(), "RecordActivity");
                            getBindingData().icStart.setClickable(true);
                            getBindingData().txtStartRecord.setText("Start Record");
                            getBindingData().txtExtra.setVisibility(View.VISIBLE);
                            getBindingData().icStart.setImageResource(R.drawable.ic_start_record);
                            nextActivity(ChangeEffectActivity.class, bundle);

                        }
                    }
                });
                stateAudio = RecordAudioType.STATE_PREPARE;
                showHideExRecord();
                return null;
            }
        });
    }

    public void startRecordAudio() {
        Log.e("VoiceChanger", "startRecordAudio");
        Log.e("record---", "startRecordAudio: ");
        this.stateAudio = RecordAudioType.STATE_START;
        ((ActivityRecordingBinding) getBindingData()).imgRecord.setImageResource(R.drawable.ic_pause);
        ((ActivityRecordingBinding) getBindingData()).icStart.setImageResource(R.drawable.ic);
        startStopRecording();
    }

    public void pauseRecordAudio() {
        Log.e("VoiceChanger", "pauseRecordAudio");
        Log.e("record---", "pauseRecordAudio: ");
        this.stateAudio = RecordAudioType.STATE_PAUSE;
        getBindingData().imgRecord.setImageResource(R.drawable.ic_play);
    }

    private void stopRecordAudio() {
        Log.e("VoiceChanger", "stopRecordAudio");
        Log.e("record---", "stopRecordAudio: ");
        this.stateAudio = RecordAudioType.STATE_STOP;
        ((ActivityRecordingBinding) getBindingData()).imgRecord.setImageResource(R.drawable.ic_start_record);
        ((ActivityRecordingBinding) getBindingData()).txtStartRecord.setText(R.string.start_record);

    }

    public void showHideExRecord() {
        int[] iArr = new int[MobileState.values().length];
        iArr[MobileState.STATE_RINGTONE.ordinal()] = 1;
        iArr[MobileState.STATE_ALARM.ordinal()] = 2;
        iArr[MobileState.STATE_NOTIFICATION.ordinal()] = 3;
        int enumSwitchMapping[] = iArr;

        int i = enumSwitchMapping[this.stateAudio.ordinal()];
        if (i == 1) {
            recorderSecondsElapsed = 0;
            playerSecondsElapsed = 0;

            getBindingData().imgRecord.setClickable(true);
            getBindingData().imgRecord.setImageResource(R.drawable.ic_start_record);
            getBindingData().rlyBottom.setVisibility(View.GONE);
            getBindingData().imgRecord.setVisibility(View.GONE);

            getBindingData().txtStartRecord.setText(R.string.start_record);

        }
    }

    public void onResume() {
        super.onResume();
        Log.e("VoiceChanger", "RecordingAct_onResume");
        this.callback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Intrinsics.checkNotNullParameter(sender, "sender");
                if (isFirstCallBack) {
                    isFirstCallBack = false;
                }
            }
        };
        ObservableInt elapsed = ((RecordingViewModel) getMViewModel()).getObservableInt();
        Observable.OnPropertyChangedCallback onPropertyChangedCallback = this.callback;
        Objects.requireNonNull(onPropertyChangedCallback, "null cannot be cast to non-null type androidx.databinding.Observable.OnPropertyChangedCallback");
        elapsed.addOnPropertyChangedCallback(onPropertyChangedCallback);
    }

    public void onPause() {
        super.onPause();
        Log.e("VoiceChanger", "RecordingAct_onPause");
        Observable.OnPropertyChangedCallback onPropertyChangedCallback = this.callback;
        if (onPropertyChangedCallback != null) {
            ((RecordingViewModel) getMViewModel()).getObservableInt().removeOnPropertyChangedCallback(onPropertyChangedCallback);
        }
    }

    public void showDialogNotSaved(boolean z, boolean z2, String str, String str2) {
        if (z) {
            Log.e("ww---", "showDialogNotSaved: 111");
            new NotRecordedDialog(str, str2, this, true, new Function0<Unit>() {
                @Override
                public Unit invoke() {
                    stateAudio = RecordAudioType.STATE_PREPARE;
                    resetFileRecord();
                    showHideExRecord();
                    if (z2) {
                        finish();
                    }
                    return null;
                }
            }).show();
            return;
        }
        stopRecordAudio();
        finish();
    }


    public void startStopRecording() {
        this.isFirstCallBack = false;
        if (!getMViewModel().getServiceRecording().get()) {
            this.isFirst = false;
            isRecording = true;
            getMViewModel().recStart();
            getWindow().addFlags(128);
            startAnim();
            startTimer();
            return;
        }
        pauseRecord();
    }

    private void pauseRecord() {
        if (!getMViewModel().getServiceRecordResume().get()) {
            getMViewModel().recResume();
            startAnim();
            startTimer();
            if (this.isFirst) {
                this.isFirst = false;
                this.isFirstCallBack = true;
            }
            this.callback = new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable observable, int propertyId) {
                    Intrinsics.checkNotNullParameter(observable, "sender");
                    if (isFirstCallBack) {
                        isFirstCallBack = false;
                    }
                }
            };
            ObservableInt elapsed = ((RecordingViewModel) getMViewModel()).getObservableInt();
            Observable.OnPropertyChangedCallback onPropertyChangedCallback = this.callback;
            Objects.requireNonNull(onPropertyChangedCallback, "null cannot be cast to non-null type androidx.databinding.Observable.OnPropertyChangedCallback");
            elapsed.addOnPropertyChangedCallback(onPropertyChangedCallback);
            return;
        }
        this.isFirst = false;
        ((RecordingViewModel) getMViewModel()).recPause();
        stopAnim();
        stopTimer();
        Observable.OnPropertyChangedCallback onPropertyChangedCallback2 = this.callback;
        if (onPropertyChangedCallback2 != null) {
            ((RecordingViewModel) getMViewModel()).getObservableInt().removeOnPropertyChangedCallback(onPropertyChangedCallback2);
        }
        isRecording = false;
    }


    public void resetFileRecord() {
        Log.e("eee---", "resetFileRecord: ");
        this.isFirst = false;
        getMViewModel().recSkipFile();
        stopAnim();
        stopTimer();
        getWindow().clearFlags(128);
        Observable.OnPropertyChangedCallback onPropertyChangedCallback = this.callback;
        getBindingData().icStart.setImageResource(R.drawable.ic_start_record);
        getBindingData().txtExtra.setVisibility(View.VISIBLE);
        getBindingData().icStart.setClickable(true);
        getBindingData().txtStartRecord.setText("Start Record");
        if (onPropertyChangedCallback != null) {
            ((RecordingViewModel) getMViewModel()).getObservableInt().removeOnPropertyChangedCallback(onPropertyChangedCallback);
        }

        recorderSecondsElapsed = 0;
        playerSecondsElapsed = 0;
        getBindingData().txtStartRecord.setText(R.string.start_record);
    }

    public void onDestroy() {
        super.onDestroy();
        Context context = this;
        ((RecordingViewModel) getMViewModel()).serviceStartStop(new Intent(context, ServiceRecordingVoice.class));
        stopService(new Intent(context, ServiceRecordingVoice.class));
        stopAnim();
        stopTimer();
    }

    public void onBackPressed() {

        Log.e("VoiceChanger", "RecordingAct_onBack");
        if (getBindingData().icStart.isClickable()) {
            super.onBackPressed();
            return;
        }

        boolean isShown = getBindingData().ivReset.isShown();
        String string = getResources().getString(R.string.audio_has_not_been_saved);
        String string2 = getResources().getString(R.string.exit);
        showDialogNotSaved(isShown, true, string, string2);
    }

    // BannerAds

    public void startAnim() {
        Log.e("eee---", "startAnim: ");
        getBindingData().recordLottie.setVisibility(View.VISIBLE);
    }

    public void stopAnim() {
        Log.e("eee---", "stopAnim: ");
        getBindingData().recordLottie.setVisibility(View.INVISIBLE);
    }

    private void startTimer() {
        Log.e("eee---", "startTimer: ");
        stopTimer();
        Timer timer = new Timer();
        this.timer = timer;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateTimer();
            }
        }, 800L, 1000L);
    }

    private void stopTimer() {
        Log.e("eee---", "stopTimer: ");
        Timer timer = this.timer;
        if (timer != null) {
            timer.purge();
            this.timer.cancel();
            this.timer = null;
        }
    }

    public void updateTimer() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView;
                int i;
                if (!isRecording) {
                    playerSecondsElapsed++;
                    textView = getBindingData().txtStartRecord;
                    i = playerSecondsElapsed;
                } else {
                    recorderSecondsElapsed++;
                    textView = getBindingData().txtStartRecord;
                    i = recorderSecondsElapsed;
                }
                textView.setText(formatSeconds(i));
                playerSecondsElapsed = i;
            }
        });
    }


    public static String formatSeconds(int i) {
        return getTwoDecimalsValue(i / 3600) + ":" + getTwoDecimalsValue(i / 60) + ":" + getTwoDecimalsValue(i % 60);
    }

    private static String getTwoDecimalsValue(int i) {
        StringBuilder sb;
        if (i < 0 || i > 9) {
            sb = new StringBuilder();
            sb.append(i);
            sb.append("");
        } else {
            sb = new StringBuilder();
            sb.append("0");
            sb.append(i);
        }
        return sb.toString();
    }




}
