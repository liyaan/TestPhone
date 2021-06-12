package com.liyaan.testphone.net;

import com.zzrv5.mylibrary.ZZRCallBack;
import com.zzrv5.mylibrary.ZZRHttp;
import ohos.hiviewdfx.HiLog;

import java.util.Map;
import java.util.logging.Logger;

public class HttpUtils {
    private Map<String, String> headerMap;
    private ZZRCallBack callBack;
    private String url;
    private Map<String, String> paramsMap;
    private String jsonStr;
    private int requestMethod;

    public HttpUtils(HttpBuilder httpBuilder) {
        this.headerMap = httpBuilder.headerMap;
        this.callBack = httpBuilder.callBack;
        this.url = httpBuilder.url;
        this.paramsMap = httpBuilder.paramsMap;
        this.jsonStr = httpBuilder.jsonStr;
        this.requestMethod = httpBuilder.requestMethod;
    }
    //发送请求
    public void sendResqyest(){
        HiLog.info(Consts.label,url+"  "+IsNullUtils.isNotEmpty(url));
        if(IsNullUtils.isNotEmpty(url)){
            throw new RuntimeException("url is empty");
        }
        if (callBack==null){
            throw new RuntimeException("callBack is null");
        }
        HiLog.info(Consts.label,"url="+url+"   "+requestMethod);
        switch (requestMethod){
            case Consts.GET:
                ZZRHttp.get(url,callBack);
                break;
            case Consts.GET_MAP:
                if (paramsMap==null){
                    throw new RuntimeException("map is null");
                }
                ZZRHttp.get(url,paramsMap,callBack);
                break;
            case Consts.GET_MAP_HEADER:
                if (paramsMap==null){
                    throw new RuntimeException("map is null");
                }
                ZZRHttp.get(url,paramsMap,headerMap,callBack);
                break;
            case Consts.POST:
                ZZRHttp.post(url,callBack);
                break;
            case Consts.POST_MAP:
                if (paramsMap==null){
                    throw new RuntimeException("map is null");
                }
                ZZRHttp.post(url,paramsMap,callBack);
                break;
            case Consts.POST_MAP_HEADER:
                if (paramsMap==null){
                    throw new RuntimeException("map is null");
                }
                ZZRHttp.post(url,paramsMap,headerMap,callBack);
                break;
            case Consts.POST_STR:
                if (!IsNullUtils.isNotEmpty(jsonStr)){
                    ZZRHttp.postJson(url,jsonStr,callBack);
                }else{
                    throw new RuntimeException("json is empty");
                }
                break;
            case Consts.POST_STR_HEADER:
                if (!IsNullUtils.isNotEmpty(jsonStr)){
                    ZZRHttp.postJson(url,jsonStr,headerMap,callBack);
                }else{
                    throw new RuntimeException("json is empty");
                }
                break;
            default:
                HiLog.info(Consts.label,"请传入请求方式");
                break;
        }
    }

    //get请求 没有header
    public void getNotHeader(){
        if(IsNullUtils.isNotEmpty(url)){
            throw new RuntimeException("url is empty");
        }
        if (callBack==null){
            throw new RuntimeException("callBack is null");
        }
        if (paramsMap!=null){
            ZZRHttp.get(url,paramsMap,callBack);
        }else{
            ZZRHttp.get(url,callBack);
        }
    }
    //get请求 有header
    public void getHaveHeader(){
        if(IsNullUtils.isNotEmpty(url)){
            throw new RuntimeException("url is empty");
        }
        if (callBack==null){
            throw new RuntimeException("callBack is null");
        }
        if (paramsMap!=null){
            ZZRHttp.get(url,paramsMap,headerMap,callBack);
        }else{
            ZZRHttp.get(url,headerMap,callBack);
        }
    }

    //post Map的请求 没有header
    public void postMapNotHeader(){
        if(IsNullUtils.isNotEmpty(url)){
            throw new RuntimeException("url is empty");
        }
        if (callBack==null){
            throw new RuntimeException("callBack is null");
        }
        if (paramsMap!=null){
            ZZRHttp.post(url,paramsMap,callBack);
        }else{
            ZZRHttp.post(url,callBack);
        }
    }
    //post Map的请求 有header
    public void postMapHaveHeader(){
        if(IsNullUtils.isNotEmpty(url)){
            throw new RuntimeException("url is empty");
        }
        if (callBack==null){
            throw new RuntimeException("callBack is null");
        }
        if (paramsMap!=null){
            ZZRHttp.post(url,paramsMap,headerMap,callBack);
        }else{
            throw new RuntimeException("map is empty");
        }
    }
    //post str的请求 没有header
    public void postStrNotHeader(){
        if(IsNullUtils.isNotEmpty(url)){
            throw new RuntimeException("url is empty");
        }
        if (callBack==null){
            throw new RuntimeException("callBack is null");
        }
        if (!IsNullUtils.isNotEmpty(jsonStr)){
            ZZRHttp.postJson(url,jsonStr,callBack);
        }else{
            throw new RuntimeException("json is empty");
        }
    }
    //post str的请求 有header
    public void postStrHaveHeader(){
        if(IsNullUtils.isNotEmpty(url)){
            throw new RuntimeException("url is empty");
        }
        if (callBack==null){
            throw new RuntimeException("callBack is null");
        }
        if (!IsNullUtils.isNotEmpty(jsonStr)){
            ZZRHttp.postJson(url,jsonStr,headerMap,callBack);
        }else{
            throw new RuntimeException("json is empty");
        }
    }
    public static class HttpBuilder{
        private  HttpUtils instance;
        private Map<String, String> headerMap;
        private Map<String, String> paramsMap;
        private ZZRCallBack callBack;
        private String url;
        private String jsonStr;
        private int requestMethod;
        public HttpUtils getInstance(){
            instance = new HttpUtils(this);
            return instance;
        }

        public HttpBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public HttpBuilder setHeaderMap(Map<String, String> headerMap) {
            //获取缓存header便利添加
            this.headerMap = headerMap;
            return this;
        }

        public HttpBuilder setParamsMap(Map<String, String> paramsMap) {
            this.paramsMap = paramsMap;
            return this;
        }

        public HttpBuilder setJsonStr(String jsonStr) {
            this.jsonStr = jsonStr;
            return this;
        }

        public HttpBuilder setRequestMethod(int requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }

        public HttpBuilder setCallBack(ZZRCallBack callBack) {
            this.callBack = callBack;
            return this;
        }

    }
}
