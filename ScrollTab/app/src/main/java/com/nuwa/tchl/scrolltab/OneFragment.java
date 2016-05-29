package com.nuwa.tchl.scrolltab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by happen on 2016/5/29.
 */
public class OneFragment  extends Fragment {
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mView==null){
            mView=inflater.inflate(R.layout.one_frag_layout,container,false);
            TextView tv_content=(TextView)mView.findViewById(R.id.tv_content);
            Bundle bundle=getArguments();
            if(bundle!=null){
                tv_content.setText(bundle.getString("extra"));
            }

        }
        return mView;
    }
}
