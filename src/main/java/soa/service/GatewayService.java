package soa.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GatewayService {

    DiscoveryClient DC;
    int current = 0;

    private final Map<String, String> publicServiceUrls = Map.of(
	    "CARTSERVICE", System.getenv("CARTSERVICE_URL"),
	    "PRODUCTSERVICE", System.getenv("PRODUCTSERVICE_URL")
	);

    public GatewayService(DiscoveryClient DC) {
        this.DC = DC;
    }

    // Invoke the service
    public Object invokeService(String service, String endpoint) {

        List<ServiceInstance> services = DC.getInstances(service);

        current = (current + 1) % services.size();

        String baseUrl = publicServiceUrls.get(service.toUpperCase());

        String url = String.format("%s/%s", baseUrl, endpoint);

        RestClient client = RestClient.create();

        return client.get().uri(url).retrieve().body(String.class);
    }
}