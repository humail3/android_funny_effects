package com.voicechanger.app.effects

import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Process
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.voicechanger.app.effects.allBaseAct.BaseActivity
import com.voicechanger.app.effects.allBaseAct.ViewModelFactory
import com.voicechanger.app.effects.custUi.AppConstant
import com.voicechanger.app.effects.getApiData.InterfaceDataManager
import com.voicechanger.app.effects.getApiData.MyAppInterfaceDataManager
import com.voicechanger.app.effects.getApiData.appScheduler.AppProvider
import com.voicechanger.app.effects.getApiData.appScheduler.SchedularProvider
import com.voicechanger.app.effects.getApiData.localData.HelpPrefClass
import com.voicechanger.app.effects.getApiData.localData.HelperPreference
import com.voicechanger.app.effects.langClass.PrefManager
import kotlin.jvm.internal.Intrinsics

class MainApplication : Application() {

    private var interfaceDataManager: InterfaceDataManager? = null
    private var modelFactory: ViewModelFactory? = null
    private var schedularProvider: SchedularProvider? = null

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        sContext = applicationContext
        application = this

        prefManager = PrefManager.getInstance(mInstance)

        val preferencesHelper: HelperPreference = HelpPrefClass(this, "VoiceEffect111")
        this.schedularProvider = AppProvider()
        interfaceDataManager = MyAppInterfaceDataManager(preferencesHelper)
        val mContexts = applicationContext
        var interfaceDataManager2 = interfaceDataManager
        if (interfaceDataManager2 == null) {
            interfaceDataManager2 = null
        }
        val schedularProvider2 = this.schedularProvider
        if (schedularProvider2 == null) {
        } else {
            schedularProvider = schedularProvider2
        }
        modelFactory = ViewModelFactory(mContexts, interfaceDataManager2, schedularProvider)
        channelNotificationCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        checkAppReplacingState()
    }

    private fun channelNotificationCreate() {
        if (Build.VERSION.SDK_INT > 26) {
            val channel = NotificationChannel(
                AppConstant.channelId,
                AppConstant.channelName,
                NotificationManager.IMPORTANCE_LOW
            )
            channel.setSound(null as Uri?, null as AudioAttributes?)
            val systemService = getSystemService(
                NotificationManager::class.java
            ) as NotificationManager
            systemService.createNotificationChannel(channel)
        }
    }

    fun requestInjectAct(baseActivity: BaseActivity<*, *>) {
        Intrinsics.checkNotNullParameter(baseActivity, "activity")
        var modelFactory1 = modelFactory
        var provider: SchedularProvider? = null
        if (modelFactory1 == null) {
            modelFactory1 = null
        }
        var interfaceDataManager2 = interfaceDataManager
        if (interfaceDataManager2 == null) {
            interfaceDataManager2 = null
        }
        val schedularProvider2 = schedularProvider
        if (schedularProvider2 == null) {
        } else {
            provider = schedularProvider2
        }
        baseActivity.inject(modelFactory1, interfaceDataManager2, provider)
    }

    public override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

    private fun checkAppReplacingState() {
        if (resources == null) {
            Process.killProcess(Process.myPid())
        }
    }

    companion object {
        private var mInstance: MainApplication? = null
        var application: MainApplication? = null
        private var prefManager: PrefManager? = null
        var sContext: Context? = null
        var context: Context? = null

        val instance: MainApplication?
            get() {
                synchronized(MainApplication::class.java) {
                    synchronized(MainApplication::class.java) {
                        application = mInstance
                    }
                }
                return application
            }

        fun isNetworkConnected(activity: Activity): Boolean {
            val cm = activity.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
        }

        fun getPrefManager(): PrefManager? {
            return prefManager
        }
    }
}
