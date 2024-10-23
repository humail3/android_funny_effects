package com.voicechanger.app.effects.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager.widget.ViewPager;

import com.voicechanger.app.effects.R;
import com.voicechanger.app.effects.adapters.DeviceVideoPager;
import com.voicechanger.app.effects.allBaseAct.BaseActivity;
import com.voicechanger.app.effects.allBaseAct.BaseFragment;
import com.voicechanger.app.effects.allBaseAct.BasePopupMenu;
import com.voicechanger.app.effects.custUi.constatnt.TapClick;
import com.voicechanger.app.effects.databinding.ActivityOpenFileBinding;
import com.voicechanger.app.effects.viewModel.OpenFileViewModel;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

public final class OpenFileActivity extends BaseActivity<OpenFileViewModel, ActivityOpenFileBinding> {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static MutableLiveData<Integer> sortingByCreateDate = new MutableLiveData<>(1);
    public static MutableLiveData<Integer> sortingByName = new MutableLiveData<>(0);
    private ImageView ivCreated;
    private ImageView ivFileName;

    public static ImageView imgSort;

    public Class<OpenFileViewModel> createViewModel() {
        return OpenFileViewModel.class;
    }

    public int getContent() {
        return R.layout.activity_open_file;
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

    public static final class Companion {
        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MutableLiveData<Integer> getLiveSortCreateAudio() {
            return OpenFileActivity.sortingByCreateDate;
        }

        public MutableLiveData<Integer> getLiveSortNameAudio() {
            return OpenFileActivity.sortingByName;
        }
    }

    public final ImageView getIvCreated() {
        return this.ivCreated;
    }

    public final void setIvCreated(ImageView imageView) {
        this.ivCreated = imageView;
    }

    public final ImageView getIvFileName() {
        return this.ivFileName;
    }

    public final void setIvFileName(ImageView imageView) {
        this.ivFileName = imageView;
    }

    public void mainView() {
        boolean z = false;
        Log.e("VoiceChanger", "OpenFileAct_onCreate");
        SharedPreferences sharedPreferences = getSharedPreferences("MY_PRE", 0);
        imgSort = getBindingData().toolbar.ivSort;
        ((TextView) getBindingData().toolbar.tvTitle).setText(getString(R.string.change_voice));
    }

    public void initViews() {
        TapClick.tap(getBindingData().toolbar.ivBack, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                onBackPressed();
                return null;
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        DeviceVideoPager deviceVideoPager = new DeviceVideoPager(fragmentManager);
        ViewPager viewPager = ((ActivityOpenFileBinding) getBindingData()).viewPager;
        viewPager.setAdapter(deviceVideoPager);

        BasePopupMenu basePopupMenus = new BasePopupMenu(this, R.layout.layout_popup_menu_sort, new BasePopupMenu.PopupMenuCustomOnClickListener() {
            @Override
            public void initView(View view) {
                setIvCreated((ImageView) view.findViewById(R.id.iv_created));
                setIvFileName((ImageView) view.findViewById(R.id.iv_file_name));
            }

            @Override
            public void onClick(int i, View view) {
                Log.e("VoiceChanger", "Click_on_Sort");
                Integer inte1;
                Integer inte2;
                if (i == R.id.ll_created) {
                    OpenFileActivity.Companion.getLiveSortNameAudio().postValue(0);
                    Integer value5 = OpenFileActivity.Companion.getLiveSortCreateAudio().getValue();
                    if ((value5 != null && value5.intValue() == 1) || ((inte1 = OpenFileActivity.Companion.getLiveSortCreateAudio().getValue()) != null && inte1.intValue() == 0)) {
                        OpenFileActivity.Companion.getLiveSortCreateAudio().postValue(2);
                        ImageView ivCreated = getIvCreated();
                        if (ivCreated != null) {
                            ivCreated.setImageResource(R.drawable.ic_menu_down);
                            return;
                        }
                        return;
                    }
                    OpenFileActivity.Companion.getLiveSortCreateAudio().postValue(1);
                    ImageView ivCreated2 = getIvCreated();
                    if (ivCreated2 != null) {
                        ivCreated2.setImageResource(R.drawable.ic_menu_up);
                    }
                } else if (i == R.id.ll_file_name) {
                    OpenFileActivity.Companion.getLiveSortCreateAudio().postValue(0);
                    Integer value7 = OpenFileActivity.Companion.getLiveSortNameAudio().getValue();
                    if ((value7 != null && value7.intValue() == 1) || ((inte2 = OpenFileActivity.Companion.getLiveSortNameAudio().getValue()) != null && inte2.intValue() == 0)) {
                        OpenFileActivity.Companion.getLiveSortNameAudio().postValue(2);
                        ImageView ivFileName = getIvFileName();
                        if (ivFileName != null) {
                            ivFileName.setImageResource(R.drawable.ic_menu_down);
                            return;
                        }
                        return;
                    }
                    OpenFileActivity.Companion.getLiveSortNameAudio().postValue(1);
                    ImageView ivFileName2 = getIvFileName();
                    if (ivFileName2 != null) {
                        ivFileName2.setImageResource(R.drawable.ic_menu_up);
                    }
                }
            }
        });
        TapClick.tap(getBindingData().toolbar.ivSort, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                BasePopupMenu basePopupMenu = basePopupMenus;
                ImageView imageView = (ImageView) getBindingData().toolbar.ivSort;
                Intrinsics.checkNotNullExpressionValue(imageView, "mDataBinding.toolbar.iv_sort");
                basePopupMenu.showRight(imageView);
                return null;
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        Log.e("VoiceChanger", "OpenFileAct_onDestroy");
        sortingByCreateDate.postValue(1);
        sortingByName.postValue(0);
    }

    public void onBackPressed() {
        Log.e("VoiceChanger", "OpenFileAct_onBack");
        finish();
    }
}
