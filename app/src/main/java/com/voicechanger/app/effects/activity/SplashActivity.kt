package com.voicechanger.app.effects.activity


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.voicechanger.app.effects.R
import com.voicechanger.app.effects.activity.LangActivity.hasPermissions
import com.voicechanger.app.effects.ads.RemoteAppDataModel
import com.voicechanger.app.effects.ads.SharedPreferencesWrapper


class SplashActivity : AppCompatActivity() {
    private var isAppOpenAdLoad = false
    var mFirebaseRemoteConfig: FirebaseRemoteConfig? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)


//        AdsHelperClass.setOpenAdsShowedCount(0)
//        getInstance()!!.setBoolean("isSplash", true)
//        GDPR.isMobileAdsInitializeCalled = AtomicBoolean(false)

        Handler(Looper.getMainLooper()).postDelayed({
            doNext(
                "{ \n" +
                        "\"Id\":1,\n" +
                        "\"bannerid\":\"ca-app-pub-3940256099942544/6300978111\",\n" +
                        "\"banner\":1,\n" +
                        "\"nativeids\":[\"ca-app-pub-3940256099942544/2247696110\"],\n" +
                        "\"native\":1,\n" +
                        "\"interstitialid\":\"ca-app-pub-3940256099942544/1033173712\",\n" +
                        "\"interstitial\":1,\n" +
                        "\"openadid\":\"ca-app-pub-3940256099942544/9257395921\",\n" +
                        "\"openad\":1,\n" +
                        "\"rewardadid\":\"ca-app-pub-3940256099942544/5224354917\",\n" +
                        "\"rewardad\":1,\n" +
                        "\"adtype\":\"admob\",\n" +
                        "\"is_ad_enable\":1,\n" +
                        "\"in_appreview\":1,\n" +
                        "\"review\":0,\n" +
                        "\"isactive\":1,\n" +
                        "\"ads_per_click\":3,\n" +
                        "\"ads_per_session\":100,\n" +
                        "\"app_open_count\":100,\n" +
                        "\"is_splash_on\":1,\n" +
                        "\"splash_time\":10,\n" +
                        "\"inter_time\":3,\n" +
                        "\"review_popup_count\":1\n" +
                        "}"
            )
        }, 2000)

//        onConfig()
    }

//
//    private fun onConfig() {
//        if (MainApplication.isNetworkConnected(this)) {
//            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
//            val configSettings =
//                FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(30).build()
//            mFirebaseRemoteConfig!!.setConfigSettingsAsync(configSettings)
//
//            mFirebaseRemoteConfig!!.fetchAndActivate()
//                .addOnCompleteListener(this) { task: Task<Boolean?> ->
//                    if (task.isSuccessful) {
//                        val mResponse = mFirebaseRemoteConfig!!.getString("config_data")
//                        doNext(mResponse)
//                        Log.e("REMOTE_CONFIG_DATA: ", mResponse)
//                    }
//                }.addOnFailureListener { e: Exception ->
//                Log.e(
//                    "Splash: ",
//                    "RemoteData: " + e.message
//                )
//            }
//        } else {
//            AdsHelperClass.setIsAdEnable(0)
//            toHome()
//        }
//    }

    private fun parseAppUserListModel(jsonObject: String?): RemoteAppDataModel? {
        try {
            val gson = Gson()
            val token: TypeToken<RemoteAppDataModel> =
                object : TypeToken<RemoteAppDataModel>() {}
            return gson.fromJson(jsonObject, token.type)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }


    private fun doNext(response: String?) {
        val appData = parseAppUserListModel(response)
        setAppData(appData!!)

        val testDeviceIds: List<String> = mutableListOf("42BF7565E0E1A46844EDD42C66097A3B")
        toHome()


    }

    private fun setAppData(appData: RemoteAppDataModel) {

    }




    private fun toHome() {

//        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
//        val LANGUAGE_SELECTED_VAR = preferences.getBoolean("", false)
        val LANGUAGE_SELECTED_VAR = SharedPreferencesWrapper(this).getBoolean("langKey", false)
        if (!LANGUAGE_SELECTED_VAR) {
            startActivity(Intent(this, LangActivity::class.java))
        } else {
            if (hasPermissions(this)) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, PermissionActivity::class.java))
            }
        }
        finish()
    }

}
