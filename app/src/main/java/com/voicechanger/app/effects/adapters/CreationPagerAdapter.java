package com.voicechanger.app.effects.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.voicechanger.app.effects.fragments.FragAudioStudio;

public final class CreationPagerAdapter extends FragmentPagerAdapter {
    public int getCount() {
        return 1;
    }

    public CreationPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    public Fragment getItem(int i) {
        return new FragAudioStudio();
    }
}
