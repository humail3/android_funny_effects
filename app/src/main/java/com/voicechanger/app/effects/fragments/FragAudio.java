package com.voicechanger.app.effects.fragments;


import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.voicechanger.app.effects.R;
import com.voicechanger.app.effects.activity.ChangeEffectActivity;
import com.voicechanger.app.effects.activity.OpenFileActivity;
import com.voicechanger.app.effects.adapters.AudioAdapter;
import com.voicechanger.app.effects.allBaseAct.BaseFragment;
import com.voicechanger.app.effects.custUi.AppConstant;
import com.voicechanger.app.effects.databinding.FragAudioBinding;
import com.voicechanger.app.effects.getApiData.allModel.AudioModel;
import com.voicechanger.app.effects.viewModel.DeviceMusicViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kotlin.jvm.internal.Intrinsics;

public final class FragAudio extends BaseFragment<DeviceMusicViewModel, FragAudioBinding> {



    //    ----------------------------------------------
    private AudioAdapter audioAdapter;
    private FragAudio fragAudio;

    public Class<DeviceMusicViewModel> createViewModel() {
        return DeviceMusicViewModel.class;
    }

    public int getFragResourceLayout() {
        fragAudio = this;
        return R.layout.frag_audio;
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void bindViews() {


        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        getDataBinding().rclAudio.setLayoutManager(manager);
        AudioAdapter audioAdapter = new AudioAdapter(requireContext(), new ArrayList(), audioModel -> {
            Intrinsics.checkNotNullParameter(audioModel, "it");
            Bundle bundles = new Bundle();
            bundles.putString(AppConstant.APP_CONSTANT.getKEY_PATH_VOICE(), audioModel.getPath());
            bundles.putString(AppConstant.APP_CONSTANT.getKEY_SCREEN_INTO_VOICE_EFFECTS(), "AudioFragment");
            showActivity(ChangeEffectActivity.class, bundles);
           

            return null;
        });
        this.audioAdapter = audioAdapter;
        getDataBinding().rclAudio.setAdapter(audioAdapter);
    }

    public void bindViewModels() {
        Objects.requireNonNull(getViewModel()).getAudioDataClass(requireContext(), getDataBinding().llLoading, getDataBinding().adViewBanner, getDataBinding().noData);
        OpenFileActivity.Companion.getLiveSortCreateAudio().observe(requireActivity(), num -> {
            if (num != null && num == 1) {
                Objects.requireNonNull(fragAudio.getViewModel()).getMutableLiveData().observe(fragAudio.requireActivity(), list -> {
                    AudioAdapter audioAdapter;
                    if ((audioAdapter = fragAudio.audioAdapter) != null) {
                        audioAdapter.sortListRefresh(list);
                    }
                });
            } else if (num != null && num == 2) {
                Objects.requireNonNull(fragAudio.getViewModel()).getMutableLiveData().observe(fragAudio.requireActivity(), list -> {
                    AudioAdapter audioAdapter;
                    if (list != null && (audioAdapter = fragAudio.audioAdapter) != null) {
                        audioAdapter.oldDataSort(list);
                    }
                });
            }
        });
        OpenFileActivity.Companion.getLiveSortNameAudio().observe(requireActivity(), num -> {
            Intrinsics.checkNotNullParameter(fragAudio, "this$0");
            if (num != null && num == 1) {
                Objects.requireNonNull(fragAudio.getViewModel()).getMutableLiveData().observe(fragAudio.requireActivity(), new Observer<List<AudioModel>>() {
                    @Override
                    public void onChanged(List<AudioModel> list) {
                        AudioAdapter audioAdapter;
                        Intrinsics.checkNotNullParameter(fragAudio, "this$0");
                        if (list != null && (audioAdapter = fragAudio.audioAdapter) != null) {
                            audioAdapter.sortByFileName(list);
                        }
                    }
                });
            } else if (num != null && num == 2) {
                fragAudio.getViewModel().getMutableLiveData().observe(fragAudio.requireActivity(), new Observer<List<AudioModel>>() {
                    @Override
                    public void onChanged(List<AudioModel> list) {
                        AudioAdapter audioAdapter;
                        Intrinsics.checkNotNullParameter(fragAudio, "this$0");
                        if (list != null && (audioAdapter = fragAudio.audioAdapter) != null) {
                            audioAdapter.sortOldByName(list);
                        }
                    }
                });
            }
        });
    }

}
