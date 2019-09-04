package com.ruanjie.camp.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.camp.R;
import com.ruanjie.camp.base.AppBaseToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Moligy
 * @date 2019/7/5.
 */
public class MapboxMainActivity extends AppBaseToolbarActivity {
    @BindView(R.id.tv_map_simple)
    AppCompatTextView tvMapSimple;
    @BindView(R.id.tv_map_marker)
    AppCompatTextView tvMapMarker;
    @BindView(R.id.tv_map_polyline)
    AppCompatTextView tvMapPolyline;

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle("Mapbox Main");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mapbox_main;
    }

    @Override
    protected void prepare() {
        super.prepare();
        initData();
    }

    @Override
    protected void initialize() {
        super.initialize();
        initView();
        loadData();
    }

    private void initData() {

    }

    private void initView() {

    }

    private void loadData() {

    }

    @OnClick({R.id.tv_map_simple, R.id.tv_map_marker, R.id.tv_map_polyline})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_map_simple:
                MapboxSimpleActivity.start(this);
                break;
            case R.id.tv_map_marker:

                break;
            case R.id.tv_map_polyline:

                break;
        }
    }
}
