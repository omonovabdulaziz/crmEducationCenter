package it.live.crm.config;

import io.github.bucket4j.*;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitFilter implements Filter {

    private static final long CAPACITY = 50;
    private static final Duration TIME_PERIOD = Duration.ofSeconds(1);
    private static final Duration WAITING_PERIOD = Duration.ofSeconds(360);

    private Bucket bucket;
    private Set<String> excludedEndpoints;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Bandwidth limit = Bandwidth.classic(CAPACITY, Refill.intervally(CAPACITY, TIME_PERIOD));
        bucket = Bucket4j.builder().addLimit(limit).build();
        excludedEndpoints = new HashSet<>(Arrays.asList("/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
        if (excludedEndpoints.contains(requestURI)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            try {
                sendRateLimitResponse(servletResponse);
                TimeUnit.SECONDS.sleep(WAITING_PERIOD.getSeconds());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                servletResponse.getWriter().write("Error occurred during rate limiting.");
                servletResponse.setContentType("text/plain");
            }
        }
    }

    private void sendRateLimitResponse(ServletResponse servletResponse) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.setStatus(429);
        httpResponse.setContentType("text/plain");
        httpResponse.getWriter().write("Rate limit exceeded.");
    }

}