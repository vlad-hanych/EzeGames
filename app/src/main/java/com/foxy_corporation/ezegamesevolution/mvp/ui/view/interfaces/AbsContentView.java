package com.foxy_corporation.ezegamesevolution.mvp.ui.view.interfaces;

import com.foxy_corporation.ezegamesevolution.mvp.ui.view.AbsView;

/**
 * Created by VlArCo on 11.06.2018.
 */

public interface AbsContentView extends AbsView {
    void needToShowContentInput();
    void prepareContentDisplaying();
    void displayContent();
}
