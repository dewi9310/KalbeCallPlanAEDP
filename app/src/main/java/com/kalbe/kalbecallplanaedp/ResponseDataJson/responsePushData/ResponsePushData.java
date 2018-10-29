package com.kalbe.kalbecallplanaedp.ResponseDataJson.responsePushData;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResponsePushData{

	@SerializedName("result")
	private Result result;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}

	@Override
 	public String toString(){
		return 
			"ResponsePushData{" + 
			"result = '" + result + '\'' + 
			"}";
		}
}