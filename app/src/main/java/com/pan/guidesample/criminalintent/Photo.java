package com.pan.guidesample.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * File Name:Photo
 * Author:Pan
 * Date:2016/2/12 15:59
 * Description:
 */
public class Photo {

    private static final String JSON_FILENAME="filename";

    private String mFileName;

    public Photo(String fileName){
        mFileName = fileName;
    }

    public Photo(JSONObject json) throws JSONException{
        mFileName = json.getString(JSON_FILENAME);
    }

    /**
     * 字符串转JSONObject对象
     * @return JSONObject
     * @throws JSONException
     */
    public JSONObject toJSON()throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_FILENAME,mFileName);
        return json;
    }

    public String getFileName(){
        return mFileName;
    }
}
