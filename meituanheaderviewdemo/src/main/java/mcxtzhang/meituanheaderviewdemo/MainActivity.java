package mcxtzhang.meituanheaderviewdemo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPagerGrid;
    private List<View> mViewPagerGridList;
    private List<HeaderViewBean> mDatas;
    private int moudlePagerWidth, moudlePagerHeight;
    private CirclePageIndicator home_indicator_moudle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPagerGrid = (ViewPager) findViewById(R.id.vp);
        home_indicator_moudle = (CirclePageIndicator) findViewById(R.id.home_indicator_moudle);
        home_indicator_moudle.setFillColor(0XFFFF7D3C);

        //FEATURE_20160216_1:添加仿美团Headerview begin
        initDatas();
        LayoutInflater inflater = LayoutInflater.from(this);
        //塞GridView至ViewPager中：
        final int pageSize = getResources().getInteger(R.integer.HomePageHeaderColumn) * 2;
        //一共的页数等于 总数/每页数量，并取整。
        int pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        //ViewPager viewpager = new ViewPager(this);
        mViewPagerGridList = new ArrayList<View>();
        for (int index = 0; index < pageCount; index++) {
            //每个页面都是inflate出一个新实例
            GridView grid = (GridView) inflater.inflate(R.layout.item_viewpager, mViewPagerGrid, false);
            grid.setAdapter(new GridViewAdapter(this, mDatas, index));
            mViewPagerGridList.add(grid);
            final int finalIndex = index;
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MainActivity.this, "test"+(position+ finalIndex *pageSize), Toast.LENGTH_SHORT).show();
                }
            });
        }
//给ViewPager设置Adapter
        //viewpager.setAdapter(new MyViewPagerAdapter(viewpagerList));
//将ViewPager作为HeaderView设置给ListView
/*        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics()));
        viewpager.setLayoutParams(params);
        addHeaderView(viewpager);*/
//FEATURE_20160216_1:添加仿美团Headerview end

        setMoudleSize();
        mViewPagerGrid.setAdapter(new MyViewPagerAdapter(mViewPagerGridList));
        home_indicator_moudle.setViewPager(mViewPagerGrid);
    }

    public void setMoudleSize() {

        moudlePagerWidth = getWindowManager().getDefaultDisplay().getWidth();
        if (getResources().getConfiguration().orientation == 1) {
            moudlePagerHeight = (int) (moudlePagerWidth * 0.25);
        } else {
            moudlePagerHeight = (int) (getWindowManager().getDefaultDisplay().getHeight() * 0.2);
        }

        ViewGroup.LayoutParams lp = mViewPagerGrid.getLayoutParams();
        lp.height = moudlePagerHeight * 2;
        lp.width = moudlePagerWidth;
        mViewPagerGrid.setLayoutParams(lp);
    }

    private void initDatas() {
        mDatas = new ArrayList<HeaderViewBean>();
        for (int i = 0; i < 3; i++) {
            mDatas.add(new HeaderViewBean("美食", R.drawable.ic_category_0));
            mDatas.add(new HeaderViewBean("电影", R.drawable.ic_category_1));
            mDatas.add(new HeaderViewBean("酒店", R.drawable.ic_category_2));
            mDatas.add(new HeaderViewBean("KTV", R.drawable.ic_category_3));
            mDatas.add(new HeaderViewBean("外卖", R.drawable.ic_category_4));
            mDatas.add(new HeaderViewBean("美女6", R.drawable.ic_category_5));
            mDatas.add(new HeaderViewBean("美女7", R.drawable.ic_category_6));
            mDatas.add(new HeaderViewBean("美女8", R.drawable.ic_category_7));
            mDatas.add(new HeaderViewBean("帅哥", R.drawable.ic_category_8));
            mDatas.add(new HeaderViewBean("帅哥2", R.drawable.ic_category_9));
            mDatas.add(new HeaderViewBean("帅哥3", R.drawable.ic_category_10));
            mDatas.add(new HeaderViewBean("帅哥4", R.drawable.ic_category_11));
            mDatas.add(new HeaderViewBean("帅哥5", R.drawable.ic_category_12));
            mDatas.add(new HeaderViewBean("帅哥6", R.drawable.ic_category_13));
            mDatas.add(new HeaderViewBean("帅哥7", R.drawable.ic_category_14));
            mDatas.add(new HeaderViewBean("帅哥8", R.drawable.ic_category_15));
            mDatas.add(new HeaderViewBean("帅哥9", R.drawable.ic_category_16));
        }
    }

}
