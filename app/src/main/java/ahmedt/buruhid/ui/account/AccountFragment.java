package ahmedt.buruhid.ui.account;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.bumptech.glide.Glide;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;

import ahmedt.buruhid.R;
import ahmedt.buruhid.login.LoginActivity;
import ahmedt.buruhid.ui.account.model.AccountModel;
import ahmedt.buruhid.utils.DialogMessage;
import ahmedt.buruhid.utils.HelperClass;
import ahmedt.buruhid.utils.SessionPrefs;
import ahmedt.buruhid.utils.UrlServer;
import es.dmoral.toasty.Toasty;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    private static final String TAG = "AccountFragment";
    private Button btnEdt, btnLogout;
    private TextView txtName, txtEmail, txtPhone;
    private ImageView imgAcc;
    private TextView txtHi;
    private String uid = Prefs.getString(SessionPrefs.U_ID, "");
    private String token_login = Prefs.getString(SessionPrefs.TOKEN_LOGIN, "");


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
                    .load(R.drawable.ic_account_circle_black_24dp)
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
    }

    private void logOut(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Logout");
        alert.setMessage("Are you sure want to logout?")
                .setIcon(R.drawable.ic_exit)
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getActivity() != null){
                            logoutUser(uid, token_login);
                            Prefs.clear();
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            getActivity().finish();
                        }
                    }
                })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
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
                                    Toasty.warning(getActivity(),R.string.suc_logout, Toast.LENGTH_SHORT, true).show();
                                }else{
                                    Toasty.warning(getActivity(), R.string.something_wrong, Toast.LENGTH_SHORT, true).show();
                                }
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            hud.dismiss();
                            Toasty.warning(getActivity(), R.string.cek_internet, Toast.LENGTH_SHORT, true).show();
                            Log.d(TAG, "onError: "+anError.getErrorDetail());
                        }
                    });
        }
    }

    private void dialogEditProfile(){
        if (getActivity() != null){
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.dialog_edit_profiles);

            LinearLayout lnName, lnPhoto, lnEmail, lnPhone, lnPass;
            lnPhone = dialog.findViewById(R.id.linear_edit_phone);
            lnPhoto = dialog.findViewById(R.id.linear_edit_photo);
            lnName  = dialog.findViewById(R.id.linear_edit_name);
            lnEmail = dialog.findViewById(R.id.linear_edit_email);
            lnPass = dialog.findViewById(R.id.linear_edit_password);


            lnPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            lnName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), EditAccountActivity.class);
                    i.putExtra("code", "name");
                    startActivity(i);
                    dialog.cancel();

                }
            });

            lnPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), EditAccountActivity.class);
                    i.putExtra("code", "phone");
                    startActivity(i);
                    dialog.cancel();

                }
            });

            lnEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), EditAccountActivity.class);
                    i.putExtra("code", "email");
                    startActivity(i);
                    dialog.cancel();
                }
            });

            lnPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            dialog.setCancelable(true);
            dialog.show();
        }
    }

}
