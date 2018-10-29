package com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadAllData;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class LtMaintenanceDetailItem{

	@SerializedName("txtMaintenanceDetailId")
	private String txtMaintenanceDetailId;

	@SerializedName("txtNoResep")
	private String txtNoResep;

	@SerializedName("txtMaintenanceHeaderId")
	private String txtMaintenanceHeaderId;

	@SerializedName("intSubDetailActivityId")
	private int intSubDetailActivityId;

	@SerializedName("txtNoOrder")
	private String txtNoOrder;

	public void setTxtMaintenanceDetailId(String txtMaintenanceDetailId){
		this.txtMaintenanceDetailId = txtMaintenanceDetailId;
	}

	public String getTxtMaintenanceDetailId(){
		return txtMaintenanceDetailId;
	}

	public void setTxtNoResep(String txtNoResep){
		this.txtNoResep = txtNoResep;
	}

	public String getTxtNoResep(){
		return txtNoResep;
	}

	public void setTxtMaintenanceHeaderId(String txtMaintenanceHeaderId){
		this.txtMaintenanceHeaderId = txtMaintenanceHeaderId;
	}

	public String getTxtMaintenanceHeaderId(){
		return txtMaintenanceHeaderId;
	}

	public void setIntSubDetailActivityId(int intSubDetailActivityId){
		this.intSubDetailActivityId = intSubDetailActivityId;
	}

	public int getIntSubDetailActivityId(){
		return intSubDetailActivityId;
	}

	public void setTxtNoOrder(String txtNoOrder){
		this.txtNoOrder = txtNoOrder;
	}

	public String getTxtNoOrder(){
		return txtNoOrder;
	}

	@Override
 	public String toString(){
		return 
			"LtMaintenanceDetailItem{" + 
			"txtMaintenanceDetailId = '" + txtMaintenanceDetailId + '\'' + 
			",txtNoResep = '" + txtNoResep + '\'' + 
			",txtMaintenanceHeaderId = '" + txtMaintenanceHeaderId + '\'' + 
			",intSubDetailActivityId = '" + intSubDetailActivityId + '\'' + 
			",txtNoOrder = '" + txtNoOrder + '\'' + 
			"}";
		}
}