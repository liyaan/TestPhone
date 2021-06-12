package com.liyaan.testphone.net;

import com.zzrv5.mylibrary.ZZRCallBack;
import com.zzrv5.mylibrary.ZZRResponse;

public  class ZZRCallBackImpl<T> extends ZZRCallBack<T> {

    @Override
    public T onParseResponse(ZZRResponse zzrResponse) {
        return null;
    }

    @Override
    public void onFailure(int i, String s) {
//http访问出错了，此部分内容在主线程中工作;
        //可以更新UI等操作,请不要执行阻塞操作。
    }

    @Override
    public void onResponse(T t) {
        //http访问成功，此部分内容在主线程中工作;
        //可以更新UI等操作，但请不要执行阻塞操作。
    }
}
