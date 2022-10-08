package pers.kun.feign.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import pers.kun.core.exception.BizException;
import pers.kun.core.rest.R;

import java.io.IOException;

/**
 * feign报错信息解析
 *
 * @author liuzhikun
 * @date 2019/12/11
 */
public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();
    private ObjectMapper mapper = new ObjectMapper();

    public FeignErrorDecoder() {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Override
    public Exception decode(String s, Response response) {
        @SuppressWarnings("all")
        R r = null;
        try {
            String body = Util.toString(response.body().asReader());
            r = mapper.readValue(body, R.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (null != r && !r.isOk()) {
            return new BizException(r.getCode(), r.getMessage());
        }

        return errorDecoder.decode(s, response);
    }
}
