package com.voicechanger.app.effects.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.voicechanger.app.effects.BuildConfig;
import com.voicechanger.app.effects.FilenameUtils;
import com.voicechanger.app.effects.R;
import com.voicechanger.app.effects.activity.CreationActivity;
import com.voicechanger.app.effects.activity.MusicPlayerActivity;
import com.voicechanger.app.effects.adapters.ItemDowAudioAdapter;
import com.voicechanger.app.effects.allBaseAct.BaseFragment;
import com.voicechanger.app.effects.allDialogs.DeleteDialog;
import com.voicechanger.app.effects.allDialogs.RenameDialog;
import com.voicechanger.app.effects.allDialogs.SetRingtoneDialog;
import com.voicechanger.app.effects.custUi.AppConstant;
import com.voicechanger.app.effects.custUi.MobileState;
import com.voicechanger.app.effects.custUi.constatnt.RingtonePermission;
import com.voicechanger.app.effects.databinding.FragmentStudioAudioBinding;
import com.voicechanger.app.effects.getApiData.allModel.AudioModel;
import com.voicechanger.app.effects.viewModel.CreationViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

public final class FragAudioStudio extends BaseFragment<CreationViewModel, FragmentStudioAudioBinding> {


    //    -----------------------------------------

    public ItemDowAudioAdapter audioAdapter;
    public AudioModel audioModel;
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<AudioModel> audioModels;

