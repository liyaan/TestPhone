package com.liyaan.testphone.slice;

import com.liyaan.testphone.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.TextAlignment;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;
import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_PARENT;

public class TabScrollTabAbilitySlice extends AbilitySlice {
    private TabList tabList;
    private ScrollView scrollView;
    private TableLayout imageGrid = null;
    private TableLayout videoGrid = null;
    private DirectionalLayout audioList = null;
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_scroll_tab);
        initImageGrid();
        initVideoGrid();
        //列表项
         tabList = (TabList) findComponentById(ResourceTable.Id_tab_list);
        //内容项
        scrollView = (ScrollView) findComponentById(ResourceTable.Id_tab_content);
        initTabList();
        audioList = new DirectionalLayout(getContext());
        tabList.addTabSelectedListener(new TabList.TabSelectedListener() {
            @Override
            public void onSelected(TabList.Tab tab) {
                if (tab.getPosition()==0){
                    scrollView.addComponent(imageGrid);
                }else if (tab.getPosition()==1){
                    scrollView.addComponent(videoGrid);
                }else{
                    scrollView.addComponent(audioList);
                }
            }

            @Override
            public void onUnselected(TabList.Tab tab) {
                if (tab.getPosition()==0){
                    scrollView.removeComponent(imageGrid);
                }else if (tab.getPosition()==1){
                    scrollView.removeComponent(videoGrid);
                }else{
                    scrollView.removeComponent(audioList);
                }
            }

            @Override
            public void onReselected(TabList.Tab tab) {

            }
        });
    }

    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private void initTabList(){
        TabList.Tab tabOne =tabList.new Tab(getContext());
        tabOne.setText("Image");
        tabList.addTab(tabOne,true);
        scrollView.addComponent(imageGrid);
        TabList.Tab tabTwo =tabList.new Tab(getContext());
        tabTwo.setText("Video");
        tabList.addTab(tabTwo);
        TabList.Tab tabThrid =tabList.new Tab(getContext());
        tabThrid.setText("Audio");
        tabList.addTab(tabThrid);
    }
    private void initVideoGrid() {
        videoGrid = new TableLayout(this);
        videoGrid.setWidth(MATCH_PARENT);
        videoGrid.setHeight(MATCH_CONTENT);
        videoGrid.setColumnCount(1);
        videoGrid.setRowCount(2);
        for (int i = 1; i < 4; i++) {
            Text text = new Text(this);
            text.setWidth(MATCH_PARENT);
            text.setHeight(800);
            text.setTextAlignment(TextAlignment.CENTER);
            text.setText("第" + i + "块视频");
            ShapeElement shapeElement = new ShapeElement();
            shapeElement.setCornerRadius(20);
            shapeElement.setRgbColor(new RgbColor(192,0,255));
            text.setBackground(shapeElement);
            text.setPadding(10,10,10,10);
            text.setMarginsTopAndBottom(10,10);
            text.setMarginsLeftAndRight(20,20);
            text.setTextSize(50);
            videoGrid.addComponent(text);
        }
    }
    private void initImageGrid() {
        imageGrid = new TableLayout(this);
        imageGrid.setWidth(MATCH_PARENT);
        imageGrid.setHeight(MATCH_CONTENT);
        imageGrid.setColumnCount(2);
        imageGrid.setRowCount(2);
        for (int i = 1; i <= 10; i++) {
            Text text = new Text(this);
            text.setWidth(400);
            text.setHeight(400);
            text.setTextAlignment(TextAlignment.CENTER);
            text.setText("第" + i + "块图片");
            ShapeElement shapeElement = new ShapeElement();
            shapeElement.setCornerRadius(20);
            shapeElement.setRgbColor(new RgbColor(0,192,255));
            text.setBackground(shapeElement);
            text.setPadding(10,10,10,10);
            text.setMarginsTopAndBottom(10,10);
            text.setMarginsLeftAndRight(20,20);
            text.setTextSize(50);
            imageGrid.addComponent(text);
        }
    }
}
