package com.example.dantczak.got.Utils.ResponseHandlers;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * This is a abstract TextHttpResponseHandler implementation, which logs response for debugging purposes.
 * The onFailure method can't be override. It stops the progress dialog (if passed) and show a toast message (if passed).
 * Can be used when we're have no requirements for custom behavior for failure response.
 * @author Dawid Antczak
 */
public abstract class OnlySuccessMattersHandler extends TextHttpResponseHandler
{
    private static final String REQUEST_TAG = "request log";
    private ProgressDialog progressDialog = null;
    private Toast failureToast = null;

    /**
     * A public no parameter constructor. Will construct a response, which logs only debug message on request failure.
     */
    public OnlySuccessMattersHandler() { }

    /**
     * A public constructor. It wll construct a response, which will cancel passed progress dialog on request failure.
     * @param progressDialog a progress dialog, which will be canceled on response failure
     */
    public OnlySuccessMattersHandler(ProgressDialog progressDialog)
    {
        this.progressDialog = progressDialog;
    }

    /**
     * A public constructor. It will construct a response, which will show passed toast message on request failure.
     * @param failureToast a progress dialog, which will be canceled on response failure
     */
    public OnlySuccessMattersHandler(Toast failureToast)
    {
        this.failureToast = failureToast;
    }

    /**
     * A public constructor. It will construct a response, which will cancel passed progress dialog
     * and show passed toast message on request failure.
     * @param progressDialog a progress dialog, which will be canceled on response failure
     * @param failureToast a progress dialog, which will be canceled on response failure
     */
    public OnlySuccessMattersHandler(ProgressDialog progressDialog, Toast failureToast)
    {
        this.progressDialog = progressDialog;
        this.failureToast = failureToast;
    }

    /**
     * A method called on HTTP response failure. Throws
     * @param statusCode response status code
     * @param headers response headers
     * @param responseString response body string
     * @param throwable a throwable object, describing exception
     */
    @Override
    public final void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
    {
        if(progressDialog != null)
            progressDialog.cancel();

        if(failureToast != null)
            failureToast.show();

        logResponseString(responseString);
    }

    /**
     * A protected method. It will cancel progress dialog (if any was passed).
     */
    protected void onSuccess()
    {
        if(progressDialog != null)
            progressDialog.cancel();
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
