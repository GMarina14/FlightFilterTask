package com.gridline.testing.service;

import com.gridline.testing.constants.FlightDuration;
import com.gridline.testing.model.Flight;
import com.gridline.testing.model.Segment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightFilterServiceTest {
    private List<Flight> test = new ArrayList<>() {{
        // flight with arrival before departure
        add(new Flight(List.of(new Segment(LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5).minusHours(4)))));
        // normal flight
        add(new Flight(List.of(new Segment(LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(3)),
                new Segment(LocalDateTime.now().plusDays(2).plusHours(4), LocalDateTime.now().plusDays(2).plusHours(10)))));
        // flight with department in the past
        add(new Flight(List.of(new Segment(LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(2).plusHours(12)))));
        // normal flight
        add(new Flight(List.of(new Segment(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(1)))));
        // flight with long transfer
        add(new Flight(List.of(new Segment(LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(3)),
                new Segment(LocalDateTime.now().plusDays(2).plusHours(6), LocalDateTime.now().plusDays(2).plusHours(10)))));
    }};


    @Test
    public void shouldReturnFlightsWithoutDeparturesBeforeToday() {
        //when
        List<Flight> expected = List.of(test.get(0), test.get(1), test.get(3), test.get(4));
        List<Flight> actual = FlightFilterService.excludeFlights(test, FlightDuration.DEPARTURE_BEFORE_CURRENT_TIME);

        //then
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void shouldReturnFlightsWithoutArrivalsBeforeDeparture() {
        //when
        List<Flight> expected = List.of(test.get(1), test.get(2), test.get(3), test.get(4));
        List<Flight> actual = FlightFilterService.excludeFlights(test, FlightDuration.ARRIVALS_BEFORE_DEPARTURE);

        //then
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void shouldReturnWithoutLongTransfers() {
        //when
        List<Flight> expected = List.of(test.get(1));
        List<Flight> actual = FlightFilterService.excludeFlights(test, FlightDuration.LONG_TRANSFER_FLIGHTS);

        //then
        Assertions.assertIterableEquals(expected, actual);
    }
}
