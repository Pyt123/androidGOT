package com.example.dantczak.got.Utils;

import android.content.Context;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.entity.StringEntity;

public class HttpUtils {
    //private static final String BASE_URL = "http://192.168.0.103:21037/";
    private static final String BASE_URL = "http://192.168.2.11:8080/";
    private static final String CONTENT_TYPE = "application/json";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, AsyncHttpResponseHandler responseHandler, String...params)
    {
        url = concatParamsToUrl(url, params);
        client.get(getAbsoluteUrl(url), responseHandler);
    }

    public static void post(String url, AsyncHttpResponseHandler responseHandler, String...params)
    {
        url = concatParamsToUrl(url, params);
        client.post(getAbsoluteUrl(url), responseHandler);
    }

    public static void getWithBody(Context context, String url, Object bodyObject,
                                   AsyncHttpResponseHandler responseHandler, String...params)
            throws JsonProcessingException, UnsupportedEncodingException
    {
        url = concatParamsToUrl(url, params);
        String str = JsonUtils.getObjectMapper().writeValueAsString(bodyObject);
        StringEntity stringEntity = new StringEntity(str);
        client.get(context, getAbsoluteUrl(url), stringEntity, CONTENT_TYPE, responseHandler);
    }

    public static void postWithBody(Context context, String url, Object bodyObject,
                                   AsyncHttpResponseHandler responseHandler, String...params)
            throws JsonProcessingException, UnsupportedEncodingException
    {
        url = concatParamsToUrl(url, params);
        String str = JsonUtils.getObjectMapper().writeValueAsString(bodyObject);
        StringEntity stringEntity = new StringEntity(str, "UTF-8");
        System.out.println(str);
        client.post(context, getAbsoluteUrl(url), stringEntity, CONTENT_TYPE, responseHandler);
    }

    @Deprecated()
    public static void getByUrl(String url, @Nullable RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    @Deprecated()
    public static void postByUrl(String url, @Nullable RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private static String concatParamsToUrl(String url, String...params)
    {
        StringBuilder urlBuilder = new StringBuilder(url);
        for(String par : params)
        {
            urlBuilder.append(par).append("/");
        }
        return urlBuilder.toString();
    }
}