package com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadAllData;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataInfoProgram{

	@SerializedName("LtInfoDetail")
	private List<LtInfoDetailItem> ltInfoDetail;

	@SerializedName("LtInfoHeader")
	private List<LtInfoHeaderItem> ltInfoHeader;

	public void setLtInfoDetail(List<LtInfoDetailItem> ltInfoDetail){
		this.ltInfoDetail = ltInfoDetail;
	}

	public List<LtInfoDetailItem> getLtInfoDetail(){
		return ltInfoDetail;
	}

	public void setLtInfoHeader(List<LtInfoHeaderItem> ltInfoHeader){
		this.ltInfoHeader = ltInfoHeader;
	}

	public List<LtInfoHeaderItem> getLtInfoHeader(){
		return ltInfoHeader;
	}

	@Override
 	public String toString(){
		return 
			"DataInfoProgram{" + 
			"ltInfoDetail = '" + ltInfoDetail + '\'' + 
			",ltInfoHeader = '" + ltInfoHeader + '\'' + 
			"}";
		}
}