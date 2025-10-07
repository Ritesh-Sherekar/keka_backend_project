package com.example.KekaActionService.response;

import com.example.KekaActionService.dto.EmailLeaveDto;
import com.example.KekaActionService.enums.LeaveStatus;

public class EmailTemplate {

    // Template with placeholders {{name}} and {{resetLink}}
    public static final String FORGOT_PASSWORD_EMAIL_HTML = "<!doctype html>\n" + "<html lang=\"en\">\n" + "<head>\n" + "  <meta charset=\"utf-8\">\n" + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" + "  <title>Reset your password</title>\n" + "  <style>\n" + "    body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial; background-color: #f6f9fc; margin: 0; padding: 0; }\n" + "    .container { max-width: 600px; margin: 32px auto; background: #ffffff; border-radius: 8px; box-shadow: 0 6px 18px rgba(0,0,0,0.06); overflow: hidden; }\n" + "    .header { padding: 24px; background: linear-gradient(90deg,#4f46e5,#06b6d4); color: #fff; }\n" + "    .header h1 { margin: 0; font-size: 20px; }\n" + "    .content { padding: 24px; color: #333; }\n" +
            // ✅ white button text enforced with !important
            "    .button { display: inline-block; margin-top: 18px; text-decoration: none; padding: 12px 20px; border-radius: 6px; background-color: #4f46e5; color: #ffffff !important; font-weight: 600; }\n" + "    .muted { color: #666; font-size: 13px; }\n" + "    .footer { padding: 16px 24px; font-size: 12px; color: #888; background: #fafafa; }\n" + "    @media (max-width: 420px) { .content { padding: 16px; } .header { padding: 16px; } }\n" + "  </style>\n" + "</head>\n" + "<body>\n" + "  <div class=\"container\">\n" + "    <div class=\"header\">\n" + "      <h1>Password reset request</h1>\n" + "    </div>\n" + "    <div class=\"content\">\n" + "      <p>Hi {{name}},</p>\n" + "      <p>We received a request to reset the password for your account. Click the button below to set a new password. This link will expire in 1 hour.</p>\n" + "      <p style=\"text-align:center;\">\n" + "        <a class=\"button\" href=\"{{resetLink}}\" target=\"_blank\">Reset your password</a>\n" + "      </p>\n" + "      <p class=\"muted\">If the button doesn't work, copy and paste the following link into your browser:</p>\n" + "      <p class=\"muted\"><a href=\"{{resetLink}}\" target=\"_blank\">{{resetLink}}</a></p>\n" + "      <hr />\n" + "      <p class=\"muted\">If you didn't request a password reset, you can ignore this email — your account is safe.</p>\n" + "    </div>\n" + "    <div class=\"footer\">\n" + "      <div>Need help? Reply to this email or contact support at <a href=\"mailto:support@example.com\">support@example.com</a></div>\n" + "      <div style=\"margin-top:6px\">© " + java.time.Year.now().getValue() + " Your Company</div>\n" + "    </div>\n" + "  </div>\n" + "</body>\n" + "</html>";

    /**
     * Simple helper to produce final HTML by replacing placeholders.
     */
    public static String getForgotPasswordHtml(String name, String resetLink) {
        if (name == null || name.isBlank()) name = "there";
        return FORGOT_PASSWORD_EMAIL_HTML.replace("{{name}}", escapeHtml(name)).replace("{{resetLink}}", escapeHtml(resetLink));
    }


    // ✅ Leave status email template
    public static final String LEAVE_STATUS_EMAIL_HTML =
            "<!doctype html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"utf-8\">\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                    "  <title>Leave Request Update</title>\n" +
                    "  <style>\n" +
                    "    body { font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; background-color: #f6f9fc; margin: 0; padding: 0; color: #333; }\n" +
                    "    .container { max-width: 600px; margin: 32px auto; background: #ffffff; border-radius: 10px; box-shadow: 0 8px 24px rgba(0,0,0,0.08); overflow: hidden; }\n" +
                    "    .header { padding: 24px; text-align: center; font-size: 22px; font-weight: 700; color: #fff; border-bottom: 1px solid #e2e8f0; }\n" +
                    "\n" +
                    "    /* Status gradients (darker left → lighter right) */\n" +
                    "    .header.approved { background: linear-gradient(90deg, #1fcc41 0%, #9ff5b0 100%); }\n" +
                    "    .header.rejected { background: linear-gradient(90deg, #cc1912 0%, #ffbfbd 100%); }\n" +
                    "    .header.pending {background: linear-gradient(90deg, #fcd34d 0%, #fef08a 100%); } \n" +

