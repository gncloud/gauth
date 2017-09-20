package co.gauth.sample.model;

import java.util.Map;

/*
 * create joonwoo 2017. 9. 19.
 * 
 */
public class GauthRequest {

    private String uri;
    private String type;
    private Map<String, Object> body;
    private Map<String, Object> header;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    public Map<String, Object> getHeader() {
        return header;
    }

    public void setHeader(Map<String, Object> header) {
        this.header = header;
    }
}