package ahmedt.buruhid.login;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("nama")
    private String nama;

    @SerializedName("foto")
    private String foto;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("telepon")
    private String telepon;

    @SerializedName("token_login")
    private String tokenLogin;

    @SerializedName("email")
    private String email;

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTokenLogin(String tokenLogin) {
        this.tokenLogin = tokenLogin;
    }

    public String getTokenLogin() {
        return tokenLogin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "nama = '" + nama + '\'' +
                        ",foto = '" + foto + '\'' +
                        ",user_id = '" + userId + '\'' +
                        ",telepon = '" + telepon + '\'' +
                        ",token_login = '" + tokenLogin + '\'' +
                        ",email = '" + email + '\'' +
                        "}";
    }
}