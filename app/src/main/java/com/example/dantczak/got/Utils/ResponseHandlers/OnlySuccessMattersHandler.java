package com.example.dantczak.got.Utils.ResponseHandlers;

import android.util.Log;

import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public abstract class OnlySuccessMattersHandler extends TextHttpResponseHandler
{
    public static final String FAILURE_TAG = "request failure";
    public static final String SUCCESS_TAG = "request success";

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
    {
        if(responseString != null)
        {
            Log.v(FAILURE_TAG, responseString);
        }
        else
        {
            Log.v(FAILURE_TAG, "no response message");
        }
    }
}
