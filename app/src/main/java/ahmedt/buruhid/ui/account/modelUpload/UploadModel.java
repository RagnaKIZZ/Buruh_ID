package ahmedt.buruhid.ui.account.modelUpload;


import com.google.gson.annotations.SerializedName;


public class UploadModel{

	@SerializedName("msg")
	private String msg;

	@SerializedName("code")
	private int code;

	@SerializedName("foto")
	private String foto;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setFoto(String foto){
		this.foto = foto;
	}

	public String getFoto(){
		return foto;
	}

	@Override
 	public String toString(){
		return 
			"UploadModel{" + 
			"msg = '" + msg + '\'' + 
			",code = '" + code + '\'' + 
			",foto = '" + foto + '\'' + 
			"}";
		}
}