package com.example.dantczak.got.Utils.ResponseHandlers;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public abstract class OnlySuccessMattersHandler extends TextHttpResponseHandler
{
    public static final String FAILURE_TAG = "request failure";
    public static final String SUCCESS_TAG = "request success";
    private ProgressDialog progressDialog = null;
    private Toast failureToast = null;

    public OnlySuccessMattersHandler() { }

    public OnlySuccessMattersHandler(ProgressDialog progressDialog)
    {
        this.progressDialog = progressDialog;
    }

    public OnlySuccessMattersHandler(ProgressDialog progressDialog, Toast failureToast)
    {
        this.progressDialog = progressDialog;
        this.failureToast = failureToast;
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
    {
        if(progressDialog != null)
            progressDialog.cancel();

        if(failureToast != null)
            failureToast.show();


        if(responseString != null)
        {
            Log.v(FAILURE_TAG, responseString);
        }
        else
        {
            Log.v(FAILURE_TAG, "no response message");
        }
    }

    protected void onSuccess()
    {
        if(progressDialog != null)
            progressDialog.cancel();
    }
}
