package com.foxy_corporation.ezegamesevolution.mvp.model;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by VlArCo on 11.06.2018.
 */

public interface ServerAPI {
    @GET()
    Observable<ResponseBody> getPhoneNumber(@Url String url);

    @GET()
    Observable<ResponseBody> checkPhoneNumber(@Url String url);
}
