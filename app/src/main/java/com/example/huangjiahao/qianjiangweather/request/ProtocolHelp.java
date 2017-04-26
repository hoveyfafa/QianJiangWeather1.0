package com.example.huangjiahao.qianjiangweather.request;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.huangjiahao.qianjiangweather.MyApplication;

import com.example.huangjiahao.qianjiangweather.util.JsonUtils;
import com.example.huangjiahao.qianjiangweather.view.LoadingDialog;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/4.
 */
public class ProtocolHelp {

    private static ProtocolHelp mInstance;
    private Context context;
    private static LoadingDialog loadingDialog;

    public static ProtocolHelp getInstance(Context context) {

        loadingDialog = new LoadingDialog(context);
        if (mInstance == null) {
            synchronized (ProtocolHelp.class) {
                if (mInstance == null) {
                    mInstance = new ProtocolHelp(context);
                }
            }
        }
        return mInstance;
    }

    private ProtocolHelp(Context context) {
        this.context = context;
    }


    public void protocolHelp(String url, ProtocolManager.HttpMethod httpMethod, final HttpCallBack httpCallBack) {
        protocolHelp(null, url, httpMethod, null, httpCallBack, false);
    }

    public void protocolHelp(String url, ProtocolManager.HttpMethod httpMethod, Class cla, final HttpCallBack httpCallBack) {
        protocolHelp(null, url, httpMethod, cla, httpCallBack, false);
    }


    public void protocolHelp(String url, final HttpCallBack httpCallBack) {
        protocolHelp(null, url, ProtocolManager.HttpMethod.POST, null, httpCallBack, false);
    }

    public void protocolHelp(String url, Class cla, final HttpCallBack httpCallBack) {
        protocolHelp(null, url, ProtocolManager.HttpMethod.POST, cla, httpCallBack, false);
    }

    public void protocolHelp(String url, Class cla, final HttpCallBack httpCallBack, boolean isList) {
        protocolHelp(null, url, ProtocolManager.HttpMethod.POST, cla, httpCallBack, isList);
    }

    public void protocolHelp(Map<String, String> param, String url, final HttpCallBack httpCallBack) {
        protocolHelp(param, url, ProtocolManager.HttpMethod.POST, null, httpCallBack, false);
    }

    public void protocolHelp(Map<String, String> param, String url, Class cla, final HttpCallBack httpCallBack) {
        protocolHelp(param, url, ProtocolManager.HttpMethod.POST, cla, httpCallBack, false);
    }


    public void protocolHelp(Map<String, String> param, String url, ProtocolManager.HttpMethod httpMethod, final HttpCallBack httpCallBack) {
        protocolHelp(param, url, httpMethod, null, httpCallBack, false);
    }


    public void protocolHelp(Map<String, String> param, String url, Class cla, ProtocolManager.HttpMethod httpMethod, final HttpCallBack httpCallBack) {
        protocolHelp(param, url, httpMethod, cla, httpCallBack, false);
    }

    public void protocolHelp(Map<String, String> param, String url, Class cla, final HttpCallBack httpCallBack, boolean isList) {
        protocolHelp(param, url, ProtocolManager.HttpMethod.POST, cla, httpCallBack, isList);
    }


    public void protocolHelp(Map<String, String> param, String url, ProtocolManager.HttpMethod httpMethod, final Class cla, final HttpCallBack httpCallBack, final boolean isList) {
        loadingDialog.show();
        ProtocolManager.getInstance().request(param, url, new ProtocolManager.ReponseCallback() {
            @Override
            public void fail(String e) {
                Toast.makeText(MyApplication.getInstance(), e, Toast.LENGTH_SHORT).show();
                if (httpCallBack != null) {
                    httpCallBack.fail(e);
                }
                loadingDialog.dismiss();
            }

            @Override
            public void success(String response) {
                Log.i("request",response);
                JSONObject objData = JsonUtils.string2JSON(response);
                boolean bizSucc = objData.optBoolean("bizSucc");
                if (bizSucc) {
                    Object result = null;
                    if (cla == null) {
                        httpCallBack.success(response);
                    } else {
                        if (isList) {
                            result = JsonUtils.fromJson(response, cla, "");
                        } else {
                            result = JsonUtils.fromJson(response, cla);
                        }
                        if (result != null) {
                            if (httpCallBack != null) {
                                httpCallBack.success(response);
                            }
                        } else {
                            httpCallBack.fail("链接超时，请稍后重试");
                        }
                    }
                } else {
                    String errMsg = objData.optString("errMsg");
                    if (errMsg != null) {
                        httpCallBack.fail(errMsg);
                    } else {
                        httpCallBack.fail("链接超时，请稍后重试");
                    }

                }
                loadingDialog.dismiss();
            }
        }, httpMethod);

    }

