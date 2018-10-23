package com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadmActivity;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("activity")
	private List<ActivityItem> activity;

	public void setActivity(List<ActivityItem> activity){
		this.activity = activity;
	}

	public List<ActivityItem> getActivity(){
		return activity;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"activity = '" + activity + '\'' + 
			"}";
		}
}