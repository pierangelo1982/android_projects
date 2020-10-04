package com.sviluppo.pierangelo.workreport.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pierangelo on 22/10/16.
 */

public class Customer {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("denominazione")
        @Expose
        private String denominazione;
        @SerializedName("nome")
        @Expose
        private String nome;
        @SerializedName("cognome")
        @Expose
        private String cognome;
        @SerializedName("piva")
        @Expose
        private String piva;
        @SerializedName("codfisc")
        @Expose
        private String codfisc;
        @SerializedName("telefono")
        @Expose
        private String telefono;
        @SerializedName("fax")
        @Expose
        private String fax;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("web")
        @Expose
        private String web;
        @SerializedName("indirizzo")
        @Expose
        private String indirizzo;
        @SerializedName("civico")
        @Expose
        private String civico;
        @SerializedName("cap")
        @Expose
        private String cap;
        @SerializedName("citta")
        @Expose
        private String citta;
        @SerializedName("latitudine")
        @Expose
        private Object latitudine;
        @SerializedName("longitudine")
        @Expose
        private Object longitudine;
        @SerializedName("note")
        @Expose
        private String note;
        @SerializedName("image")
        @Expose
        private String image;
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
     * The denominazione
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     *
     * @param denominazione
     * The denominazione
     */
    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    /**
     *
     * @return
     * The nome
     */
    public String getNome() {
        return nome;
    }

    /**
     *
     * @param nome
     * The nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @return
     * The cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     *
     * @param cognome
     * The cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     *
     * @return
     * The piva
     */
    public String getPiva() {
        return piva;
    }

    /**
     *
     * @param piva
     * The piva
     */
    public void setPiva(String piva) {
        this.piva = piva;
    }

    /**
     *
     * @return
     * The codfisc
     */
    public String getCodfisc() {
        return codfisc;
    }

    /**
     *
     * @param codfisc
     * The codfisc
     */
    public void setCodfisc(String codfisc) {
        this.codfisc = codfisc;
    }

    /**
     *
     * @return
     * The telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     *
     * @param telefono
     * The telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     *
     * @return
     * The fax
     */
    public String getFax() {
        return fax;
    }

    /**
     *
     * @param fax
     * The fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The web
     */
    public String getWeb() {
        return web;
    }

    /**
     *
     * @param web
     * The web
     */
    public void setWeb(String web) {
        this.web = web;
    }

    /**
     *
     * @return
     * The indirizzo
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     *
     * @param indirizzo
     * The indirizzo
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    /**
     *
     * @return
     * The civico
     */
    public String getCivico() {
        return civico;
    }

    /**
     *
     * @param civico
     * The civico
     */
    public void setCivico(String civico) {
        this.civico = civico;
    }

    /**
     *
     * @return
     * The cap
     */
    public String getCap() {
        return cap;
    }

    /**
     *
     * @param cap
     * The cap
     */
    public void setCap(String cap) {
        this.cap = cap;
    }

    /**
     *
     * @return
     * The citta
     */
    public String getCitta() {
        return citta;
    }

    /**
     *
     * @param citta
     * The citta
     */
    public void setCitta(String citta) {
        this.citta = citta;
    }

    /**
     *
     * @return
     * The latitudine
     */
    public Object getLatitudine() {
        return latitudine;
    }

    /**
     *
     * @param latitudine
     * The latitudine
     */
    public void setLatitudine(Object latitudine) {
        this.latitudine = latitudine;
    }

    /**
     *
     * @return
     * The longitudine
     */
    public Object getLongitudine() {
        return longitudine;
    }

    /**
     *
     * @param longitudine
     * The longitudine
     */
    public void setLongitudine(Object longitudine) {
        this.longitudine = longitudine;
    }

    /**
     *
     * @return
     * The note
     */
    public String getNote() {
        return note;
    }

    /**
     *
     * @param note
     * The note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
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
