package com.kalbe.kalbecallplanaedp.Common;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/22/2018.
 */

public class clsPushData {
    private clsDataJson dataJson;
    private HashMap<String, byte[]> FileUpload;
    private List<String> FileName;

    public clsDataJson getDataJson() {
        return dataJson;
    }

    public void setDataJson(clsDataJson dataJson) {
        this.dataJson = dataJson;
    }

    public HashMap<String, byte[]> getFileUpload() {
        return FileUpload;
    }

    public void setFileUpload(HashMap<String, byte[]> fileUpload) {
        FileUpload = fileUpload;
    }

    public List<String> getFileName() {
        return FileName;
    }

    public void setFileName(List<String> fileName) {
        FileName = fileName;
    }
}
