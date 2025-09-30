package com.example.KekaActionService.response;

public class EmailRegularizationTemplate {

        // Leave Regularization email template with placeholders
        public static final String LEAVE_REGULARIZATION_EMAIL_HTML =
                "<!doctype html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                        "  <title>Leave Regularization Update</title>\n" +
                        "  <style>\n" +
                        "    body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial; background-color: #f6f9fc; margin: 0; padding: 0; }\n" +
                        "    .container { max-width: 600px; margin: 32px auto; background: #ffffff; border-radius: 8px; box-shadow: 0 6px 18px rgba(0,0,0,0.06); overflow: hidden; }\n" +
                        // ✅ Indigo → Cyan gradient from your first template
                        "    .header { padding: 24px; background: linear-gradient(90deg,#4f46e5,#06b6d4); color: #fff; }\n" +
                        "    .header h1 { margin: 0; font-size: 20px; }\n" +
                        "    .content { padding: 24px; color: #333; }\n" +
                        // ✅ Status color switched to Indigo (#4f46e5)
                        "    .status { font-weight: bold; color: #4f46e5; }\n" +
                        "    .remarks { margin-top: 12px; font-style: italic; color: #555; }\n" +
                        "    .footer { padding: 16px 24px; font-size: 12px; color: #888; background: #fafafa; }\n" +
                        "    @media (max-width: 420px) { .content { padding: 16px; } .header { padding: 16px; } }\n" +
                        "  </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "  <div class=\"container\">\n" +
                        "    <div class=\"header\">\n" +
                        "      <h1>Leave Regularization Update</h1>\n" +
                        "    </div>\n" +
                        "    <div class=\"content\">\n" +
                        "      <p>Hi {{name}},</p>\n" +
                        "      <p>Your leave request for <b>{{leaveDate}}</b> has been reviewed.</p>\n" +
                        "      <p>Status: <span class=\"status\">{{status}}</span></p>\n" +
                        "      <p class=\"remarks\">Remarks: {{remarks}}</p>\n" +
                        "      <hr />\n" +
                        "      <p class=\"muted\">If you have questions, please reach out to your manager or HR team.</p>\n" +
                        "    </div>\n" +
                        "    <div class=\"footer\">\n" +
                        "      <div>Need help? Contact support at <a href=\"mailto:support@example.com\">support@example.com</a></div>\n" +
                        "      <div style=\"margin-top:6px\">© " + java.time.Year.now().getValue() + " Your Company</div>\n" +
                        "    </div>\n" +
                        "  </div>\n" +
                        "</body>\n" +
                        "</html>";


    /**
         * Helper to generate Leave Regularization mail.
         */
        public static String getLeaveRegularizationHtml(String name, String leaveDate, String status, String remarks) {
            if (name == null || name.isBlank()) name = "there";
            if (remarks == null || remarks.isBlank()) remarks = "No additional remarks.";
            return LEAVE_REGULARIZATION_EMAIL_HTML
                    .replace("{{name}}", escapeHtml(name))
                    .replace("{{leaveDate}}", escapeHtml(leaveDate))
                    .replace("{{status}}", escapeHtml(status))
                    .replace("{{remarks}}", escapeHtml(remarks));
        }

        // Simple HTML escaping
        private static String escapeHtml(String s) {
            if (s == null) return "";
            return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                    .replace("\"", "&quot;").replace("'", "&#x27;");
        }
}

