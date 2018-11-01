package com.kalbe.kalbecallplanaedp.Model;

import android.graphics.drawable.Drawable;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Dewi Oktaviani on 11/1/2018.
 */

public class clsInfoProgram {
    private String txtId;
    private Integer intImgView;
    private String txtTittle;
    private String txtSubTittle;
    private String txtDesc;
    private int intColor;
    private String txtImgName;
    private int intFlagContent;
    private boolean isChecked;
    private String txtFileName;

    public String getTxtId() {
        return txtId;
    }

    public void setTxtId(String txtId) {
        this.txtId = txtId;
    }

    public Integer getIntImgView() {
        return intImgView;
    }

    public void setIntImgView(Integer intImgView) {
        this.intImgView = intImgView;
    }

    public String getTxtTittle() {
        return txtTittle;
    }

    public void setTxtTittle(String txtTittle) {
        this.txtTittle = txtTittle;
    }

    public String getTxtSubTittle() {
        return txtSubTittle;
    }

    public void setTxtSubTittle(String txtSubTittle) {
        this.txtSubTittle = txtSubTittle;
    }

    public String getTxtDesc() {
        return txtDesc;
    }

    public void setTxtDesc(String txtDesc) {
        this.txtDesc = txtDesc;
    }

    public int getIntColor() {
        return intColor;
    }

    public void setIntColor(int intColor) {
        this.intColor = intColor;
    }


    public String getTxtImgName() {
        return txtImgName;
    }

    public void setTxtImgName(String txtImgName) {
        this.txtImgName = txtImgName;
    }

    public int getIntFlagContent() {
        return intFlagContent;
    }

    public void setIntFlagContent(int intFlagContent) {
        this.intFlagContent = intFlagContent;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTxtFileName() {
        return txtFileName;
    }

    public void setTxtFileName(String txtFileName) {
        this.txtFileName = txtFileName;
    }
}
