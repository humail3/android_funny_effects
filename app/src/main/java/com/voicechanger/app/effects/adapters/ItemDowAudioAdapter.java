package com.voicechanger.app.effects.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.voicechanger.app.effects.R;
import com.voicechanger.app.effects.allBaseAct.BaseAdapter;
import com.voicechanger.app.effects.custUi.constatnt.AppDataException;
import com.voicechanger.app.effects.databinding.ItemCreationBinding;
import com.voicechanger.app.effects.getApiData.allModel.AudioModel;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public final class ItemDowAudioAdapter extends BaseAdapter<ItemCreationBinding, AudioModel> {
    private final Context mCtx;
    private final List<AudioModel> modelList;
    private final Function1 onDeleteClick;
    private final Function1 onItemClick;
    private final Function1 onRenameClick;
    private final Function1 onSetAsClick;
    private final Function1 onShareClick;

    public int getLayoutRes() {
        return R.layout.item_creation;
    }

    public List<AudioModel> getModelList() {
        return this.modelList;
    }


    public Function1 getOnItemClick() {
        return this.onItemClick;
    }


    public Function1 getOnRenameClick() {
        return this.onRenameClick;
    }


    public Function1 getOnSetAsClick() {
        return onSetAsClick;
    }


    public Function1 getOnShareClick() {
        return this.onShareClick;
    }

    public Function1 getOnDeleteClick() {
        return this.onDeleteClick;
    }

    public ItemDowAudioAdapter(Context context2, List<AudioModel> list, Function1<? super AudioModel, Unit> onItemClick, Function1<? super AudioModel, Unit> onRenameClick, Function1<? super AudioModel, Unit> onSetAsClick, Function1<? super AudioModel, Unit> onShareClick, Function1<? super AudioModel, Unit> onDeleteClick) {
        super(list);
        this.mCtx = context2;
        this.modelList = list;
        this.onItemClick = onItemClick;
        this.onRenameClick = onRenameClick;
        this.onSetAsClick = onSetAsClick;
        this.onShareClick = onShareClick;
        this.onDeleteClick = onDeleteClick;
    }

    public RecyclerView.ViewHolder createVH(ItemCreationBinding itemAudioStudioBinding) {
        return new AudioViewHolder(this, itemAudioStudioBinding);
    }

    public final class AudioViewHolder extends BaseAdapter<ItemCreationBinding, AudioModel>.BaseVH<Object> {
        final ItemDowAudioAdapter itemDowAudioAdapter;

        public AudioViewHolder(ItemDowAudioAdapter itemDowAudioAdapter, ItemCreationBinding itemAudioStudioBinding) {
            super(itemDowAudioAdapter, itemAudioStudioBinding);
            this.itemDowAudioAdapter = itemDowAudioAdapter;
        }

        public void onItemClickListener(AudioModel audioModel) {
            super.onItemClickListener(audioModel);
            this.itemDowAudioAdapter.getOnItemClick().invoke(audioModel);
        }

        public String convertLongDataToDuration(long longTime) {
            String strFirst;
            String strSec;

            if (longTime > 3600000) {
                int i = (int) (longTime / 3600000);
                if (i < 10) {
                    strFirst = "0" + i + ":";
                } else {
                    strFirst = i + ":";
                }
            } else {
                strFirst = "";
            }
            int i2 = ((int) (longTime / 60000)) % 60;
            if (i2 < 10) {
                strSec = strFirst + "0" + i2 + ":";
            } else {
                strSec = strFirst + i2 + ":";
            }
            int i3 = ((int) (longTime / 1000)) % 60;
            if (i3 < 10) {
                return strSec + "0" + i3;
            }
            return strSec + i3;
        }

        public void bind(AudioModel audioModel) {
            super.bind(audioModel);
            getBinding().setAudioModel(audioModel);

            getBinding().tvDetail.setText(convertLongDataToDuration(Long.parseLong(audioModel.getDuration())) + " | " + convertSize(new File(audioModel.getPath()).length()));

            getBinding().ivMenu.setOnClickListener(v -> {
                BottomSheetDialog sheetDialog = new BottomSheetDialog(mCtx, R.style.BottomSheetDialogTheme);
                sheetDialog.setContentView(R.layout.dialog_menu);
                sheetDialog.setCanceledOnTouchOutside(false);
                sheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                sheetDialog.getWindow().setLayout(-1, -2);

                LinearLayout rename = sheetDialog.findViewById(R.id.llyRename);
                LinearLayout setAsRing = sheetDialog.findViewById(R.id.setAsRing);
                LinearLayout share = sheetDialog.findViewById(R.id.share);
                LinearLayout delete = sheetDialog.findViewById(R.id.delete);
                ImageView imgClose = sheetDialog.findViewById(R.id.imgClose);

                imgClose.setOnClickListener(d -> {
                    sheetDialog.dismiss();
                });

                setAsRing.setOnClickListener(d -> {
                    getOnSetAsClick().invoke(audioModel);
                    sheetDialog.dismiss();
                });
                share.setOnClickListener(d -> {
                    getOnShareClick().invoke(audioModel);
                    sheetDialog.dismiss();
                });
                delete.setOnClickListener(d -> {
                    getOnDeleteClick().invoke(audioModel);
                    sheetDialog.dismiss();
                });
                rename.setOnClickListener(d -> {
                    getOnRenameClick().invoke(audioModel);
                    sheetDialog.dismiss();
                });

                if (!sheetDialog.isShowing()) {
                    sheetDialog.show();
                }
            });

            Glide.with(mCtx).load(Integer.valueOf(AppDataException.getIconEffect(audioModel.getFileName()))).into(((ItemCreationBinding) getBinding()).ivEffect);
        }
    }

    public String convertLongDataToDuration(long longTime) {
        String strFirst;
        String strSec;
        if (longTime > 3600000) {
            int i = (int) (longTime / 3600000);
            if (i < 10) {
                strFirst = "0" + i + ":";
            } else {
                strFirst = i + ":";
            }
        } else {
            strFirst = "";
        }
        int i2 = ((int) (longTime / 60000)) % 60;
        if (i2 < 10) {
            strSec = strFirst + "0" + i2 + ":";
        } else {
            strSec = strFirst + i2 + ":";
        }
        int i3 = ((int) (longTime / 1000)) % 60;
        if (i3 < 10) {
            return strSec + "0" + i3;
        }
        return strSec + i3;
    }

    public String convertSize(long j) {
        double d = j / 1024.0d;
        if (d >= 1048576.0d) {
            double d2 = 1024;
            return new DecimalFormat("0.00").format((d / d2) / d2) + " GB";
        } else if (d >= 1024.0d) {
            return new DecimalFormat("0.00").format(d / 1024) + " MB";
        } else {
            return new DecimalFormat("0").format(d) + " KB";
        }
    }

    public void sortLatestData(List<AudioModel> audioModels) {
        this.modelList.clear();
        if (audioModels.size() > 1) {
            CollectionsKt.sortWith(audioModels, new Comparator<AudioModel>() {
                @Override
                public int compare(AudioModel o1, AudioModel o2) {
                    return ComparisonsKt.compareValues(Long.valueOf(((AudioModel) o2).getDateCreate()), Long.valueOf(((AudioModel) o1).getDateCreate()));
                }
            });
        }
        this.modelList.addAll(audioModels);
        notifyDataSetChanged();
    }

    public void sortOldestData(List<AudioModel> audioModels) {
        this.modelList.clear();
        if (audioModels.size() > 1) {
            CollectionsKt.sortWith(audioModels, new Comparator<AudioModel>() {
                @Override
                public int compare(AudioModel o1, AudioModel o2) {
                    return ComparisonsKt.compareValues(Long.valueOf(((AudioModel) o1).getDateCreate()), Long.valueOf(((AudioModel) o2).getDateCreate()));
                }
            });
        }
        this.modelList.addAll(audioModels);
        notifyDataSetChanged();
    }

    public void sortLatestDataByFileName(List<AudioModel> audioModels) {
        Intrinsics.checkNotNullParameter(audioModels, "newList");
        this.modelList.clear();
        if (audioModels.size() > 1) {
            CollectionsKt.sortWith(audioModels, new Comparator<AudioModel>() {
                @Override
                public int compare(AudioModel o1, AudioModel o2) {
                    String s = o2.getFileName().toLowerCase();
                    String s1 = o1.getFileName().toLowerCase();
                    return ComparisonsKt.compareValues(s, s1);
                }
            });
        }
        this.modelList.addAll(audioModels);
        notifyDataSetChanged();
    }

    public void sortOldestByFileName(List<AudioModel> list) {
        Intrinsics.checkNotNullParameter(list, "newList");
        this.modelList.clear();
        if (list.size() > 1) {
            CollectionsKt.sortWith(list, new Comparator<AudioModel>() {
                @Override
                public int compare(AudioModel o1, AudioModel o2) {
                    String s1 = o1.getFileName().toLowerCase();
                    String s = o2.getFileName().toLowerCase();
                    return ComparisonsKt.compareValues(s1, s);
                }
            });
        }
        this.modelList.addAll(list);
        notifyDataSetChanged();
    }
}
