package ahmedt.buruhid.promotion.modelPromo;


import com.google.gson.annotations.SerializedName;


public class DataItem{

	@SerializedName("kode_promo")
	private String kodePromo;

	@SerializedName("end_date")
	private String endDate;

	@SerializedName("nama_promo")
	private String namaPromo;

	@SerializedName("min_harga")
	private String minHarga;

	@SerializedName("id")
	private String id;

	@SerializedName("isi_promo")
	private String isiPromo;

	@SerializedName("start_date")
	private String startDate;

	public void setKodePromo(String kodePromo){
		this.kodePromo = kodePromo;
	}

	public String getKodePromo(){
		return kodePromo;
	}

	public void setEndDate(String endDate){
		this.endDate = endDate;
	}

	public String getEndDate(){
		return endDate;
	}

	public void setNamaPromo(String namaPromo){
		this.namaPromo = namaPromo;
	}

	public String getNamaPromo(){
		return namaPromo;
	}

	public void setMinHarga(String minHarga){
		this.minHarga = minHarga;
	}

	public String getMinHarga(){
		return minHarga;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setIsiPromo(String isiPromo){
		this.isiPromo = isiPromo;
	}

	public String getIsiPromo(){
		return isiPromo;
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
			"kode_promo = '" + kodePromo + '\'' + 
			",end_date = '" + endDate + '\'' + 
			",nama_promo = '" + namaPromo + '\'' + 
			",min_harga = '" + minHarga + '\'' + 
			",id = '" + id + '\'' + 
			",isi_promo = '" + isiPromo + '\'' + 
			",start_date = '" + startDate + '\'' + 
			"}";
		}
}