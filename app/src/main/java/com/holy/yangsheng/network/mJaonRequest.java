package com.holy.yangsheng.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;

/**
 * Created by Hailin on 2017/2/15.
 */

public class mJaonRequest extends JsonArrayRequest {


    public mJaonRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
//            response.headers.put("HTTP.CONTENT_TYPE", "utf-8");
//                String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            String jsonString = new String(response.data,"UTF-8");
            return Response.success(new JSONArray(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
        catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

}
