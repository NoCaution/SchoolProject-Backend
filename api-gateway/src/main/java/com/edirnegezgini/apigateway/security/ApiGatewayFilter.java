package com.edirnegezgini.apigateway.security;

import com.edirnegezgini.apigateway.service.ApiGatewayService;
import com.edirnegezgini.commonservice.entity.Response;
import com.edirnegezgini.commonservice.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.Objects;

@Component
public class ApiGatewayFilter extends AbstractGatewayFilterFactory<ApiGatewayFilter.Config> {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private ApiGatewayService apiGatewayService;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();


    public ApiGatewayFilter() {
        super(Config.class);
    }

    private static final List<String> WHITE_LIST = List.of(
            "/auth-service/api/**"
    );

    private boolean isInWhiteList(String requestPath) {
        return WHITE_LIST.stream().anyMatch(path -> pathMatcher.match(path, requestPath));
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            String requestPath = request.getURI().getPath();
            HttpHeaders headers = request.getHeaders();

            //is uri in WHITE_LIST
            if (isInWhiteList(requestPath)) {
                return chain.filter(exchange);
            }

            boolean isAuthHeaderMissing = !headers.containsKey("Authorization");

            if (isAuthHeaderMissing) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            //get headers
            List<String> authHeaders = headers.get("Authorization");

            if (authHeaders == null || !authHeaders.get(0).startsWith("Bearer ")) {
                response.setStatusCode(HttpStatus.BAD_REQUEST);
                return response.setComplete();
            }

            //get token
            String authHeaderValue = authHeaders.get(0);
            String token = authHeaderValue.substring("Bearer ".length()).trim();
            String id = jwtUtil.extractUserId(token);

            //build request
            if (id != null) {
                String customHeaderValue;

                boolean isNotGetAuthenticatedUserRequest = !Objects.equals(requestPath, "/user-service/api/getAuthenticatedUser");

                //if request is to get authenticated user, don't need to call getUser method, token is enough to send.
                //if request is not to get authenticated user, then request must be built in this if bloc.
                if (isNotGetAuthenticatedUserRequest) {
                    Response<User> getUserResponse = apiGatewayService.getUser(token);

                    if (getUserResponse.getHttpStatus() != HttpStatus.OK) {
                        response.setStatusCode(getUserResponse.getHttpStatus());
                        return response.setComplete();
                    }

                    User authenticatedUser = getUserResponse.getResult();

                    boolean isTokenValid = jwtUtil.isTokenValid(token, authenticatedUser);

                    if (isTokenValid) {
                        String userName = authenticatedUser.getUsername();
                        String password = authenticatedUser.getPassword();
                        String role = authenticatedUser.getAuthorities().stream().toList().get(0).getAuthority();

                        customHeaderValue = String.join(" ", token, userName, password, role);
                        request = exchange.getRequest().mutate().header("CustomAuthorization", customHeaderValue).build();
                    }

                } else {
                    customHeaderValue = token;
                    request = request.mutate().header("CustomAuthorization", customHeaderValue).build();
                }
            }

            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    public static class Config {

    }
}
