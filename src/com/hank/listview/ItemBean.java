package com.hank.listview;

import java.util.Map;

/**
 * 列表中的Item
 * 
 * @author zhouhuakang
 *
 */
public class ItemBean {
    public static final int ITEM_TYPE_0 = 0;
    public static final int ITEM_TYPE_1 = 1;
    public static final int ITEM_TYPE_2 = 2;
    public static final int ITEM_TYPE_3 = 3;
    private int itemType;
    private Map<String, String> data;
    private String message;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
