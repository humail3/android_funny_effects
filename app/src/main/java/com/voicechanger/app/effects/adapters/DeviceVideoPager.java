package com.voicechanger.app.effects.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.voicechanger.app.effects.fragments.FragAudio;

import kotlin.jvm.internal.Intrinsics;

public final class DeviceVideoPager extends FragmentPagerAdapter {
    public int getCount() {
        return 1;
    }
    public DeviceVideoPager(FragmentManager manager) {
        super(manager);
        Intrinsics.checkNotNullParameter(manager, "fm");
    }

    public Fragment getItem(int i) {
        return new FragAudio();
    }
}
