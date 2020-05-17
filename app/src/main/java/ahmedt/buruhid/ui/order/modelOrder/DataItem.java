package ahmedt.buruhid.ui.order.modelOrder;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@SerializedName("end_date")
	private String endDate;

	@SerializedName("tukang_id")
	private String tukangId;

	@SerializedName("anggota")
	private String anggota;

	@SerializedName("jobdesk")
	private String jobdesk;

	@SerializedName("telepon")
	private String telepon;

	@SerializedName("rating")
	private String rating;

	@SerializedName("status_order")
	private String statusOrder;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("order_date")
	private String orderDate;

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
			",telepon = '" + telepon + '\'' + 
			",rating = '" + rating + '\'' + 
			",status_order = '" + statusOrder + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",order_date = '" + orderDate + '\'' + 
			",nama = '" + nama + '\'' + 
			",foto = '" + foto + '\'' + 
			",code_order = '" + codeOrder + '\'' + 
			",id = '" + id + '\'' + 
			",start_date = '" + startDate + '\'' + 
			"}";
		}
}