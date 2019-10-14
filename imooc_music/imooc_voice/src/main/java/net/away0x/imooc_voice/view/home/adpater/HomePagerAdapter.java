package net.away0x.imooc_voice.view.home.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.away0x.imooc_voice.view.discory.DiscoryFragment;
import net.away0x.imooc_voice.view.friend.FriendFragment;
import net.away0x.imooc_voice.view.home.model.CHANNEL;
import net.away0x.imooc_voice.view.mine.MineFragment;

/**
 * 首页 ViewPger 的 Adpater
 */
public class HomePagerAdapter extends FragmentPagerAdapter {
    private CHANNEL[] mList;

    public HomePagerAdapter(FragmentManager fm, CHANNEL[] datas) {
        super(fm);
        mList = datas;
    }

    // 初始化对应的 fragment
    @Override
    public Fragment getItem(int position) {
        int type = mList[position].getValue();
        switch (type) {
            case CHANNEL.MINE_ID:
                return MineFragment.newInstance();
            case CHANNEL.DISCORY_ID:
                return DiscoryFragment.newInstance();
            case CHANNEL.FRIEND_ID:
                return FriendFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.length;
    }
}

