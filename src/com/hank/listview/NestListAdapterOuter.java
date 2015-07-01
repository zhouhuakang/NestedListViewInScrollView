package com.hank.listview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.mush.listview.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 外层ListView的适配器
 * 
 * @author zhouhuakang
 *
 */
public class NestListAdapterOuter extends BaseAdapter {
    private Context mContext;
    private static final String tag = "HolderAdapter";
    /**
     * ListView 中视图的种类个数 必须准确指定这个值，并覆盖超类的getViewTypeCount()和getItemViewType（）方法 否则不能正常加载不同的View
     */
    private final int MEX_ITEM_TYPE = 4;
    /**
     * List中的数据
     */
    private List<ItemBean> data;
    /**
     * 嵌套的ListView的适配器
     */
    private NestListAdaperInsider twoItemAdapter;
    /**
     * layout ID
     */
    private LayoutInflater mInflater;

    public NestListAdapterOuter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.data = new ArrayList<ItemBean>();
    }

    /*
     * （非 Javadoc）
     * 
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.data.size();
    }

    /*
     * （非 Javadoc）
     * 
     * @see android.widget.BaseAdapter#getItemViewType(int)
     */
    @Override
    public int getItemViewType(int position) {
        int type = super.getItemViewType(position);
        ItemBean item = this.data.get(position);
        if (item != null) {
            type = item.getItemType();
        }
        return type;
    }

    @Override
    public ItemBean getItem(int position) {
        // TODO Auto-generated method stub
        return this.data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * （非 Javadoc）
     * 
     * @see android.widget.BaseAdapter#getViewTypeCount()
     */
    @Override
    public int getViewTypeCount() {
        return MEX_ITEM_TYPE;
    }

    /**
     * 设置ListView 中的数据
     * 
     * @param items
     * @param titles
     * @param blockKeys
     */
    public void setDataSource(List<ItemBean> data) {
        Log.d("setDataSource", "Size: " + data.size());
        this.data = data;
    }

    /**
     * 清空当List中的数据
     */
    public void cleanAll() {
        this.data.clear();
    }

    /*
     * （非 Javadoc）
     * 
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parentView) {
        Log.d(tag + "getView", "position:" + position);
        ViewHolder holder = null;
        ItemBean data = this.data.get(position);
        int type = data.getItemType();

        if (convertView == null) {
            switch (type) {
                case ItemBean.ITEM_TYPE_0: {
                }
                case ItemBean.ITEM_TYPE_2: {
                }
                case ItemBean.ITEM_TYPE_3: {
                    convertView = mInflater.inflate(R.layout.nestlistview_item_list, null);
                    holder = new ViewHolder();

                    holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                    holder.lv_contain = (ListView) convertView.findViewById(R.id.lv_contain);
                    convertView.setTag(holder);
                    break;
                }
                case ItemBean.ITEM_TYPE_1: {
                    convertView = mInflater.inflate(R.layout.nestlistview_item_text, null);
                    holder = new ViewHolder();
                    holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                    holder.tv_msg = (TextView) convertView.findViewById(R.id.tv_message);
                    Log.d("txt", holder.tv_msg.toString());
                    convertView.setTag(holder);
                    break;
                }
            }
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 显示数据

        if (type != ItemBean.ITEM_TYPE_1) {
            twoItemAdapter = new NestListAdaperInsider(this.mContext, R.layout.nestlistview_item_twotxt);
            Map<String, String> map = data.getData();
            Set<Entry<String, String>> entrys = map.entrySet();
            for (Map.Entry<String, String> entry : entrys) {
                String[] temp = new String[2];
                temp[0] = entry.getKey();
                temp[1] = entry.getValue();
                twoItemAdapter.addItem(temp);
            }
            holder.lv_contain.setAdapter(twoItemAdapter);
            holder.lv_contain.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // TODO 自动生成的方法存根
                    String[] str = twoItemAdapter.getItem(position);
                    Builder builder = new AlertDialog.Builder(mContext);
                    Dialog mDialog = null;
                    builder.setTitle(str[0] != null ? str[0].toString() : "状态码");
                    builder.setMessage(str[1].toString());
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    mDialog = builder.create();
                    mDialog.show();
                }
            });
            setListViewHeightBasedOnChildren(holder.lv_contain);
        }
        switch (type) {
            case ItemBean.ITEM_TYPE_0: {
                holder.tv_title.setText("HTTPRequestHead");
                break;
            }
            case ItemBean.ITEM_TYPE_2: {
                holder.tv_title.setText("HTTPResponseHead");
                break;
            }
            case ItemBean.ITEM_TYPE_3: {
                Log.d("requestparam", "3");
                holder.tv_title.setText("HTTPRequestParam");
                break;
            }
            case ItemBean.ITEM_TYPE_1: {
                holder.tv_msg.setText(data.getMessage());
                holder.tv_title.setText("HTTPResponse");
                break;
            }
        }
        return convertView;
    }

    /**
     * 根据内嵌ListView高度调整外部ListView Item的高度
     * 
     * @param listView
     */
    private void setListViewHeightBasedOnChildren(ListView listView) {
        android.widget.ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    class ViewHolder implements Serializable {
        private static final long serialVersionUID = -4533547076190005167L;
        TextView tv_title;
        ListView lv_contain;
        TextView tv_msg;
    }

}
