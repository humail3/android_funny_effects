package com.voicechanger.app.effects.ads;

import android.app.Activity;


import com.google.android.ump.ConsentInformation;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GDPR {
    static ConsentInformation consentInformation;
    public static AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);

    public static void initGDPR(Activity activity,List<String> testDeviceIds,getAdsDataListner listner) {

//        if (AdsHelperClass.getIsAdEnable() == 1) {
//            consentInformation = UserMessagingPlatform.getConsentInformation(activity);
//
//            // Check if you can initialize the Google Mobile Ads SDK in parallel
//            // while checking for new consent information. Consent obtained in
//            // the previous session can be used to request ads.
//
//            ConsentRequestParameters params = new ConsentRequestParameters
//                    .Builder()
//                    .setTagForUnderAgeOfConsent(false)
//                    .build();
//            consentInformation.requestConsentInfoUpdate(
//                    activity,
//                    params,
//                    (ConsentInformation.OnConsentInfoUpdateSuccessListener) () -> {
//                        UserMessagingPlatform.loadAndShowConsentFormIfRequired(
//                                activity,
//                                (ConsentForm.OnConsentFormDismissedListener) loadAndShowError -> {
//                                    if (loadAndShowError != null) {
//                                        // Consent gathering failed.
//                                        Log.w("TAG", String.format("%s: %s",
//                                                loadAndShowError.getErrorCode(),
//                                                loadAndShowError.getMessage()));
//                                        AdmobSdk(activity,listner, testDeviceIds);
//                                    }
//
//                                    // Consent has been gathered.
//                                    if (consentInformation.canRequestAds()) {
//                                        initializeMobileAdsSdk(activity,listner, testDeviceIds);
//                                    }
//                                }
//                        );
//
//                    },
//                    (ConsentInformation.OnConsentInfoUpdateFailureListener) requestConsentError -> {
//                        // Consent gathering failed.
//                        Log.w("TAG", String.format("%s: %s",
//                                requestConsentError.getErrorCode(),
//                                requestConsentError.getMessage()));
//
//                        AdmobSdk(activity,listner, testDeviceIds);
////
//                    });
//            if (consentInformation.canRequestAds()) {
//                initializeMobileAdsSdk(activity,listner, testDeviceIds);
//            }
//
//        } else {
//            listner.onSuccess();
//        }
    }

    private static void initializeMobileAdsSdk(Activity activity,getAdsDataListner listner, List<String> testDeviceIds) {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return;
        }

        AdmobSdk(activity,listner, testDeviceIds);
    }

    private static void AdmobSdk(Activity activity,getAdsDataListner listner, List<String> testDeviceIds) {




    }

    public interface getAdsDataListner {

        void onSuccess();
        void onFail();
    }

}
