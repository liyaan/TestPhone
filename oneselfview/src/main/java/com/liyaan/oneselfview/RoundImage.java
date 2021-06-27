package com.liyaan.oneselfview;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.render.Canvas;
import ohos.agp.render.PixelMapHolder;
import ohos.agp.utils.RectFloat;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.PixelFormat;
import ohos.media.image.common.Rect;
import ohos.media.image.common.Size;

import java.io.IOException;
import java.io.InputStream;

public class RoundImage extends Image {
    //log日志打印
    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.DEBUG, 0, "RoundImage");
    //像素图片持有者
    private PixelMapHolder mPixelMapHolder;
    //目标区域
    private RectFloat rectDst;
    //源目标
    private RectFloat rectRes;
    public RoundImage(Context context) {
        this(context,null,null);
    }

    public RoundImage(Context context, AttrSet attrSet) {
        this(context, attrSet,null);
    }

    public RoundImage(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        HiLog.error(LABEL,"RoundImage");
    }

    private void onRoundRectDraw(int radius){
        //添加绘制任务
        this.addDrawTask((view, canvas) -> {
            if (mPixelMapHolder==null){
                return;
            }
            synchronized (mPixelMapHolder){
                //给目标区域赋值，宽度和高度取自xml配置文件中的属性
                rectDst = new RectFloat(0,0,getWidth(),getHeight());
                //绘制圆角图片
                canvas.drawPixelMapHolderRoundRectShape(mPixelMapHolder,rectRes,rectDst,radius,radius);
                mPixelMapHolder = null;
            }
        });
    }
    /**
     * 对外调用的api，设置圆角图片方法
     * @param resId
     * @param radius
     */
    public void setPixelMapAndRoundRect(int resId,int radius){
        PixelMap pixelMap = getPixelMap(resId);
        putPixelMap(pixelMap);
        onRoundRectDraw(radius);
    }
    public void setPixelMapAndCricle(int resId){
        PixelMap pixelMap = getPixelMap(resId);
        putPixelMap(pixelMap);
        onCircleDraw();
    }
    //使用canvas绘制圆形
    private void onCircleDraw() {
        this.addDrawTask((view, canvas) -> {
            if (mPixelMapHolder==null){
                return;
            }
            synchronized (mPixelMapHolder){
                rectDst = new RectFloat(0,0,getWidth(),getHeight());
                canvas.drawPixelMapHolderRoundRectShape(mPixelMapHolder,rectRes,rectDst,getWidth()/2,getHeight()/2);
                mPixelMapHolder = null;
            }
        });
    }

    /**
     *获取原有Image中的位图资源后重新检验绘制该组件
     * @param pixelMap
     */
    private void putPixelMap(PixelMap pixelMap) {
        if (pixelMap!=null){
            rectRes = new RectFloat(0,0,pixelMap.getImageInfo().size.width,pixelMap.getImageInfo().size.height);
            mPixelMapHolder = new PixelMapHolder(pixelMap);
            invalidate();
        }else{
            mPixelMapHolder = null;
            setPixelMap(null);
        }
    }
    /**
     * 通过资源ID获取位图对象
     **/
    private PixelMap getPixelMap(int resId) {
        InputStream drawableInputStream = null;
        try {
            drawableInputStream = getResourceManager().getResource(resId);
            //实例化一个资源选项类
            ImageSource.SourceOptions sourceOptions = new ImageSource.SourceOptions();
            //选择解码png图片
            sourceOptions.formatHint = "image/png";
            //图片资源
            ImageSource imageSource = ImageSource.create(drawableInputStream,null);
            //实例化一个解码选项
            ImageSource.DecodingOptions decodingOptions = new ImageSource.DecodingOptions();
            decodingOptions.desiredSize = new Size(0,0);
            decodingOptions.desiredRegion = new Rect(0,0,0,0);
            decodingOptions.desiredPixelFormat = PixelFormat.ARGB_8888;
            PixelMap pixelMap = imageSource.createPixelmap(decodingOptions);
            return pixelMap;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (drawableInputStream!=null){
                try {
                    drawableInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
