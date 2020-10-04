package com.sviluppo.pierangelo.androidretrofitgson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierangelo on 18/06/16.
 */


public class Cluniacensi {

        @SerializedName("titolo")
        @Expose
        private String titolo;
        @SerializedName("titolomenu")
        @Expose
        private String titolomenu;
        @SerializedName("latitudine")
        @Expose
        private Double latitudine;
        @SerializedName("longitudine")
        @Expose
        private Double longitudine;
        @SerializedName("via")
        @Expose
        private String via;
        @SerializedName("citta")
        @Expose
        private String citta;
        @SerializedName("web")
        @Expose
        private String web;
        @SerializedName("introduzione")
        @Expose
        private String introduzione;
        @SerializedName("body")
        @Expose
        private String body;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("thumb")
        @Expose
        private String thumb;
        @SerializedName("cropping")
        @Expose
        private String cropping;
        @SerializedName("slider")
        @Expose
        private String slider;
        @SerializedName("revolution")
        @Expose
        private String revolution;
        @SerializedName("croplibero")
        @Expose
        private String croplibero;
        @SerializedName("album")
        @Expose
        private Object album;
        @SerializedName("pub_date")
        @Expose
        private String pubDate;
        @SerializedName("video")
        @Expose
        private List<Object> video = new ArrayList<Object>();
        @SerializedName("galleria")
        @Expose
        private List<Object> galleria = new ArrayList<Object>();
        @SerializedName("documenti")
        @Expose
        private List<Object> documenti = new ArrayList<Object>();

        /**
         * @return The titolo
         */
        public String getTitolo() {
            return titolo;
        }

        /**
         * @param titolo The titolo
         */
        public void setTitolo(String titolo) {
            this.titolo = titolo;
        }

        /**
         * @return The titolomenu
         */
        public String getTitolomenu() {
            return titolomenu;
        }

        /**
         * @param titolomenu The titolomenu
         */
        public void setTitolomenu(String titolomenu) {
            this.titolomenu = titolomenu;
        }

        /**
         * @return The latitudine
         */
        public Double getLatitudine() {
            return latitudine;
        }

        /**
         * @param latitudine The latitudine
         */
        public void setLatitudine(Double latitudine) {
            this.latitudine = latitudine;
        }

        /**
         * @return The longitudine
         */
        public Double getLongitudine() {
            return longitudine;
        }

        /**
         * @param longitudine The longitudine
         */
        public void setLongitudine(Double longitudine) {
            this.longitudine = longitudine;
        }

        /**
         * @return The via
         */
        public String getVia() {
            return via;
        }

        /**
         * @param via The via
         */
        public void setVia(String via) {
            this.via = via;
        }

        /**
         * @return The citta
         */
        public String getCitta() {
            return citta;
        }

        /**
         * @param citta The citta
         */
        public void setCitta(String citta) {
            this.citta = citta;
        }

        /**
         * @return The web
         */
        public String getWeb() {
            return web;
        }

        /**
         * @param web The web
         */
        public void setWeb(String web) {
            this.web = web;
        }

        /**
         * @return The introduzione
         */
        public String getIntroduzione() {
            return introduzione;
        }

        /**
         * @param introduzione The introduzione
         */
        public void setIntroduzione(String introduzione) {
            this.introduzione = introduzione;
        }

        /**
         * @return The body
         */
        public String getBody() {
            return body;
        }

        /**
         * @param body The body
         */
        public void setBody(String body) {
            this.body = body;
        }

        /**
         * @return The image
         */
        public String getImage() {
            return image;
        }

        /**
         * @param image The image
         */
        public void setImage(String image) {
            this.image = image;
        }

        /**
         * @return The thumb
         */
        public String getThumb() {
            return thumb;
        }

        /**
         * @param thumb The thumb
         */
        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        /**
         * @return The cropping
         */
        public String getCropping() {
            return cropping;
        }

        /**
         * @param cropping The cropping
         */
        public void setCropping(String cropping) {
            this.cropping = cropping;
        }

        /**
         * @return The slider
         */
        public String getSlider() {
            return slider;
        }

        /**
         * @param slider The slider
         */
        public void setSlider(String slider) {
            this.slider = slider;
        }

        /**
         * @return The revolution
         */
        public String getRevolution() {
            return revolution;
        }

        /**
         * @param revolution The revolution
         */
        public void setRevolution(String revolution) {
            this.revolution = revolution;
        }

        /**
         * @return The croplibero
         */
        public String getCroplibero() {
            return croplibero;
        }

        /**
         * @param croplibero The croplibero
         */
        public void setCroplibero(String croplibero) {
            this.croplibero = croplibero;
        }

        /**
         * @return The album
         */
        public Object getAlbum() {
            return album;
        }

        /**
         * @param album The album
         */
        public void setAlbum(Object album) {
            this.album = album;
        }

        /**
         * @return The pubDate
         */
        public String getPubDate() {
            return pubDate;
        }

        /**
         * @param pubDate The pub_date
         */
        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        /**
         * @return The video
         */
        public List<Object> getVideo() {
            return video;
        }

        /**
         * @param video The video
         */
        public void setVideo(List<Object> video) {
            this.video = video;
        }

        /**
         * @return The galleria
         */
        public List<Object> getGalleria() {
            return galleria;
        }

        /**
         * @param galleria The galleria
         */
        public void setGalleria(List<Object> galleria) {
            this.galleria = galleria;
        }

        /**
         * @return The documenti
         */
        public List<Object> getDocumenti() {
            return documenti;
        }

        /**
         * @param documenti The documenti
         */
        public void setDocumenti(List<Object> documenti) {
            this.documenti = documenti;
        }
    }