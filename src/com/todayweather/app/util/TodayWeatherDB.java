package com.todayweather.app.util;

import java.util.ArrayList;
import java.util.List;

import com.todayweather.app.db.TodayWeatherOpenHelper;
import com.todayweather.app.moddle.City;
import com.todayweather.app.moddle.County;
import com.todayweather.app.moddle.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TodayWeatherDB {
public static final String DB_Name = "Today_weather";
public static final int VERSION =1;
private static TodayWeatherDB todayWeatherDB;
private SQLiteDatabase db;
 private TodayWeatherDB(Context context){
	 TodayWeatherOpenHelper dbhelper = new TodayWeatherOpenHelper(context, DB_Name, null, VERSION);
	 db = dbhelper.getWritableDatabase();
 }
 public synchronized static TodayWeatherDB getInstance(Context context){
	 if (todayWeatherDB == null) {
		 todayWeatherDB = new TodayWeatherDB(context);
		 }
	return todayWeatherDB;
	 
 }
 //保存省份
 public void saveProvice(Province province){
	 if(province !=null){
		  ContentValues values = new ContentValues();
		  values.put("province_name",province.getProvinceName());
		  values.put("province_code", province.getProvinceCode());
		  db.insert("province", null, values);
		 
	 }
	 
 }
 //查询全部身份
 public List<Province> loadProvinces(){
	  List<Province> list = new ArrayList<Province>();
	  Cursor cursor = db.query("province", null, null,null, null, null, null);
	  if(cursor.moveToFirst()){
		  do {
			Province province = new Province();
			province.setId(cursor.getInt(cursor.getColumnIndex("id")));
			province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
			province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
			list.add(province);
		} while (cursor.moveToNext());
		  
	  }
	 
	 
	return list;
	 
 }
 //保存城市
 public void saveCity(City city){
	 if(city !=null){
		  ContentValues values = new ContentValues();
		  values.put("city_name",city.getCityName());
		  values.put("city_code", city.getCityCode());
		  values.put("province_id", city.getProvinceId());
		  db.insert("city", null, values);
		 
	 }
 }
 //根据省份id 查询城市信息
 public List<City> loadCities(int provinceId){
	 List<City> list = new ArrayList<City>();
	 Cursor  cursor=  db.query("city", null, "province_id = ?", new String[]{String.valueOf(provinceId)}, null, null, null);
	 if(cursor.moveToFirst()){
		 do {
			City city = new City();
			city.setId(cursor.getInt(cursor.getColumnIndex("id")));
			city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
			city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
			city.setProvinceId(provinceId);
			list.add(city);
		} while (cursor.moveToNext());
	 }
	 
	return list;
	 
 }
 //保存county
 public void saveCounty(County county){
	 if(county!= null){
		 ContentValues values = new ContentValues();
		  values.put("county_name",county.getCountyName());
		  values.put("county_code", county.getCountyCode());
		  values.put("city_id", county.getCityId());
		  db.insert("county", null, values);
	 }
 }
 //通过城市id 查询所有的县
 public List<County> loadCounties(int cityId){
	  List<County> list = new ArrayList<County>();
	  Cursor cursor = db.query("county", null, "city_id = ?", new String[]{String.valueOf(cityId)}, null, null, null);
	  if(cursor.moveToFirst()){
		  do {
			County county = new County();
			county.setId(cursor.getInt(cursor.getColumnIndex("id")));
			county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
			county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
			county.setCityId(cityId);
			list.add(county);
		} while (cursor.moveToNext());
		  
	  }
	 
	return list;
	 
 }
}
