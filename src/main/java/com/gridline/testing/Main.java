package com.gridline.testing;

import com.gridline.testing.constants.FlightDuration;
import com.gridline.testing.model.Flight;
import com.gridline.testing.model.FlightBuilder;
import com.gridline.testing.service.FlightFilterService;

import java.time.LocalDateTime;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("Without flights with departure date before today");
        List<Flight> withoutIncorrectDates = FlightFilterService.excludeFlights(flights, LocalDateTime.now(), FlightDuration.DEPARTURE_BEFORE_CURRENT_TIME);
        for (Flight withoutIncorrectDate : withoutIncorrectDates) {
            System.out.println(withoutIncorrectDate);
        }

        System.out.println();
        System.out.println("Without flights with incorrect data: departure date is after arrival date");
        List<Flight> withoutArrivalsBeforeDeparture = FlightFilterService.excludeFlights(flights, LocalDateTime.now(), FlightDuration.ARRIVALS_BEFORE_DEPARTURE);
        for (Flight flight : withoutArrivalsBeforeDeparture) {
            System.out.println(flight);
        }

        System.out.println();
        System.out.println("Without flights with long transfers: 2 and more hours between flights (segments)");
        List<Flight> withoutLongTransfers = FlightFilterService.excludeFlights(flights, LocalDateTime.now(), FlightDuration.LONG_TRANSFER_FLIGHTS);
        for (Flight withoutLongTransfer : withoutLongTransfers) {
            System.out.println(withoutLongTransfer);
        }
    }
}
