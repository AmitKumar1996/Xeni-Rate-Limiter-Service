package dev.xeni.xeni_rate_limiter.service.strategy;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import dev.xeni.xeni_rate_limiter.model.ClientConfig;

@Component
public class FixedWindowRateLimiter {

    // Map of clientId -> (windowStartTime -> request count)
    private final Map<String, WindowInfo> clientWindowMap = new ConcurrentHashMap<>();

    public boolean isRequestAllowed(ClientConfig config, long timestamp) {
        long windowSize = config.getWindowInSeconds();
        long currentWindow = timestamp / windowSize;

        clientWindowMap.putIfAbsent(config.getClientId(), new WindowInfo(currentWindow, new AtomicInteger(0)));
        WindowInfo windowInfo = clientWindowMap.get(config.getClientId());

        synchronized (windowInfo) {
            if (windowInfo.window != currentWindow) {
                // New window started; reset
                windowInfo.window = currentWindow;
                windowInfo.counter.set(1);
                return true;
            }

            if (windowInfo.counter.get() < config.getRequestsPerWindow()) {
                windowInfo.counter.incrementAndGet();
                return true;
            } else {
                return false;
            }
        }
    }

    private static class WindowInfo {
        long window;
        AtomicInteger counter;

        public WindowInfo(long window, AtomicInteger counter) {
            this.window = window;
            this.counter = counter;
        }
    }
}

