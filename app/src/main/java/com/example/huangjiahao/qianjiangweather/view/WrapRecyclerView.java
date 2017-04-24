package com.example.huangjiahao.qianjiangweather.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import com.example.huangjiahao.qianjiangweather.R;
/**
 * Created by JiaHao.H on 2017/4/24.
 */

public class WrapRecyclerView extends RecyclerView {

    private WrapAdapter mWrapAdapter;
    private boolean shouldAdjustSpanSize;

    // 临时头部View集合,用于存储没有设置Adapter之前添加的头部
    private ArrayList<View> mTmpHeaderView = new ArrayList<>();

    public ArrayList<View> getmTmpFooterView() {
        return mTmpFooterView;
    }

    // 临时尾部View集合,用于存储没有设置Adapter之前添加的尾部
    private ArrayList<View> mTmpFooterView = new ArrayList<>();


    private boolean isLoadingData; // 是否正在加载数据

    public void setIsLoadingDatah(boolean isLoadingData) {
        this.isLoadingData = isLoadFinish;
    }

    public void setIsLoadFinish(boolean isLoadFinish) {
        this.isLoadFinish = isLoadFinish;
    }

    private boolean isLoadFinish = false; //是否已经全部加载完毕
    private LoadDataListener mLoadDataListener;
    private LScrollListener srcollDownListener;
    private boolean loadMore = true;

    public WrapRecyclerView(Context context) {
        this(context, null);
    }

