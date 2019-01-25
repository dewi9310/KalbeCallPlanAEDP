package com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadApotekAEDP;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("records")
	private List<RecordsItem> records;

	public void setRecords(List<RecordsItem> records){
		this.records = records;
	}

	public List<RecordsItem> getRecords(){
		return records;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"records = '" + records + '\'' + 
			"}";
		}
}