                    "\n" +
                    "    .header h1 { margin: 0; font-size: 22px; font-weight: 700; }\n" +
                    "\n" +
                    "    .content { padding: 24px; font-size: 15px; line-height: 1.4; }\n" +
                    "    .content p { margin: 4px 0; }\n" +
                    "\n" +
                    "    .status.approved { color: #1fcc41; font-weight: 600; }\n" +
                    "    .status.rejected { color: #cc1912; font-weight: 600; }\n" +
                    "    .status.pending { color: #fcd34d; font-weight: 600; }\n" +
                    "\n" +
                    "    .muted { color: #6b7280; font-size: 13px; }\n" +
                    "    .footer { padding: 16px 24px; font-size: 12px; color: #9ca3af; background: #f9fafb; text-align: center; }\n" +
                    "    a { color: #4f46e5; text-decoration: none; }\n" +
                    "    hr { border: none; border-top: 1px solid #e5e7eb; margin: 16px 0; }\n" +
                    "\n" +
                    "    @media (max-width: 420px) {\n" +
                    "        .content { padding: 16px; }\n" +
                    "        .header { padding: 16px; font-size: 20px; }\n" +
                    "    }\n" +
                    "  </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "  <div class=\"container\">\n" +
                    "    <div class=\"header {{headerClass}}\">\n" +
                    "      <h1>Leave Request {{statusText}}</h1>\n" +
                    "    </div>\n" +
                    "    <div class=\"content\">\n" +
                    "      <p>Hi {{name}},</p>\n" +
                    "      <p>Your leave request has been <span class=\"status {{statusClass}}\">{{statusText}}</span>.</p>\n" +
                    "\n" +
                    "      <p><strong>Leave Type:</strong> {{leaveType}}</p>\n" +
                    "      <p><strong>From:</strong> {{startDate}} <strong>To:</strong> {{endDate}}</p>\n" +
                    "      <p><strong>Total Days:</strong> {{totalDays}}</p>\n" +
                    "      <p><strong>Approver:</strong> {{approverName}}</p>\n" +
                    "      <p><strong>Reason:</strong> {{reason}}</p>\n" +
                    "\n" +
                    "      <hr />\n" +
                    "      <p class=\"muted\">If you have any questions, please contact your manager or HR team.</p>\n" +
                    "    </div>\n" +
                    "    <div class=\"footer\">\n" +
                    "      HR Department | Your Company<br>© " + java.time.Year.now().getValue() + " Your Company\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "</body>\n" +
                    "</html>";






    // Dynamic method for generating leave status email using EmailLeaveDto
    public static String getLeaveStatusHtml(EmailLeaveDto leaveDto) {
        String headerClass;
        String statusClass;
        String statusText;

        if (leaveDto.getLeaveStatus() == null) leaveDto.setLeaveStatus(LeaveStatus.PENDING);

        switch (leaveDto.getLeaveStatus()) {
            case APPROVED:
                headerClass = "approved";
                statusClass = "approved";
                statusText = "Approved";
                break;
            case REJECTED:
                headerClass = "rejected";
                statusClass = "rejected";
                statusText = "Rejected";
                break;
            case PENDING:
            default:
                headerClass = "pending";
                statusClass = "pending";
                statusText = "Pending";
                break;
        }

        String startDate = leaveDto.getLeaveDays().isEmpty() ? "" :
                leaveDto.getLeaveDays().get(0).getLeaveDate().toString();
        String endDate = leaveDto.getLeaveDays().isEmpty() ? "" :
                leaveDto.getLeaveDays().get(leaveDto.getLeaveDays().size() - 1).getLeaveDate().toString();

        return LEAVE_STATUS_EMAIL_HTML
                .replace("{{name}}", escapeHtml(leaveDto.getEmployeeName()))
                .replace("{{startDate}}", escapeHtml(startDate))
                .replace("{{endDate}}", escapeHtml(endDate))
                .replace("{{totalDays}}", String.valueOf(leaveDto.getLeaveDaysCount()))
                .replace("{{approverName}}", escapeHtml(leaveDto.getApproverName()))
                .replace("{{leaveType}}", leaveDto.getLeaveType() != null ? leaveDto.getLeaveType().name() : "")
                .replace("{{reason}}", escapeHtml(leaveDto.getReason()))
                .replace("{{headerClass}}", headerClass)
                .replace("{{statusClass}}", statusClass)
                .replace("{{statusText}}", statusText);
    }







    // Very small helper to avoid accidental raw HTML injection — keeps it simple.
    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#x27;");
    }
}
