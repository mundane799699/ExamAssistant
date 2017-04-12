package com.mundane.examassistant.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mundane.examassistant.bean.ParameterizedTypeImpl;
import com.mundane.examassistant.bean.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装的是使用Gson解析json的方法
 * @author Administrator
 *
 */
public class GsonUtil {

	/**
	 * 把一个map变成json字符串
	 * @param map
	 * @return
	 */
	public static String parseMapToJson(Map<?, ?> map) {
		try {
			Gson gson = new Gson();
			return gson.toJson(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把一个json字符串变成对象
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> T parseJsonToBean(String json, Class<T> cls) {
		Gson gson = new Gson();
		T t = null;
		try {
			t = gson.fromJson(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	private static <T> Result<T> fromJsonObject(String str, Class<T> clazz) {
		Gson gson = new Gson();
		Type type = new ParameterizedTypeImpl(Result.class, new Class[]{clazz});
		return gson.fromJson(str, type);
	}

	private static <T> Result<List<T>> fromJsonArray(String str, Class<T> clazz) {
		Gson gson = new Gson();
		// 生成List<T> 中的 List<T>
		Type listType = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
		// 根据List<T>生成完整的Result<List<T>>
		Type type = new ParameterizedTypeImpl(Result.class, new Type[]{listType});
		return gson.fromJson(str, type);
	}

	/**
	 * 把json字符串变成map
	 * @param json
	 * @return
	 */
	public static HashMap<String, Object> parseJsonToMap(String json) {
		Gson gson = new Gson();
		Type type = new TypeToken<HashMap<String, Object>>() {}.getType();
		HashMap<String, Object> map = null;
		try {
			map = gson.fromJson(json, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 把json字符串变成集合
	 * params: new TypeToken<List<yourbean>>(){}.getType(),
	 * 
	 * @param json
	 * @param type  new TypeToken<List<yourbean>>(){}.getType()
	 * @return
	 */
	//	ArrayList<Category> categories = (ArrayList<Category>) DataLoader.getInstance().getListData(Url.Category,new TypeToken<List<Category>>(){}.getType());

	//	这里的问号不能改成T吗？
	public static List<?> parseJsonToList(String json, Type type) {
		Gson gson = new Gson();
		List<?> list = gson.fromJson(json, type);
		return list;
	}


	/**
	 * 
	 * 获取json串中某个字段的值，注意，只能获取同一层级的value
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getFieldValue(String json, String key) {
		if (TextUtils.isEmpty(json))
			return null;
		if (!json.contains(key))
			return "";
		JSONObject jsonObject = null;
		String value = null;
		try {
			jsonObject = new JSONObject(json);
			value = jsonObject.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return value;
	}

}
