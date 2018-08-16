package com.foxy_corporation.ezegamesevolution.mvp.presenter;

import android.support.annotation.Nullable;

import com.foxy_corporation.ezegamesevolution.mvp.ui.view.AbsView;

/**
 * Created by VlArCo on 11.06.2018.
 */

public abstract class BasePresenter<V extends AbsView> {

    @Nullable
    private V view;

    public void attachView(V v) {
        this.view = v;
    }

    @Nullable
    public V getView() {
        return view;
    }

    public void cleanView() {
        view = null;
    }
}
