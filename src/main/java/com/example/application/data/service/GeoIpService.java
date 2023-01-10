package com.example.application.data.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLEncoder;

@Service
public class GeoIpService {

    /**
     * {
     *   "query": "24.48.0.1",
     *   "status": "success",
     *   "country": "Canada",
     *   "countryCode": "CA",
     *   "region": "QC",
     *   "regionName": "Quebec",
     *   "city": "Montreal",
     *   "zip": "H1K",
     *   "lat": 45.6085,
     *   "lon": -73.5493,
     *   "timezone": "America/Toronto",
     *   "isp": "Le Groupe Videotron Ltee",
     *   "org": "Videotron Ltee",
     *   "as": "AS5769 Videotron Telecom Ltee"
     * }
     */
    public record IpApiResponse(String city) {}

    WebClient client = WebClient.create("http://ip-api.com/json/");

    @Cacheable("ip-api-city")
    public String getCity(String ip) {
        // hack for localhost testing
        if(ip.startsWith("127") || ip.startsWith("0:")) {
            ip = ""; //ip-api uses request IP if parameter is null
        }
        IpApiResponse response = client.get().uri("{ip}",ip).retrieve()
                .bodyToMono(IpApiResponse.class).block();
        return response.city;
    }
}
