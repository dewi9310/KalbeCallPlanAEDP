package com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadmActivity;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("LtActivity")
	private List<LtActivityItem> ltActivity;

	public void setLtActivity(List<LtActivityItem> ltActivity){
		this.ltActivity = ltActivity;
	}

	public List<LtActivityItem> getLtActivity(){
		return ltActivity;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"ltActivity = '" + ltActivity + '\'' + 
			"}";
		}
}