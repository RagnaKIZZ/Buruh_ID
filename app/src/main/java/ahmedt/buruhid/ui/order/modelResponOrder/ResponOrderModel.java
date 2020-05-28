package ahmedt.buruhid.ui.order.modelResponOrder;


import com.google.gson.annotations.SerializedName;


public class ResponOrderModel {

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
			"ResponOrderModel{" +
			"msg = '" + msg + '\'' + 
			",code = '" + code + '\'' + 
			"}";
		}
}