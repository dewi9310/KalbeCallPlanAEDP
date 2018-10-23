package com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadSubActivityDetail;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("LtSubActivityDetailData")
	private List<LtSubActivityDetailDataItem> ltSubActivityDetailData;

	public void setLtSubActivityDetailData(List<LtSubActivityDetailDataItem> ltSubActivityDetailData){
		this.ltSubActivityDetailData = ltSubActivityDetailData;
	}

	public List<LtSubActivityDetailDataItem> getLtSubActivityDetailData(){
		return ltSubActivityDetailData;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"ltSubActivityDetailData = '" + ltSubActivityDetailData + '\'' + 
			"}";
		}
}