package com.nuwa.tchl.scrolltab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by happen on 2016/5/29.
 */
public class InfoFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private View mView;
    LayoutInflater mLayoutInflater;
    ViewPager info_viewpager;
    private CNKFixedPagerAdapter mPagerAdater;
    /**
     * 当前选择的分类
     */
    private int mCurClassIndex=0;
    /**
     * 选择的分类字体颜色
     */
    private int mColorSelected;
    /**
     * 非选择的分类字体颜色
     */
    private int mColorUnSelected;
    /**

    /**
     * 水平滚动的Tab容器
     */
    private HorizontalScrollView mScrollBar;
    /**
     * 分类导航的容器
     */
    private ViewGroup mClassContainer;
    int mScrollX = 0;
    private List<Fragment> fragments;
    private String[] titles=new String[]{"全部","氪TV","O2O","新硬件","Fun!!","企业服务","Fit&Health","在线教育","互联网金融","大公司","专栏","新产品"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mView==null){
            mView = inflater.inflate(R.layout.info_fragment_layout,container,false);
            mLayoutInflater = inflater;
            initViews();
            initValidata();
        } return mView;

    }

    private void initValidata() {
        //添加Tab标签
        addScrollView(titles);
        mScrollBar.post(new Runnable() {
            @Override
            public void run() {
                mScrollBar.scrollTo(mScrollX,0);
            }
        });
        fragments=new ArrayList<>();
        for(int i=0;i<12;i++){
            OneFragment oneFragment=new OneFragment();
            Bundle bundle=new Bundle();
            bundle.putString("extra",titles[i]);
            oneFragment.setArguments(bundle);
            fragments.add(oneFragment);
        }

        mPagerAdater=new CNKFixedPagerAdapter(getChildFragmentManager());
        mPagerAdater.setTitles(titles);
        mPagerAdater.setFragments(fragments);
        info_viewpager.setAdapter(mPagerAdater);
        info_viewpager.setOnPageChangeListener(this);
    }

    private void addScrollView(String[] titles) {
        final int count = titles.length;
        for (int i = 0; i < count; i++) {
            Log.e("tchl","onclick: i:"+i);
            final String title = titles[i];
            final View view = mLayoutInflater.inflate(R.layout.horizontal_item_layout, null);
            final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.horizontal_linearlayout_type);
            final ImageView img_type = (ImageView) view.findViewById(R.id.horizontal_img_type);
            final TextView type_name = (TextView) view.findViewById(R.id.horizontal_tv_type);
            type_name.setText(title);
            if (i == mCurClassIndex) {
                //已经选中
                type_name.setTextColor(getResources().getColor(R.color.color_selected));
                img_type.setImageResource(R.drawable.bottom_line_blue);
            } else {
                //未选中
                type_name.setTextColor(getResources().getColor(R.color.color_unselected));
                img_type.setImageResource(R.drawable.bottom_line_gray);
            }
           final int index=i;
            //点击顶部Tab标签，动态设置下面的ViewPager页面
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //首先设置当前的Item为正常状态
                    Log.e("tchl","onclick: first mCurClassIndex:"+mCurClassIndex);
                    View currentItem=mClassContainer.getChildAt(mCurClassIndex);
                    ((TextView)(currentItem.findViewById(R.id.horizontal_tv_type))).setTextColor(getResources().getColor(R.color.color_unselected));
                    ((ImageView)(currentItem.findViewById(R.id.horizontal_img_type))).setImageResource(R.drawable.bottom_line_gray);
                    mCurClassIndex=index;
                    Log.e("tchl","onclick: first index:"+index);
                    //设置点击状态
                    img_type.setImageResource(R.drawable.bottom_line_blue);
                    type_name.setTextColor(getResources().getColor(R.color.color_selected));
                    //跳转到指定的ViewPager
                    info_viewpager.setCurrentItem(mCurClassIndex);
                }
            });

            mClassContainer.addView(view);
        }

    }

    /*
    * 初始化布局控件
    * */
    private void initViews() {

        info_viewpager=(ViewPager)mView.findViewById(R.id.info_viewpager);
        mScrollBar=(HorizontalScrollView)mView.findViewById(R.id.horizontal_info);
        mClassContainer=(ViewGroup)mView.findViewById(R.id.linearlayout_container);
    }

    @Override
    public void onPageSelected(int position) {
        //首先设置当前的Item为正常状态
        Log.e("tchl","onPageSelected:"+position);
        View preView=mClassContainer.getChildAt(mCurClassIndex);
        ((TextView)(preView.findViewById(R.id.horizontal_tv_type))).setTextColor(getResources().getColor(R.color.color_unselected));
        ((ImageView)(preView.findViewById(R.id.horizontal_img_type))).setImageResource(R.drawable.bottom_line_gray);
        mCurClassIndex=position;
        //设置当前为选中状态
        View currentItem=mClassContainer.getChildAt(mCurClassIndex);
        ((ImageView)(currentItem.findViewById(R.id.horizontal_img_type))).setImageResource(R.drawable.bottom_line_blue);
        ((TextView)(currentItem.findViewById(R.id.horizontal_tv_type))).setTextColor(getResources().getColor(R.color.color_selected));
        //这边移动的距离 是经过计算粗略得出来的
        mScrollX=currentItem.getLeft()-300;
        Log.d("zttjiangqq", "mScrollX:" + mScrollX);
        mScrollBar.post(new Runnable() {
            @Override
            public void run() {
                mScrollBar.scrollTo(mScrollX,0);
            }
        });
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //下面三个回调方法 分别是在ViewPager进行滑动的时候调用
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
}
