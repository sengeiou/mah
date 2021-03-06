package com.modiwu.mah.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.modiwu.mah.R;
import com.modiwu.mah.base.BaseCommonActivity;
import com.modiwu.mah.mvp.constract.ShouCangContract;
import com.modiwu.mah.mvp.model.bean.MeShouCangBean;
import com.modiwu.mah.mvp.presenter.ShouCangPresenter;
import com.modiwu.mah.ui.adapter.MeShouCangAdapter;

import java.util.ArrayList;
import java.util.Locale;

import top.jplayer.baseprolibrary.mvp.model.bean.BaseBean;
import top.jplayer.baseprolibrary.utils.ActivityUtils;

/**
 * Created by Obl on 2018/2/6.
 * com.modiwu.mah.ui.activity
 */


public class MeShouCangActivity extends BaseCommonActivity implements ShouCangContract.IShouCangView {

    private ShouCangPresenter mPresenter;
    private MeShouCangAdapter mAdapter;

    @Override
    public int setBaseLayout() {
        return R.layout.activity_me_order;
    }

    RecyclerView mRecyclerView;

    @Override
    public void initBaseData() {
        findToolBarView(addRootView);
        mMultipleStatusView = addRootView.findViewById(R.id.multiplestatusview);
        smartRefreshLayout = addRootView.findViewById(R.id.smartRefreshLayout);
        mRecyclerView = addRootView.findViewById(R.id.recyclerView);
        DividerItemDecoration decor = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        decor.setDrawable(getResources().getDrawable(R.drawable.shape_divider));
        mRecyclerView.addItemDecoration(decor);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));

        ArrayList<MeShouCangBean.RowsBean> data = new ArrayList<>();
        mAdapter = new MeShouCangAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            MeShouCangBean.RowsBean rowsBean = (MeShouCangBean.RowsBean) adapter.getData().get(position);
            mPresenter.requestShouCangDel(rowsBean.fangan_id, position);
            return false;
        });
        mAdapter.setOnItemClickListener((adapter, view, position) ->
        {
            MeShouCangBean.RowsBean bean = mAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putString("fangan_id", String.format(Locale.CHINA, "%d", bean.fangan_id));
            ActivityUtils.init().start(this, SchemeDetailActivity.class, bean.fangan_name,
                    bundle);
        });
        showLoading();
        mPresenter = new ShouCangPresenter(this);
        mPresenter.requestShouCangData();
        smartRefreshLayout.setOnRefreshListener(refresh -> mPresenter.requestShouCangData());
    }

    @Override
    public void setShouCangData(MeShouCangBean bean) {
        mAdapter.setNewData(bean.rows);
    }

    @Override
    public void setDelShouCang(BaseBean baseBean, int pos) {
        mPresenter.requestShouCangData(true);
    }
}