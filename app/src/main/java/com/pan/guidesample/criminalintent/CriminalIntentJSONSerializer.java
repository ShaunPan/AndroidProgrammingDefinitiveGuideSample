package com.pan.guidesample.criminalintent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/*
 * File Name:CriminalIntentJSONSerializer
 * Author:Pan
 * Date:2016/1/17 15:35
 * Description:
 */
public class CriminalIntentJSONSerializer {

    private Context mContext;
    private String mFileName;

    public CriminalIntentJSONSerializer(Context mContext, String mFileName) {
        this.mContext = mContext;
        this.mFileName = mFileName;
    }

    /**
     * 保存记录数据
     *
     * @param crimes 数据记录
     * @throws IOException
     * @throws JSONException
     */
    public void saveCrimes(ArrayList<Crime> crimes) throws IOException, JSONException {
        JSONArray jsonArray = new JSONArray();
        //修复当数据只有一条时，执行删除操作后，没有进行文件存储的bug
        //书中的代码时有这个bug的
        if (crimes.size() != 0) {
            for (Crime crime : crimes) {
                jsonArray.put(crime.toJSON());
                writeDataToFile(jsonArray);
            }
        } else {
            writeDataToFile(jsonArray);//当数据全被删除时，存入空的json字符串
        }

    }

    /**
     * 将Json数据写入文件
     *
     * @param jsonArray Json数据
     * @throws IOException
     * @throws JSONException
     */
    private void writeDataToFile(JSONArray jsonArray) throws IOException, JSONException {
        Writer writer = null;
        try {
            OutputStream outputStream = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(outputStream);
            writer.write(jsonArray.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    /**
     * 加载数据
     *
     * @return 返回数据
     * @throws IOException
     * @throws JSONException
     */
    public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
        ArrayList<Crime> crimes = new ArrayList<>();
        BufferedReader reader = null;
        try {
            InputStream fileInputStream = mContext.openFileInput(mFileName);
            reader = new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < array.length(); i++) {
                crimes.add(new Crime(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                reader.close();
        }
        return crimes;
    }
}
