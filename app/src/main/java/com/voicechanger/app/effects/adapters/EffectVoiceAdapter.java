package com.voicechanger.app.effects.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.voicechanger.app.effects.fragments.FragAllEffects;
import com.voicechanger.app.effects.fragments.FragOtherEffects;
import com.voicechanger.app.effects.fragments.FragPeopleEffects;
import com.voicechanger.app.effects.fragments.FragRobotEffects;
import com.voicechanger.app.effects.fragments.FragScaryEffects;

import kotlin.jvm.internal.Intrinsics;

public final class EffectVoiceAdapter extends FragmentPagerAdapter {
    public int getCount() {
        return 5;
    }

    public EffectVoiceAdapter(FragmentManager manager) {
        super(manager);
        Intrinsics.checkNotNullParameter(manager, "fm");
    }

    public Fragment getItem(int pos) {
        if (pos == 0) {
            return new FragAllEffects();
        }
        if (pos == 1) {
            return new FragScaryEffects();
        }
        if (pos == 2) {
            return new FragOtherEffects();
        }
        if (pos == 3) {
            return new FragPeopleEffects();
        }
        if (pos == 4) {
            return new FragRobotEffects();

        }
        return new FragAllEffects();
    }
}
