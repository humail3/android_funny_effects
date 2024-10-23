package com.voicechanger.app.effects.allBaseAct;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.voicechanger.app.effects.MainApplication;
import com.voicechanger.app.effects.custUi.SetLanguage;
import com.voicechanger.app.effects.getApiData.InterfaceDataManager;
import com.voicechanger.app.effects.getApiData.appScheduler.SchedularProvider;

import javax.inject.Inject;

import kotlin.jvm.internal.Intrinsics;

public abstract class BaseActivity<VM extends BaseViewModel, DB extends ViewDataBinding> extends AppCompatActivity implements Navigators {
    BaseActivity baseActivity;
    @Inject
    public InterfaceDataManager interfaceDataManager;
    @Inject
    public ViewModelFactory factory;
    public DB mDataBinding;

    public VM mViewModel;
    @Inject
    public SchedularProvider schedulerProvider;

    public abstract void initViews();

    public abstract Class<VM> createViewModel();

    public abstract int getContent();
    public abstract void mainView();

    public final ViewModelFactory getFactory() {
        ViewModelFactory viewModelFactory = this.factory;
        if (viewModelFactory != null) {
            return viewModelFactory;
        }
        return null;
    }

    public final void setFactory(ViewModelFactory factory) {
        this.factory = factory;
    }

    public final InterfaceDataManager getDataManager() {
        InterfaceDataManager interfaceDataManager2 = this.interfaceDataManager;
        return interfaceDataManager2;
    }

    public final void setDataManager(InterfaceDataManager interfaceDataManager2) {
        Intrinsics.checkNotNullParameter(interfaceDataManager2, "<set-?>");
        this.interfaceDataManager = interfaceDataManager2;
    }

    public final SchedularProvider getSchedulerProvider() {
        SchedularProvider schedularProvider = this.schedulerProvider;
        if (schedularProvider != null) {
            return schedularProvider;
        }
        return null;
    }

    public final void setSchedulerProvider(SchedularProvider schedularProvider) {
        Intrinsics.checkNotNullParameter(schedularProvider, "<set-?>");
        this.schedulerProvider = schedularProvider;
    }

    public final void inject(ViewModelFactory viewModelFactory, InterfaceDataManager interfaceDataManager2, SchedularProvider schedularProvider) {
        setFactory(viewModelFactory);
        setDataManager(interfaceDataManager2);
        setSchedulerProvider(schedularProvider);
    }

    public final VM getMViewModel() {
        VM vm = this.mViewModel;
        if (vm != null) {
            return vm;
        }
        return null;
    }

    public final void setMViewModel(VM vm) {
        Intrinsics.checkNotNullParameter(vm, "<set-?>");
        this.mViewModel = vm;
    }

    public final DB getBindingData() {
        DB db = this.mDataBinding;
        if (db != null) {
            return db;
        }
        return null;
    }

    public final void setBindingData(DB db) {
        this.mDataBinding = db;
    }

    public void onCreate(Bundle bundle) {
        Application application = getApplication();
        MainApplication mainApplication = application instanceof MainApplication ? (MainApplication) application : null;
        if (mainApplication != null) {
            mainApplication.requestInjectAct(this);
        }
        if (android.os.Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        baseActivity = this;
        SetLanguage.setLocale(this);
        super.onCreate(bundle);
        ViewDataBinding contentView = DataBindingUtil.setContentView(this, getContent());
        setBindingData((DB) contentView);
        ViewModel viewModel = ViewModelProviders.of(this, getFactory()).get(createViewModel());
        setMViewModel((VM) viewModel);
        getBindingData().setVariable(2, getMViewModel());
        mainView();
        initViews();

    }

    public void nextActivity(Class<?> classs, Bundle bundle) {
        Intent intent = new Intent(this, classs);
        if (bundle == null) {
            bundle = new Bundle();
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void fragmentRequestInject(BaseFragment<?, ?> baseFragment) {
        baseFragment.inject(getFactory(), getSchedulerProvider(), getDataManager());
    }

}
