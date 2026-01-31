package sg.edu.nus.iss.d13revision.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecurityHeadersFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Add cache control headers to prevent caching of sensitive content
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setHeader("Expires", "0");
        
        // Hide server information to prevent proxy disclosure
        httpResponse.setHeader("Server", "");
        
        chain.doFilter(request, response);
    }
}
