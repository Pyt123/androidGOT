package com.example.dantczak.got.Utils;

import android.content.Context;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * This is a class with static methods to help create HTTP requests to server.
 * @author Dawid Antczak
 */
public class HttpUtils
{
    private static final String CONTENT_TYPE = "application/json";
    private static AsyncHttpClient client = new AsyncHttpClient();

    /**
     * Performs a get request without body
     * @param url a relative url String
     * @param responseHandler an AsyncHttpResponseHandler, which will response the request
     * @param params a list of String parameters, which will bez concatenated to the url
     */
    public static void get(String url, AsyncHttpResponseHandler responseHandler, String...params)
    {
        url = concatParamsToUrl(url, params);
        client.get(getAbsoluteUrl(url), responseHandler);
    }

    /**
     * Performs a post request without body
     * @param url a relative url String
     * @param responseHandler an AsyncHttpResponseHandler, which will response the request
     * @param params a list of String parameters, which will bez concatenated to the url
     */
    public static void post(String url, AsyncHttpResponseHandler responseHandler, String...params)
    {
        url = concatParamsToUrl(url, params);
        client.post(getAbsoluteUrl(url), responseHandler);
    }

    /**
     * Performs a get request with body
     * @param context current application context
     * @param url a relative url String
     * @param bodyObject a object, which will be send as request body
     * @param responseHandler a AsyncHttpResponseHandler, which will response the request
     * @param params a list of String parameters, which will bez concatenated to the url
     */
    public static void getWithBody(Context context, String url, Object bodyObject,
                                   AsyncHttpResponseHandler responseHandler, String...params)
            throws JsonProcessingException, UnsupportedEncodingException
    {
        url = concatParamsToUrl(url, params);
        String str = JsonUtils.getObjectMapper().writeValueAsString(bodyObject);
        StringEntity stringEntity = new StringEntity(str);
        client.get(context, getAbsoluteUrl(url), stringEntity, CONTENT_TYPE, responseHandler);
    }

    /**
     * Performs a post request with body
     * @param context current application context
     * @param url a relative url String
     * @param bodyObject a object, which will be send as request body
     * @param responseHandler a AsyncHttpResponseHandler, which will response the request
     * @param params a list of String parameters, which will bez concatenated to the url
     */
    public static void postWithBody(Context context, String url, Object bodyObject,
                                   AsyncHttpResponseHandler responseHandler, String...params)
            throws JsonProcessingException, UnsupportedEncodingException
    {
        url = concatParamsToUrl(url, params);
        String str = JsonUtils.getObjectMapper().writeValueAsString(bodyObject);
        StringEntity stringEntity = new StringEntity(str);
        client.post(context, getAbsoluteUrl(url), stringEntity, CONTENT_TYPE, responseHandler);
    }

    /**
     * Performs a get request without body.
     * Deprecated because it forces to concatenate the relative url with String parameters before call.
     * @param url a relative url String
     * @param params a RequestParams object, used by the get AsyncHttpClient.get method
     * @param responseHandler an AsyncHttpResponseHandler, which will response the request
     */
    @Deprecated()
    public static void getByUrl(String url, @Nullable RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    /**
     * Performs a post request without body
     * Deprecated because it forces to concatenate the relative url with String parameters before call.
     * @param url a relative url String
     * @param params a RequestParams object, used by the get AsyncHttpClient.get method
     * @param responseHandler an AsyncHttpResponseHandler, which will response the request
     */
    @Deprecated()
    public static void postByUrl(String url, @Nullable RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    /**
     * Performs a post request without body
     * Deprecated because it forces to concatenate the relative url with String parameters before call.
     * @param relativeUrl a relative url String
     * @return returns a String with concatenated BASE_URL/relativeUrl
     */
    private static String getAbsoluteUrl(String relativeUrl) {
        return StaticMockValues.BASE_URL + "/" + relativeUrl;
    }

    /**
     * Performs a post request without body
     * Deprecated because it forces to concatenate the relative url with String parameters before call.
     * @param url a url String
     * @param params a list of String parameters
     * @return returns a String with concatenated params to, after '/' each
     */
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