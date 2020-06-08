package ahmedt.buruhid.ui.account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;

import ahmedt.buruhid.R;
import ahmedt.buruhid.ui.account.modelEdit.EditModel;
import ahmedt.buruhid.ui.account.modelUpload.UploadModel;
import ahmedt.buruhid.utils.HelperClass;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

public class DetailFotoActivity extends AppCompatActivity {
    private static final String TAG = "DetailFotoActivity";
    ProgressBar progressBar;
    PhotoView photoView;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_foto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.pp));

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        photoView = (PhotoView) findViewById(R.id.photo_view);

        String foto = Prefs.getString(SessionPrefs.FOTO, "");
        if (foto.isEmpty()) {
            Glide.with(this)
                    .load(R.drawable.blank_profile)
                    .into(photoView);
        } else {
            HelperClass.loadGambar(DetailFotoActivity.this, UrlServer.URL_FOTO + foto, progressBar, photoView);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.foto_edit:
                ImagePicker.Companion.with(DetailFotoActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(720, 720)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
                break;
            case R.id.foto_delete:
                deleteFoto();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void deleteFoto() {
        AlertDialog.Builder alert = new AlertDialog.Builder(DetailFotoActivity.this);

        alert.setTitle(getString(R.string.del_pho));
        alert.setMessage(getString(R.string.want_del))
                .setIcon(R.drawable.ic_delete_black_24dp2)
                .setCancelable(true)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        hapusFoto(Prefs.getString(SessionPrefs.U_ID, ""), Prefs.getString(SessionPrefs.TOKEN_LOGIN, ""));
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailFotoActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        alert.show();
    }

    private void uploadFoto(String id, String token, File file) {
        final KProgressHUD hud = new KProgressHUD(this);
        HelperClass.loading(hud, null, null, false);
        AndroidNetworking.upload(UrlServer.URL_CHANGE_FOTO)
                .addMultipartParameter("id", id)
                .addMultipartParameter("token_login", token)
                .addMultipartFile("foto", file)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {

                    }
                })
                .getAsOkHttpResponseAndObject(UploadModel.class, new OkHttpResponseAndParsedRequestListener<UploadModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, UploadModel response) {
                        hud.dismiss();
                        if (okHttpResponse.isSuccessful()) {
                            if (response.getCode() == 200) {
                                HelperClass.loadGambar(DetailFotoActivity.this, UrlServer.URL_FOTO + response.getFoto(), progressBar, photoView);
//                                Glide.with(DetailFotoActivity.this)
//                                        .load(UrlServer.URL_FOTO + response.getFoto())
//                                        .into(photoView);
                                Prefs.putString(SessionPrefs.FOTO, response.getFoto());
                                Log.d(TAG, "onResponse: " + Prefs.getString(SessionPrefs.FOTO, ""));
                                Toasty.success(DetailFotoActivity.this, response.getMsg(), Toasty.LENGTH_SHORT).show();
                            } else {
                                Toasty.warning(DetailFotoActivity.this, response.getMsg(), Toasty.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                        if (anError.getErrorCode() != 0) {
                            Log.d("ERR", "onError: " + anError.getErrorDetail());
                            Toasty.error(DetailFotoActivity.this, R.string.server_error, Toast.LENGTH_SHORT, true).show();
                        } else {
                            Log.d("ERR", "onError: " + anError.getErrorCode());
                            Log.d("ERR", "onError: " + anError.getErrorBody());
                            Log.d("ERR", "onError: " + anError.getErrorDetail());
                            Toasty.error(DetailFotoActivity.this, R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    private void hapusFoto(String id, String token) {
        final KProgressHUD hud = new KProgressHUD(this);
        HelperClass.loading(hud, null, null, false);
        AndroidNetworking.post(UrlServer.URL_DELETE_FOTO)
                .addBodyParameter("id", id)
                .addBodyParameter("token_login", token)
                .build()
                .getAsOkHttpResponseAndObject(EditModel.class, new OkHttpResponseAndParsedRequestListener<EditModel>() {
                    @Override
                    public void onResponse(Response okHttpResponse, EditModel response) {
                        hud.dismiss();
                        if (okHttpResponse.isSuccessful()) {
                            if (response.getCode() == 200) {
                                Glide.with(DetailFotoActivity.this)
                                        .load(R.drawable.blank_profile)
                                        .into(photoView);
                                Prefs.putString(SessionPrefs.FOTO, "");
                                Log.d(TAG, "onResponse: " + Prefs.getString(SessionPrefs.FOTO, ""));
                                Toasty.success(DetailFotoActivity.this, response.getMsg(), Toasty.LENGTH_SHORT).show();
                            } else {
                                Toasty.warning(DetailFotoActivity.this, response.getMsg(), Toasty.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                        if (anError.getErrorCode() != 0) {
                            Log.d("ERR", "onError: " + anError.getErrorDetail());
                            Toasty.error(DetailFotoActivity.this, R.string.server_error, Toast.LENGTH_SHORT, true).show();
                        } else {
                            Log.d("ERR", "onError: " + anError.getErrorCode());
                            Log.d("ERR", "onError: " + anError.getErrorBody());
                            Log.d("ERR", "onError: " + anError.getErrorDetail());
                            Toasty.error(DetailFotoActivity.this, R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == ImagePicker.REQUEST_CODE) {
                file = ImagePicker.Companion.getFile(data);
                uploadFoto(Prefs.getString(SessionPrefs.U_ID, ""), Prefs.getString(SessionPrefs.TOKEN_LOGIN, ""), file);
            }
        }
    }
}
