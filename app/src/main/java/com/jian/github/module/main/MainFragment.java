package com.jian.github.module.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;

import com.jakewharton.rxbinding.view.RxView;
import com.jian.github.base.CommonConstants;
import com.jian.github.base.LazyLoadFragment;
import com.jian.github.bean.apibean.RepoSearchResult;
import com.jian.github.bean.apibean.Repository;
import com.jian.github.bean.localbean.SearchFactor;
import com.jian.github.module.common.RepositoryAdapter;
import com.jian.github.utils.Actions;
import com.jian.github.utils.CommonUtils;
import com.jian.github.utils.DialogUtils;
import com.jian.github.utils.Log;
import com.jian.github.utils.ScreenUtils;
import com.jian.github.utils.UiUtils;
import com.jian.github.R;
import com.ouyangzn.recyclerview.BaseRecyclerViewAdapter;
import com.trello.rxlifecycle.android.FragmentEvent;

import java.util.ArrayList;

import static com.jian.github.module.main.MainContract.IMainPresenter;
import static com.jian.github.module.main.MainContract.IMainView;

public class MainFragment extends LazyLoadFragment<IMainView, IMainPresenter>
        implements MainContract.IMainView, BaseRecyclerViewAdapter.OnLoadingMoreListener,
        BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener,
        BaseRecyclerViewAdapter.OnRecyclerViewItemLongClickListener, View.OnClickListener {

    private static final String LANGUAGE = "language";
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    private RepositoryAdapter mAdapter;
    private SearchFactor mSearchFactor;

    public static MainFragment getInstance(String language) {
        MainFragment fragment = new MainFragment();
        Bundle data = new Bundle();
        data.putString(LANGUAGE, language);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected Status getCurrentStatus() {
        return Status.STATUS_LOADING;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_main;
    }

    @Override
    public MainContract.IMainPresenter initPresenter() {
        return new MainPresenter(getContext(), mProvider);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String language = getArguments().getString(LANGUAGE);
        mSearchFactor = new SearchFactor();
        mSearchFactor.language = language;

        mAdapter = new RepositoryAdapter(getContext(), new ArrayList<>(0));
        mAdapter.setOnRecyclerViewItemClickListener(this);
        mAdapter.setOnRecyclerViewItemLongClickListener(this);
        mAdapter.setOnLoadingMoreListener(this);
    }

    @Override
    public void onDestroyView() {
        mRecyclerView.setAdapter(null);
        super.onDestroyView();
    }

    @Override
    protected void lazyInitView(View parent) {
        search(false);
        requestNoToolbar();

        mRefreshLayout.setOnRefreshListener(() -> search(true));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RxView.touches(mRecyclerView, motionEvent -> {
            ScreenUtils.hideKeyBoard(mRecyclerView);
            return false;
        }).compose(mProvider.bindUntilEvent(FragmentEvent.DESTROY_VIEW)).subscribe();
        mAdapter.setEmptyView(mInflater.inflate(R.layout.item_no_data, mRecyclerView, false));
        UiUtils.setRecyclerViewLoadMore(mAdapter, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        View errorView = mInflater.inflate(R.layout.view_error_main, (ViewGroup) parent, false);
        errorView.findViewById(R.id.layout_main_loading_failure).setOnClickListener(this);
        setErrorView(errorView);

    }

    private void search(boolean isRefresh) {
        if (isRefresh) {
            mSearchFactor.page = 1;
            mRefreshLayout.setRefreshing(true);
        }
        Log.d(TAG, "----------搜索数据:" + mSearchFactor);
        mPresenter.queryData(mSearchFactor);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_main_loading_failure:
                switchStatus(Status.STATUS_LOADING);
                search(false);
                break;
            case R.id.tv_reload_more: {
                mAdapter.reloadMore();
                break;
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Repository repository = mAdapter.getItem(position);
        Actions.openUrl(this.getActivity(), repository.getHtmlUrl());
    }

    @Override
    public boolean onItemLongClick(View view, final int position) {
        AlertDialog.Builder builder = DialogUtils.getAlertDialog(getActivity());
        builder.setItems(R.array.long_click_main_dialog_item, (dialog, which) -> {
            Repository item = mAdapter.getItem(position);
            switch (which) {
                case 0:
                    copyUrl(item.getHtmlUrl());
                    break;
                case 1:
                    collectProject(item);
                    break;
            }
            dialog.dismiss();
        }).show();
        return true;
    }

    private void copyUrl(String url) {
        CommonUtils.copy(getContext(), url);
        toast(R.string.tip_copy_success);
    }

    private void collectProject(Repository repo) {
        mPresenter.collectRepo(repo);
    }

    @Override
    public void requestMoreData() {
        search(false);
    }

    @Override
    public void showErrorOnQueryData(String tips) {
        toast(tips);
        stopRefresh();
        if (mAdapter.isLoadingMore()) {
            switchStatus(Status.STATUS_NORMAL);
            mAdapter.loadMoreFailure();
        } else {
            switchStatus(Status.STATUS_ERROR);
        }
    }

    @Override
    public void showQueryDataResult(RepoSearchResult result) {
        switchStatus(Status.STATUS_NORMAL);
        stopRefresh();
        mSearchFactor.page++;
        boolean hasMore = result.getRepositories().size() == mSearchFactor.limit;
        mAdapter.setHasMore(hasMore);
        mAdapter.setLanguageVisible(CommonConstants.GitHub.LANG_ALL.equals(mSearchFactor.language));
        if (mAdapter.isLoadingMore()) {
            mAdapter.loadMoreFinish(hasMore, result.getRepositories());
        } else {
            mAdapter.resetData(result.getRepositories());
        }
    }

    @Override
    public void showCollected() {
        toast(R.string.tip_collect_success);
    }

    @Override
    public void showCollectedFailure() {
        toast(R.string.error_collect_failure);
    }

    @Override
    public void setLoadingIndicator(boolean isActive) {

    }

    private void stopRefresh() {
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }
}
