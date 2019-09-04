package com.softgarden.baselibrary.base;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;


/**
 * Created by DELL on 2017/6/28.
 */

public abstract class BaseSectionAdapter<T extends SectionInterface, VH extends BaseRVHolder> extends BaseQuickAdapter<T, VH> {
    private int mSectionHeadResId;
    public static final int SECTION_HEADER_VIEW = 0x01011;

    public BaseSectionAdapter(@LayoutRes int mSectionHeadResId, @LayoutRes int mLayoutResId) {
        super(mLayoutResId);
        this.mSectionHeadResId = mSectionHeadResId;
        //gridLayoutManager 时 设置单行或单列
        this.setSpanSizeLookup(new SpanSizeLookup() {
                                   @Override
                                   public int getSpanSize(GridLayoutManager gridLayoutManager, int position1) {
                                       return BaseSectionAdapter.this.getDefItemViewType(position1) == SECTION_HEADER_VIEW ? gridLayoutManager.getSpanCount() : 1;
                                   }
                               }
        );
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

        switch (holder.getItemViewType()) {
            case SECTION_HEADER_VIEW:
                setFullSpan(holder);//StaggeredGridLayoutManager 时 设置单行或单列
                onBindHeader(holder, mData.get(holder.getLayoutPosition() - getHeaderLayoutCount()), position);
                break;
            default:
                super.onBindViewHolder(holder, position);
                break;
        }
    }


    @Override
    protected int getDefItemViewType(int position) {
        return mData.get(position).isHeader() ? SECTION_HEADER_VIEW : 0;
    }

    @Override
    protected VH onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SECTION_HEADER_VIEW)
            return createBaseViewHolder(getItemView(mSectionHeadResId, parent));

        return super.onCreateDefViewHolder(parent, viewType);
    }


    protected abstract void onBindHeader(BaseRVHolder holder, T data, int position);


    @Override
    public void setOnItemClick(View v, int position) {
        if (isLoadMoreEnable() && (position == getItemCount() - 1))
            return;//当开启更多的时候，就屏蔽掉底部的点击事件
        if(getData().isEmpty()) return;
        super.setOnItemClick(v, position);
    }
}
