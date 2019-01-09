package com.example.dantczak.got.Utils.ResponseHandlers;

import android.util.Log;

import cz.msebera.android.httpclient.Header;

public final class NoReponseHandler extends OnlySuccessMattersHandler
{
    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString)
    {
        if(responseString != null)
        {
            Log.v(OnlySuccessMattersHandler.SUCCESS_TAG, responseString);
        }
        else
        {
            Log.v(OnlySuccessMattersHandler.SUCCESS_TAG, "no response message");
        }
    }
}
