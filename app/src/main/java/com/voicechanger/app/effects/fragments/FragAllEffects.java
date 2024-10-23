package com.voicechanger.app.effects.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

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

public final class FragAllEffects extends BaseFragment<ChangeEffectViewModel, FragAllEffectsBinding> {
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctx, Intent intent) {
            EffectModel effectModel;
            ItemEffectAdapter access$getItemEffectAdapter$p;
            if (Intrinsics.areEqual((Object) intent == null ? null : intent.getAction(), (Object) "select_effect") && (effectModel = (EffectModel) intent.getParcelableExtra("effect_model")) != null && (access$getItemEffectAdapter$p = itemEffectAdapter) != null) {
                access$getItemEffectAdapter$p.selectEffectItem(effectModel);
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
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(this.receiver, new IntentFilter("select_effect"));
        Context context = requireContext();
        List<EffectModel> effects = ((ChangeEffectViewModel) getViewModel()).getAllVoiceEffects(context);
        RecyclerView rclEffect = ((FragAllEffectsBinding) getDataBinding()).rclEffect;
        rclEffect.setHasFixedSize(true);
        Context requireContext2 = requireContext();
        ItemEffectAdapter itemEffectAdapter2 = new ItemEffectAdapter(requireContext2, effects, new Function1<EffectModel, Unit>() {
            @Override
            public Unit invoke(EffectModel effectModel) {
                Intrinsics.checkNotNullParameter(effectModel, "it");
                ItemEffectAdapter itemEffectAdapter1 = itemEffectAdapter;
                if (itemEffectAdapter1 != null) {
                    itemEffectAdapter1.selectEffectItem(effectModel);
                }
                Context context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "context");
                String nameOrigin = effectModel.getNameOrigin();
                Intrinsics.checkNotNull(nameOrigin);
                ((ChangeEffectActivity) requireActivity()).playEffect(effectModel.getId());
                ChangeEffectActivity.Companion.setEffectModelSelected(effectModel);
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(new Intent("select_effect").putExtra("effect_model", effectModel));

                return null;
            }
        });
        this.itemEffectAdapter = itemEffectAdapter2;
        rclEffect.setAdapter(itemEffectAdapter2);
    }

    public void onResume() {
        ItemEffectAdapter itemEffectAdapter2;
        super.onResume();
        EffectModel effectModelSelected = ChangeEffectActivity.Companion.getEffectModelSelected();
        if (effectModelSelected != null && (itemEffectAdapter2 = this.itemEffectAdapter) != null) {
            itemEffectAdapter2.selectEffectItem(effectModelSelected);
        }
    }
}
