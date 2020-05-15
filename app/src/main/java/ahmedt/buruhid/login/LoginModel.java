package ahmedt.buruhid.login;


import com.google.gson.annotations.SerializedName;


public class LoginModel{

	@SerializedName("msg")
	private String msg;

	@SerializedName("code")
	private int code;

	@SerializedName("data")
	private Data data;

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

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"LoginModel{" + 
			"msg = '" + msg + '\'' + 
			",code = '" + code + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}