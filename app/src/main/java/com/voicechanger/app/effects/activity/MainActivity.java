package com.voicechanger.app.effects.activity;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;
import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

import com.voicechanger.app.effects.BroadcastReceiver;
import com.voicechanger.app.effects.BuildConfig;
import com.voicechanger.app.effects.R;
import com.voicechanger.app.effects.allBaseAct.BaseActivity;
import com.voicechanger.app.effects.allBaseAct.BaseFragment;
import com.voicechanger.app.effects.custUi.AppConstant;
import com.voicechanger.app.effects.custUi.constatnt.TapClick;
import com.voicechanger.app.effects.databinding.ActivityMainBinding;
import com.voicechanger.app.effects.viewModel.MainActViewModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

public final class MainActivity extends BaseActivity<MainActViewModel, ActivityMainBinding> {

    // Update Variable
    public int MY_REQUEST_CODE = 111;
    AppUpdateManager unregisterListener;

//    -----------------

    //Permission Variable
    String[] strArr;
    String[] strArr33;
    boolean ifPer = false;
    Dialog settingDialog;
    Dialog permission_dialog;
    private Dialog dialog;
    private int requestCode = -1;
    public boolean aBoolean = false;
    private ActivityResultLauncher<Intent> resultHandler = null;



    //    -----------------------------------------
    private AlertDialog alertDialog;
    public boolean isResumeApp;
    Dialog exitDialog;
    public static boolean interAdsShow = false;

    public Class<MainActViewModel> createViewModel() {
        return MainActViewModel.class;
    }

    public int getContent() {

        strArr = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.RECORD_AUDIO"};
        strArr33 = new String[]{"android.permission.RECORD_AUDIO", "android.permission.READ_MEDIA_AUDIO"};

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver();
        ContextCompat.registerReceiver(
                this,
                broadcastReceiver,
                broadcastReceiver.getDataFilter(),
                ContextCompat.RECEIVER_NOT_EXPORTED
        );

        // PermissionCheck
        permissionCheck();
        registerForActivityResult();

        Log.e("VoiceChanger", "MainAct_onCreate");


        return R.layout.activity_main;
    }

    public void navigate(int i, Bundle bundle2, boolean z) {
    }

    public void navigateUp() {
    }

    public void onFragmentResumed(BaseFragment<?, ?> baseFragment) {
        Intrinsics.checkNotNullParameter(baseFragment, "fragment");
    }

    public void switchFragment(KClass<?> kClass, Bundle bundle2, boolean z) {
        Intrinsics.checkNotNullParameter(kClass, "fragment");
    }

