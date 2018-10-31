package com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadtMaintenance;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("LtMaintenanceHeader")
	private List<LtMaintenanceHeaderItem> ltMaintenanceHeader;

	@SerializedName("LtMaintenanceDetail")
	private List<LtMaintenanceDetailItem> ltMaintenanceDetail;

	public void setLtMaintenanceHeader(List<LtMaintenanceHeaderItem> ltMaintenanceHeader){
		this.ltMaintenanceHeader = ltMaintenanceHeader;
	}

	public List<LtMaintenanceHeaderItem> getLtMaintenanceHeader(){
		return ltMaintenanceHeader;
	}

	public void setLtMaintenanceDetail(List<LtMaintenanceDetailItem> ltMaintenanceDetail){
		this.ltMaintenanceDetail = ltMaintenanceDetail;
	}

	public List<LtMaintenanceDetailItem> getLtMaintenanceDetail(){
		return ltMaintenanceDetail;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"ltMaintenanceHeader = '" + ltMaintenanceHeader + '\'' + 
			",ltMaintenanceDetail = '" + ltMaintenanceDetail + '\'' + 
			"}";
		}
}