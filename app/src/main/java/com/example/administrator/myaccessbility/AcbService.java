package com.example.administrator.myaccessbility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Path;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;


/**
 * AccessibilityService不是用户去开启的，无法使用startservice()
 */
public class AcbService extends AccessibilityService {
    private static String TAG = "AcbService";
    public boolean start = false;
    private Handler handler = new Handler();

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
     *
     * @param event
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        String eventText = "";
        if (AirButtonService.start && !start) {
            showToast("开始启动");
            handler.post(startRunnable);
            start = !start;
        } else if (!AirButtonService.start && start) {
            showToast("结束启动");
            endMyJiaoben();
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

    private void showToast(String msg) {
        Toast.makeText(AcbService.this, msg, Toast.LENGTH_SHORT).show();
        sleep500milles();
    }

    private void showToast(int i) {
        showToast(i + "");
    }

    private void LogE(String msg) {
        Log.e(TAG, "LogE: " + msg);
    }

    /**
     * 第一步：comment_cnt
     */
    private void startMyJiaoben() {
        LogE("正在获取窗口内容...");
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            LogE("获取窗口内容成功..正在解析");
            handleFirst(nodeInfo);
        } else {
            LogE("获取窗口内容失败");
        }
    }

    Runnable startRunnable = new Runnable() {
        @Override
        public void run() {
            startMyJiaoben();
        }
    };

    /**
     * 处理第一个页面的评论按钮。id是com.youxiang.soyoungapp:id/comment_cnt
     *
     * @param nodeInfo
     */
    private void handleFirst(AccessibilityNodeInfo nodeInfo) {
        String id = "com.youxiang.soyoungapp:id/comment_cnt";
        LogE("正在查找第一个评论按钮");
        List<AccessibilityNodeInfo> infos = nodeInfo.findAccessibilityNodeInfosByViewId(id);
        LogE("找到" + infos.size() + "个评论按钮");
        if (infos.size() > 0) {
            infos.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            threadSleep(2000);
            handleSecond();
        }
    }

    /**
     * 处理第二个页面的评论按钮：id是com.youxiang.soyoungapp:id/comment_ll
     */
    private void handleSecond() {
        String id = "com.youxiang.soyoungapp:id/comment_ll";
        LogE("正在查找第二个评论按钮");
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            LogE("获取成功");
            List<AccessibilityNodeInfo> infos = nodeInfo.findAccessibilityNodeInfosByViewId(id);
            LogE("找到" + infos.size() + "个第二个评论按钮");
            if (infos.size() > 0) {
                infos.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                threadSleep(2000);
                handleThird();
            }
        } else {
            LogE("获取失败");
        }
    }

    /**
     * 复制评论到评论区域
     */
    private void handleThird() {
        String id = "com.youxiang.soyoungapp:id/content";
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            LogE("获取成功");
            List<AccessibilityNodeInfo> infos = nodeInfo.findAccessibilityNodeInfosByViewId(id);
            LogE("找到" + infos.size() + "个评论区域");
            if (infos.size() > 0) {
                ClipboardManager clipboard = (ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", "漂亮的"+ new Random());
                clipboard.setPrimaryClip(clip);
                infos.get(0).performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                infos.get(0).performAction(AccessibilityNodeInfo.ACTION_PASTE);
                threadSleep(2000);
                handleFourth();
            }
        } else {
            LogE("获取失败");
        }
    }

    /**
     * 点击发表评论
     */
    private void handleFourth(){
        String id = "com.youxiang.soyoungapp:id/comfirm";
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            LogE("获取成功");
            List<AccessibilityNodeInfo> infos = nodeInfo.findAccessibilityNodeInfosByText("发表");
            LogE("找到" + infos.size() + "个发表按钮");
            if (infos.size() > 0) {
                infos.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
            threadSleep(2000);
            handleFifth();
        } else {
            LogE("获取失败");
        }
    }

    /**
     * 此时是发表评论结束了，需要点击返回
     */
    private void handleFifth(){
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        String id = "com.youxiang.soyoungapp:id/top_left";
        if (nodeInfo != null) {
            LogE("获取成功");
            List<AccessibilityNodeInfo> infos = nodeInfo.findAccessibilityNodeInfosByViewId(id);
            LogE("找到" + infos.size() + "个评论按钮");
            if (infos.size() > 0) {
                infos.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
            nodeInfo.recycle();
            threadSleep(2000);
            handleSixth();
        } else {
            LogE("获取失败");
        }
    }

    /**
     * 需要进行滑动了，滑动的距离是900个像素
     */
    private void handleSixth(){
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        String id = "com.youxiang.soyoungapp:id/img_ll";
        LogE("正在查找第一个评论按钮");
        List<AccessibilityNodeInfo> infos = nodeInfo.findAccessibilityNodeInfosByViewId(id);
        LogE("找到" + infos.size() + "个评论按钮");
        if (infos.size() > 0) {
            AccessibilityNodeInfo nodeInfo1 = infos.get(0);
            while (nodeInfo1!= null){
                if (nodeInfo1.isScrollable()){
                    nodeInfo1.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                    return;
                }
                nodeInfo1 = nodeInfo1.getParent();
            }
        }

//        try {
//            Process process = Runtime.getRuntime().exec("shell input swipe 250 250 250 1200");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        LogE(execRootCmd("shell input swipe 250 250 250 1200"));
    }

    private void endMyJiaoben() {
        handler.removeCallbacks(startRunnable);
    }

    private void sleep500milles() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void threadSleep(long l){
        try {
            Thread.sleep(l);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * 执行命令但不关注结果输出
     */
    public static int execRootCmdSilent(String cmd) {
        int result = -1;
        DataOutputStream dos = null;

        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());

            Log.e(TAG, "execRootCmdSilent: "+cmd );
            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
            result = p.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 执行命令并且输出结果
     */
    public static String execRootCmd(String cmd) {
        String result = "";
        DataOutputStream dos = null;
        DataInputStream dis = null;

        try {
            Process p = Runtime.getRuntime().exec("su");// 经过Root处理的android系统即有su命令
            dos = new DataOutputStream(p.getOutputStream());
            dis = new DataInputStream(p.getInputStream());

            Log.i(TAG, cmd);
            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            String line = null;
            while ((line = dis.readLine()) != null) {
                Log.d("result", line);
                result += line;
            }
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
