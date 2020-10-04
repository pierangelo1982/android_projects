package com.orizio.pierangelo.bresciahotspotwifi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pierangelo on 24/12/17.
 */

public class HotSpot {
    @SerializedName(":@computed_region_6hky_swhk")
    @Expose
    private String computedRegion6hkySwhk;
    @SerializedName(":@computed_region_ttgh_9sm5")
    @Expose
    private String computedRegionTtgh9sm5;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("latitudine")
    @Expose
    private String latitudine;
    @SerializedName("longitudine")
    @Expose
    private String longitudine;
    @SerializedName("tipologia")
    @Expose
    private String tipologia;
    @SerializedName("ubicazione")
    @Expose
    private String ubicazione;

    public String getComputedRegion6hkySwhk() {
        return computedRegion6hkySwhk;
    }

    public void setComputedRegion6hkySwhk(String computedRegion6hkySwhk) {
        this.computedRegion6hkySwhk = computedRegion6hkySwhk;
    }

    public String getComputedRegionTtgh9sm5() {
        return computedRegionTtgh9sm5;
    }

    public void setComputedRegionTtgh9sm5(String computedRegionTtgh9sm5) {
        this.computedRegionTtgh9sm5 = computedRegionTtgh9sm5;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(String latitudine) {
        this.latitudine = latitudine;
    }

    public String getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(String longitudine) {
        this.longitudine = longitudine;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getUbicazione() {
        return ubicazione;
    }

    public void setUbicazione(String ubicazione) {
        this.ubicazione = ubicazione;
    }
}
