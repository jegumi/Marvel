package com.jegumi.marvel.network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.jegumi.marvel.MarvelApplication;
import com.jegumi.marvel.helpers.PropertiesHelper;
import com.jegumi.marvel.model.DataWrapper;
import com.jegumi.marvel.model.DataWrapperItem;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Api {

    private static final String TAG = Api.class.getSimpleName();
    public static final String API_PUBLIC_PROPERTY = "api_public";
    public static final String API_SECRET_PROPERTY = "api_secret";

    private static final String BASE_URL = "http://gateway.marvel.com/v1/public/";
    private static final String CHARACTERS_ENDPOINT = "characters";
    private static final String API_KEY = "apikey";
    private static final String HASH_KEY = "hash";
    private static final String TIMESTAMP_KEY = "ts";
    private static final String TIMESTAMP_VALUE = "1";
    private static final String LIMIT_KEY = "limit";
    private static final String OFFSET_KEY = "offset";
    private static final String NAME_KEY = "nameStartsWith";
    private static final int LIMIT_VALUE = 40;
    private static final int SEARCH_LIMIT_VALUE = 10;
    private static final Context mContext = MarvelApplication.getContext();

    private String apiPublic;
    private String apiSecret;
    private RequestQueue requestQueue;

    public Api(Context context) {
        Network network = new BasicNetwork(new OkHttpStack());
        requestQueue =  new RequestQueue(new DiskBasedCache(new File(context.getCacheDir(), "volley")), network);
        requestQueue.start();

        try {
            apiPublic = PropertiesHelper.getProperty(API_PUBLIC_PROPERTY, mContext);
            apiSecret = PropertiesHelper.getProperty(API_SECRET_PROPERTY, mContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSearchEndPoint(String name) {
        Uri.Builder urlBuilder = Uri.parse(BASE_URL).buildUpon();
        urlBuilder.appendPath(CHARACTERS_ENDPOINT);
        urlBuilder.appendQueryParameter(TIMESTAMP_KEY, TIMESTAMP_VALUE);
        urlBuilder.appendQueryParameter(LIMIT_KEY, String.valueOf(SEARCH_LIMIT_VALUE));
        urlBuilder.appendQueryParameter(NAME_KEY, name);
        urlBuilder.appendQueryParameter(API_KEY, apiPublic);
        urlBuilder.appendQueryParameter(HASH_KEY, getPrivateHash());

        return urlBuilder.build().toString();
    }

    public String getCharactersEndPoint(int page) {
        Uri.Builder urlBuilder = Uri.parse(BASE_URL).buildUpon();
        urlBuilder.appendPath(CHARACTERS_ENDPOINT);
        urlBuilder.appendQueryParameter(TIMESTAMP_KEY, TIMESTAMP_VALUE);
        urlBuilder.appendQueryParameter(LIMIT_KEY, String.valueOf(LIMIT_VALUE));
        urlBuilder.appendQueryParameter(OFFSET_KEY, String.valueOf(page * LIMIT_VALUE));
        urlBuilder.appendQueryParameter(API_KEY, apiPublic);
        urlBuilder.appendQueryParameter(HASH_KEY, getPrivateHash());

        return urlBuilder.build().toString();
    }

    public String getItemsEndPoint(String url) {
        Uri.Builder urlBuilder = Uri.parse(url).buildUpon();
        urlBuilder.appendQueryParameter(TIMESTAMP_KEY, TIMESTAMP_VALUE);
        urlBuilder.appendQueryParameter(API_KEY, apiPublic);
        urlBuilder.appendQueryParameter(HASH_KEY, getPrivateHash());

        return urlBuilder.build().toString();
    }

    public String getPrivateHash() {
        try {
            String value = TIMESTAMP_VALUE + apiSecret + apiPublic;
            MessageDigest md5Encoder = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5Encoder.digest(value.getBytes());

            StringBuilder md5 = new StringBuilder();
            for (byte md5Byte : md5Bytes) {
                md5.append(Integer.toHexString((md5Byte & 0xFF) | 0x100).substring(1, 3));
            }
            return md5.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "MD5", e);
        }
        return null;
    }

    public void cancelAllRequests() {
        requestQueue.cancelAll(TAG);
    }

    public void loadCharacters(String endPoint, Response.Listener<DataWrapper> listener, Response.ErrorListener errorListener) {
        GSonRequest<DataWrapper> jsObjRequest = new GSonRequest<>(
                Request.Method.GET,
                endPoint,
                DataWrapper.class,
                listener,
                errorListener);

        jsObjRequest.setTag(TAG);
        requestQueue.add(jsObjRequest);
    }

    public void loadItems(String endPoint, Response.Listener<DataWrapperItem> listener, Response.ErrorListener errorListener) {
        GSonRequest<DataWrapperItem> jsObjRequest = new GSonRequest<>(
                Request.Method.GET,
                endPoint,
                DataWrapperItem.class,
                listener,
                errorListener);

        jsObjRequest.setTag(TAG);
        requestQueue.add(jsObjRequest);
    }
}
