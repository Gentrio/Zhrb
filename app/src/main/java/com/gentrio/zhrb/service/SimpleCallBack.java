package com.gentrio.zhrb.service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Gentrio on 2016/10/21.
 */
public abstract class SimpleCallBack<T> implements Callback<T>{

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onResponse(response.body(), response.code(), response.message());
        } else {
            onResponseFail();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }

    /**
     * on response return
     *
     * @param result result
     * @param code   http code
     * @param msg    http msg
     */
    public abstract void onResponse(final T result, int code, String msg);

    public void onResponseFail() {

    }
}
