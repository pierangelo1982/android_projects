package com.sviluppo.pierangelo.workreport.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pierangelo on 29/06/16.
 */
public class Worker {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("role_id")
    @Expose
    private Integer roleId;
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("cognome")
    @Expose
    private String cognome;
    @SerializedName("codfisc")
    @Expose
    private String codfisc;
    @SerializedName("piva")
    @Expose
    private String piva;
    @SerializedName("indirizzo")
    @Expose
    private String indirizzo;
    @SerializedName("cap")
    @Expose
    private String cap;
    @SerializedName("citta")
    @Expose
    private String citta;
    @SerializedName("provincia")
    @Expose
    private String provincia;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("cellulare")
    @Expose
    private String cellulare;
    @SerializedName("costo_ora")
    @Expose
    private String costoOra;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("active")
    @Expose
    private Boolean active;
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
     * The roleId
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     *
     * @param roleId
     * The role_id
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
     * The provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     *
     * @param provincia
     * The provincia
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
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
     * The cellulare
     */
    public String getCellulare() {
        return cellulare;
    }

    /**
     *
     * @param cellulare
     * The cellulare
     */
    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    /**
     *
     * @return
     * The costoOra
     */
    public String getCostoOra() {
        return costoOra;
    }

    /**
     *
     * @param costoOra
     * The costo_ora
     */
    public void setCostoOra(String costoOra) {
        this.costoOra = costoOra;
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
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     * The active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     *
     * @param active
     * The active
     */
    public void setActive(Boolean active) {
        this.active = active;
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
