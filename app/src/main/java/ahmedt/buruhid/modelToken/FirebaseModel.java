package ahmedt.buruhid.modelToken;


import com.google.gson.annotations.SerializedName;


public class FirebaseModel{

	@SerializedName("msg")
	private String msg;

	@SerializedName("code")
	private int code;

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

	@Override
 	public String toString(){
		return 
			"FirebaseModel{" + 
			"msg = '" + msg + '\'' + 
			",code = '" + code + '\'' + 
			"}";
		}
}