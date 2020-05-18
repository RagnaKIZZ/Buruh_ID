package ahmedt.buruhid.splash.CurrentPriceModel;


import com.google.gson.annotations.SerializedName;


public class Data{

	@SerializedName("harga")
	private String harga;

	@SerializedName("id")
	private String id;

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"harga = '" + harga + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}