    public WrapRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public WrapRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initFooter(context);
    }

    private void initFooter(Context context) {
        View footer = LayoutInflater.from(context).inflate(R.layout.loadmore_footer_view, null);
        addFooterView(footer);
    }

    /**
     * 设置加载更多数据的监听
     *
     * @param listener
     */
    public void setLoadDataListener(LoadDataListener listener) {
        mLoadDataListener = listener;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof WrapAdapter) {
            mWrapAdapter = (WrapAdapter) adapter;
            super.setAdapter(adapter);
        } else {
            mWrapAdapter = new WrapAdapter(adapter);
            for (View view : mTmpHeaderView) {
                mWrapAdapter.addHeaderView(view);
            }
            if (mTmpHeaderView.size() > 0) {
                mTmpHeaderView.clear();
            }

            for (View view : mTmpFooterView) {
                mWrapAdapter.addFooterView(view);
            }
            if (mTmpFooterView.size() > 0) {
                mTmpFooterView.clear();
            }

            super.setAdapter(mWrapAdapter);
        }

        if (shouldAdjustSpanSize) {
            mWrapAdapter.adjustSpanSize(this);
        }

        getWrappedAdapter().registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();
    }

    /**
     * Retrieves the previously set wrap adapter or null if no adapter is set.
     *
     * @return The previously set adapter
     */
    @Override
    public WrapAdapter getAdapter() {
        return mWrapAdapter;
    }

    /**
     * Gets the real adapter
     *
     * @return T:
     * @version 1.0
     */
    public Adapter getWrappedAdapter() {
        if (mWrapAdapter == null) {
            throw new IllegalStateException("You must set a adapter before!");
        }
        return mWrapAdapter.getWrappedAdapter();
    }

    /**
     * Adds a header view
     *
     * @param view
     * @version 1.0
     */
    public void addHeaderView(View view) {
        if (null == view) {
            throw new IllegalArgumentException("the view to add must not be null!");
        } else if (mWrapAdapter == null) {
            mTmpHeaderView.add(view);
        } else {
            mWrapAdapter.addHeaderView(view);
        }
    }

    /**
     * Adds a footer view
     *
     * @param view
     * @version 1.0
     */
    public void addFooterView(View view) {
        if (null == view) {
            throw new IllegalArgumentException("the view to add must not be null!");
        } else if (mWrapAdapter == null) {
            mTmpFooterView.add(view);
        } else {
            mWrapAdapter.addFooterView(view);
        }
    }

    /**
     * Adds a footer view
     *
     * @param view
     * @param reverse
     */
    public void addFooterView(View view, boolean reverse) {
        if (null == view) {
            throw new IllegalArgumentException("the view to add must not be null!");
        } else if (mWrapAdapter == null) {
            mTmpFooterView.add(view);
        } else {
            mWrapAdapter.addFooterView(view, reverse);
        }
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if (layout instanceof GridLayoutManager || layout instanceof StaggeredGridLayoutManager) {
            this.shouldAdjustSpanSize = true;
        }
    }

    /**
     * gets the headers view
     *
     * @return List:
     * @version 1.0
     */
    public List<View> getHeadersView() {
        if (mWrapAdapter == null) {
            throw new IllegalStateException("You must set a adapter before!");
        }
        return mWrapAdapter.getHeadersView();
    }

    /**
     * gets the footers view
     *
     * @return List:
     * @version 1.0
     */
    public List<View> getFootersView() {
        if (mWrapAdapter == null) {
            throw new IllegalStateException("You must set a adapter before!");
        }
        return mWrapAdapter.getFootersView();
    }

    /**
     * Setting the visibility of the header views
     *
     * @param shouldShow
     * @version 1.0
     */
    public void setFooterVisibility(boolean shouldShow) {
        if (mWrapAdapter == null) {
            throw new IllegalStateException("You must set a adapter before!");
        }
        mWrapAdapter.setFooterVisibility(shouldShow);
    }

    /**
     * Setting the visibility of the footer views
     *
     * @param shouldShow
     * @version 1.0
     */
    public void setHeaderVisibility(boolean shouldShow) {
        if (mWrapAdapter == null) {
            throw new IllegalStateException("You must set a adapter before!");
        }
        mWrapAdapter.setHeaderVisibility(shouldShow);
    }

    /**
     * get the count of headers
     *
     * @return number of headers
     * @version 1.0
     */
    public int getHeadersCount() {
        if (mWrapAdapter == null) {
            throw new IllegalStateException("You must set a adapter before!");
        }
        return mWrapAdapter.getHeadersCount();
    }

    /**
     * get the count of footers
     *
     * @return the number of footers
     * @version 1.0
     */
    public int getFootersCount() {
        if (mWrapAdapter == null) {
            throw new IllegalStateException("You must set a adapter before!");
        }
        return mWrapAdapter.getFootersCount();
    }

    /**
     * 加载更多数据完成后调用，必须在UI线程中
     */
    public void loadMoreComplete() {
        loadMoreComplete(false);
    }

    public void loadMoreComplete(boolean isOver) {
        isLoadingData = false;
        isLoadFinish = isOver;

        if (mWrapAdapter.getFootersView().size() > 0) {
            ((LinearLayout) mWrapAdapter.getFootersView().get(mWrapAdapter.getFootersView().size() - 1)).setVisibility(GONE);
//            if (isOver) {
//                ((LinearLayout) mWrapAdapter.getFootersView().get(0)).getChildAt(0).setVisibility(GONE);
//                ((LinearLayout) mWrapAdapter.getFootersView().get(0)).getChildAt(1).setVisibility(VISIBLE);
//                ((LinearLayout) mWrapAdapter.getFootersView().get(0)).setVisibility(GONE);
//            } else {
//                ((LinearLayout) mWrapAdapter.getFootersView().get(0)).getChildAt(1).setVisibility(GONE);
//                ((LinearLayout) mWrapAdapter.getFootersView().get(0)).getChildAt(0).setVisibility(VISIBLE);
//                ((LinearLayout) mWrapAdapter.getFootersView().get(0)).setVisibility(GONE);
//            }
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        try {
            if (dy > 20) {
                if (srcollDownListener != null) {
                    srcollDownListener.onScrollDown();
                }
            }
        } catch (Exception e) {
            Log.e("onScrolled", e.getMessage());
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        // 当前不滚动，且不是正在刷新或加载数据
        if (isLoadFinish)
            return;
        if (state == RecyclerView.SCROLL_STATE_IDLE && mLoadDataListener != null &&
                !isLoadingData) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            // 获取最后一个正在显示的Item的位置
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager)
                        .getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(
                        into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
            }

            if (layoutManager.getChildCount() > 0
                    && lastVisibleItemPosition >= layoutManager.getItemCount() - 1) {
                if (loadMore) {
                    if (mWrapAdapter.getFootersView().size() > 0) {
                        ((View) mWrapAdapter.getFootersView().get(mWrapAdapter.getFootersView().size() - 1)).setVisibility(VISIBLE);
                    }
                    // 加载更多
                    isLoadingData = true;
                    mLoadDataListener.onLoadMore();
                } else {
                    ((View) mWrapAdapter.getFootersView().get(mWrapAdapter.getFootersView().size() - 1)).setVisibility(GONE);
                }
            }
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


    private final AdapterDataObserver mDataObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

//        @Override
//        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
//            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
//        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };

    public void setCannleLoadMore(boolean loadMore) {
        this.loadMore = loadMore;
    }

    /**
     * 刷新和加载更多数据的监听接口
     */
    public interface LoadDataListener {
        /**
         * 执行加载更多
         */
        void onLoadMore();

    }

    public void setScroLister(LScrollListener lister) {
        this.srcollDownListener = lister;
    }

    public interface LScrollListener {
        void onScrollDown();
    }

    //滑动距离 以及坐标
    private float donwY, upY;
    private boolean isDown = true;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDown) {
                    donwY = ev.getRawY();
                    isDown = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                upY = ev.getRawY();
                isDown = true;
//                if (donwY > upY) {
//                    EventHideTitle hie = new EventHideTitle();
//                    hie.isHide = true;
//                    EventBus.getDefault().post(hie);
//                } else if (upY > donwY) {
//                    EventHideTitle hie = new EventHideTitle();
//                    hie.isHide = false;
//                    EventBus.getDefault().post(hie);
//                }
                break;
        }
        return super.onTouchEvent(ev);
    }

}