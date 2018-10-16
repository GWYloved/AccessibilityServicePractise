package com.example.administrator.myaccessbility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Service;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;


/**
 * AccessibilityService不是用户去开启的，无法使用startservice()
 */
public class AcbService extends AccessibilityService {
    private static String TAG = "AcbService";
    private boolean start = false;
    public AcbService() {
        Log.i(TAG, "AcbService: start");
    }

    /**
     * 配置工作一般在这边完成，可以在代码中配置，也可以在xml中配置
     */
    @Override
    protected void onServiceConnected() {
//        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
//        accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
//        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
//        accessibilityServiceInfo.notificationTimeout = 1000;
//        setServiceInfo(accessibilityServiceInfo);
//        AirButtonService.start = false;
        Log.i(TAG, "onServiceConnected: true");
    }

    /**
     * 响应用户操作的事件进行分析
     * @param event
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        String eventText = "";
        if (AirButtonService.start && !start){
            showToast("开始启动");
            startMyJiaoben(event);
            start = !start;
        }else if (!AirButtonService.start &&start){
            showToast("结束启动");
            endMyJiaoben(event);
            start = !start;
        }
//        Toast.makeText(AcbService.this,event.getPackageName()+"",Toast.LENGTH_LONG).show();
//        Log.i(TAG, "==============Start====================");
//        switch (eventType) {
//            //被点击
//            case AccessibilityEvent.TYPE_VIEW_CLICKED:
//                eventText = "TYPE_VIEW_CLICKED";
//                break;
//            //被聚焦
//            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
//                eventText = "TYPE_VIEW_FOCUSED";
//                break;
//            //被长按
//            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
//                eventText = "TYPE_VIEW_LONG_CLICKED";
//                break;
//            //被选中
//            case AccessibilityEvent.TYPE_VIEW_SELECTED:
//                eventText = "TYPE_VIEW_SELECTED";
//                break;
//            //文字改变
//            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
//                eventText = "TYPE_VIEW_TEXT_CHANGED";
//                break;
//            //窗口状态变更
//            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
//                eventText = "TYPE_WINDOW_STATE_CHANGED";
//                break;
//            //通知状态变更
//            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
//
//                eventText = "TYPE_NOTIFICATION_STATE_CHANGED";
//                break;
//
//            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
//                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_END";
//                break;
//            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
//                eventText = "TYPE_ANNOUNCEMENT";
//                break;
//            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
//                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_START";
//                break;
//            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
//                eventText = "TYPE_VIEW_HOVER_ENTER";
//                break;
//            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
//                eventText = "TYPE_VIEW_HOVER_EXIT";
//                break;
//            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
//                eventText = "TYPE_VIEW_SCROLLED";
//                break;
//            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
//                eventText = "TYPE_VIEW_TEXT_SELECTION_CHANGED";
//                break;
//            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
//                eventText = "TYPE_WINDOW_CONTENT_CHANGED";
//                break;
//        }
        eventText = eventText + ":" + eventType;
//        Log.i(TAG, eventText);
//        Log.i(TAG, "=============END=====================");
    }

    /**
     * 打断获取事件的过程
     */
    @Override
    public void onInterrupt() {

    }

    private void showToast(String msg){
        Toast.makeText(AcbService.this, msg,Toast.LENGTH_SHORT).show();
    }

    private void showToast(int i){
        showToast(i+"");
    }

    /**
     * 第一步：comment_cnt
     * @param event
     */
    private void startMyJiaoben(AccessibilityEvent event){
//        List<AccessibilityNodeInfo> comment_nodes =
//                event.getSource()
//                        .findAccessibilityNodeInfosByViewId("@id/comment_cnt");
//        showToast("1");
//        if (comment_nodes!=null && ! comment_nodes.isEmpty()){
//            showToast("2");
//            AccessibilityNodeInfo nodeInfo;
//            for (int i = 0; i < comment_nodes.size(); i++){
//                showToast("3");
//                nodeInfo = comment_nodes.get(i);
//                if (nodeInfo.getClassName().toString().contains("SyTextView")){
//                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                }
//            }
//        }
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        showToast(1);
        if (nodeInfo != null){
            handleComment_cnt(nodeInfo);
        }
    }

    private void handleComment_cnt(AccessibilityNodeInfo nodeInfo){
        String id = "com.youxiang.soyoungapp:id/comment_cnt";
        if (nodeInfo.getChildCount() == 0){
            List<AccessibilityNodeInfo> infos = nodeInfo.findAccessibilityNodeInfosByViewId(id);
            nodeInfo.recycle();
            for (AccessibilityNodeInfo info:infos){
                info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }else {
            for (int i = 0; i < nodeInfo.getChildCount(); i ++){
                if (nodeInfo.getChild(i) != null){
                    handleComment_cnt(nodeInfo);
                }
            }
        }
    }

    private void endMyJiaoben(AccessibilityEvent event){

    }
}
