package co.gauth.sample;

import co.gauth.sample.model.GauthRequest;
import co.gauth.sample.model.GauthResponse;
import com.google.gson.Gson;
import okhttp3.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * create joonwoo 2017. 9. 19.
 *
 */

@RestController
public class GauthApi {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(GauthApi.class);


    @Value("${GAUTH.HOST}")
    public String GAUTH_HOST;


    @RequestMapping(value = "/gauth", method = RequestMethod.POST, produces = "application/json")
    public GauthResponse pending(@org.springframework.web.bind.annotation.RequestBody LinkedHashMap gauthRequest) {

        GauthRequest request = new GauthRequest();
        request.setUri(gauthRequest.get("url").toString());
        request.setType(gauthRequest.get("type").toString());

        if(gauthRequest.get("body") != null){
            request.setBody((Map<String, Object>) gauthRequest.get("body"));
        }else{
            request.setBody(new LinkedHashMap<>());
        }

        if(gauthRequest.get("header") != null){
            request.setHeader((Map<String, Object>) gauthRequest.get("header"));
        }else{
            request.setHeader(new LinkedHashMap<>());
        }

        return gauthExecute(request);
    }


    private GauthResponse gauthExecute(GauthRequest gauthRequest) {
        GauthResponse gauthResponse = new GauthResponse();

        OkHttpClient client = new OkHttpClient();

        Request.Builder builder = new Request.Builder();
        builder = builder.url(GAUTH_HOST + gauthRequest.getUri());

        if("get".equalsIgnoreCase(gauthRequest.getType())){
            builder = builder.get();
        }else if("post".equalsIgnoreCase(gauthRequest.getType())){
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(gauthRequest.getBody()));
            builder = builder.post(requestBody);
        }else if("put".equalsIgnoreCase(gauthRequest.getType())){
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(gauthRequest.getBody()));
            builder = builder.put(requestBody);
        }else if("delete".equalsIgnoreCase(gauthRequest.getType())){
            builder = builder.delete();
        }else if("head".equalsIgnoreCase(gauthRequest.getType())){
            builder = builder.head();
        }

        Iterator<String> iterator = gauthRequest.getHeader().keySet().iterator();
        while(iterator.hasNext()){
            String name = iterator.next();
            builder.addHeader(name, gauthRequest.getHeader().get(name).toString());
        }

        builder = builder.addHeader("Content-Type", "application/json");

        Request request = builder.build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            gauthResponse.setCode(String.valueOf(response.code()));
            gauthResponse.setGauth_result(response.body().string());

        } catch (IOException e) {
            logger.error("", e);
        }
        return gauthResponse;
    }


}