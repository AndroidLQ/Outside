package com.yunwang.utils;

import com.yunwang.model.SelectCommonModel;
import com.yunwang.model.SelectItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/3.
 */

public class AnaysisUtil {

    public static ArrayList<SelectCommonModel> getDataList(String json) {

        ArrayList<SelectCommonModel> data_List = null;
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray motorbike_ARRAY = obj.getJSONArray("motorbike");
            data_List = new ArrayList<>();
            SelectCommonModel selectCommonModel;
            for (int i = 0 ; i < motorbike_ARRAY.length() ; i++){
                JSONObject content_obj = motorbike_ARRAY.getJSONObject(i);
                selectCommonModel = new SelectCommonModel();
                selectCommonModel.setType(content_obj.getInt("type"));

                JSONArray data_array = content_obj.getJSONArray("data");
                ArrayList<SelectItemModel> selectItemModel_List = new ArrayList<>();
                SelectItemModel selectItemModel = null;
                for (int j = 0 ; j < data_array.length() ; j++){
                    selectItemModel = new SelectItemModel();
                    JSONObject data_obj = data_array.getJSONObject(j);
                    selectItemModel.setBool(data_obj.getBoolean("tag"));
                    selectItemModel.setTitle(data_obj.getString("title"));
                    selectItemModel_List.add(selectItemModel);
                }
                selectCommonModel.setData(selectItemModel_List);
                data_List.add(selectCommonModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(data_List == null){
            data_List = new ArrayList<>();
        }

        return data_List;


    }


}
