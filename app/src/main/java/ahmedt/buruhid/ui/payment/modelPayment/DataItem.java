package ahmedt.buruhid.ui.payment.modelPayment;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class DataItem implements Parcelable {

	@SerializedName("end_date")
	private String endDate;

	@SerializedName("tukang_id")
	private String tukangId;

	@SerializedName("anggota")
	private String anggota;

	@SerializedName("nominal")
	private String nominal;

	@SerializedName("status_pembayaran")
	private String statusPembayaran;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("code_pembayaran")
	private String codePembayaran;

	@SerializedName("id")
	private String id;

	@SerializedName("create_date")
	private String createDate;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("bukti_pembayaran")
	private String buktiPembayaran;

	public DataItem(Parcel in) {
		endDate = in.readString();
		tukangId = in.readString();
		anggota = in.readString();
		nominal = in.readString();
		statusPembayaran = in.readString();
		userId = in.readString();
		codePembayaran = in.readString();
		id = in.readString();
		createDate = in.readString();
		orderId = in.readString();
		buktiPembayaran = in.readString();
	}

	public static final Creator<DataItem> CREATOR = new Creator<DataItem>() {
		@Override
		public DataItem createFromParcel(Parcel in) {
			return new DataItem(in);
		}

		@Override
		public DataItem[] newArray(int size) {
			return new DataItem[size];
		}
	};

	public DataItem() {

	}

	public void setEndDate(String endDate){
		this.endDate = endDate;
	}

	public String getEndDate(){
		return endDate;
	}

	public void setTukangId(String tukangId){
		this.tukangId = tukangId;
	}

	public String getTukangId(){
		return tukangId;
	}

	public void setAnggota(String anggota){
		this.anggota = anggota;
	}

	public String getAnggota(){
		return anggota;
	}

	public void setNominal(String nominal){
		this.nominal = nominal;
	}

	public String getNominal(){
		return nominal;
	}

	public void setStatusPembayaran(String statusPembayaran){
		this.statusPembayaran = statusPembayaran;
	}

	public String getStatusPembayaran(){
		return statusPembayaran;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setCodePembayaran(String codePembayaran){
		this.codePembayaran = codePembayaran;
	}

	public String getCodePembayaran(){
		return codePembayaran;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCreateDate(String createDate){
		this.createDate = createDate;
	}

	public String getCreateDate(){
		return createDate;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	public void setBuktiPembayaran(String buktiPembayaran){
		this.buktiPembayaran = buktiPembayaran;
	}

	public String getBuktiPembayaran(){
		return buktiPembayaran;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"end_date = '" + endDate + '\'' + 
			",tukang_id = '" + tukangId + '\'' + 
			",anggota = '" + anggota + '\'' + 
			",nominal = '" + nominal + '\'' + 
			",status_pembayaran = '" + statusPembayaran + '\'' + 
			",user_id = '" + userId + '\'' + 
			",code_pembayaran = '" + codePembayaran + '\'' + 
			",id = '" + id + '\'' + 
			",create_date = '" + createDate + '\'' + 
			",order_id = '" + orderId + '\'' + 
			",bukti_pembayaran = '" + buktiPembayaran + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(endDate);
		dest.writeString(tukangId);
		dest.writeString(anggota);
		dest.writeString(nominal);
		dest.writeString(statusPembayaran);
		dest.writeString(userId);
		dest.writeString(codePembayaran);
		dest.writeString(id);
		dest.writeString(createDate);
		dest.writeString(orderId);
		dest.writeString(buktiPembayaran);
	}
}