package com.android.httplib.gson;

import com.android.httplib.basebean.ApiException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 类名称：CustomGsonResponseBodyConverter
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/11/11 00:11
 * 描述：TODO
 */

final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        HttpStatus httpStatus = gson.fromJson(response, HttpStatus.class);
        //过滤正确返回码操作，非正常返回码统一在error中处理
        if (!httpStatus.isSuccess()) {
            value.close();
            throw new ApiException(httpStatus.getCode(), httpStatus.getMessage());
        }
        // 预防api返回结果中没有data字段，防止缺少字段导致解析报错，手动添加该字段
        JsonParser parse = new JsonParser();
        JsonObject json = (JsonObject) parse.parse(response);
        if (!json.has("data")) {
            json.add("data", null);
        }
        response = json.toString();
        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);

        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}
