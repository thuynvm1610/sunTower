package com.estate.api.payment;

import com.estate.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PaymentAPI {

    @Autowired
    private VnPayService vnPayService;

    @GetMapping(value = "/payment/vnpay/{invoiceId}", produces = "text/plain; charset=UTF-8")
    public String createVnPay(@PathVariable Long invoiceId, HttpServletRequest request) {
        return vnPayService.createPayment(invoiceId, request).getPaymentUrl();
    }

    @GetMapping(value = "/payment/vnpay-return", produces = "text/html; charset=UTF-8")
    public String vnpayReturn(@RequestParam Map<String, String> params) {

        boolean success = false;

        if (params != null && !params.isEmpty() && params.containsKey("vnp_ResponseCode")) {
            success = vnPayService.handleReturn(params);
        }

        String redirectUrl = success
                ? "/customer/invoice/list?paySuccess"
                : "/customer/invoice/list?payFail";

        return """
                <html>
                  <head><meta charset="utf-8"/></head>
                  <body>
                    <script>
                      window.location.href = '%s';
                    </script>
                  </body>
                </html>
                """.formatted(redirectUrl);
    }

    @GetMapping("/payment/vnpay-ipn")
    public Map<String, String> ipn(@RequestParam Map<String, String> params) {
        boolean ok = vnPayService.handleReturn(params);

        if (ok) return Map.of("RspCode", "00", "Message", "Confirm Success");
        return Map.of("RspCode", "97", "Message", "Invalid Signature");
    }
}
