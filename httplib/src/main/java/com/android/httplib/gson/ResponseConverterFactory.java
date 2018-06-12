package com.android.httplib.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * ResponseConverterFactory Create on 2017/11/10 23:48
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : 自定义GsonConvert
 */

public class ResponseConverterFactory extends Converter.Factory {

    public static ResponseConverterFactory create() {
        return create(new Gson());
    }


    public static ResponseConverterFactory create(Gson gson) {
        return new ResponseConverterFactory(gson);
    }

    private final Gson gson;

    private ResponseConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        //返回我们自定义的Gson请求体变换器
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomGsonResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        //返回我们自定义的Gson响应体变换器
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomGsonRequestBodyConverter<>(gson,adapter);
    }
}