            if (intent.getAction().equals("delete_file")) {
                TypeIntrinsics.asMutableCollection(list).remove(audioModel);
                Log.e("tt---", "bindViewModel: list:  " + list.size());
                setAdapter();
                Toast.makeText(getContext(), getString(R.string.Successfully), Toast.LENGTH_SHORT).show();
                ItemDowAudioAdapter adapter = FragAudioStudio.this.audioAdapter;
                if (adapter != null && (audioModels = adapter.getModelList()) != null && audioModels.size() == 0) {
                    list.add(new AudioModel("", "Sample.mp3", "", 0, "", ""));
                    ItemDowAudioAdapter audioAdapter1 = FragAudioStudio.this.audioAdapter;
                    audioAdapter1.notifyDataSetChanged();
                }
            } else if (intent.getAction().equals("rename_file")) {
                Iterator it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    AudioModel next = (AudioModel) it.next();
                    if (Intrinsics.areEqual(next, next)) {
                        next.setFileName(newFileName);
                        break;
                    }
                }
                setAdapter();
                Toast.makeText(getContext(), getString(R.string.Successfully), Toast.LENGTH_SHORT).show();
            }
        }
    };
    public DeleteDialog deleteDialog;
    public RenameDialog renameDialog;
    public File fromPath;
    public List<AudioModel> list = new ArrayList();
    public String newFileName = "";

    public File fileTo;
    public ArrayList<Uri> uris = new ArrayList<>();


    public Class<CreationViewModel> createViewModel() {
        return CreationViewModel.class;
    }

    public int getFragResourceLayout() {
        return R.layout.fragment_studio_audio;
    }

    public void onDestroyView() {
        super.onDestroyView();
    }


    public void bindViews() {



        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("delete_file");
        intentFilter.addAction("rename_file");
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(this.receiver, intentFilter);
        RecyclerView rclAudio = ((FragmentStudioAudioBinding) getDataBinding()).rclAudio;
        rclAudio.setHasFixedSize(true);
        Context requireContext = requireContext();
        ItemDowAudioAdapter itemDowAudioAdapter = new ItemDowAudioAdapter(requireContext, new ArrayList(), new Function1<AudioModel, Unit>() {
            @Override
            public Unit invoke(AudioModel audioModel) {
                Bundle bundle = new Bundle();
                bundle.putString(AppConstant.APP_CONSTANT.getKEY_PATH_VOICE(), audioModel.getPath());
                bundle.putString(AppConstant.APP_CONSTANT.getKEY_FILENAME_EFFECT(), audioModel.getFileName());
                showActivity(MusicPlayerActivity.class, bundle);

                return null;
            }
        }, new Function1<AudioModel, Unit>() {
            @Override
            public Unit invoke(AudioModel audioModel) {
                FragAudioStudio.this.audioModel = audioModel;
                FragmentActivity requireActivity = requireActivity();
                String fileName = audioModel.getFileName();
                renameDialog = new RenameDialog(requireActivity, true, fileName, new Function2<String, String, Unit>() {
                    public Unit invoke(String str, String str2) {
                        boolean z;
                        ItemDowAudioAdapter audioAdapter1 = audioAdapter;
                        String str3 = null;
                        String newName = str + "_" + str2;
                        List<AudioModel> lists = audioAdapter1 == null ? null : audioAdapter1.getModelList();
                        Intrinsics.checkNotNull(lists);
                        assert lists != null;

                        Iterator<AudioModel> it = lists.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (Intrinsics.areEqual(FilenameUtils.getBaseName(it.next().getFileName()), newName)) {
                                    z = true;
                                    break;
                                }
                            } else {
                                z = false;
                                break;
                            }
                        }

                        if (z) {
                            Toast.makeText(getContext(), getContext().getString(R.string.this_audio_already_exists), Toast.LENGTH_SHORT).show();
                            return null;
                        }

                        AudioModel audioModel1 = audioModel;
                        fromPath = new File(audioModel1.getPath());

                        File file = new File(audioModel1.getPath());
                        File file2 = new File(file.getAbsolutePath());
                        File file3 = new File(file.getAbsolutePath().replace(file.getName(), str + "_" + str2 + ".mp3"));

                        fileTo = file3;

                        CreationActivity.COMPANION.setFrom(fromPath);
                        CreationActivity.COMPANION.setTo(fileTo);
                        File from1 = fromPath;

                        if (Build.VERSION.SDK_INT <= 29) {
                            file2.renameTo(fileTo);
                            Toast.makeText(getContext(), getString(R.string.Successfully), Toast.LENGTH_SHORT).show();
                            setAdapter();
                        } else {
                            if (from1 != null) {
                                str3 = from1.getAbsolutePath();
                            }
                            String valueOf = String.valueOf(str3);
                            Log.e("rename---", "invoke: valueOf :: " + valueOf);
                            Context context = getContext();
                            Uri withAppendedId = ContentUris.withAppendedId(MediaStore.Audio.Media.getContentUri("external"), getFilePathToMedia(valueOf, context));
                            Log.e("rename---", "invoke: withAppendedId :: " + withAppendedId);
                            uris.clear();
                            uris.add(withAppendedId);
                            reqRenameData(uris);
                        }

                        RenameDialog rename = renameDialog;
                        if (rename != null) {
                            rename.dismiss();
                        }
                        return null;
                    }
                });

                RenameDialog renameDialog1 = renameDialog;
                renameDialog1.show();
                return null;
            }
        }, new Function1<AudioModel, Unit>() {
            @Override
            public Unit invoke(AudioModel audioModel) {

                if (RingtonePermission.checkSystemWritePermission(requireContext())) {
                    new SetRingtoneDialog(requireActivity(), true, new Function1<MobileState, Unit>() {
                        @Override
                        public Unit invoke(MobileState phoneState) {

                            int[] iArr = new int[MobileState.values().length];
                            iArr[MobileState.STATE_RINGTONE.ordinal()] = 1;
                            iArr[MobileState.STATE_ALARM.ordinal()] = 2;
                            iArr[MobileState.STATE_NOTIFICATION.ordinal()] = 3;
                            int $EnumSwitchMapping$0[] = iArr;

                            int i = $EnumSwitchMapping$0[phoneState.ordinal()];
                            if (i == 1) {
                                Context requireContext = requireContext();
                                Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext()");
                                settingsPhoneRing(requireContext, audioModel.getPath(), MobileState.STATE_RINGTONE, true);
                            } else if (i == 2) {
                                Context requireContext2 = requireContext();
                                Intrinsics.checkNotNullExpressionValue(requireContext2, "requireContext()");
                                settingsPhoneRing(requireContext2, audioModel.getPath(), MobileState.STATE_ALARM, true);
                            } else if (i == 3) {
                                Context requireContext3 = requireContext();
                                Intrinsics.checkNotNullExpressionValue(requireContext3, "requireContext()");
                                settingsPhoneRing(requireContext3, audioModel.getPath(), MobileState.STATE_NOTIFICATION, true);
                            }
                            return null;
                        }
                    }).show();
                } else {
                    AppConstant.APP_CONSTANT.setCheckResumePermissionRingtone(true);
                    RingtonePermission.openAndroidPermissionsMenu((Activity) requireContext);
                }
                return Unit.INSTANCE;
            }
        }, new Function1<AudioModel, Unit>() {
            @Override
            public Unit invoke(AudioModel audioModel) {
                Intrinsics.checkNotNullParameter(audioModel, "it");
                AppConstant.APP_CONSTANT.setCheckResumeShareMyVoice(true);
                Context requireContext = requireContext();
                Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext()");
                shareFileProject(requireContext, audioModel.getPath());
                return null;
            }
        }, new Function1<AudioModel, Unit>() {
            @Override
            public Unit invoke(AudioModel p1) {
                audioModel = p1;
                AudioModel model = p1;
                final File file = new File(model == null ? null : model.getPath());
                FragmentActivity requireActivity = requireActivity();

                if (Build.VERSION.SDK_INT <= 29) {
                    deleteDialog = new DeleteDialog(requireActivity, true, new Function0<Unit>() {
                        public Unit invoke() {
                            List<AudioModel> lists;
                            file.delete();
                            TypeIntrinsics.asMutableCollection(list).remove(p1);
                            boolean z = false;
                            Toast.makeText(getContext(), getString(R.string.Successfully), Toast.LENGTH_SHORT).show();
                            ItemDowAudioAdapter audioAdapter2 = audioAdapter;
                            if (!(audioAdapter2 == null || (lists = audioAdapter2.getModelList()) == null || lists.size() != 0)) {
                                z = true;
                            }
                            if (z) {
                                list.add(new AudioModel("", "Sample.mp3", "", 0, "", ""));
                                ItemDowAudioAdapter audioAdapter1 = audioAdapter;
                                audioAdapter1.notifyDataSetChanged();
                            }
                            setAdapter();
                            return null;
                        }
                    });
                    DeleteDialog deleteDialog1 = deleteDialog;
                    deleteDialog1.show();
                } else {
                    String str = file.getAbsolutePath().toString();
                    Context context = getContext();
                    Uri withAppendedId = ContentUris.withAppendedId(MediaStore.Audio.Media.getContentUri("external"), getFilePathToMedia(str, context));
                    uris.clear();
                    uris.add(withAppendedId);
                    reqDeleteData(uris);
                }
                return null;
            }
        });
        this.audioAdapter = itemDowAudioAdapter;
        rclAudio.setAdapter(itemDowAudioAdapter);

    }

    public void shareFileProject(Context context, String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(str, "path");
        Uri uriForFile = FileProvider.getUriForFile(context, context.getPackageName()+".provider", new File(str));
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("audio/*");
        String msgShare;
        msgShare = context.getResources().getString(R.string.appShare) + "\n\n" + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n";
        intent.putExtra("android.intent.extra.SUBJECT", context.getString(R.string.app_name));
        intent.putExtra("android.intent.extra.TEXT", msgShare);
        intent.putExtra("android.intent.extra.STREAM", uriForFile);

        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)));
    }

    public void bindViewModels() {
        setAdapter();
    }

    public void setAdapter() {

        Context requireContext = requireContext();
        ((CreationViewModel) getViewModel()).getAudioDataClass(requireContext, getDataBinding().llLoading, getDataBinding().adViewBanner, getDataBinding().noData);

        CreationActivity.COMPANION.getLiveSortCreateStudio().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer num) {
                if (num != null && num == 1) {
                    ((CreationViewModel) getViewModel()).getListMutableLiveData().observe(requireActivity(), new Observer<List<AudioModel>>() {
                        @Override
                        public void onChanged(List<AudioModel> audioModels) {
                            Log.e("ee----", "onChanged: audioModels 11  :: " + audioModels.size());
                            ItemDowAudioAdapter itemDowAudioAdapter;
                            if ((itemDowAudioAdapter = audioAdapter) != null) {
                                itemDowAudioAdapter.sortLatestData(audioModels);
                            }
                        }
                    });
                } else if (num != null && num == 2) {
                    ((CreationViewModel) getViewModel()).getListMutableLiveData().observe(requireActivity(), new Observer<List<AudioModel>>() {
                        @Override
                        public void onChanged(List<AudioModel> audioModels) {
                            Log.e("ee----", "onChanged: audioModels 22  :: " + audioModels.size());
                            ItemDowAudioAdapter itemDowAudioAdapter;
                            if ((itemDowAudioAdapter = audioAdapter) != null) {
                                itemDowAudioAdapter.sortOldestData(audioModels);
                            }
                        }
                    });
                }
            }
        });

        CreationActivity.COMPANION.getLiveSortNameStudio().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer num) {
                Intrinsics.checkNotNullParameter(requireContext, "this$0");
                if (num != null && num.intValue() == 1) {
                    ((CreationViewModel) getViewModel()).getListMutableLiveData().observe(requireActivity(), new Observer<List<AudioModel>>() {
                        @Override
                        public void onChanged(List<AudioModel> audioModels) {
                            ItemDowAudioAdapter itemDowAudioAdapter;
                            Intrinsics.checkNotNullParameter(requireContext, "this$0");
                            if (audioModels != null && (itemDowAudioAdapter = audioAdapter) != null) {
                                itemDowAudioAdapter.sortLatestDataByFileName(audioModels);
                            }
                        }
                    });
                } else if (num != null && num.intValue() == 2) {
                    ((CreationViewModel) getViewModel()).getListMutableLiveData().observe(requireActivity(), new Observer<List<AudioModel>>() {
                        @Override
                        public void onChanged(List<AudioModel> audioModels) {
                            ItemDowAudioAdapter itemDowAudioAdapter;
                            Intrinsics.checkNotNullParameter(requireContext, "this$0");
                            if (audioModels != null && (itemDowAudioAdapter = audioAdapter) != null) {
                                itemDowAudioAdapter.sortOldestByFileName(audioModels);
                            }
                        }
                    });
                }
            }
        });

        Log.e("tt---", "setAdapter: " + list.size());
    }

    public final long getFilePathToMedia(String strPaths, Context mCtx) {
        ContentResolver resolver = mCtx.getContentResolver();
        Uri external = MediaStore.Files.getContentUri("external");
        Cursor cursor = resolver.query(external, new String[]{"_id"}, Intrinsics.stringPlus("_data", "=?"), new String[]{strPaths}, (String) null);
        long j = 0;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String string = cursor.getString(cursor.getColumnIndex("_id"));
                j = Long.parseLong(string);
            }
        }
        return j;
    }

    public void reqRenameData(ArrayList<Uri> uris) {
        if (Build.VERSION.SDK_INT >= 30) {
            PendingIntent writeRequest = MediaStore.createWriteRequest(getContext().getContentResolver(), uris);
            try {
                requireActivity().startIntentSenderForResult(writeRequest.getIntentSender(), 1112, (Intent) null, 0, 0, 67108864);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    }


    public void reqDeleteData(ArrayList<Uri> uris) {
        Log.e("del===", "reqDeleteData: uris : " + uris);
        PendingIntent deleteRequest = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            deleteRequest = MediaStore.createDeleteRequest(getContext().getContentResolver(), uris);
        }
        try {
            requireActivity().startIntentSenderForResult(deleteRequest.getIntentSender(), 1111, (Intent) null, 0, 0, 67108864);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(this.receiver);
    }


    public void settingsPhoneRing(Context mCtx, String strPath, MobileState stateMobile, boolean z) {

        int[] iArr = new int[MobileState.values().length];
        iArr[MobileState.STATE_ALARM.ordinal()] = 1;
        iArr[MobileState.STATE_NOTIFICATION.ordinal()] = 2;
        iArr[MobileState.STATE_RINGTONE.ordinal()] = 3;
        int[] switchMapping = iArr;


        File file;
        Intrinsics.checkNotNullParameter(mCtx, "context");
        Intrinsics.checkNotNullParameter(strPath, "path");
        Intrinsics.checkNotNullParameter(stateMobile, "state");
        try {
            file = new File(strPath);
            Uri fromFile = Uri.fromFile(file);
            int i = switchMapping[stateMobile.ordinal()];
            if (i == 1) {
                RingtoneManager.setActualDefaultRingtoneUri(mCtx, 4, fromFile);
                if (z) {
                    Toast makeText = Toast.makeText(mCtx, mCtx.getString(R.string.success), Toast.LENGTH_LONG);
                    makeText.show();
                }
            } else if (i == 2) {
                RingtoneManager.setActualDefaultRingtoneUri(mCtx, 2, fromFile);
                if (z) {
                    Toast makeText2 = Toast.makeText(mCtx, mCtx.getString(R.string.success), Toast.LENGTH_LONG);
                    makeText2.show();
                }
            } else if (i == 3) {
                RingtoneManager.setActualDefaultRingtoneUri(mCtx, 1, fromFile);
                if (z) {
                    Toast makeText3 = Toast.makeText(mCtx, mCtx.getString(R.string.success), Toast.LENGTH_LONG);
                    makeText3.show();
                }
            }
        } catch (Throwable th) {
            Log.e("setAsDefaultRingtone err", th.toString());
        }
    }

}
