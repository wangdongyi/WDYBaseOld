package com.base.library.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.base.library.application.BaseApplication;
import com.base.library.util.ToastUtil;

/**
 * 作者：王东一 on 2016/3/22 09:53
 **/
public abstract class WDYBaseFragment extends Fragment {
    //懒加载
    protected boolean isViewShown = false;
    protected View main;
    protected boolean isBuild = false;
    protected ToastUtil toastUtil;
    protected Context wdyContext;
    private BaseHandler handler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        wdyContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        wdyContext = null;
    }

    protected void setContentView(LayoutInflater inflater, int layoutResID) {
        if (main == null) {
            this.main = inflater.inflate(layoutResID, null);
            if (!isViewShown && !isBuild) {
                lazyLoad();
            }
        }
    }

    //初始化代码
    protected abstract void init();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewShown = null != getView();
    }


    protected void lazyLoad() {
        isBuild = true;
        handler = new BaseHandler();
        init();
    }

    private ToastUtil getToastUtil() {
        if (toastUtil == null) {
            synchronized (ToastUtil.class) {
                if (toastUtil == null)
                    try {
                        toastUtil = new ToastUtil(wdyContext);
                    } catch (Exception e) {
                        e.printStackTrace();
                        toastUtil = new ToastUtil(BaseApplication.getInstance());
                    }
            }
        }
        return toastUtil;
    }

    @SuppressLint("HandlerLeak")
    private class BaseHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 1001:
                    getToastUtil().showMiddleToast(String.valueOf(msg.obj));
                    break;
            }
        }
    }

    protected void showMiddleToast(final String msg) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1001;
                message.obj = msg;
                handler.sendMessage(message);
            }
        }, 500);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
