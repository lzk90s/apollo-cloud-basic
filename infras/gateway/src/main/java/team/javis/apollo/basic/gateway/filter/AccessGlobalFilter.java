package team.javis.apollo.basic.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import team.javis.apollo.basic.gateway.model.RequestContent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.util.Objects;

@Component
@Slf4j
public class AccessGlobalFilter implements GlobalFilter, Ordered {
    private static final String START_TIME = "startTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(START_TIME, System.currentTimeMillis());

        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        // probably should reuse buffers
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        //释放掉内存
                        DataBufferUtils.release(dataBuffer);
                        String s = new String(content, Charset.forName("UTF-8"));
                        //TODO，s就是response的值，想修改、查看就可随意而为

                        Long startTime = exchange.getAttribute(START_TIME);
                        if (startTime != null) {
                            RequestContent requestContent = RequestContent.builder()
                                    .method(Objects.requireNonNull(exchange.getRequest().getMethod()).name())
                                    .param(exchange.getRequest().getQueryParams().toString())
                                    .remoteHost(exchange.getRequest().getURI().getHost())
                                    .path(exchange.getRequest().getURI().getPath())
                                    .status(Objects.requireNonNull(exchange.getResponse().getStatusCode()).value())
                                    .response(s)
                                    .responseTime((System.currentTimeMillis() - startTime))
                                    .build();
                            log.info(requestContent.toString());
                        }

                        byte[] uppedContent = new String(content, Charset.forName("UTF-8")).getBytes();
                        return bufferFactory.wrap(uppedContent);
                    }));
                }
                // if body is not a flux. never got there.
                return super.writeWith(body);
            }
        };
        // replace response with decorator
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return -100;
    }
}

