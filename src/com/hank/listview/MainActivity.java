package com.hank.listview;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mush.listview.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private NestListAdapterOuter mNestListAdapter;
    private ListView mListView1;
    private List<ItemBean> mLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initData();
        setListener();

    }

    private void findView() {
        mListView1 = (ListView) findViewById(R.id.listView1);
    }

    private void initData() {
        mListView1.setCacheColorHint(0);
        mNestListAdapter = new NestListAdapterOuter(this);
        // 制造假数据
        mLists = new ArrayList<ItemBean>();
        for (int j = 0; j < 5; j++) {
            Map<String, String> kv = new LinkedHashMap<String, String>();
            ItemBean item = new ItemBean();
            for (int i = 0; i < 5; i++) {
                kv.put("key" + i * i, "value" + j);
            }
            item.setData(kv);
            item.setItemType(ItemBean.ITEM_TYPE_0);
            mLists.add(item);
        }
        ItemBean item1 = new ItemBean();
        item1.setItemType(ItemBean.ITEM_TYPE_1);
        item1.setMessage("alkdjfklajsdflkajsdklfjaklsdjflaksjdflhgjgkhjhkghjgkhljhkgkhlkhgjghgklhgjasjdflka\nkhhkjhkjhkjhkjhkjhkjhkjhjkhjk");
        mLists.add(item1);

        mNestListAdapter.setDataSource(mLists);
        mListView1.setAdapter(mNestListAdapter);
        setListViewHeightBasedOnChildren(mListView1);
    }

    private void setListener() {
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * ListView嵌套在ScrollView中时，ListView只会现实1行 调用此方法可计算出全部的item的高度 使ListView正常显示
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
