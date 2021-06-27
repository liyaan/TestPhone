package com.liyaan.testphone.slice;

import com.liyaan.oneselfview.RoundImage;
import com.liyaan.testphone.ResourceTable;
import com.liyaan.testphone.net.Consts;
import com.liyaan.testphone.net.HttpUtils;
import com.liyaan.testphone.net.ImageNetWork;
import com.zzrv5.mylibrary.ZZRCallBack;
import com.zzrv5.mylibrary.ZZRResponse;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;

import java.util.Timer;
import java.util.TimerTask;


public class HomeAbilitySlice extends AbilitySlice {
    private Text text,it_value;
    private Image it_image;
    private RoundImage it_img_round;
    private Button btn;
    // 计时器对象
    private Timer timer;
    // 数秒计数
    private int count = 0;
    // 是否正在计时
    private boolean flag = true;
    private HttpUtils mHttpUtils;
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_home);
        text = (Text)findComponentById(ResourceTable.Id_time);
        it_image = (Image) findComponentById(ResourceTable.Id_it_image);
        it_img_round = (RoundImage) findComponentById(ResourceTable.Id_it_img_round);
        it_value = (Text)findComponentById(ResourceTable.Id_it_value);
        btn = (Button) findComponentById(ResourceTable.Id_btn_zanting);
        if(intent != null){
            String user = intent.getStringParam("user");
            it_value.append("," + user);
        }
        new ImageNetWork(this,it_image,"https://ost.aitifen.com/gsvipstu/banner/banner2.jpg")
                .start();
        it_img_round.setPixelMapAndCricle(ResourceTable.Media_man);
        mHttpUtils =  new HttpUtils.HttpBuilder().setRequestMethod(1)
                .setUrl("https://api.aitifen.com/gsvipStu/index/banner/list")
                .setCallBack(new ZZRCallBack.CallBackString() {
                    @Override
                    public void onFailure(int code, String errorMessage) {
                        HiLog.warn(Consts.label,errorMessage);
                    }

                    @Override
                    public void onResponse(String response) {
                        HiLog.info(Consts.label,response);
                    }
                }).getInstance();
        btn.setClickedListener(component -> {
            mHttpUtils.sendResqyest();
            if (flag == true) {
                // 当正在计时时，暂停计时
                timer.cancel();
                timer = null;
                getUITaskDispatcher().asyncDispatch(new Runnable() {
                    @Override
                    public void run() {

                        btn.setText("继续");
                    }
                });
                Intent it= new Intent();
                present(new TabScrollTabAbilitySlice(),it);
            } else {
                // 当暂停计时时，继续计时
                createTimer();
                getUITaskDispatcher().asyncDispatch(new Runnable() {
                    @Override
                    public void run() {
                        btn.setText("暂停");
                    }
                });
            }
            flag = !flag;
        });
        // 开始计时
        createTimer();
    }
    // 创建计时器对象
    private void createTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count ++;
                getUITaskDispatcher().asyncDispatch(new Runnable() {
                    @Override
                    public void run() {
                        text.setText("" + count);
                    }
                });
            }
        }, 0, 1000);
    }
    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
