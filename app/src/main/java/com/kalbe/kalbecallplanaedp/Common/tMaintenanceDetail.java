package com.kalbe.kalbecallplanaedp.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/24/2018.
 */

@DatabaseTable
public class tMaintenanceDetail implements Serializable {
    @DatabaseField(id = true)
    private String txtDetailId;
    @DatabaseField
    private String txtHeaderId;
    @DatabaseField
    private int intSubDetailActivityId;
    @DatabaseField
    private String txtNoDoc;
//    @DatabaseField
//    private String txtNoOrder;

    public String Property_txtDetailId = "txtDetailId";
    public String Property_txtHeaderId = "txtHeaderId";
    public String Property_intSubDetailActivityId = "intSubDetailActivityId";
    public String Property_txtNoDoc = "txtNoDoc";
//    public String Property_txtNoOrder = "txtNoOrder";
    public String Property_ListOfDataTMaintenanceDetail = "ListOfDataTMaintenanceDetail";

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

    public int getIntSubDetailActivityId() {
        return intSubDetailActivityId;
    }

    public void setIntSubDetailActivityId(int intSubDetailActivityId) {
        this.intSubDetailActivityId = intSubDetailActivityId;
    }

//    public String getTxtNoResep() {
//        return txtNoResep;
//    }
//
//    public void setTxtNoResep(String txtNoResep) {
//        this.txtNoResep = txtNoResep;
//    }
//
//    public String getTxtNoOrder() {
//        return txtNoOrder;
//    }
//
//    public void setTxtNoOrder(String txtNoOrder) {
//        this.txtNoOrder = txtNoOrder;
//    }


    public String getTxtNoDoc() {
        return txtNoDoc;
    }

    public void setTxtNoDoc(String txtNoDoc) {
        this.txtNoDoc = txtNoDoc;
    }
}
