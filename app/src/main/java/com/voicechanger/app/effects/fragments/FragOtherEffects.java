package com.voicechanger.app.effects.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.voicechanger.app.effects.R;
import com.voicechanger.app.effects.activity.ChangeEffectActivity;
import com.voicechanger.app.effects.adapters.ItemEffectAdapter;
import com.voicechanger.app.effects.allBaseAct.BaseFragment;
import com.voicechanger.app.effects.databinding.FragAllEffectsBinding;
import com.voicechanger.app.effects.getApiData.allModel.EffectModel;
import com.voicechanger.app.effects.viewModel.ChangeEffectViewModel;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public final class FragOtherEffects extends BaseFragment<ChangeEffectViewModel, FragAllEffectsBinding> {
    private BroadcastReceiver model = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            EffectModel model1;
            ItemEffectAdapter access$getItemEffectAdapter$p;
            if (Intrinsics.areEqual((Object) intent == null ? null : intent.getAction(), (Object) "select_effect") && (model1 = (EffectModel) intent.getParcelableExtra("effect_model")) != null && (access$getItemEffectAdapter$p = itemEffectAdapter) != null) {
                access$getItemEffectAdapter$p.selectEffectItem(model1);
            }
        }
    };

    public ItemEffectAdapter itemEffectAdapter;


    public void bindViewModels() {
    }

    public Class<ChangeEffectViewModel> createViewModel() {
        return ChangeEffectViewModel.class;
    }

    public int getFragResourceLayout() {
        return R.layout.frag_all_effects;
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void bindViews() {
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(this.model, new IntentFilter("select_effect"));
        Context context = requireContext();
        List<EffectModel> effects = getViewModel().getAllOtherEffects(context);
        getDataBinding().rclEffect.setHasFixedSize(true);
        ItemEffectAdapter itemEffectAdapter2 = new ItemEffectAdapter(requireContext(), effects, new Function1<EffectModel, Unit>() {
            @Override
            public Unit invoke(EffectModel effectModel) {
                ItemEffectAdapter itemEffectAdapter1 = itemEffectAdapter;
                if (itemEffectAdapter1 != null) {
                    itemEffectAdapter1.selectEffectItem(effectModel);
                }
                Context context = getContext();
                String nameOrigin = effectModel.getNameOrigin();
                ((ChangeEffectActivity) requireActivity()).playEffect(effectModel.getId());
                ChangeEffectActivity.Companion.setEffectModelSelected(effectModel);
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(new Intent("select_effect").putExtra("effect_model", effectModel));
                return null;
            }
        });
        this.itemEffectAdapter = itemEffectAdapter2;
        getDataBinding().rclEffect.setAdapter(itemEffectAdapter2);
    }

    public void onResume() {
        ItemEffectAdapter effectAdapter;
        super.onResume();
        EffectModel modelSelected = ChangeEffectActivity.Companion.getEffectModelSelected();
        if (modelSelected != null && (effectAdapter = this.itemEffectAdapter) != null) {
            effectAdapter.selectEffectItem(modelSelected);
        }
    }
}
