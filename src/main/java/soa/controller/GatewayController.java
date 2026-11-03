package soa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import soa.service.GatewayService;

@RestController
@RequestMapping("/")
public class GatewayController {

    private final GatewayService gatewayService;

    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @GetMapping("/{service}/{endpoint}")
    public Object invoke(
            @PathVariable String service,
            @PathVariable String endpoint) {

        return gatewayService.invokeService(service, endpoint);
    }
}