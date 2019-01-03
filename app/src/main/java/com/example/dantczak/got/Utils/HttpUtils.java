package com.example.dantczak.got.Utils;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;

public class HttpUtils {
    private static final String BASE_URL = "http://192.168.0.103:21037/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void getWithBody(Context context, String url, Object bodyObject, AsyncHttpResponseHandler responseHandler)
            throws JsonProcessingException, UnsupportedEncodingException {
        String str = JsonUtils.getObjectMapper().writeValueAsString(bodyObject);
        StringEntity stringEntity = new StringEntity(str);
        client.get(context, getAbsoluteUrl(url), stringEntity, "application/json", responseHandler);
    }

    public static void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}