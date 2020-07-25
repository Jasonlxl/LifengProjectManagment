package com.sd.lifeng.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.CollationKey;
import java.text.Collator;
import java.util.*;

public class JSONArraySortUtil {
    /**
     * 按照JSONArray中的对象的某个字段进行排序(采用fastJson)
     *
     * @param jsonArr,key
     *            json数组字符串
     *
     */
    public JSONArray jsonArraySort(JSONArray jsonArr, String key) {
        JSONArray sortedJsonArray = new JSONArray();
        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonArr.size(); i++) {
            jsonValues.add(jsonArr.getJSONObject(i));
        }

        Collections.sort(jsonValues, new Comparator<JSONObject>() {
            Collator collator = Collator.getInstance(Locale.CHINA);
            String KEY = key;
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                CollationKey key1 = collator.getCollationKey(o1.getString(KEY));
                CollationKey key2 = collator.getCollationKey(o2.getString(KEY));
                return key1.compareTo(key2);
            }
        });

        for (int i = 0; i < jsonArr.size(); i++) {
            jsonValues.get(i).put("id",i+1);
            sortedJsonArray.add(jsonValues.get(i));
        }
        return sortedJsonArray;
    }

    /*Collections.sort(jsonValues, new Comparator<JSONObject>() {
            // You can change "Name" with "ID" if you want to sort by ID
            String KEY_NAME = "part_name";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA = a.getString(KEY_NAME);
                String valB = b.getString(KEY_NAME);
*//*                try {
                    // 这里是a、b需要处理的业务，需要根据你的规则进行修改。
//                    String aStr = a.getString(KEY_NAME);
//                    valA = aStr.replaceAll("-", "");
//                    String bStr = b.getString(KEY_NAME);
//                    valB = bStr.replaceAll("-", "");
//                    valA = a.getString(KEY_NAME);
//                    valB = b.getString(KEY_NAME);
                } catch (JSONException e) {
                    // do something
                }*//*
                return -valA.compareTo(valB);
                // if you want to change the sort order, simply use the following:
                // return -valA.compareTo(valB);
            }
        });*/
}
