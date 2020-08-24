package notificationmicro.Controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import notificationmicro.Services.InvoiceService;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    InvoiceService invoiceService;

    @GetMapping("/hello")
    public String hello(HttpServletRequest request){
        return "HELLO, I'M NOTIFICATION'S MICROSERVICE, RUNNING ON: " + request.getLocalPort();
    }
}