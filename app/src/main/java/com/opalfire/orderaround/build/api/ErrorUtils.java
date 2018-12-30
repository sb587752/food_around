package com.opalfire.orderaround.build.api;

import java.io.IOException;
import java.lang.annotation.Annotation;

import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    public static APIError parseError(Response<?> paramResponse) {
        Converter localConverter = ApiClient.getRetrofit().responseBodyConverter(APIError.class, new Annotation[0]);
        try {
            APIError fd = (APIError) localConverter.convert(paramResponse.errorBody());
            return fd;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new APIError();
    }
}
