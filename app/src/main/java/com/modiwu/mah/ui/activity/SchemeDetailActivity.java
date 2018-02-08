package com.modiwu.mah.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.modiwu.mah.R;
import com.modiwu.mah.base.BaseSpecialActivity;
import com.modiwu.mah.mvp.constract.SchemeDetialContract;
import com.modiwu.mah.mvp.model.bean.SchemeDetailBean;
import com.modiwu.mah.mvp.presenter.SchemeDetailPresenter;
import com.modiwu.mah.ui.adapter.AdapterPagerSchemeDetail;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.widgets.MultipleStatusView;

/**
 * Created by Obl on 2018/1/23.
 * com.modiwu.mah.ui.activity
 */
public class SchemeDetailActivity extends BaseSpecialActivity implements SchemeDetialContract.ISchemeDetialView {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.tvToCard)
    TextView tvToCard;
    @BindView(R.id.ivCirRed)
    ImageView mIvCirRed;
    @BindView(R.id.tvToBuy)
    TextView mTvToBuy;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.rlSchemeDetailBottom)
    LinearLayout llSchemeDetailBottom;
    @BindView(R.id.multiplestatusview)
    MultipleStatusView mMultipleStatusView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private Unbinder mUnbinder;
    public SchemeDetailPresenter mPresenter;
    private AdapterPagerSchemeDetail mAdapter;
    private String mFangan_id;

    @Override
    public int setBaseLayout() {
        return R.layout.activity_scheme_detail;
    }

    @Override
    public void initBaseData() {
        mUnbinder = ButterKnife.bind(this, contentView);
        findToolBarView(contentView);
        customBarLeft();
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("整装");
        strings.add("硬装");
        strings.add("软装");
        strings.add("楼盘");
        strings.add("单品");
        mAdapter = new AdapterPagerSchemeDetail(getSupportFragmentManager(), strings);
        mPresenter = new SchemeDetailPresenter(this);
        mFangan_id = mBundle.getString("fangan_id");
        if (mFangan_id != null) {
            mMultipleStatusView.showLoading();
            mPresenter.requestSchemeDetialData(mFangan_id);
            smartRefreshLayout.setOnRefreshListener(refresh -> mPresenter.requestSchemeDetialData(mFangan_id));
        } else {
            mMultipleStatusView.showEmpty();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    public SchemeDetailBean mSchemeDetailBean;

    @Override
    public void setSchemeDetialData(SchemeDetailBean bean) {
        mMultipleStatusView.showContent();
        smartRefreshLayout.finishRefresh();
        this.mSchemeDetailBean = bean;
        viewPager.setAdapter(mAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtil.e("onTabSelected" + tab.getPosition());
                if (tab.getPosition() == 3 || tab.getPosition() == 4) {
                    llSchemeDetailBottom.setVisibility(View.GONE);
                } else
                    llSchemeDetailBottom.setVisibility(View.VISIBLE);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LogUtil.e("onTabUnselected" + tab.getPosition());

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                LogUtil.e("onTabReselected" + tab.getPosition());
            }
        });
        tvToCard.setOnClickListener(view -> startActivity(new Intent(mBaseActivity, ShopCartActivity.class)));
        mTvToBuy.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("fangan_id", mFangan_id);
            ActivityUtils.init().start(mBaseActivity, SchemeOrderCreateActivity.class, "方案订单", bundle);
        });
    }

    @Override
    public void showError() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showError();
        }
        if (smartRefreshLayout != null && smartRefreshLayout.isRefreshing()) {
            smartRefreshLayout.finishRefresh();
        }
    }

    @Override
    public void showLoading() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showLoading();
        }
    }

    @Override
    public void showEmpty() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showEmpty();
        }
        if (smartRefreshLayout != null && smartRefreshLayout.isRefreshing()) {
            smartRefreshLayout.finishRefresh();
        }
    }
}
