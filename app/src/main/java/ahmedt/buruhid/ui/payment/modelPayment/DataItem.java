package ahmedt.buruhid.ui.payment.modelPayment;


import com.google.gson.annotations.SerializedName;


public class DataItem{

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
}