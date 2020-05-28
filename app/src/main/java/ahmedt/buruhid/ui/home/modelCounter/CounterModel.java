package ahmedt.buruhid.ui.home.modelCounter;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CounterModel{

	@SerializedName("msg")
	private String msg;

	@SerializedName("code")
	private int code;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("countPromo")
	private int countPromo;

	@SerializedName("countNotif")
	private int countNotif;

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

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setCountPromo(int countPromo){
		this.countPromo = countPromo;
	}

	public int getCountPromo(){
		return countPromo;
	}

	public void setCountNotif(int countNotif){
		this.countNotif = countNotif;
	}

	public int getCountNotif(){
		return countNotif;
	}

	@Override
 	public String toString(){
		return 
			"CounterModel{" + 
			"msg = '" + msg + '\'' + 
			",code = '" + code + '\'' + 
			",data = '" + data + '\'' + 
			",countPromo = '" + countPromo + '\'' + 
			",countNotif = '" + countNotif + '\'' + 
			"}";
		}
}