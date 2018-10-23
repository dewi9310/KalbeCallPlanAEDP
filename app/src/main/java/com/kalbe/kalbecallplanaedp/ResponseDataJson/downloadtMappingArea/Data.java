package com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadtMappingArea;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("ltMappingArea")
	private List<LtMappingAreaItem> ltMappingArea;

	public void setLtMappingArea(List<LtMappingAreaItem> ltMappingArea){
		this.ltMappingArea = ltMappingArea;
	}

	public List<LtMappingAreaItem> getLtMappingArea(){
		return ltMappingArea;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"ltMappingArea = '" + ltMappingArea + '\'' + 
			"}";
		}
}