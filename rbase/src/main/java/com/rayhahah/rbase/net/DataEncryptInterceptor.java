package com.rayhahah.rbase.net;

import android.util.Base64;

import com.luck.picture.lib.tools.Constant;
import com.rayhahah.rbase.utils.RSAEncrypt;
import com.rayhahah.rbase.utils.base.ConvertUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Set;

import javax.crypto.spec.SecretKeySpec;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class DataEncryptInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        // Set<String> queryList = url.queryParameterNames();

        if ("GET".equals(request.method())) {
            HttpUrl httpurl = request.url();
            String url = httpurl.toString();
            /*int index = url.indexOf("?");
            String newParam = "";
            if (index > 0) {
                url = url.substring(0, index);
                String newParamJson = httpurl.toString().substring(index + 1, httpurl.toString().length());
                byte[] newByte = RSAEncrypt.getInstance().encryptByte(newParamJson.getBytes());
                newParam = Base64.encodeToString(newByte, Base64.DEFAULT);
            }*/
            if (url.contains("uid")) {
                url = httpurl.toString() + "&" + "token_key" + "=" + ConvertUtils.encrypt32(SPManager.get().getStringValue("uid") + SPManager.get().getStringValue("token"));
            }

            //重新构建请求
            request = request.newBuilder().url(url).build();
        } else {

            //RequestBody oldRequestBody = request.body();
            //Buffer requestBuffer = new Buffer();
            //oldRequestBody.writeTo(requestBuffer);
            //String oldBodyStr = requestBuffer.readUtf8();
            //requestBuffer.close();
            //随机AES密钥加密oldBodyStr
            //byte[] newByte = RSAEncrypt.getInstance().encryptByte(oldBodyStr.getBytes());
            //String newBodyStr = Base64.encodeToString(newByte, Base64.DEFAULT);
            //RequestBody newBody = RequestBody.create(mediaType, newBodyStr);
            RequestBody requestBody = request.body();

            if (requestBody instanceof FormBody) {
                FormBody.Builder builder = new FormBody.Builder();
                FormBody formBody = (FormBody) requestBody;
                for (int i = 0; i < formBody.size(); i++) {
                    builder.add(formBody.encodedName(i), URLDecoder.decode(formBody.encodedValue(i), "UTF-8"));
                    if ("uid".equals(formBody.encodedName(i))) {
                        //builder.add("token", SPManager.get().getStringValue("token"));
                        //builder.add("uid_id", SPManager.get().getStringValue("uid") );
                        builder.add("token_key", ConvertUtils.encrypt32(SPManager.get().getStringValue("uid") + SPManager.get().getStringValue("token")));
                    }
                }

                request = request.newBuilder()
                        .header("Content-Type", "text/xml; charset=utf-8")
                        //.header("Content-Length", String.valueOf(newBody.contentLength()))
                        .post(builder.build())
                        //.method(request.method(), newBody)
                        .build();
                //md5("123"+token);
                //builder.add("rsa", newBodyStr);
            } else if (requestBody instanceof MultipartBody) {
                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.setType(MultipartBody.FORM);
                MultipartBody multipartBody = (MultipartBody) requestBody;
                builder.addFormDataPart("token_key", ConvertUtils.encrypt32(SPManager.get().getStringValue("uid") + SPManager.get().getStringValue("token")));

                //添加原请求体
                for (int i = 0; i < multipartBody.size(); i++) {
                    builder.addPart(multipartBody.part(i));
                }

                request = request.newBuilder()
                        //.header("Content-Type", newBody.contentType().toString())
                        //.header("Content-Length", String.valueOf(newBody.contentLength()))
                        .post(builder.build())
                        //.method(request.method(), newBody)
                        .build();
            }

        }

        Response response = chain.proceed(request);
        return response;
    }
}
