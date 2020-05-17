package ahmedt.buruhid.ui.order.modelHistoryOrder;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class OrderHistoryModel{

	@SerializedName("msg")
	private String msg;

	@SerializedName("item_count")
	private int itemCount;

	@SerializedName("code")
	private int code;

	@SerializedName("data")
	private List<DataItem> data;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setItemCount(int itemCount){
		this.itemCount = itemCount;
	}

	public int getItemCount(){
		return itemCount;
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

	@Override
 	public String toString(){
		return 
			"OrderHistoryModel{" + 
			"msg = '" + msg + '\'' + 
			",item_count = '" + itemCount + '\'' + 
			",code = '" + code + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}