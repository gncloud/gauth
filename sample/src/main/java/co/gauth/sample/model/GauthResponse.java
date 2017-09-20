package co.gauth.sample.model;

/*
 * create joonwoo 2017. 9. 19.
 * 
 */
public class GauthResponse {

    private String code;
    private String gauth_result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGauth_result() {
        return gauth_result;
    }

    public void setGauth_result(String gauth_result) {
        this.gauth_result = gauth_result;
    }
}