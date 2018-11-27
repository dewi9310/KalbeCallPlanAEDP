package com.kalbe.kalbecallplanaedp.ResponseDataJson.PushLogError;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class PushLogError{

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
			"PushLogError{" + 
			"result = '" + result + '\'' + 
			"}";
		}
}