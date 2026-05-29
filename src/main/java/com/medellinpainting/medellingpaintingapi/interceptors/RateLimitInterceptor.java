package com.medellinpainting.medellingpaintingapi.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final int MAX_REQUESTS = 5;
    private static final long WINDOW_MS = 60_000;

    private final ConcurrentHashMap<String, List<Long>> requestLog = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (!"/api/message/nuevo".equals(request.getRequestURI()) || !"POST".equals(request.getMethod())) {
            return true;
        }
        String ip = resolveClientIp(request);
        long now = System.currentTimeMillis();

        requestLog.compute(ip, (k, timestamps) -> {
            if (timestamps == null) timestamps = new ArrayList<>();
            timestamps.removeIf(t -> now - t > WINDOW_MS);
            return timestamps;
        });

        List<Long> timestamps = requestLog.get(ip);
        if (timestamps.size() >= MAX_REQUESTS) {
            response.setStatus(429);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"status\":429,\"error\":\"Too many requests — please wait a moment before trying again.\"}");
            return false;
        }

        timestamps.add(now);
        return true;
    }

    private String resolveClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
