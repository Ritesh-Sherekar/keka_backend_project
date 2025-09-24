package com.example.KekaActionService.response;

public class EmailTemplate {

    // Template with placeholders {{name}} and {{resetLink}}
    public static final String FORGOT_PASSWORD_EMAIL_HTML =
            "<!doctype html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"utf-8\">\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                    "  <title>Reset your password</title>\n" +
                    "  <style>\n" +
                    "    body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial; background-color: #f6f9fc; margin: 0; padding: 0; }\n" +
                    "    .container { max-width: 600px; margin: 32px auto; background: #ffffff; border-radius: 8px; box-shadow: 0 6px 18px rgba(0,0,0,0.06); overflow: hidden; }\n" +
                    "    .header { padding: 24px; background: linear-gradient(90deg,#4f46e5,#06b6d4); color: #fff; }\n" +
                    "    .header h1 { margin: 0; font-size: 20px; }\n" +
                    "    .content { padding: 24px; color: #333; }\n" +
                    // ✅ white button text enforced with !important
                    "    .button { display: inline-block; margin-top: 18px; text-decoration: none; padding: 12px 20px; border-radius: 6px; background-color: #4f46e5; color: #ffffff !important; font-weight: 600; }\n" +
                    "    .muted { color: #666; font-size: 13px; }\n" +
                    "    .footer { padding: 16px 24px; font-size: 12px; color: #888; background: #fafafa; }\n" +
                    "    @media (max-width: 420px) { .content { padding: 16px; } .header { padding: 16px; } }\n" +
                    "  </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "  <div class=\"container\">\n" +
                    "    <div class=\"header\">\n" +
                    "      <h1>Password reset request</h1>\n" +
                    "    </div>\n" +
                    "    <div class=\"content\">\n" +
                    "      <p>Hi {{name}},</p>\n" +
                    "      <p>We received a request to reset the password for your account. Click the button below to set a new password. This link will expire in 1 hour.</p>\n" +
                    "      <p style=\"text-align:center;\">\n" +
                    "        <a class=\"button\" href=\"{{resetLink}}\" target=\"_blank\">Reset your password</a>\n" +
                    "      </p>\n" +
                    "      <p class=\"muted\">If the button doesn't work, copy and paste the following link into your browser:</p>\n" +
                    "      <p class=\"muted\"><a href=\"{{resetLink}}\" target=\"_blank\">{{resetLink}}</a></p>\n" +
                    "      <hr />\n" +
                    "      <p class=\"muted\">If you didn't request a password reset, you can ignore this email — your account is safe.</p>\n" +
                    "    </div>\n" +
                    "    <div class=\"footer\">\n" +
                    "      <div>Need help? Reply to this email or contact support at <a href=\"mailto:support@example.com\">support@example.com</a></div>\n" +
                    "      <div style=\"margin-top:6px\">© " + java.time.Year.now().getValue() + " Your Company</div>\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "</body>\n" +
                    "</html>";

    /**
     * Simple helper to produce final HTML by replacing placeholders.
     */
    public static String getForgotPasswordHtml(String name, String resetLink) {
        if (name == null || name.isBlank()) name = "there";
        return FORGOT_PASSWORD_EMAIL_HTML
                .replace("{{name}}", escapeHtml(name))
                .replace("{{resetLink}}", escapeHtml(resetLink));
    }

    // Very small helper to avoid accidental raw HTML injection — keeps it simple.
    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#x27;");
    }
}
