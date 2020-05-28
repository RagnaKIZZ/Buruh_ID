package ahmedt.buruhid.ui.account;


import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.bumptech.glide.Glide;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;

import ahmedt.buruhid.R;
import ahmedt.buruhid.login.LoginActivity;
import ahmedt.buruhid.ui.account.model.AccountModel;
import ahmedt.buruhid.utils.HelperClass;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    private static final String TAG = "AccountFragment";
    private Button btnEdt, btnLogout;
    private TextView txtName, txtEmail, txtPhone;
    private ImageView imgAcc;
    private TextView txtHi;
    private String foto = Prefs.getString(SessionPrefs.FOTO, "");



    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        findView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            String extra = data.getStringExtra("extra");
            if (requestCode == 1){
                String name = data.getStringExtra("extra");
                if (extra.contains(" ")){
                    extra = extra.substring(0, extra.indexOf(" "));
                }
                txtName.setText(name);
                txtHi.setText("Hi "+extra+",");
            }else if (requestCode == 2){
                txtEmail.setText(extra);
            }else if (requestCode == 3){
                txtPhone.setText(extra);
            }else{

            }
        }
    }

    private void findView(View view) {
        btnEdt = view.findViewById(R.id.btn_edit_account);
        btnLogout = view.findViewById(R.id.btn_logout_account);
        txtEmail = view.findViewById(R.id.txt_email_account);
        txtName = view.findViewById(R.id.txt_name_account);
        txtPhone = view.findViewById(R.id.txt_phone_account);
        imgAcc = view.findViewById(R.id.img_account);
        txtHi = view.findViewById(R.id.txt_hi_account);

        String nama = Prefs.getString(SessionPrefs.NAMA, "");
        String email = Prefs.getString(SessionPrefs.EMAIL, "");
        String telepon = Prefs.getString(SessionPrefs.TELEPON, "");
        String foto = Prefs.getString(SessionPrefs.FOTO, "");
        if (nama.contains(" ")){
            nama = nama.substring(0, nama.indexOf(" "));
        }
        txtHi.setText("Hi "+nama+",");

        if (!foto.isEmpty()){
            Glide.with(getActivity())
                    .load(UrlServer.URL_FOTO+foto)
                    .into(imgAcc);
        }else {
            Glide.with(getActivity())
                    .load(R.drawable.blank_profile)
                    .into(imgAcc);
        }


        txtName.setText(Prefs.getString(SessionPrefs.NAMA, ""));
        txtEmail.setText(email);
        txtPhone.setText(telepon);



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

        btnEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditProfile();
            }
        });

        imgAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DetailFotoActivity.class);
                View shareView = imgAcc;
                String transitionName = getString(R.string.img);
                ActivityOptions transOpt = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    transOpt = ActivityOptions.makeSceneTransitionAnimation(getActivity(), shareView, transitionName);
                    startActivity(i, transOpt.toBundle());
                }else {
                    startActivity(i);
                }
            }
        });
    }

    private void logOut(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle(R.string.logout);
        alert.setMessage(getString(R.string.are_logout))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getActivity() != null){
                            String uid = Prefs.getString(SessionPrefs.U_ID, "");
                            String token_login = Prefs.getString(SessionPrefs.TOKEN_LOGIN, "");
                            Log.d(TAG, "onClick: "+token_login);
                            logoutUser(uid, token_login);
                        }
                    }
                })
        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();
    }

    private void logoutUser(String id, String token) {
        if (getActivity() != null) {
            final KProgressHUD hud = new KProgressHUD(getActivity());
            HelperClass.loading(hud, null, null, false);
            AndroidNetworking.post(UrlServer.URL_LOGOUT)
                    .addBodyParameter("id", id)
                    .addBodyParameter("token", token)
                    .build()
                    .getAsOkHttpResponseAndObject(AccountModel.class, new OkHttpResponseAndParsedRequestListener<AccountModel>() {
                        @Override
                        public void onResponse(Response okHttpResponse, AccountModel response) {
                            hud.dismiss();
                            if (okHttpResponse.isSuccessful()){
                                if (response.getCode() == 200){
                                    Toasty.success(getActivity(),R.string.suc_logout, Toast.LENGTH_SHORT, true).show();
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    Prefs.clear();
                                    startActivity(intent);
                                    getActivity().finish();
                                }else{
                                    Toasty.warning(getActivity(), R.string.something_wrong, Toast.LENGTH_SHORT, true).show();
                                }
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            hud.dismiss();
                            if (anError.getErrorCode() != 0){
                                Log.d(TAG, "onError: "+anError.getErrorDetail());
                                Toasty.error(getActivity(), R.string.server_error, Toast.LENGTH_SHORT, true).show();
                            }else{
                                Log.d(TAG, "onError: "+anError.getErrorCode());
                                Log.d(TAG, "onError: "+anError.getErrorBody());
                                Log.d(TAG, "onError: "+anError.getErrorDetail());
                                Toasty.error(getActivity(), R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    });
        }
    }

    private void dialogEditProfile(){
        if (getActivity() != null){
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.dialog_edit_profiles);

            LinearLayout lnName, lnEmail, lnPhone, lnPass, lnLang;
            lnPhone = dialog.findViewById(R.id.linear_edit_phone);
            lnName  = dialog.findViewById(R.id.linear_edit_name);
            lnEmail = dialog.findViewById(R.id.linear_edit_email);
            lnPass = dialog.findViewById(R.id.linear_edit_password);
            lnLang = dialog.findViewById(R.id.linear_change_language);

            lnName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), EditAccountActivity.class);
                    i.putExtra("code", "1");
                    startActivityForResult(i, 1);
                    dialog.cancel();

                }
            });

            lnPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), EditAccountActivity.class);
                    i.putExtra("code", "3");
                    startActivityForResult(i, 3);
                    dialog.cancel();
                }
            });

            lnEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), EditAccountActivity.class);
                    i.putExtra("code", "2");
                    startActivityForResult(i, 2);
                    dialog.cancel();
                }
            });

            lnPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), EditAccountActivity.class);
                    i.putExtra("code", "4");
                    startActivityForResult(i, 4);
                    dialog.cancel();
                }
            });

            lnLang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                    startActivity(i);
                }
            });

            dialog.setCancelable(true);
            dialog.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Prefs.getString(SessionPrefs.FOTO,"").isEmpty()){
            Glide.with(getActivity())
                    .load(R.drawable.blank_profile)
                    .into(imgAcc);
            Log.d(TAG, "onResume: kosong: "+Prefs.getString(SessionPrefs.FOTO,""));
        }else{
            if (!foto.equals(Prefs.getString(SessionPrefs.FOTO, ""))){
                Glide.with(getActivity())
                        .load(UrlServer.URL_FOTO+Prefs.getString(SessionPrefs.FOTO, ""))
                        .into(imgAcc);
                Log.d(TAG, "onResume: beda"+foto);
                Log.d(TAG, "onResume: beda: "+Prefs.getString(SessionPrefs.FOTO,""));
            }
        }

    }


}
