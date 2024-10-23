package com.voicechanger.app.effects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.voicechanger.app.effects.R;
import com.voicechanger.app.effects.adapters.CreationPagerAdapter;
import com.voicechanger.app.effects.allBaseAct.BaseActivity;
import com.voicechanger.app.effects.allBaseAct.BaseFragment;
import com.voicechanger.app.effects.allBaseAct.BasePopupMenu;
import com.voicechanger.app.effects.custUi.constatnt.TapClick;
import com.voicechanger.app.effects.databinding.ActivityCreationBinding;
import com.voicechanger.app.effects.viewModel.CreationStudioViewModel;
import java.io.File;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

public final class CreationActivity extends BaseActivity<CreationStudioViewModel, ActivityCreationBinding> {
    public static final Companion COMPANION = new Companion((DefaultConstructorMarker) null);
    public static File fromFile;
    public static MutableLiveData<Integer> dateSort = new MutableLiveData<>(1);
    public static MutableLiveData<Integer> nameSort = new MutableLiveData<>(0);
    public static File toFile;
    public ImageView ivCreated;
    public ImageView ivFileName;
    public static ImageView imgSort;

    public Class<CreationStudioViewModel> createViewModel() {
        return CreationStudioViewModel.class;
    }

    public int getContent() {
        return R.layout.activity_creation;
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

        public File getFrom() {
            return CreationActivity.fromFile;
        }

        public void setFrom(File file) {
            CreationActivity.fromFile = file;
        }

        public File getTo() {
            return CreationActivity.toFile;
        }

        public void setTo(File file) {
            CreationActivity.toFile = file;
        }

        public MutableLiveData<Integer> getLiveSortCreateStudio() {
            return CreationActivity.dateSort;
        }

        public MutableLiveData<Integer> getLiveSortNameStudio() {
            return CreationActivity.nameSort;
        }

    }

    public ImageView getIvCreated() {
        return this.ivCreated;
    }

    public void setIvCreated(ImageView imageView) {
        this.ivCreated = imageView;
    }

    public ImageView getIvFileName() {
        return this.ivFileName;
    }

    public void setIvFileName(ImageView imageView) {
        this.ivFileName = imageView;
    }

    public void mainView() {
        imgSort = getBindingData().toolbar.ivSort;
        getBindingData().toolbar.tvTitle.setText(getString(R.string.my_voice));
    }

    public void initViews() {
        Log.e("VoiceChanger", "CreationAct_onCreate");
        TapClick.tap(getBindingData().toolbar.ivBack, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                onBackPressed();
                return null;
            }
        });

        FragmentManager manager = getSupportFragmentManager();
        CreationPagerAdapter creationPagerAdapter = new CreationPagerAdapter(manager);
        ViewPager viewPager = ((ActivityCreationBinding) getBindingData()).viewPager;
        viewPager.setAdapter(creationPagerAdapter);

        BasePopupMenu basePopupMenu = new BasePopupMenu(this, R.layout.layout_popup_menu_sort, new BasePopupMenu.PopupMenuCustomOnClickListener() {
            @Override
            public void initView(View view) {
                setIvCreated((ImageView) view.findViewById(R.id.iv_created));
                setIvFileName((ImageView) view.findViewById(R.id.iv_file_name));
            }

            @Override
            public void onClick(int i, View view) {
                Integer val1;
                Integer val2;
                if (i != R.id.ll_created) {
                    if (i == R.id.ll_file_name) {
                        if (viewPager.getCurrentItem() == 0) {
                            CreationActivity.COMPANION.getLiveSortCreateStudio().postValue(0);
                            Integer val3 = CreationActivity.COMPANION.getLiveSortNameStudio().getValue();
                            Log.e("fff----", "onClick: val3 :: " + val3);
                            if ((val3 != null && val3 == 1) || ((val2 = CreationActivity.COMPANION.getLiveSortNameStudio().getValue()) != null && val2.intValue() == 0)) {
                                CreationActivity.COMPANION.getLiveSortNameStudio().postValue(2);
                                ImageView ivFileName = getIvFileName();
                                if (ivFileName != null) {
                                    ivFileName.setImageResource(R.drawable.ic_menu_down);
                                    return;
                                }
                                return;
                            }
                            CreationActivity.COMPANION.getLiveSortNameStudio().postValue(1);
                            ImageView ivFileName2 = getIvFileName();
                            if (ivFileName2 != null) {
                                ivFileName2.setImageResource(R.drawable.ic_menu_up);
                            }
                        }
                    }
                } else if (viewPager.getCurrentItem() == 0) {
                    CreationActivity.COMPANION.getLiveSortNameStudio().postValue(0);
                    Integer value4 = CreationActivity.COMPANION.getLiveSortCreateStudio().getValue();
                    if ((value4 != null && value4 == 1) || ((val1 = CreationActivity.COMPANION.getLiveSortCreateStudio().getValue()) != null && val1 == 0)) {
                        CreationActivity.COMPANION.getLiveSortCreateStudio().postValue(2);
                        ImageView ivCreated = getIvCreated();
                        if (ivCreated != null) {
                            ivCreated.setImageResource(R.drawable.ic_menu_down);
                            return;
                        }
                        return;
                    }
                    CreationActivity.COMPANION.getLiveSortCreateStudio().postValue(1);
                    ImageView ivCreated2 = getIvCreated();
                    if (ivCreated2 != null) {
                        ivCreated2.setImageResource(R.drawable.ic_menu_up);
                    }
                }
            }
        });
        TapClick.tap(getBindingData().toolbar.ivSort, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                BasePopupMenu basePopupMenus = basePopupMenu;
                basePopupMenus.showRight(getBindingData().toolbar.ivSort);
                return null;
            }
        });

    }

    public void onBackPressed() {
        Log.e("VoiceChanger", "CreationAct_onBack");
        finish();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(this);
        if (i == 1112 && i2 == -1) {
            File fromFile1 = fromFile;
            if (fromFile1 != null) {
                fromFile1.renameTo(toFile);
            }
            instance.sendBroadcast(new Intent("rename_file"));
        }
        if (i == 1111 && i2 == -1) {
            instance.sendBroadcast(new Intent("delete_file"));
        }
    }

    public void onDestroy() {
        super.onDestroy();
        Log.e("VoiceChanger", "CreationAct_onDestroy");
        dateSort.postValue(1);
        nameSort.postValue(0);
        fromFile = null;
        toFile = null;
    }
}
