package net.away0x.lib_network.okhttp.response;

import android.os.Handler;
import android.os.Looper;

import net.away0x.lib_network.okhttp.exception.OkHttpException;
import net.away0x.lib_network.okhttp.response.listener.DisposeDataHandle;
import net.away0x.lib_network.okhttp.response.listener.DisposeDataListener;
import net.away0x.lib_network.okhttp.utils.ResponseEntityToModule;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 处理json类型的响应
 */
public class CommonJsonCallback implements Callback {

    protected final String EMPTY_MSG = "";

    /**
     * the java layer exception, do not same to the logic error
     */
    protected final int NETWORK_ERROR = -1; // the network relative error
    protected final int JSON_ERROR = -2; // the JSON relative error
    protected final int OTHER_ERROR = -3; // the unknow error

    private DisposeDataListener mListener; // 业务层用的回调
    private Class<?> mClass; // json 转成的对象
    private Handler mDeliveryHandler; // 和主线程通信

    public CommonJsonCallback(DisposeDataHandle handle) {
        mListener = handle.mListener;
        mClass = handle.mClass;
        mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

    // 请求失败
    @Override
    public void onFailure(Call call, final IOException e) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new OkHttpException(NETWORK_ERROR, e));
            }
        });
    }

    // 响应成功
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
            }
        });
    }

    private void handleResponse(String result) {
        if (result == null || result.trim().equals("")) {
            mListener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }

        try {
            //不需要解析
            if (mClass == null) {
                mListener.onSuccess(result);
            } else {
                //解析为实体对象，可用gson，fastjson替换
                Object obj = ResponseEntityToModule.parseJsonToModule(result, mClass);
                if (obj != null) {
                    mListener.onSuccess(obj);
                } else {
                    mListener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                }
            }
        } catch (Exception e) {
            mListener.onFailure(new OkHttpException(OTHER_ERROR, e));
        }
    }
}
