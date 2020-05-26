package ahmedt.buruhid.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HelperClass {
    public static void getDate (Date date, String time, TextView txt){
        long now = System.currentTimeMillis();
        if (Math.abs(now - date.getTime()) > TimeUnit.MINUTES.toMillis(1)) {
            time = String.valueOf(DateUtils.getRelativeTimeSpanString(date.getTime(), now,
                    DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                            | DateUtils.FORMAT_NUMERIC_DATE));
        } else {
            time = "just now";
        }
        txt.setText(time);
    }
    public static void loadGambar(Context context, String url, final ProgressBar progressBar, ImageView img){
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(url)
                .centerCrop()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(img);
    }

    public static void loading(KProgressHUD hud, String judul, String deskripsi, boolean cancelable){
        if (judul==null){
            judul="Loading";
        }
        if (deskripsi==null){
            deskripsi="Please Wait";
        }
        hud.setLabel(judul);
        hud.setDimAmount(0.5f);
        hud.setDetailsLabel(deskripsi);
        hud.setCancellable(cancelable);
        hud.setCornerRadius(14);
        hud.show();
    }
}
