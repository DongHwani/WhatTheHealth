package com.wthealth.service.exinfo.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wthealth.common.GetProperties;
import com.wthealth.common.JsonStringParse;
import com.wthealth.common.Search;
import com.wthealth.common.URLConnection;
import com.wthealth.domain.Post;
import com.wthealth.domain.Weather;
import com.wthealth.service.exinfo.ExInfoDao;
import com.wthealth.service.exinfo.ExInfoService;


@Service("exInfoServiceImpl")
public class ExInfoServiceImpl implements ExInfoService {

	//Field
	@Autowired
	@Qualifier("exInfoDaoImpl")
	private ExInfoDao exInfoDao;
	public void setExInfoDao(ExInfoDao exInfoDao) {
		this.exInfoDao = exInfoDao;
	}
	
	//Constructor
	public ExInfoServiceImpl() {
		System.out.println(this.getClass());
	}
	
	@Override
	public void addExInfo(Post post) throws Exception {
		exInfoDao.addExInfo(post);
	}
	
	
	@Override
	public void updateExInfo(Post post) throws Exception {
		exInfoDao.updateExInfo(post);
	}
	
	@Override
	public Post getExInfo(int postNo) throws Exception {
		return exInfoDao.getExInfo(postNo);
	}

	@Override
	public Map<String, Object> listExInfo(Search search) throws Exception {
		
		List<Post> list = exInfoDao.listExInfo(search);
		
		int totalCount = exInfoDao.getTotalCount(search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}


	@Override
	public Map<String, Object> listWeatherRecom(String mainCityName) throws Exception  {

		//OpenWeatherMap API URL 변수값
		String url = "http://api.openweathermap.org/data/2.5/weather?"
                + "q="+mainCityName+"&appid=d61d8ae1c0822fa84c4234c5c5a5f290";
		
		JSONObject result = URLConnection.getJSON_PARAM
							 (URLConnection.HTTPMETHOD_GET, url, mainCityName, null);
		
		
		JsonStringParse parseObj = new JsonStringParse();
		JSONObject obj = parseObj.JSONArrayToJSONObject(result, "weather", 0);
		JSONObject jsonTemperature = (JSONObject) result.get("main");
		

		//properties get value
		GetProperties pro = new GetProperties();
		//온도 값 추출 
	    double ktemp = Double.parseDouble(jsonTemperature.get("temp").toString());		
		
		//Weather 도메인 셋팅
		Weather weather = new Weather();	
				
		weather.setCityName(pro.getValue(GetProperties.weatherFileName, parseObj.JsonToString(result, "name")));
        weather.setCurrentWeather(pro.getValue(GetProperties.weatherFileName, ((String) obj.get("id").toString())));
        weather.setTemperature(Double.parseDouble(String.format("%.2f\n", ktemp-273.15)));
        
	    
        List<Post> weatherRecom = exInfoDao.listWeatherRecom(weather);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("weather", weather);
        map.put("weatherRecom", weatherRecom);
        
		return map;
	}

	@Override
	public void deleteStatus(String postNo) throws Exception {
		exInfoDao.deleteStatus(postNo);
		
	}
	
   @Override
   public Map<String, Object> listExInfoRecom(Search search) throws Exception {
      List<Post> exInfoList = exInfoDao.listExInfoRecom(search);
      int totalCount = exInfoDao.getTotalCount(search);
      
      Map<String,Object> map = new HashMap<String,Object>();
      map.put("exInfoList", exInfoList);
      map.put("totalCount", new Integer(totalCount));
      return map;
   }
	   

}
