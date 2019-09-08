package net.away0x.italker.smartbutler;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import net.away0x.italker.smartbutler.fragment.ButlerFragment;
import net.away0x.italker.smartbutler.fragment.GirlFragment;
import net.away0x.italker.smartbutler.fragment.UserFragment;
import net.away0x.italker.smartbutler.fragment.WechatFragment;
import net.away0x.italker.smartbutler.ui.BaseActivity;
import net.away0x.italker.smartbutler.ui.SettingActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mFloatingButton;

    private List<String> mTitle;
    private List<Fragment> mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 去掉阴影
        getSupportActionBar().setElevation(0);

        initData();
        initView();
    }

    // 初始化数据
    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add("服务管家");
        mTitle.add("微信精选");
        mTitle.add("美女社区");
        mTitle.add("个人中心");

        mFragment = new ArrayList<>();
        mFragment.add(new ButlerFragment());
        mFragment.add(new WechatFragment());
        mFragment.add(new GirlFragment());
        mFragment.add(new UserFragment());
    }

    // 初始化 view
    @SuppressLint("RestrictedApi")
    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mVuewPager);
        mFloatingButton = (FloatingActionButton) findViewById(R.id.fab_setting);
        mFloatingButton.setOnClickListener(this);
        // 默认隐藏
        mFloatingButton.setVisibility(View.GONE);


        // view pager 预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        // view pager 适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            // 选中的 item
            @Override
            public Fragment getItem(int i) {
                return mFragment.get(i);
            }
            // 返回 item 的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }
            // 设置标题

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        // view pager 的滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    mFloatingButton.setVisibility(View.GONE);
                } else {
                    mFloatingButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        // 绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}
