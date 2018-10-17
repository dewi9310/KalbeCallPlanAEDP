package com.kalbe.kalbecallplanaedp.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/15/2018.
 */

@DatabaseTable
public class mSubActivity implements Serializable {
    @DatabaseField(id = true, columnName = "intSubActivityid")
    public int intSubActivityid;
    @DatabaseField(columnName = "intActivityid")
    public int intActivityid;
    @DatabaseField(columnName = "txtName")
    public String txtName;
    @DatabaseField(columnName = "txtDesc")
    public String txtDesc;

    public int getIntSubActivityid() {
        return intSubActivityid;
    }

    public void setIntSubActivityid(int intSubActivityid) {
        this.intSubActivityid = intSubActivityid;
    }

    public int getIntActivityid() {
        return intActivityid;
    }

    public void setIntActivityid(int intActivityid) {
        this.intActivityid = intActivityid;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public String getTxtDesc() {
        return txtDesc;
    }

    public void setTxtDesc(String txtDesc) {
        this.txtDesc = txtDesc;
    }
}
