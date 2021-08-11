package com.baeldung.server;

import com.baeldung.api.CabBookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;


@SpringBootApplication
public class RmiServer {

    @Autowired ConfigProperties properties;

    @Bean CabBookingService bookingService() {
        return new CabBookingServiceImpl();
    }

    @Bean RmiServiceExporter exporter(CabBookingService implementation) {

        // Expose a service via RMI. Remote obect URL is:
        // rmi://<HOST>:<PORT>/<SERVICE_NAME>
        // 1099 is the default port

        Class<CabBookingService> serviceInterface = CabBookingService.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(implementation);
        exporter.setServiceName(serviceInterface.getSimpleName());
        exporter.setRegistryPort(properties.getPort());
        System.out.println("Expose a service via RMI. Remote object URL is: " + "rmi://HOST:" + properties.getPort() + "/CabBookingService");
        return exporter;
    }

    public static void main(String[] args) {
        SpringApplication.run(RmiServer.class, args);
    }

}
