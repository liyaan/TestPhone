package com.liyaan.testphone.net;

import com.zzrv5.mylibrary.ZZRCallBack;
import com.zzrv5.mylibrary.ZZRHttp;
import com.zzrv5.mylibrary.ZZRResponse;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.Image;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;

public class ImageNetWork {
    private final static HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0, "HmOSImageLoader");
    private  AbilitySlice abilitySlice;
    private  Image image;
    private  String url;

    public ImageNetWork(AbilitySlice abilitySlice, Image image, String url) {
        this.abilitySlice = abilitySlice;
        this.image = image;
        this.url = url;
    }

    public  void start() {
        ZZRHttp.get(url, new ZZRCallBack.CallBackString() {
            @Override
            public void onFailure(int code, String errorMessage) {
                //http访问出错了，此部分内容在主线程中工作;
                //可以更新UI等操作,请不要执行阻塞操作。
                System.out.println("errorMessage"+errorMessage);
            }
            @Override
            public String onParseResponse(ZZRResponse response) {
                //创建图片源
                ImageSource imageSource = ImageSource.create(response.inputStream, null);
                //根据图片源创建位图
                PixelMap pixelMap = imageSource.createPixelmap(null);
                //需要异步渲染UI
                abilitySlice.getUITaskDispatcher().asyncDispatch(new Runnable() {
                    @Override
                    public void run() {
                        //展示到组件上
                        System.out.println("挂载了");
                        image.setPixelMap(pixelMap);
                        pixelMap.release();

                    }
                });
                return super.onParseResponse(response);
            }
            @Override
            public void onResponse(String response) {

            }
        });
    }
}
