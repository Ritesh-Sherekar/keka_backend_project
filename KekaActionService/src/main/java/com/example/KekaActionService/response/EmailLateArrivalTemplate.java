package com.example.KekaActionService.response;

public class EmailLateArrivalTemplate {
        // {{name}}, {{attendanceDate}}, {{arrivalTime}}, {{remarks}}
        public static final String LATE_ARRIVAL_EMAIL_HTML =
                "<!doctype html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                        "  <title>Late Arrival Notification</title>\n" +
                        "  <style>\n" +
                        "    body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial; background-color: #f6f9fc; margin: 0; padding: 0; }\n" +
                        "    .container { max-width: 600px; margin: 32px auto; background: #ffffff; border-radius: 8px; box-shadow: 0 6px 18px rgba(0,0,0,0.06); overflow: hidden; }\n" +
                        "    .header { padding: 24px; background: linear-gradient(90deg,#4f46e5,#06b6d4); color: #fff; }\n" +
                        "    .header h1 { margin: 0; font-size: 20px; }\n" +
                        "    .content { padding: 24px; color: #333; }\n" +
                        "    .status { font-weight: bold; color: #ef4444; } /* red for late */\n" +
                        "    .remarks { margin-top: 12px; font-style: italic; color: #555; }\n" +
                        "    .footer { padding: 16px 24px; font-size: 12px; color: #888; background: #fafafa; }\n" +
                        "    @media (max-width: 420px) { .content { padding: 16px; } .header { padding: 16px; } }\n" +
                        "  </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "  <div class=\"container\">\n" +
                        "    <div class=\"header\">\n" +
                        "      <h1>Late Arrival Alert</h1>\n" +
                        "    </div>\n" +
                        "    <div class=\"content\">\n" +
                        "      <p>Hi {{name}},</p>\n" +
                        "      <p>Our records show that you arrived late on <b>{{attendanceDate}}</b> at <b>{{arrivalTime}}</b>.</p>\n" +
                        "      <p>Status: <span class=\"status\">Late</span></p>\n" +
                        "      <p class=\"remarks\">Remarks: {{remarks}}</p>\n" +
                        "      <hr />\n" +
                        "      <p class=\"muted\">Please ensure timely attendance to maintain workflow efficiency.</p>\n" +
                        "    </div>\n" +
                        "    <div class=\"footer\">\n" +
                        "      <div>Need help? Contact support at <a href=\"mailto:support@example.com\">support@example.com</a></div>\n" +
                        "      <div style=\"margin-top:6px\">© " + java.time.Year.now().getValue() + " Your Company</div>\n" +
                        "    </div>\n" +
                        "  </div>\n" +
                        "</body>\n" +
                        "</html>";

        /**
         * Helper to generate Late Arrival mail.
         */
        public static String getLateArrivalHtml(String name, String attendanceDate, String arrivalTime, String remarks) {
            if (name == null || name.isBlank()) name = "there";
            if (remarks == null || remarks.isBlank()) remarks = "Please ensure punctuality.";
            return LATE_ARRIVAL_EMAIL_HTML
                    .replace("{{name}}", escapeHtml(name))
                    .replace("{{attendanceDate}}", escapeHtml(attendanceDate))
                    .replace("{{arrivalTime}}", escapeHtml(arrivalTime))
                    .replace("{{remarks}}", escapeHtml(remarks));
        }

        private static String escapeHtml(String s) {
            if (s == null) return "";
            return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                    .replace("\"", "&quot;").replace("'", "&#x27;");
        }
}
