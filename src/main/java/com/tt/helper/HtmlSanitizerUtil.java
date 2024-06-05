package com.tt.helper;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class HtmlSanitizerUtil {
    private static final PolicyFactory POLICY_FACTORY = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

    public static String sanitize(String html) {
        return POLICY_FACTORY.sanitize(html);
    }
}
