package com.baeldung.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import com.baeldung.api.Booking;
import com.baeldung.api.BookingException;
import com.baeldung.api.CabBookingService;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class RmiClient {

    @Value("${rmi.port}")
    int port;

    @Bean
    RmiProxyFactoryBean service() {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        final String url = "rmi://localhost:@PORT@/CabBookingService".replaceAll("@PORT@", "" + port);
        rmiProxyFactory.setServiceUrl(url);
        System.out.println("Service URL is " + url);
        rmiProxyFactory.setServiceInterface(CabBookingService.class);
        return rmiProxyFactory;
    }

    public static void main(String[] args) throws BookingException {
        CabBookingService service = SpringApplication.run(RmiClient.class, args).getBean(CabBookingService.class);
        for (int i = 0; i < 5; i++) {
            try {
                Booking bookingOutcome = service.bookRide("13 Seagate Blvd, Key Largo, FL 33037");
                System.out.println(bookingOutcome);
            } catch (Exception e) {
                System.out.println("The book cannot be found because " + e.getMessage());
                e.printStackTrace(System.out);
            }
        }
    }

}
