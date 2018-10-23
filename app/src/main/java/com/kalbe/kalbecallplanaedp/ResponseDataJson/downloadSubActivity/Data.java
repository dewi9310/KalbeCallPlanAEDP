package com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadSubActivity;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("ltSubActivity")
	private List<LtSubActivityItem> ltSubActivity;

	public void setLtSubActivity(List<LtSubActivityItem> ltSubActivity){
		this.ltSubActivity = ltSubActivity;
	}

	public List<LtSubActivityItem> getLtSubActivity(){
		return ltSubActivity;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"ltSubActivity = '" + ltSubActivity + '\'' + 
			"}";
		}
}