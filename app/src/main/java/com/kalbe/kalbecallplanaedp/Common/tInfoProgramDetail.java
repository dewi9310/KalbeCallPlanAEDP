package com.kalbe.kalbecallplanaedp.Common;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/24/2018.
 */

@DatabaseTable
public class tInfoProgramDetail implements Serializable {
    @DatabaseField(id = true)
    private String txtDetailId;
    @DatabaseField
    private String txtHeaderId;
    @DatabaseField
    private String intSubDetailActivityId;
    @DatabaseField
    private String txtFileName;
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] blobFile;
    @DatabaseField
    private int intFlagChecklist;
    @DatabaseField
    private String dtChecklist;


    public String Property_txtDetailId = "txtDetailId";
    public String Property_txtHeaderId = "txtHeaderId";
    public String Property_intSubDetailActivityId = "intSubDetailActivityId";
    public String Property_txtFileName = "txtFileName";
    public String Property_blobFile = "blobFile";
    public String Property_intFlagChecklist = "intFlagChecklist";
    public String Property_dtChecklist = "dtChecklist";

    public String getTxtDetailId() {
        return txtDetailId;
    }

    public void setTxtDetailId(String txtDetailId) {
        this.txtDetailId = txtDetailId;
    }

    public String getTxtHeaderId() {
        return txtHeaderId;
    }

    public void setTxtHeaderId(String txtHeaderId) {
        this.txtHeaderId = txtHeaderId;
    }

    public String getIntSubDetailActivityId() {
        return intSubDetailActivityId;
    }

    public void setIntSubDetailActivityId(String intSubDetailActivityId) {
        this.intSubDetailActivityId = intSubDetailActivityId;
    }

    public String getTxtFileName() {
        return txtFileName;
    }

    public void setTxtFileName(String txtFileName) {
        this.txtFileName = txtFileName;
    }

    public byte[] getBlobFile() {
        return blobFile;
    }

    public void setBlobFile(byte[] blobFile) {
        this.blobFile = blobFile;
    }

    public int getIntFlagChecklist() {
        return intFlagChecklist;
    }

    public void setIntFlagChecklist(int intFlagChecklist) {
        this.intFlagChecklist = intFlagChecklist;
    }

    public String getDtChecklist() {
        return dtChecklist;
    }

    public void setDtChecklist(String dtChecklist) {
        this.dtChecklist = dtChecklist;
    }
}
