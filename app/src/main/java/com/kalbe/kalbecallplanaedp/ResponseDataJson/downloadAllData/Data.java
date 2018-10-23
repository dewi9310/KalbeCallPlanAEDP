package com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadAllData;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("dataVMmUserMappingArea")
	private List<DataVMmUserMappingAreaItem> dataVMmUserMappingArea;

	@SerializedName("dataVMSubActvty")
	private List<DataVMSubActvtyItem> dataVMSubActvty;

	@SerializedName("dataVMmSubDetailActivity")
	private List<DataVMmSubDetailActivityItem> dataVMmSubDetailActivity;

	@SerializedName("dataVMActivity")
	private List<DataVMActivityItem> dataVMActivity;

	@SerializedName("datatProgramVisitDetail_")
	private DatatProgramVisitDetail datatProgramVisitDetail;

	public void setDataVMmUserMappingArea(List<DataVMmUserMappingAreaItem> dataVMmUserMappingArea){
		this.dataVMmUserMappingArea = dataVMmUserMappingArea;
	}

	public List<DataVMmUserMappingAreaItem> getDataVMmUserMappingArea(){
		return dataVMmUserMappingArea;
	}

	public void setDataVMSubActvty(List<DataVMSubActvtyItem> dataVMSubActvty){
		this.dataVMSubActvty = dataVMSubActvty;
	}

	public List<DataVMSubActvtyItem> getDataVMSubActvty(){
		return dataVMSubActvty;
	}

	public void setDataVMmSubDetailActivity(List<DataVMmSubDetailActivityItem> dataVMmSubDetailActivity){
		this.dataVMmSubDetailActivity = dataVMmSubDetailActivity;
	}

	public List<DataVMmSubDetailActivityItem> getDataVMmSubDetailActivity(){
		return dataVMmSubDetailActivity;
	}

	public void setDataVMActivity(List<DataVMActivityItem> dataVMActivity){
		this.dataVMActivity = dataVMActivity;
	}

	public List<DataVMActivityItem> getDataVMActivity(){
		return dataVMActivity;
	}

	public void setDatatProgramVisitDetail(DatatProgramVisitDetail datatProgramVisitDetail){
		this.datatProgramVisitDetail = datatProgramVisitDetail;
	}

	public DatatProgramVisitDetail getDatatProgramVisitDetail(){
		return datatProgramVisitDetail;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"dataVMmUserMappingArea = '" + dataVMmUserMappingArea + '\'' + 
			",dataVMSubActvty = '" + dataVMSubActvty + '\'' + 
			",dataVMmSubDetailActivity = '" + dataVMmSubDetailActivity + '\'' + 
			",dataVMActivity = '" + dataVMActivity + '\'' + 
			",datatProgramVisitDetail_ = '" + datatProgramVisitDetail + '\'' + 
			"}";
		}
}