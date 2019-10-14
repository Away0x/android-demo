package net.away0x.imooc_voice.view.mine;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.away0x.imooc_voice.R;
import net.away0x.imooc_voice.view.discory.DiscoryFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {

    private Context mContext;
    private TextView mTextView;


    public MineFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        DiscoryFragment fragment = new DiscoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discory, container, false);
        mTextView = rootView.findViewById(R.id.textView);
        mTextView.setText("我的");
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 发请求更新 UI
    }

}
