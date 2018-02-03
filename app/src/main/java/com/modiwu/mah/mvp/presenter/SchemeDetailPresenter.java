package com.modiwu.mah.mvp.presenter;

import com.modiwu.mah.mvp.constract.SchemeDetialContract;
import com.modiwu.mah.mvp.model.SchemeDetailModel;
import com.modiwu.mah.mvp.model.bean.SchemeDetailBean;
import com.modiwu.mah.ui.activity.SchemeDetailActivity;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.SampleShowDialogObserver;

/**
 * Created by Obl on 2018/1/25.
 * com.modiwu.mah.mvp.presenter
 */

public class SchemeDetailPresenter extends BasePresenter<SchemeDetailActivity> implements SchemeDetialContract.ISchemeDetialPresenter {

    private final SchemeDetailModel mModel;

    public SchemeDetailPresenter(SchemeDetailActivity iView) {
        super(iView);
        mModel = new SchemeDetailModel();
    }

    @Override
    public void requestSchemeDetialData(String fangan_id) {
        mModel.requestSchemeDetailBean(fangan_id)
                .subscribe(schemeDetailBean -> mIView.setSchemeDetialData(schemeDetailBean));
    }

    public void requestUpSchemeDetialData(String fangan_id) {
        mModel.requestSchemeDetailBean(fangan_id)
                .subscribe(new SampleShowDialogObserver<SchemeDetailBean>(mIView) {

                    @Override
                    protected void onSuccess(SchemeDetailBean schemeDetailBean) throws Exception {
                        mIView.setSchemeDetialData(schemeDetailBean);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mIView.showError();
                    }
                });
    }
}