    /**
     * 列表
     * @param param
     * @param url
     * @param httpMethod
     * @param cla
     * @param httpCallBack
     * @param isList
     */
//    public void protocolHelp(Map<String, String> param, String url, ProtocolManager.HttpMethod httpMethod, final Class cla, final HttpCallBack httpCallBack, final String isList) {
//        loadingDialog.show();
//        ProtocolManager.getInstance().request(param, url, new ProtocolManager.ReponseCallback() {
//            @Override
//            public void fail(String e) {
//                Toast.makeText(MyApplication.getInstance(), e, Toast.LENGTH_SHORT).show();
//                if (httpCallBack != null) {
//                    httpCallBack.fail(e);
//                }
//                loadingDialog.dismiss();
//            }
//
//            @Override
//            public void success(String response) {
//                Log.i("request",response);
//                JSONObject objData = JsonUtils.string2JSON(response);
//                boolean bizSucc = objData.optBoolean("bizSucc");
//                if (bizSucc) {
//                    Object result = null;
//                    if (cla == null) {
//                        httpCallBack.success(response);
//                    } else {
//                        result = JsonUtils.fromJson(response, cla, isList);
//                        if (result != null) {
//                            if (httpCallBack != null) {
//                                httpCallBack.success(result);
//                            }
//                        } else {
//                            httpCallBack.fail("链接超时，请稍后重试");
//                        }
//                    }
//                } else {
//                    String errMsg = objData.optString("errMsg");
//                    if (errMsg != null) {
//                        httpCallBack.fail(errMsg);
//                    } else {
//                        httpCallBack.fail("链接超时，请稍后重试");
//                    }
//
//                }
//                loadingDialog.dismiss();
//            }
//        }, httpMethod);
//
//    }
    /**
     * 对象
     * @param param
     * @param url
     * @param httpMethod
     * @param cla
     * @param httpCallBack
     */
    public void protocolHelp(Map<String, String> param, String url, ProtocolManager.HttpMethod httpMethod, final Class cla, final HttpCallBack httpCallBack) {
        loadingDialog.show();
        ProtocolManager.getInstance().request(param, url, new ProtocolManager.ReponseCallback() {
            @Override
            public void fail(String e) {
//                Toast.makeText(FaceScoreLoanApplication.getInstance(), e, Toast.LENGTH_SHORT).show();
                if (httpCallBack != null) {
                    httpCallBack.fail(e);
                }
                loadingDialog.dismiss();
            }

            @Override
            public void success(String response) {
                Log.e("response",response);
                JSONObject objData = JsonUtils.string2JSON(response);

                Object result = null;
                httpCallBack.success(response);
//                if (cla == null) {
//                    httpCallBack.success(response);
//                } else {
//                    result = JsonUtils.fromJson(response, cla);
//                    if (result != null) {
//                        if (httpCallBack != null) {
//                            httpCallBack.success(result);
//                        }
//                    } else {
//                        httpCallBack.fail("链接超时，请稍后重试");
//                    }
//                }
//                if (bizSucc) {
//                    Object result = null;
//                    if (cla == null) {
//                        httpCallBack.success(response);
//                    } else {
//                        result = JsonUtils.fromJson(response, cla);
//                        if (result != null) {
//                            if (httpCallBack != null) {
//                                httpCallBack.success(result);
//                            }
//                        } else {
//                            httpCallBack.fail("链接超时，请稍后重试");
//                        }
//                    }
//                } else {
//                    String errMsg = objData.optString("errMsg");
//                    if (errMsg != null) {
//                        httpCallBack.fail(errMsg);
//                    } else {
//                        httpCallBack.fail("链接超时，请稍后重试");
//                    }
//
//                }
                loadingDialog.dismiss();
            }
        }, httpMethod);

    }

    public interface HttpCallBack {
        void fail(String message);

        void success(String object);
    }


}
