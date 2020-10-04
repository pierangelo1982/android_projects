package com.sviluppo.pierangelo.workreport.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pierangelo on 05/11/16.
 */

public class Timesheet {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("worker_id")
    @Expose
    private Integer workerId;
    @SerializedName("custumer_id")
    @Expose
    private Object custumerId;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("inizio")
    @Expose
    private String inizio;
    @SerializedName("fine")
    @Expose
    private String fine;
    @SerializedName("ore")
    @Expose
    private String ore;
    @SerializedName("costo")
    @Expose
    private String costo;
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("latitudine")
    @Expose
    private Double latitudine;
    @SerializedName("longitudine")
    @Expose
    private Double longitudine;
    @SerializedName("latitudine_fine")
    @Expose
    private Double latitudineFine;
    @SerializedName("longitudine_fine")
    @Expose
    private Double longitudineFine;
    @SerializedName("note")
    @Expose
    private Object note;
    @SerializedName("confermato")
    @Expose
    private Object confermato;
    @SerializedName("url")
    @Expose
    private String url;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The workerId
     */
    public Integer getWorkerId() {
        return workerId;
    }

    /**
     *
     * @param workerId
     * The worker_id
     */
    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    /**
     *
     * @return
     * The custumerId
     */
    public Object getCustumerId() {
        return custumerId;
    }

    /**
     *
     * @param custumerId
     * The custumer_id
     */
    public void setCustumerId(Object custumerId) {
        this.custumerId = custumerId;
    }

    /**
     *
     * @return
     * The data
     */
    public String getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The inizio
     */
    public String getInizio() {
        return inizio;
    }

    /**
     *
     * @param inizio
     * The inizio
     */
    public void setInizio(String inizio) {
        this.inizio = inizio;
    }

    /**
     *
     * @return
     * The fine
     */
    public String getFine() {
        return fine;
    }

    /**
     *
     * @param fine
     * The fine
     */
    public void setFine(String fine) {
        this.fine = fine;
    }

    /**
     *
     * @return
     * The ore
     */
    public String getOre() {
        return ore;
    }

    /**
     *
     * @param ore
     * The ore
     */
    public void setOre(String ore) {
        this.ore = ore;
    }

    /**
     *
     * @return
     * The costo
     */
    public String getCosto() {
        return costo;
    }

    /**
     *
     * @param costo
     * The costo
     */
    public void setCosto(String costo) {
        this.costo = costo;
    }

    /**
     *
     * @return
     * The location
     */
    public Object getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(Object location) {
        this.location = location;
    }

    /**
     *
     * @return
     * The latitudine
     */
    public Double getLatitudine() {
        return latitudine;
    }

    /**
     *
     * @param latitudine
     * The latitudine
     */
    public void setLatitudine(Double latitudine) {
        this.latitudine = latitudine;
    }

    /**
     *
     * @return
     * The longitudine
     */
    public Double getLongitudine() {
        return longitudine;
    }

    /**
     *
     * @param longitudine
     * The longitudine
     */
    public void setLongitudine(Double longitudine) {
        this.longitudine = longitudine;
    }

    /**
     *
     * @return
     * The latitudineFine
     */
    public Double getLatitudineFine() {
        return latitudineFine;
    }

    /**
     *
     * @param latitudineFine
     * The latitudine_fine
     */
    public void setLatitudineFine(Double latitudineFine) {
        this.latitudineFine = latitudineFine;
    }

    /**
     *
     * @return
     * The longitudineFine
     */
    public Double getLongitudineFine() {
        return longitudineFine;
    }

    /**
     *
     * @param longitudineFine
     * The longitudine_fine
     */
    public void setLongitudineFine(Double longitudineFine) {
        this.longitudineFine = longitudineFine;
    }

    /**
     *
     * @return
     * The note
     */
    public Object getNote() {
        return note;
    }

    /**
     *
     * @param note
     * The note
     */
    public void setNote(Object note) {
        this.note = note;
    }

    /**
     *
     * @return
     * The confermato
     */
    public Object getConfermato() {
        return confermato;
    }

    /**
     *
     * @param confermato
     * The confermato
     */
    public void setConfermato(Object confermato) {
        this.confermato = confermato;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
