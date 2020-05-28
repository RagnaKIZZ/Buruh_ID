package ahmedt.buruhid.ui.order.modelOrder;


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

	@SerializedName("jobdesk")
	private String jobdesk;

	@SerializedName("harga_promo")
	private String hargaPromo;

	@SerializedName("telepon")
	private String telepon;

	@SerializedName("rating")
	private String rating;

	@SerializedName("status_order")
	private String statusOrder;

	@SerializedName("promo_id")
	private String promoId;

	@SerializedName("angka_unik")
	private String angkaUnik;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("order_date")
	private String orderDate;

	@SerializedName("harga")
	private String harga;

	@SerializedName("nama")
	private String nama;

	@SerializedName("foto")
	private String foto;

	@SerializedName("code_order")
	private String codeOrder;

	@SerializedName("id")
	private String id;

	@SerializedName("start_date")
	private String startDate;

	public DataItem(Parcel in) {
		endDate = in.readString();
		tukangId = in.readString();
		anggota = in.readString();
		jobdesk = in.readString();
		hargaPromo = in.readString();
		telepon = in.readString();
		rating = in.readString();
		statusOrder = in.readString();
		promoId = in.readString();
		angkaUnik = in.readString();
		alamat = in.readString();
		orderDate = in.readString();
		harga = in.readString();
		nama = in.readString();
		foto = in.readString();
		codeOrder = in.readString();
		id = in.readString();
		startDate = in.readString();
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

	public void setJobdesk(String jobdesk){
		this.jobdesk = jobdesk;
	}

	public String getJobdesk(){
		return jobdesk;
	}

	public void setHargaPromo(String hargaPromo){
		this.hargaPromo = hargaPromo;
	}

	public String getHargaPromo(){
		return hargaPromo;
	}

	public void setTelepon(String telepon){
		this.telepon = telepon;
	}

	public String getTelepon(){
		return telepon;
	}

	public void setRating(String rating){
		this.rating = rating;
	}

	public String getRating(){
		return rating;
	}

	public void setStatusOrder(String statusOrder){
		this.statusOrder = statusOrder;
	}

	public String getStatusOrder(){
		return statusOrder;
	}

	public void setPromoId(String promoId){
		this.promoId = promoId;
	}

	public String getPromoId(){
		return promoId;
	}

	public void setAngkaUnik(String angkaUnik){
		this.angkaUnik = angkaUnik;
	}

	public String getAngkaUnik(){
		return angkaUnik;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setOrderDate(String orderDate){
		this.orderDate = orderDate;
	}

	public String getOrderDate(){
		return orderDate;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setFoto(String foto){
		this.foto = foto;
	}

	public String getFoto(){
		return foto;
	}

	public void setCodeOrder(String codeOrder){
		this.codeOrder = codeOrder;
	}

	public String getCodeOrder(){
		return codeOrder;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setStartDate(String startDate){
		this.startDate = startDate;
	}

	public String getStartDate(){
		return startDate;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"end_date = '" + endDate + '\'' + 
			",tukang_id = '" + tukangId + '\'' + 
			",anggota = '" + anggota + '\'' + 
			",jobdesk = '" + jobdesk + '\'' + 
			",harga_promo = '" + hargaPromo + '\'' + 
			",telepon = '" + telepon + '\'' + 
			",rating = '" + rating + '\'' + 
			",status_order = '" + statusOrder + '\'' + 
			",promo_id = '" + promoId + '\'' + 
			",angka_unik = '" + angkaUnik + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",order_date = '" + orderDate + '\'' + 
			",harga = '" + harga + '\'' + 
			",nama = '" + nama + '\'' + 
			",foto = '" + foto + '\'' + 
			",code_order = '" + codeOrder + '\'' + 
			",id = '" + id + '\'' + 
			",start_date = '" + startDate + '\'' + 
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
		dest.writeString(jobdesk);
		dest.writeString(hargaPromo);
		dest.writeString(telepon);
		dest.writeString(rating);
		dest.writeString(statusOrder);
		dest.writeString(promoId);
		dest.writeString(angkaUnik);
		dest.writeString(alamat);
		dest.writeString(orderDate);
		dest.writeString(harga);
		dest.writeString(nama);
		dest.writeString(foto);
		dest.writeString(codeOrder);
		dest.writeString(id);
		dest.writeString(startDate);
	}
}