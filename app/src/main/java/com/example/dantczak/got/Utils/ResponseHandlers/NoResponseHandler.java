package com.example.dantczak.got.Utils.ResponseHandlers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * This is a dummy TextHttpResponseHandler implementation, which logs only the response for debugging purposes.
 * Can be used, when we're not interest in request correctness.
 * @author Dawid Antczak
 */
public final class NoResponseHandler extends TextHttpResponseHandler
{
    private static final String REQUEST_TAG = "request log";

    /**
     * A method called on HTTP response failure.
     * @param statusCode response status code
     * @param headers response headers
     * @param responseString response body string
     * @param throwable a throwable object, describing exception
     */
    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
    {
        logResponseString(responseString);
    }

    /**
     * A method called on HTTP response success.
     * @param statusCode response status code
     * @param headers response headers
     * @param responseString response body string
     */
    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString)
    {
        logResponseString(responseString);
    }

    /**
     * Logs the response to console.
     * @param response response body string (can be null)
     */
    private void logResponseString(@Nullable String response)
    {
        if(response != null)
        {
            Log.v(REQUEST_TAG, response);
        }
        else
        {
            Log.v(REQUEST_TAG, "no response message");
        }
    }
}
