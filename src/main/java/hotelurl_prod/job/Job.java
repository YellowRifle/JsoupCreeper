package hotelurl_prod.job;

import hotelurl_prod.utils.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netscape.javascript.JSObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import sun.net.www.http.HttpClient;


import javax.swing.text.html.parser.Element;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @program: JsoupCreeper
 * @description:
 * @author: YellowRifle
 * @create: 2019-04-01 13:55
 */
public class Job {

    @Test
    public void t(){
        String body = new String();


        //创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建httpost
        HttpPost httpPost = new HttpPost("https://hotels.ctrip.com/Domestic/Tool/AjaxHotelList.aspx");
        //用NameValuePair类型数组存储请求参数
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        //创建存储请求头和参数的map
        Map<String,String> urlParam = new HashMap<String, String>();
        Map<String,String> postHeader = new HashMap<String, String>();
        //调用参数设置函数
        setUrlParam(urlParam);
        setHeaderParam(postHeader);

        for (Map.Entry<String, String> stringStringEntry : urlParam.entrySet()) {

            System.out.println(stringStringEntry.getKey());
            //设置参数
            formparams.add(new BasicNameValuePair(stringStringEntry.getKey(),stringStringEntry.getValue()));
        }
        for (Map.Entry<String, String> stringStringEntry : postHeader.entrySet()) {
            //设置请求头
            httpPost.setHeader(stringStringEntry.getKey(),stringStringEntry.getValue());
        }

        try {
            //调用setEntity方法将param注入httppost
            httpPost.setEntity(new UrlEncodedFormEntity(formparams, HTTP.UTF_8));

            System.out.println("请求参数："+formparams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            //执行post请求
            CloseableHttpResponse response = httpClient.execute(httpPost);
            //获取返回entity
            HttpEntity entity = response.getEntity();
            if (entity != null){
                System.out.println(entity);
                //将httpentity转换为string
              body = EntityUtils.toString(entity, HTTP.UTF_8);
            }
            //关闭HttpEntity流
            EntityUtils.consume(entity);
            //释放链接
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(body);

        JSONObject jsonObject = JSONObject.fromObject(body);
        JSONArray arrayHotelDeatil = jsonObject.getJSONArray("hotelPositionJSON");
        System.out.println(arrayHotelDeatil);
        JSONObject price = jsonObject.getJSONObject("HotelMaiDianData");
        Collection collection = price.values();
        String c = collection.toString();
        c = c.substring(33);
        c = c.substring(0,c.length()-1);
        JSONObject jsonObject1 = JSONObject.fromObject(c);
        //解析出price所在的json
        JSONArray arrayHotelPrice = jsonObject1.getJSONArray("htllist");

        System.out.println(arrayHotelPrice);
        /******这里已经获取到所有需要的信息，具体处理看自身需求*******/



    }

    /**
       * @Param: UrlMap
     * @return Map
     * 获取爬取url的集合
     */
    public void setUrlParam(Map<String,String> UrlMap){
        //通过对携程网页ajax Post参数分析,提取关键信息
        UrlMap.put("__VIEWSTATEGENERATOR","DB1FBB6D");
        UrlMap.put("cityName","%E6%88%90%E9%83%BD");
        UrlMap.put("StartTime", DateUtil.getCurrentDate());
        UrlMap.put("DepTime",DateUtil.getTomorrow());
        UrlMap.put("cityLat","30.663491162");
        UrlMap.put("cityLng","104.0723267245");
        UrlMap.put("ubt_price_key","htl_search_result_promotion");
        UrlMap.put("cityId","28");
        UrlMap.put("cityPY","chengdu");
        UrlMap.put("cityCode","028");
        UrlMap.put("page",""+2);
        UrlMap.put("IsOnlyAirHotel","F");
        UrlMap.put("operationtype","NEWHOTELORDER");
        UrlMap.put("RoomGuestCo","1,1,0");
                // __VIEWSTATEGENERATOR	DB1FBB6D
    }

    /**
      设置请求头
     * @return
     */
    public void setHeaderParam(Map<String, String> headMap){
        headMap.put("accept","*/*");
        headMap.put("accept-encoding","gzip, deflate, br");
        headMap.put("accept-language","zh-CN,zh;q=0.9,en;q=0.8");
        headMap.put("cache-control","max-age=0");
        headMap.put("content-type","application/x-www-form-urlencoded; charset=UTF-8");
        headMap.put("if-modified-since","Thu, 01 Jan 1970 00:00:00 GMT");
        headMap.put("origin","https://hotels.ctrip.com");
        headMap.put("referer","https://hotels.ctrip.com/domestic/hotel/chengdu28");
        headMap.put("Content-type","application/x-www-form-urlencoded");
        headMap.put("user-agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36");

    }
}


