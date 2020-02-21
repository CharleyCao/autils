package cn.caoleix.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;

/**
  * @author charley
  * @date 2020/2/11 2:31
  * @desc 自定义Activity栈帮助类
  */
public class ActivityStack {

    /************单例开始************/
    private static ActivityStack instance = new ActivityStack();

    private ActivityStack() { }

    public static ActivityStack get() {
        return instance;
    }
    /************单例结束************/

    /**
     * Activity栈
     */
    private Deque<Activity> activities = new ArrayDeque<>();

    /**
     * 将参数activity压站
     * @param activity
     */
    public void push(Activity activity) {
        activities.push(activity);
    }

//    /**
//     * 获取栈顶的Activity, 并弹栈
//     * @return
//     */
//    public Activity pop() {
//        return activities.pop();
//    }

    public boolean remove(Activity activity) {
        return activities.remove(activity);
    }

    /**
     * 获取栈顶的Activity
     * @return
     */
    public Activity top() {
        return activities.peek();
    }

    /**
     * 栈是否为空
     * @return
     */
    public boolean empty() {
        return activities.size() == 0;
    }

    private Application.ActivityLifecycleCallbacks callbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
            push(activity);
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {

        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {

        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {

        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
            remove(activity);
        }
    };

    public void registerActivityLifecycleCallbacks(Application application) {
        application.registerActivityLifecycleCallbacks(callbacks);
    }

    public void unregisterActivityLifecycleCallbacks(Application application) {
        application.unregisterActivityLifecycleCallbacks(callbacks);
    }

}
