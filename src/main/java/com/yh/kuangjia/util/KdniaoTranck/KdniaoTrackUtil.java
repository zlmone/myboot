package com.yh.kuangjia.util.KdniaoTranck;

//package com.kuaiban.benyuan.utils.KdniaoTranck;
//import com.kuaiban.benyuan.base.Result;
//import com.kuaiban.benyuan.models.Enums.DictTypeEnmu;
//import com.kuaiban.benyuan.models.KdNiao.KdniaoContext;
//import com.kuaiban.benyuan.models.KdNiao.KdniaoList;
//import com.kuaiban.benyuan.models.SysDictionary.SysDictionaryList;
//import com.kuaiban.benyuan.services.SysDictionaryService;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.security.MessageDigest;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//@Service("KdniaoTrackUtil")
//public class KdniaoTrackUtil {
//
//    @Autowired
//    SysDictionaryService sysDictionaryService;
//    //电商ID
//    private String EBusinessID="1434463";
//    //电商加密私钥，快递鸟提供，注意保管，不要泄漏
//    private String AppKey="fcfec467-6c1e-4f84-9adf-65b7149c46a4";
//    //请求url
//    private String ReqURL="http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";
//
//    /**
//     * Json方式 查询订单物流轨迹
//     * @throws Exception
//     */
//    public String getOrderTracesByJson(String expCode, String expNo) throws Exception{
//        String requestData= "{'OrderCode':'','ShipperCode':'" + expCode + "','LogisticCode':'" + expNo + "'}";
//
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
//        params.put("EBusinessID", EBusinessID);
//        params.put("RequestType", "1002");
//        String dataSign=encrypt(requestData, AppKey, "UTF-8");
//        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
//        params.put("DataType", "2");
//
//        String result=sendPost(ReqURL, params);
//
//        //根据公司业务处理返回的信息......
//
//        return result;
//    }
//
//    /**
//     * MD5加密
//     * @param str 内容
//     * @param charset 编码方式
//     * @throws Exception
//     */
//    @SuppressWarnings("unused")
//    private String MD5(String str, String charset) throws Exception {
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        md.update(str.getBytes(charset));
//        byte[] result = md.digest();
//        StringBuffer sb = new StringBuffer(32);
//        for (int i = 0; i < result.length; i++) {
//            int val = result[i] & 0xff;
//            if (val <= 0xf) {
//                sb.append("0");
//            }
//            sb.append(Integer.toHexString(val));
//        }
//        return sb.toString().toLowerCase();
//    }
//
//    /**
//     * base64编码
//     * @param str 内容
//     * @param charset 编码方式
//     * @throws UnsupportedEncodingException
//     */
//    private String base64(String str, String charset) throws UnsupportedEncodingException{
//        String encoded = base64Encode(str.getBytes(charset));
//        return encoded;
//    }
//
//    @SuppressWarnings("unused")
//    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException{
//        String result = URLEncoder.encode(str, charset);
//        return result;
//    }
//
//    /**
//     * 电商Sign签名生成
//     * @param content 内容
//     * @param keyValue Appkey
//     * @param charset 编码方式
//     * @throws UnsupportedEncodingException ,Exception
//     * @return DataSign签名
//     */
//    @SuppressWarnings("unused")
//    private String encrypt (String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception
//    {
//        if (keyValue != null)
//        {
//            return base64(MD5(content + keyValue, charset), charset);
//        }
//        return base64(MD5(content, charset), charset);
//    }
//
//    /**
//     * 向指定 URL 发送POST方法的请求
//     * @param url 发送请求的 URL
//     * @param params 请求的参数集合
//     * @return 远程资源的响应结果
//     */
//    @SuppressWarnings("unused")
//    private String sendPost(String url, Map<String, String> params) {
//        OutputStreamWriter out = null;
//        BufferedReader in = null;
//        StringBuilder result = new StringBuilder();
//        try {
//            URL realUrl = new URL(url);
//            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
//            // 发送POST请求必须设置如下两行
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            // POST方法
//            conn.setRequestMethod("POST");
//            // 设置通用的请求属性
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.connect();
//            // 获取URLConnection对象对应的输出流
//            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
//            // 发送请求参数
//            if (params != null) {
//                StringBuilder param = new StringBuilder();
//                for (Map.Entry<String, String> entry : params.entrySet()) {
//                    if(param.length()>0){
//                        param.append("&");
//                    }
//                    param.append(entry.getKey());
//                    param.append("=");
//                    param.append(entry.getValue());
//                    //System.out.println(entry.getKey()+":"+entry.getValue());
//                }
//                //System.out.println("param:"+param.toString());
//                out.write(param.toString());
//            }
//            // flush输出流的缓冲
//            out.flush();
//            // 定义BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result.append(line);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //使用finally块来关闭输出流、输入流
//        finally{
//            try{
//                if(out!=null){
//                    out.close();
//                }
//                if(in!=null){
//                    in.close();
//                }
//            }
//            catch(IOException ex){
//                ex.printStackTrace();
//            }
//        }
//        return result.toString();
//    }
//
//
//    private static char[] base64EncodeChars = new char[] {
//            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
//            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
//            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
//            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
//            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
//            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
//            'w', 'x', 'y', 'z', '0', '1', '2', '3',
//            '4', '5', '6', '7', '8', '9', '+', '/' };
//
//    public static String base64Encode(byte[] data) {
//        StringBuffer sb = new StringBuffer();
//        int len = data.length;
//        int i = 0;
//        int b1, b2, b3;
//        while (i < len) {
//            b1 = data[i++] & 0xff;
//            if (i == len)
//            {
//                sb.append(base64EncodeChars[b1 >>> 2]);
//                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
//                sb.append("==");
//                break;
//            }
//            b2 = data[i++] & 0xff;
//            if (i == len)
//            {
//                sb.append(base64EncodeChars[b1 >>> 2]);
//                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
//                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
//                sb.append("=");
//                break;
//            }
//            b3 = data[i++] & 0xff;
//            sb.append(base64EncodeChars[b1 >>> 2]);
//            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
//            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
//            sb.append(base64EncodeChars[b3 & 0x3f]);
//        }
//        return sb.toString();
//    }
//
//    public Result getOrderTraces(String expCode, String expNo) {
//        KdniaoTrackUtil api = new KdniaoTrackUtil();
//        KdniaoContext kdniaoContext = new KdniaoContext();
//        try {
//            String result = api.getOrderTracesByJson(expCode, expNo);
//            JSONObject jsonObject = JSONObject.fromObject(result);
//            String ShipperCode = jsonObject.getString("ShipperCode");
//            String LogisticCode = jsonObject.getString("LogisticCode");
//            String state = jsonObject.getString("State");
//            String success = jsonObject.getString("Success");
//            kdniaoContext.setState(state);
//            kdniaoContext.setSuccess(success);
//            JSONArray Traces = jsonObject.getJSONArray("Traces");
//            List<SysDictionaryList> sysDictionaryLists = sysDictionaryService.GetList(DictTypeEnmu.快递公司.getCode());
//            sysDictionaryLists.forEach(o ->{
//                if (o.getDict_code().equals(ShipperCode)) {
//                    kdniaoContext.setShippername(o.getDict_name());
//                }
//            });
//            kdniaoContext.setShipperCode(ShipperCode);
//            kdniaoContext.setLogisticCode(LogisticCode);
//            List<KdniaoList> list = new ArrayList<>();
//            for(int i = 0; i < Traces.size(); i++) {
//                KdniaoList kdniaoList = new KdniaoList();
//                JSONObject object = (JSONObject) Traces.get(i);
//                String AcceptTime = object.getString("AcceptTime");
//                String AcceptStation = object.getString("AcceptStation");
//                kdniaoList.setDatetime(AcceptTime);
//                kdniaoList.setContext(AcceptStation);
//                list.add(kdniaoList);
//            }
//            kdniaoContext.setList(list);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return Result.success(kdniaoContext);
//    }
//}