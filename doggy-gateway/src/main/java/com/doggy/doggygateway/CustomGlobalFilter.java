package com.doggy.doggygateway;

import cn.hutool.core.img.ImgUtil;
import com.doggy.doggyclientsdk.utils.SignUtils;
import com.doggy.doggycommon.model.entity.InterfaceInfo;
import com.doggy.doggycommon.model.entity.User;
import com.doggy.doggycommon.service.InnerInterfaceInfoService;
import com.doggy.doggycommon.service.InnerUserInterfaceInfoService;
import com.doggy.doggycommon.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * 全局过滤
 * @author ZiQing Sun
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    private static final String INTERFACE_HOST = "http://localhost:8123";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /**
         * 1.用户发送请求到 API 网关
         */

        /**
         * 2. 请求日志
         */
        ServerHttpRequest request = exchange.getRequest();
        String path = INTERFACE_HOST + request.getPath().value();
        String method = request.getMethod().toString();
        log.info("请求唯一标识：" + request.getId());
        log.info("请求路径：" + path);
        log.info("请求方法：" + method);
        log.info("请求参数：" + request.getQueryParams());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址：" + sourceAddress);
        log.info("请求来源地址：" + request.getRemoteAddress());
          // 拿到响应对象
        ServerHttpResponse response = exchange.getResponse();
        /**
         * 3. 访问控制——黑白名单
         */
        if (!IP_WHITE_LIST.contains(sourceAddress)){
            //设置响应状态码未403 Forbidden（禁止访问）
            response.setStatusCode(HttpStatus.FORBIDDEN);
            //返回处理完成的响应
            return response.setComplete();
        }
        /**
         * 4. 用户鉴权(判断 ak、sk 是合法)
         */
        //从请求头中获取参数
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");

        //实际情况应该是去数据库中查是否已分配给用户
        User invokeUser = null;
        try{
            //调用内部服务，根据访问密钥获取用户信息
            invokeUser = innerUserService.getInvokeUser(accessKey);
        }catch (Exception e){
            //捕获异常，记录日志
            log.error("getInvokeUser error",e);
        }
        if(invokeUser ==null){
            //如果用户信息为空，处理未授权情况并返回响应
            return handleNoAuth(response);
        }
        if(Long.parseLong(nonce)>10000L){
            return handleNoAuth(response);
        }
        //  时间和当前时间不能超过5分钟
        //首先,获取当前时间的时间戳,以秒为单位
        //System.currentTimeMillis()返回当前时间的毫秒数，除以109后得到当前时间的秒数。
        Long currentTime = System.currentTimeMillis() / 1000;
        //定义一个常量FIVE_MINUTES，表示五分钟的时间间隔(乘以60,将分钟转换为秒,得到五分钟的时间间隔）
        final Long FIVE_MINUTES = 60 * 5L;
        //判断当前时间与传入的时间戳是否相差五分钟或以上
        //Long.parseLong(timestamp)将传入的时间戳转换成长整型
        //然后计算当前时间与传入时间戳之间的差值(以秒为单位),如果差值大于等于五分钟,则返回true,否则返回false
        if ((currentTime - Long.parseLong(timestamp)) >=FIVE_MINUTES) {
            //如果时间戳与当前时间相差五分钟或以上，调用handleNoAuth(response)方法进行处理
            return handleNoAuth(response);
        }

        //实际情况中是从数据库中查询出secretKey
        //从获取到的用户信息中获取用户的密钥
        String secretKey = invokeUser.getSecretKey();
        //使用获取到的密钥对请求体进行签名
        String serverSign = SignUtils.getSign(body,secretKey);
        //检查请求中的签名是否为空，或者是否与服务器生成的签名不一致
        if(sign == null || !sign.equals(serverSign)){
            //如果签名为空或者签名不一致，返回处理未授权的响应
            return handleNoAuth(response);
        }
        /**
         * 5. 判断请求的模拟接口是否存在?
         */
         //初始化一个 InterfaceInfo 对象，用于存储查询结果
        InterfaceInfo interfaceInfo = null;
        try{
            //尝试从内部接口信息服务获取指定路径和方法的接口信息
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(path,method);
        }catch (Exception e){
            //如果获取接口信息时出现异常，记录错误日志
            log.error("getInterfaceInfo error",e);
        }
        //检查是否成功获取到接口信息
        if(interfaceInfo == null){
            //如果未获取到接口信息，返回处理未授权的响应
            return handleNoAuth(response);
        }

        /**
         * 6. 请求转发，调用模拟接口+ 7. 响应日志
         */
//        Mono<Void> filter = chain.filter(exchange);
//        return filter()
        return handleResponse(exchange,chain,interfaceInfo.getId(),invokeUser.getId());

//        log.info("响应：" + response.getStatusCode());
//        return filter;
    }

    public Mono<Void> handleResponse(ServerWebExchange exchange,GatewayFilterChain chain,long interfaceInfoId,long userId) {
        try {
            //获取原始的响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            //获取数据缓冲工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            //获取响应的状态码
            HttpStatus statusCode = originalResponse.getStatusCode();

            //判断状态码是否为200 OK（可按道理来说，现在没有调用，怎么拿到响应码？）
            if (statusCode == HttpStatus.OK) {
                //创建一个装饰后的响应对象
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    //重写writeWith方法，用于处理响应体的数据
                    //这段方法就是只要当我们的模拟接口调用完成之后,等它返回结果
                    //就会调用writewith方法,我们就能根据响应结果做一些自己的处理
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            //返回一个处理后的响应体
                            //拼接字符串，它把缓冲区的数据取出来,一点一点拼接好
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                /**
                                 * 8. 调用成功，接口调用次数 + 1 invokeCount
                                 */
                                try{
                                    //调用内部用户接口信息服务，记录该接口调用次数
                                   innerUserInterfaceInfoService.invokeCount(interfaceInfoId,userId);
                                }catch (Exception e){
                                    //捕获异常并记录错误信息
                                    log.error("invokeCount error",e);
                                }
                                byte[] content = new byte[dataBuffer.readableByteCount()];//读取响应体的内容并转换为字节数组
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                sb2.append(data);
                                //打印日志
                                log.info("响应结果：" + data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            /**
                             * 9. 调用失败，返回一个规范的错误码
                             */
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                //对于200 OK的请求，将装饰后的响应对象传递给下一个过滤器链，并继续处理（设置response对象为修饰过的）
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            //对于非200 OK的请求，直接返回，进行降级处理
            return chain.filter(exchange);
        } catch (Exception e) {
            //处理异常情况，记录错误日志
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}

