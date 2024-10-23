package com.voicechanger.app.effects;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class RattingApp extends Dialog implements View.OnClickListener {
    int countRattings;
    SharedPreferences sharedPreferences;
    Context activity;

    ImageView rate1;
    ImageView rate2;
    ImageView rate3;
    ImageView rate4;
    ImageView rate5;

    TextView txt_submit;
    TextView txtShare;
    ImageView imgClose;

    private final ImageView[] ratingStar = new ImageView[5];
    private int ratingReview = 3;

    public RattingApp(@NonNull Context context) {
        super(context);
        activity = context;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ratting);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setGravity(Gravity.CENTER);
        setCanceledOnTouchOutside(false);

        PowerManager mgr = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "MyWakeLock");
        wakeLock.acquire();

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        countRattings = this.sharedPreferences.getInt("firsttime_rateShow", 0);

        txt_submit = findViewById(R.id.txtSubmit);
        txtShare = findViewById(R.id.txtShare);
        imgClose = findViewById(R.id.imgClose);
        rate1 = findViewById(R.id.rate1);
        rate2 = findViewById(R.id.rate2);
        rate3 = findViewById(R.id.rate3);
        rate4 = findViewById(R.id.rate4);
        rate5 = findViewById(R.id.rate5);

        rate1.setOnClickListener(this);
        rate2.setOnClickListener(this);
        rate3.setOnClickListener(this);
        rate4.setOnClickListener(this);
        rate5.setOnClickListener(this);

        ratingStar[0] = rate1;
        ratingStar[1] = rate2;
        ratingStar[2] = rate3;
        ratingStar[3] = rate4;
        ratingStar[4] = rate5;
        setStarBar();

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                countRattings++;
                String packageName = activity.getPackageName();

                try {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                }
                Toast.makeText(activity, activity.getResources().getString(R.string.thanks_rate), Toast.LENGTH_SHORT).show();
            }
        });

        txtShare.setOnClickListener(v -> {
            dismiss();
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, activity.getResources().getString(R.string.app_name));
                String msgShare;
                msgShare = activity.getResources().getString(R.string.appShare) + "\n\n" + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, msgShare);
                activity.startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception ignored) {
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rate1) {
            ratingReview = 1;
            setStarBar();
        } else if (view.getId() == R.id.rate2) {
            ratingReview = 2;
            setStarBar();
        } else if (view.getId() == R.id.rate3) {
            ratingReview = 3;
            setStarBar();
        } else if (view.getId() == R.id.rate4) {
            ratingReview = 4;
            setStarBar();
        } else if (view.getId() == R.id.rate5) {
            ratingReview = 5;
            setStarBar();
        }
    }

    private void setStarBar() {
        for (int i = 0; i < this.ratingStar.length; i++) {
            if (i < this.ratingReview) {
                this.ratingStar[i].setImageResource(R.drawable.ic_selected_rate);
            } else {
                this.ratingStar[i].setImageResource(R.drawable.ic_unselected_rate);
            }
        }

    }
}
