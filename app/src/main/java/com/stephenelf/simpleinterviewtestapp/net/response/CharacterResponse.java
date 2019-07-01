package com.stephenelf.simpleinterviewtestapp.net.response;

public class CharacterResponse {

    private int code;
    private String status;
    private String etag;
    private String copyright;
    private String attributionText;
    private String attributionHTML;

    private CharacterContainer data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    public String getAttributionHTML() {
        return attributionHTML;
    }

    public void setAttributionHTML(String attributionHTML) {
        this.attributionHTML = attributionHTML;
    }

    public CharacterContainer getData() {
        return data;
    }

    public void setData(CharacterContainer data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CharacterResponse{" +
                "code=" + code +
                ", status='" + status + '\'' +
                ", etag='" + etag + '\'' +
                ", copyright='" + copyright + '\'' +
                ", attributionText='" + attributionText + '\'' +
                ", attributionHTML='" + attributionHTML + '\'' +
                ", data=" + data +
                '}';
    }
}