    public void mainView() {
        showExitDialog();
        updatedialog();

        AlertDialog dialog1 = new AlertDialog.Builder(this, R.style.AlertDialogCustom).create();
        this.alertDialog = dialog1;
        AlertDialog alertDialog2 = null;
        dialog1.setTitle(getString(R.string.Grant_Permission));
        AlertDialog alertDialog1 = this.alertDialog;
        if (alertDialog1 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alertDialog");
            alertDialog1 = null;
        }
        alertDialog1.setCancelable(false);
        AlertDialog alertDialog4 = this.alertDialog;
        if (alertDialog4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alertDialog");
            alertDialog4 = null;
        }
        alertDialog4.setMessage(getString(R.string.Please_grant_all_permissions));
        AlertDialog dialog2 = this.alertDialog;
        if (dialog2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alertDialog");
        } else {
            alertDialog2 = dialog2;
        }

        alertDialog2.setButton(-1, getString(R.string.Go_to_setting), new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intrinsics.checkNotNullParameter(this, "this$0");
                AppConstant.APP_CONSTANT.setCheckResumePermissionMain(true);
                AlertDialog alertDialog2 = alertDialog;
                if (alertDialog2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("alertDialog");
                    alertDialog2 = null;
                }
                alertDialog2.dismiss();
                requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.RECORD_AUDIO"}, 1112);
                Intent strIntent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                strIntent.setData(Uri.fromParts("package", getApplicationContext().getPackageName(), (String) null));
                startActivity(strIntent);
            }
        });




    }

    private void showExitDialog() {
        exitDialog = new Dialog(MainActivity.this, R.style.BottomSheetDialogTheme);
        exitDialog.setContentView(R.layout.dialog_exit);
        exitDialog.setCancelable(true);
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.getWindow().setLayout(-1, -2);
        exitDialog.getWindow().setGravity(Gravity.CENTER);
        exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView btnExit = exitDialog.findViewById(R.id.btnExit);
        ImageView imgClose = exitDialog.findViewById(R.id.imgClose);

        imgClose.setOnClickListener(v -> {
            exitDialog.dismiss();
        });

        btnExit.setOnClickListener(v -> {
            exitDialog.dismiss();
            finishAffinity();
        });
    }

    public void initViews() {
        toolBar();
        mainEvents();
    }

    private void toolBar() {
        TapClick.tap(getBindingData().toolbar.ivMenu, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                getBindingData().drawerLayout.openDrawer((int) GravityCompat.START);
                return null;
            }
        });
        TapClick.tap(getBindingData().layoutContent.llLanguage, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                Log.e("VoiceChanger", "Click_Language");
                closeDrawer();
                startActivity(new Intent(MainActivity.this, LangActivity.class));
                return null;
            }
        });

        getBindingData().layoutContent.txtCancel.setOnClickListener(v -> {
            if (getBindingData().drawerLayout.isDrawerOpen(GravityCompat.START)) {
                getBindingData().drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        TapClick.tap(getBindingData().layoutContent.llRateUs, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                closeDrawer();
                Log.e("VoiceChanger", "Click_Rate");

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                return null;
            }
        });
        TapClick.tap(getBindingData().layoutContent.llShare, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                AppConstant.APP_CONSTANT.setCheckResumeShare(true);
                closeDrawer();
                appShare();
                return null;
            }
        });

        TapClick.tap(getBindingData().layoutContent.llPolicy, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                closeDrawer();
                Log.e("VoiceChanger", "Click_Policy");

                Intent intent = new Intent(getApplicationContext(), PrivacyActivity.class);
                startActivity(intent);
                return null;
            }
        });
    }

    private void mainEvents() {

        TapClick.tap(getBindingData().llRecord, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                Log.e("tap--", "invoke: ");

                nextActivity(RecordingActivity.class, null);
                return null;
            }
        });

        TapClick.tap(getBindingData().llOpenFile, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {

                nextActivity(OpenFileActivity.class, null);

                return null;
            }
        });
        TapClick.tap(getBindingData().llTextAudio, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                nextActivity(TxtToAudioActivity.class, null);

                return null;
            }
        });
        TapClick.tap(getBindingData().llMyVoice, new Function1<View, Unit>() {
            @Override
            public Unit invoke(View view) {
                nextActivity(CreationActivity.class, null);
                return null;
            }
        });
    }


    public void onResume() {
        super.onResume();

        Log.e("VoiceChanger", "MainAct_onResume");

        if (aBoolean) {
            aBoolean = false;
            Log.e("bbb", "onResume: ");
            sendnotification();
        }

        if (AlreadyGranted()) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            } else if (permission_dialog != null && permission_dialog.isShowing()) {
                permission_dialog.dismiss();
            } else if (settingDialog != null && settingDialog.isShowing()) {
                settingDialog.dismiss();
            }
        }

        unregisterListener.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                try {
                    unregisterListener.startUpdateFlowForResult(appUpdateInfo, IMMEDIATE, this, MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                SnackBarUpdateDialog();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (exitDialog != null && exitDialog.isShowing()) {

        }
    }

    // UpdateDialog
    public void updatedialog() {
        unregisterListener = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = unregisterListener.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && (appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE) || appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE))) {
                try {
                    Log.e("uuu", "appUpdateInfoTask:::::2222222222:::::::: ");
                    unregisterListener.startUpdateFlowForResult(appUpdateInfo, this, AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).setAllowAssetPackDeletion(true).build(), MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    Log.e("uuu", "appUpdateInfoTask:::::11111:::::::: " + e);
                    e.printStackTrace();
                }
            }
        });
        unregisterListener.registerListener(listener);

    }

    InstallStateUpdatedListener listener = state -> {

        if (state.installStatus() == InstallStatus.DOWNLOADING) {
            long bytesDownloaded = state.bytesDownloaded();
            long totalBytesToDownload = state.totalBytesToDownload();

        }
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            SnackBarUpdateDialog();
        }
    };

    private void SnackBarUpdateDialog() {
        Snackbar make = Snackbar.make(findViewById(R.id.drawer_layout), "App Update Alomost done.", Snackbar.LENGTH_INDEFINITE);
        make.setAction("RESTART", view -> unregisterListener.completeUpdate());
        make.setActionTextColor(getResources().getColor(R.color.black));
        make.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("VoiceChanger", "MainAct_onStop");
        unregisterListener.unregisterListener(listener);
    }

    private void copySimpleToDevice(int ii, String s) throws IOException {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = getResources().openRawResource(ii);
            Intrinsics.checkNotNullExpressionValue(inputStream, "resources.openRawResource(id)");
            outputStream = new FileOutputStream(s);
            byte[] bytes = new byte[1024];
            while (true) {
                int readInt = inputStream.read(bytes);
                if (readInt > 0) {
                    outputStream.write(bytes, 0, readInt);
                } else {
                    inputStream.close();
                    outputStream.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            inputStream.close();
            outputStream.close();
            throw th;
        }
    }

    public void appShare() {
        try {
            Log.e("VoiceChanger", "Click_Share");
            this.isResumeApp = true;
            Intent intentShare = new Intent("android.intent.action.SEND");
            intentShare.setType("text/plain");
            String msgShare;
            msgShare = getResources().getString(R.string.appShare) + "\n\n" + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            intentShare.putExtra("android.intent.extra.SUBJECT", getString(R.string.app_name));
            intentShare.putExtra("android.intent.extra.TEXT", msgShare);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(Intent.createChooser(intentShare, getString(R.string.share_to)));
                }
            }, 250);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeDrawer() {
        if (getBindingData().drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getBindingData().drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void onBackPressed() {
        Log.e("VoiceChanger", "MainAct_onBack");
        if (getBindingData().drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getBindingData().drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            exitDialog.show();
            Log.e("VoiceChanger", "ExitDialog_Show");
        }
    }





    // Permission Methods
    public void permissiondialog() {
        Log.e("vvccc", "permissiondialog: ");
        LayoutInflater dialogLayout = LayoutInflater.from(MainActivity.this);
        View DialogView = dialogLayout.inflate(R.layout.dialog_permission, null);
        permission_dialog = new Dialog(MainActivity.this);
        permission_dialog.setContentView(DialogView);
        permission_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        permission_dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        permission_dialog.getWindow().setLayout(-1, -2);
        permission_dialog.setCancelable(false);
        permission_dialog.setCanceledOnTouchOutside(false);

        ((TextView) permission_dialog.findViewById(R.id.permission_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                permission_dialog.dismiss();
                if (AlreadyGranted()) {
                    return;
                }
                Log.e("zzzzz", "onActivityResult: 222");
                requestPermission();
            }
        });
        ((TextView) permission_dialog.findViewById(R.id.cancle_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                permission_dialog.dismiss();
                finishAffinity();
            }
        });
        permission_dialog.show();
    }

    private void openSettingsDialog() {
        LayoutInflater dialogLayout = LayoutInflater.from(MainActivity.this);
        View DialogView = dialogLayout.inflate(R.layout.dialog_permission, null);
        settingDialog = new Dialog(MainActivity.this);
        settingDialog.setContentView(DialogView);
        settingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        settingDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        settingDialog.getWindow().setLayout(-1, -2);

        settingDialog.setCancelable(false);
        settingDialog.setCanceledOnTouchOutside(false);
        ((TextView) settingDialog.findViewById(R.id.cancle_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                settingDialog.dismiss();
                finishAffinity();
            }
        });
        ((TextView) settingDialog.findViewById(R.id.permission_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                MainActivity.this.settingDialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResultt(intent, 100);
            }
        });
        settingDialog.show();

    }

    private void registerForActivityResult() {
        this.resultHandler = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() {
            public void onActivityResult(Object var1) {
                this.onActivityResult((ActivityResult) var1);
            }

            public void onActivityResult(ActivityResult result) {
                MainActivity.this.onActivityResult(requestCode, result.getResultCode(), result.getData());
                requestCode = -1;
            }
        });
    }

    private boolean AlreadyGranted() {
        int result;
        int result1;
        int result2;
        int result3;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO);
            result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
            result3 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS);
            return result == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED;
        } else {
            result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_AUDIO)) {
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO);
            }
            ActivityCompat.requestPermissions(this, strArr33, 1);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO);
            }
            ActivityCompat.requestPermissions(this, strArr, 1);
            Log.e("per", "requestPermission: ");
        }

    }

    public void startActivityForResultt(Intent intent, int requestCode) {
        this.requestCode = requestCode;
        if (resultHandler != null) {
            resultHandler.launch(intent);
        }
    }

    private void requestForSpecificPermission() {
        Log.e("qqaa", "requestForSpecificPermission: qqqq");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
    }

    public void sendnotification() {
        Log.e("bbb", "sendnotification: ");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            Log.e("bbb", "sendnotification: 1111");
            if (!checkIfAlreadyhavePermission()) {
                Log.e("bbb", "sendnotification: 2222");
                requestForSpecificPermission();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private boolean checkIfAlreadyhavePermission() {
        Log.e("nnnnn", "checkIfAlreadyhavePermission: ");
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void permissionCheck() {
        if (AlreadyGranted()) {
//            new RattingApp(MainActivity.this).show();
            return;
        }
        Log.e("zzzzz", "onActivityResult: 333");
        requestPermission();
    }

    private void permissionNotAllowed() {

        Dialog dialog = new Dialog(MainActivity.this);
        TextView yes;
        TextView cancel;

        this.dialog = dialog;
        this.dialog.setContentView(R.layout.dialog_permission);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.setCancelable(false);
        this.dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (!this.dialog.isShowing()) {
            this.dialog.show();
            Log.e("per", "permissionNotAllowed: ");
        }

        yes = this.dialog.findViewById(R.id.permission_btn);
        cancel = this.dialog.findViewById(R.id.cancle_btn);

        yes.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
            @Override
            public void onClick(View view) {
                MainActivity.this.dialog.dismiss();
                String calendar = Manifest.permission.WRITE_CALENDAR;
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, calendar)) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
                    return;
                }
                Intent intent = new Intent();
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//for Android 5-7
                intent.putExtra("app_package", getPackageName());
                intent.putExtra("app_uid", getApplicationInfo().uid);
// for Android 8 and above
                intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
                aBoolean = true;
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finishAffinity();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);

        if (i == 1) {
            boolean allPermissionsGranted = true;
            if (iArr.length > 0) {
                for (int grantResult : iArr) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        allPermissionsGranted = false;
                        break;
                    }
                }
            }
            if (allPermissionsGranted) {
                if (!ifPer) {
//                    Toast.makeText(this, getString(R.string.grantPermission), Toast.LENGTH_SHORT).show();
                    ifPer = true;
                    Log.e("VoiceChanger", "Permission_Granted");
                    sendnotification();
                }
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S_V2) {
                    permissionCheck();
                }
            } else {
                boolean showRationale;
                boolean showRationale1;
                boolean showRationale2;
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
                    showRationale1 = shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_AUDIO);
                    showRationale2 = shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO);
                    if (!showRationale1 && !showRationale2) {
                        openSettingsDialog();
                    } else {
                        permissiondialog();
                    }
                } else {
                    showRationale = shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    showRationale1 = shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE);
                    showRationale2 = shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO);
                    if (!showRationale && !showRationale1 && !showRationale2) {
                        openSettingsDialog();
                    } else {
                        permissiondialog();
                    }
                }
            }
        }

        if (i == 101) {
            if (iArr[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.grantPermission), Toast.LENGTH_SHORT).show();
                Log.e("VoiceChanger", "Permission_Granted");
                permissionCheck();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
                    requestForSpecificPermission();
                } else {
                    permissionNotAllowed();
                }
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // For Update Dialog
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.e("TAG", "onActivityResult: " + resultCode);
            }
        }


        // For Storage Permission
        if (requestCode == 100) {
            if (AlreadyGranted()) {
                Log.e("VoiceChanger", "Permission_Granted");
                Toast.makeText(this, getString(R.string.grantPermission), Toast.LENGTH_SHORT).show();
                sendnotification();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S_V2) {
                    permissionCheck();
                }
                return;
            }
            requestPermission();
        }
    }


}
