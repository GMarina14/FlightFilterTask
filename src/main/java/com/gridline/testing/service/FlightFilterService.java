package com.gridline.testing.service;

import com.gridline.testing.constants.FlightDuration;
import com.gridline.testing.model.Flight;
import com.gridline.testing.model.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FlightFilterService {

    public static List<Flight> excludeFlights(List<Flight> flights, LocalDateTime dateOfTheFlight, FlightDuration flightDuration) {
        List<Flight> result = new ArrayList<>();

        // format the given date, so it can be compared to segments' date
        dateOfTheFlight.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));


        switch (flightDuration) {
            case DEPARTURE_BEFORE_CURRENT_TIME:
                result = excludeFlightsWithDepartureBeforeCurrentDate(flights, dateOfTheFlight);
                break;

            case ARRIVALS_BEFORE_DEPARTURE:
                result = excludeFlightsWithArrivalsBeforeDeparture(flights);
                break;

            case LONG_TRANSFER_FLIGHTS:
                result = excludeFlightsWithLongTransfers(flights);
                break;
        }
        return result;
    }

    public static List<Flight> excludeFlights(List<Flight> flights, FlightDuration flightDuration) {
        return excludeFlights(flights, LocalDateTime.now(), flightDuration);
    }


    /**
     * Method excludes segments with departure date before the needed date
     *
     * @param flights
     * @param date
     * @return rez
     */
    private static List<Flight> excludeFlightsWithDepartureBeforeCurrentDate(List<Flight> flights, LocalDateTime date) {
        List<Flight> rez = new ArrayList<>();
        for (Flight flight : flights) {
            List<Segment> segments = flight.getSegments();
            List<Segment> seg = new ArrayList<>();

            for (int i = 0; i < segments.size(); i++) {
                if (segments.get(i).getDepartureDate().isAfter(date)) {
                    seg.add(segments.get(i));
                }
            }
            if (!seg.isEmpty()) {
                rez.add(new Flight(seg));
            }
        }
        return rez;
    }

    /**
     * Method excludes segments with arrival date before departure date
     *
     * @param flights
     * @return rez
     */
    private static List<Flight> excludeFlightsWithArrivalsBeforeDeparture(List<Flight> flights) {
        List<Flight> rez = new ArrayList<>();
        for (Flight flight : flights) {
            List<Segment> segments = flight.getSegments();
            List<Segment> seg = new ArrayList<>();

            for (int i = 0; i < segments.size(); i++) {
                if (segments.get(i).getArrivalDate().isAfter(segments.get(i).getDepartureDate())) {
                    seg.add(segments.get(i));
                }
            }
            if (!seg.isEmpty()) {
                rez.add(new Flight(seg));
            }
        }
        return rez;
    }

    /**
     * Method excludes flights, if there is two and more hours of waiting between segments (i.e. before next flight)
     *
     * @param flights
     * @return rez
     */
    private static List<Flight> excludeFlightsWithLongTransfers(List<Flight> flights) {
        List<Flight> rez = new ArrayList<>();

        for (Flight flight : flights) {
            List<Segment> segments = flight.getSegments();
            List<Segment> seg = new ArrayList<>();

            for (int i = 1; i < segments.size(); i++) {
                if ((Duration.between(segments.get(i - 1).getArrivalDate(), (segments.get(i).getDepartureDate()))).toHours() < 2) {
                    rez.add(flight);
                }
            }
        }
        return rez;
    }
}
