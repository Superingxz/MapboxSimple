package com.softgarden.baselibrary.base;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.softgarden.baselibrary.utils.EmptyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持 单选 多选功能的Adapter
 */

public abstract class SelectedAdapter<T> extends BaseRVAdapter<T> {
    protected boolean selectMode = true;//总开关 开启选择功能
    private int defSelectIndex = 0;//默认单个选中状态
    private int curSelectIndex = 0;//当前单个选中状态
    protected boolean multiSelected;//是否多选 默认为单选
    private ArrayList<Integer> selectList = new ArrayList<>();//多选下标集合
    private boolean cancelSingleSeleted = false;//是否可以单选取消
    private ArrayList<Integer> selectNotList = new ArrayList<>();//不能选择的下标集合

    public SelectedAdapter(@LayoutRes int mLayoutResId) {
        super(mLayoutResId);

    }

    public void setCancelSingleSeleted(boolean cancelSingleSeleted) {
        this.cancelSingleSeleted = cancelSingleSeleted;
    }

    public ArrayList<Integer> getSelectNotList() {
        return selectNotList;
    }

    public void setSelectNotList(ArrayList<Integer> selectNotList) {
        this.selectNotList = selectNotList;
        notifyDataSetChanged();
    }

    public void addSelectNotPosition(int position) {
        if (!selectNotList.contains(position)) {
            this.selectNotList.add(position);
        }
    }

    /**
     * 设置开启模式
     *
     * @param isOpen 开启选择模式  默认单选
     */
    public void setSelectMode(boolean isOpen) {
        this.selectMode = isOpen;
        notifyDataSetChanged();
        setSelectMode(isOpen, defSelectIndex);
    }


    /**
     * @param isOpen
     * @param defaultIndex 单选的 默认选中下标
     */
    public void setSelectMode(boolean isOpen, int defaultIndex) {
        this.selectMode = isOpen;
        this.defSelectIndex = defaultIndex;
        this.curSelectIndex = this.defSelectIndex;
        notifyDataSetChanged();
    }

    /**
     * 设置开启模式
     *
     * @param isOpen        开启选择模式
     * @param multiSelected 是否多选
     */
    public void setSelectMode(boolean isOpen, boolean multiSelected) {
        this.selectMode = isOpen;
        this.multiSelected = multiSelected;
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getSelectedList() {
        return selectList;
    }

    /**
     * 默认的选择下标
     *
     * @return
     */
    public int getDefSelectedIndex() {
        return defSelectIndex;
    }

    public void setDefSelectedIndex(int index) {
        defSelectIndex = index;
        curSelectIndex = defSelectIndex;
        notifyDataSetChanged();
    }

    /**
     * 当前的选择下标
     *
     * @return
     */
    public int getSelectedIndex() {
        return curSelectIndex;
    }

    /**
     * 当前的选择下标
     *
     * @return
     */
    public T getSelectedItem() {
        int selectedIndex = getSelectedIndex();
        if (selectedIndex != -1) {
            return getItem(selectedIndex);
        }
        return null;
    }

    public ArrayList<T> getSelectedData(){
        ArrayList<Integer> selectedList = getSelectedList();
        ArrayList<T> selects = new ArrayList<>();
        List<T> datas = getData();
        if (EmptyUtil.isNotEmpty(selectedList)) {
            for (int i = 0; i < selectedList.size(); i++) {
                selects.add(datas.get(selectedList.get(i)));
            }
        }
        return selects;
    }

    public void setSelectedIndex(int index) {
        curSelectIndex = index;
        notifyDataSetChanged();
    }


    @Override
    public void setNewData(List<T> data) {
        curSelectIndex = defSelectIndex;
        selectList.clear();
        super.setNewData(data);
    }

    public void mutiSelectAdd(List<Integer> positions){
        if (EmptyUtil.isNotEmpty(positions)) {
            for (int i = 0; i < positions.size(); i++) {
                if (!this.selectList.contains(positions.get(i))) {
                    selectList.add(positions.get(i));
                }
            }
            notifyDataSetChanged();
        }
    }

    public void mutiSelectAdd(int position){
        if (!this.selectList.contains(position)) {
            selectList.add(position);
        }
        notifyDataSetChanged();
    }

    public void mutiSelectDel(int position){
        if (this.selectList.contains(position + getHeaderLayoutCount())) {
            selectList.remove(position + getHeaderLayoutCount());
        }
        notifyDataSetChanged();
    }

    public void toggleMutiSelect(int position){
        if (this.selectList.contains(position + getHeaderLayoutCount())) {
            selectList.remove(Integer.valueOf(position + getHeaderLayoutCount()));
        } else {
            selectList.add(position + getHeaderLayoutCount());
        }
        notifyDataSetChanged();
    }

    public void selectAll(){
        if (getItemCount() - getHeaderLayoutCount() != 0) {
            for (int i = 0; i < getItemCount() - getHeaderLayoutCount(); i++) {
                if (!selectList.contains(i + getHeaderLayoutCount())) {
                    selectList.add(i + getHeaderLayoutCount());
                }
            }
        }
        notifyDataSetChanged();
    }

    public void clearSelected() {
        curSelectIndex = defSelectIndex;
        selectList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void setOnItemClick(View v, int position) {
        //设置选择器
        if (selectMode) {
            if (multiSelected && !selectNotList.contains(position)) {
                if (selectList.contains(position)) {
                    selectList.remove((Integer) position);
                    if (onItemSelectListener != null) {
                        onItemSelectListener.onmultiSelectedCancel(v, position);
                    }
                } else {
                    selectList.add(position);
                    if (onItemSelectListener != null) {
                        onItemSelectListener.onmultiSelected(v, position);
                    }
                }
            } else {
                if (curSelectIndex > -1) notifyItemChanged(curSelectIndex);
                if (curSelectIndex == position && cancelSingleSeleted) {//同一位置，且开启取消选择
                    curSelectIndex = -1;
                } else {
                    curSelectIndex = position;
                }
                if (onItemSelectListener != null) {
                    onItemSelectListener.onSingleSelect(v, position);
                }
            }
            notifyItemChanged(position);
        }
    }

    @Override
    public void onBindViewHolder(BaseRVHolder holder, int position) {
        if (selectMode) {
            boolean isSelected = multiSelected ? selectList.contains(position) : curSelectIndex == position;//设置状态
            holder.itemView.setSelected(isSelected);
            if (onItemSelectListener != null) {
                onItemSelectListener.convertSelect(holder, getItem(position), isSelected);
            }
        }
        super.onBindViewHolder(holder, position);
    }

    public void setOnSelectClickListener(OnItemSelectListener onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }

    private OnItemSelectListener onItemSelectListener;

    public interface OnItemSelectListener<T>{
        void onSingleSelect(View v,int position);

        void convertSelect(BaseViewHolder helper, T item, boolean isSelected);

        void onmultiSelected(View v,int position);

        void onmultiSelectedCancel(View v,int position);
    }
}
