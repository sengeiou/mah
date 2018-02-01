package com.modiwu.mah.ui.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.modiwu.mah.MainActivity;
import com.modiwu.mah.R;
import com.modiwu.mah.base.BaseFragment;
import com.modiwu.mah.mvp.constract.CarpenterContract;
import com.modiwu.mah.mvp.model.bean.CarpenterBean;
import com.modiwu.mah.mvp.presenter.CarpenterPresenter;
import com.modiwu.mah.ui.adapter.CarpenterAdapter;
import com.modiwu.mah.ui.adapter.DockerAdapter;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;
import top.jplayer.baseprolibrary.listener.NetNavigationBarListener;
import top.jplayer.baseprolibrary.utils.LogUtil;

/**
 * Created by Obl on 2018/1/19.
 * com.modiwu.mah.ui.Fragment
 */

public class CarpenterFragment extends BaseFragment implements CarpenterContract.ICarpenterView {
    protected RecyclerView mRecyclerView1;
    protected RecyclerView mRecyclerView2;
    private NavigationTabBar mNavigationTabBar;
    private CarpenterPresenter mPresenter;
    private CarpenterAdapter mAdapter1;
    private DockerAdapter mAdapter2;

    @Override
    public int initLayout() {
        return R.layout.fragment_carpenter;
    }

    @Override
    protected void initData(View rootView) {
        mMultipleStatusView = rootView.findViewById(R.id.multiplestatusview);
        smartRefreshLayout = rootView.findViewById(R.id.smartRefreshLayout);
        mNavigationTabBar = rootView.findViewById(R.id.ntb);
        mRecyclerView1 = rootView.findViewById(R.id.recyclerView1);
        mRecyclerView2 = rootView.findViewById(R.id.recyclerView2);

        mPresenter = new CarpenterPresenter(this);
        showLoading();
        mPresenter.requestCarpenterData();
        bottomBar(mNavigationTabBar);
        setShowTypeByClickMore();

        List<CarpenterBean.RecordsBean> list1 = new ArrayList<>();
        initRecyclerView1(list1);

        initRecyclerView2(new ArrayList<>());
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.drawable.pic_06);
        list.add(R.drawable.pic_06);
        list.add(R.drawable.pic_06);
        list.add(R.drawable.pic_06);
        list.add(R.drawable.pic_06);
        mAdapter2.setNewData(list);
    }


    private void initRecyclerView2(ArrayList<Integer> list) {
        mRecyclerView2.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mAdapter2 = new DockerAdapter(list);
        mAdapter2.addHeaderView(View.inflate(getContext(), R.layout.adapter_home_body_toshop, null));
        mRecyclerView2.setAdapter(mAdapter2);
    }

    private void initRecyclerView1(List<CarpenterBean.RecordsBean> list) {
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter1 = new CarpenterAdapter(list);
        mRecyclerView1.setAdapter(mAdapter1);
    }

    /**
     * 设置底部栏
     */
    private void bottomBar(NavigationTabBar navigationTabBar) {

        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        String[] titleArrs = new String[]{"匠", "器"};
        int[] drawArrs = new int[]{R.drawable.main_home, R.drawable.main_scheme, R.drawable.main_charpenter, R.drawable.main_me};
        for (int i = 0; i < titleArrs.length; i++) {
            models.add(new NavigationTabBar.Model.Builder(
                    getResources().getDrawable(drawArrs[i]),
                    getResources().getColor(top.jplayer.baseprolibrary.R.color.black))
                    .title(titleArrs[i]).build());
        }
        navigationTabBar.setModels(models);
        navigationTabBar.setOnTabBarSelectedIndexListener(new NetNavigationBarListener() {
            @Override
            public void onceSelected(NavigationTabBar.Model model, int index) {
                if (index == 0) {
                    mRecyclerView1.setVisibility(View.VISIBLE);
                    mRecyclerView2.setVisibility(View.GONE);
                } else {
                    mRecyclerView1.setVisibility(View.GONE);
                    mRecyclerView2.setVisibility(View.VISIBLE);
                }
                LogUtil.e(index + "----");
            }
        });
        navigationTabBar.setModelIndex(0, true);
    }

    /**
     * 根据首页点击更多，实现进入不同界面
     */
    private void setShowTypeByClickMore() {
        mNavigationTabBar.setModelIndex(((MainActivity) getActivity()).carFragmentType);
    }

    @Override
    protected void onShowFragment() {
        setShowTypeByClickMore();
    }

    @Override
    public void setCarpenterData(CarpenterBean bean) {
        mAdapter1.setNewData(bean.records);
       setShowTypeByClickMore();
    }
}
