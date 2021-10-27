package client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import business.utils.DateUtils;
import facade.dtos.DayPeriod;
import facade.dtos.SeatDTO;
import facade.exceptions.ApplicationException;
import facade.services.AssignVenueService;
import facade.services.DailyTicketPurchaseService;
import facade.services.AddEventService;
import facade.services.TicketPassPurchaseService;
import facade.startup.EventSys;

public class SimpleClient {

	private static final String delim = "====================================";

	public static void main (String [] args) {

		EventSys app = new EventSys();

		try {

			app.run();
			AddEventService es = app.getEventService();

			// New tests (Anexo Projeto)

			List<DayPeriod> days = new ArrayList<>();

			// Test 1:

			System.out.println(delim + " T1 " + delim);

			DayPeriod d1 = new DayPeriod (DateUtils.fakeParse("09/05/2021 21:00"), DateUtils.fakeParse("09/05/2021 23:59")); // E SUPOSTO SER 0,0,0 MAS DA PROBLEMA! E PRECISO MUDAR O OVERLAP PARA CASO DA MEIANOITE
			days.add(d1);
			createNewEvent(es, "Bye Semestre X", "TeteATete", 1, days);

			System.out.println(delim + " T2 " + delim);

			// Test 2:	

			days = new ArrayList<>();
			d1 = new DayPeriod (DateUtils.fakeParse("09/05/2021 20:00"), DateUtils.fakeParse("09/05/2021 22:00"));
			days.add(d1);
			createNewEvent(es, "Bye Semestre Y", "TeteATete", 1, days);

			System.out.println(delim + " T3 " + delim);

			// Test 3:

			days = new ArrayList<>();
			d1 = new DayPeriod (DateUtils.fakeParse("17/07/2021 21:00"), DateUtils.fakeParse("17/07/2021 23:30"));
			DayPeriod d2 = new DayPeriod (DateUtils.fakeParse("18/07/2021 15:00"), DateUtils.fakeParse("18/07/2021 20:00"));
			days.add(d1);
			days.add(d2);
			createNewEvent(es, "Open dos Exames", "BandoSentado", 1, days);

			System.out.println(delim + " T4 " + delim);

			// Test 4:

			days = new ArrayList<>();
			d1 = new DayPeriod (DateUtils.fakeParse("31/07/2021 21:00"), DateUtils.fakeParse("31/07/2021 23:00"));
			d2 = new DayPeriod (DateUtils.fakeParse("01/08/2021 14:00"), DateUtils.fakeParse("01/08/2021 19:00"));
			days.add(d1);
			days.add(d2);
			try {
				createNewEvent(es, "Festival Estou de Ferias", "MultidaoEmPe", 1, days);
			} catch (ApplicationException e) {
				System.err.println("Error: " + e.getMessage());
				if (e.getCause() != null) {
					System.err.println("Cause: " + e.getCause());
				}
			}
			
			System.out.println(delim + " T5 " + delim);

			// Test 5:

			days = new ArrayList<>();
			d1 = new DayPeriod (DateUtils.fakeParse("31/12/2021 21:00"), DateUtils.fakeParse("31/12/2021 23:00"));
			d2 = new DayPeriod (DateUtils.fakeParse("01/08/2021 14:00"), DateUtils.fakeParse("01/08/2021 19:00") );
			days.add(d1);
			days.add(d2);
			try {
				createNewEvent(es, "Festival Estou de Ferias", "MultidaoEmPe", 2, days);
			} catch (ApplicationException e) {
				System.err.println("Error: " + e.getMessage());
				if (e.getCause() != null) {
					System.err.println("Cause: " + e.getCause());
				}
			}

			System.out.println(delim + " T6 " + delim);

			// Test 6:

			days = new ArrayList<>();
			d1 = new DayPeriod (DateUtils.fakeParse("31/07/2021 21:00"), DateUtils.fakeParse("31/07/2021 23:00"));
			d2 = new DayPeriod (DateUtils.fakeParse("01/08/2021 14:00"), DateUtils.fakeParse("01/08/2021 19:00"));
			days.add(d1);
			days.add(d2);
			createNewEvent(es, "Festival Estou de Ferias", "MultidaoEmPe", 2, days);

			System.out.println(delim + " T7 " + delim);

			// Test 7
			try {
				startVenueAssignment(app, "Bye Semestre X", "Mini Estadio", LocalDate.of(2021, 5, 1), 5, 7);
			} catch (ApplicationException e) {
				System.err.println("Error: " + e.getMessage());
				if (e.getCause() != null) {
					System.err.println("Cause: " + e.getCause());
				}
			}

			System.out.println(delim + " T8 " + delim);

			// Test 8 
			startVenueAssignment(app, "Bye Semestre X", "Micro Pavilhao", LocalDate.of(2021, 5, 1), 20);
			System.out.println(delim + " T9 " + delim);

			// Test 9
			try {
				startVenueAssignment(app, "Bye Semestre Y", "Micro Pavilhao", LocalDate.of(2021, 5, 1), 20);
			} catch (ApplicationException e) {
				System.err.println("Error: " + e.getMessage());
				if (e.getCause() != null) {
					System.err.println("Cause: " + e.getCause());
				}
			}
			System.out.println(delim + " T10 " + delim);

			// Test 10
			startVenueAssignment(app, "Open dos Exames", "Mini Estadio", LocalDate.of(2021, 5, 1), 20, 30);
			System.out.println(delim + " T11 " + delim);

			// Test 11
			startVenueAssignment(app, "Festival Estou de Ferias", "Pequeno Relvado", LocalDate.of(2021, 5, 1), 15);

			System.out.println(delim + " T12 " + delim);

			List<SeatDTO> seats = Arrays.asList(new SeatDTO ("A",1), new SeatDTO ("A",2), new SeatDTO ("B",1));
			makeDailyTicketReservation (app, "Bye Semestre X", LocalDate.of(2021, 5, 9), seats, "u1@gmail.com");

			System.out.println(delim + " T13 " + delim);

			seats = Arrays.asList(new SeatDTO ("B",1));
			try {
				makeDailyTicketReservation (app, "Bye Semestre X", LocalDate.of(2021, 5, 9), seats, "u2@gmail.com");
			} catch (ApplicationException e) {
				System.err.println("Error: " + e.getMessage());
				if (e.getCause() != null) {
					System.err.println("Cause: " + e.getCause());
				}
			}

			System.out.println(delim + " T14 " + delim);

			seats = Arrays.asList(new SeatDTO ("B",2));
			makeDailyTicketReservation (app, "Bye Semestre X", LocalDate.of(2021, 5, 9), seats, "u2@gmail.com");

			System.out.println(delim + " T15 " + delim);

			try {
				makeDailyTicketReservation (app, "Festival Estou de Ferias", LocalDate.of(2021, 5, 9), seats, "u2@gmail.com");
			} catch (ApplicationException e) {
				System.err.println("Error: " + e.getMessage());
				if (e.getCause() != null) {
					System.err.println("Cause: " + e.getCause());
				}
			}

			System.out.println(delim + " T16 " + delim);

			seats = Arrays.asList(new SeatDTO ("A",1), new SeatDTO ("A",2));
			makeDailyTicketReservation (app, "Open dos Exames", LocalDate.of(2021, 7, 17), seats, "u3@gmail.com");

			System.out.println(delim + " T17 " + delim);

			int amount = 2;
			makeTicketPassReservation (app, "Open dos Exames", amount, "u4@gmail.com");

			System.out.println(delim + " T18 " + delim);

			amount = 3;
			makeTicketPassReservation (app, "Open dos Exames", amount, "u5@gmail.com");

			System.out.println(delim + " T19 " + delim);

			amount = 7;
			try {
				makeTicketPassReservation (app, "Festival Estou de Ferias", amount, "u6@gmail.com");
			} catch (ApplicationException e) {
				System.err.println("Error: " + e.getMessage());
				if (e.getCause() != null) {
					System.err.println("Cause: " + e.getCause());
				}
			}

			System.out.println(delim + " T20 " + delim);

			amount = 4;
			try {
				makeTicketPassReservation (app, "Festival Estou de Ferias", amount, "u5@gmail.com");
			} catch (ApplicationException e) {
				System.err.println("Error: " + e.getMessage());
				if (e.getCause() != null) {
					System.err.println("Cause: " + e.getCause());
				}
			}


			app.stopRun();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void makeTicketPassReservation (EventSys app, String eventDesig, int desiredAmount, String userEmail) throws ApplicationException {
		TicketPassPurchaseService tpps = app.getTicketPassPurchaseService();
		int n = tpps.startTicketPassReservation(eventDesig);
		System.out.println("Number of available ticket passes for the event \"" + eventDesig + "\": " + n);
		System.out.println("Quantidade escolhida: " + desiredAmount);
		System.out.println("\nPayment Details:\n" + tpps.chooseTicketPassQuantity(desiredAmount, userEmail));
		System.out.println("\nSuccesfully made the reservation");
	}

	private static void makeDailyTicketReservation (EventSys app, String eventDesig, LocalDate date, List<SeatDTO> seats, String userEmail ) throws ApplicationException {

		DailyTicketPurchaseService tps = app.getDailyTicketPurchaseService();
		List<LocalDate> dates = tps.startDailyTicketReservation(eventDesig);

		System.out.println("Available Dates for the event \"" + eventDesig + "\":");
		for (LocalDate d : dates)
			System.out.println(d);

		List<SeatDTO> orderedSeats = tps.chooseDate(date);
		
		System.out.println("\nAvailable Seats for " + date + ":\n" + orderedSeats);

		System.out.println("\nSeats chosen by user: " + seats);

		System.out.println("Payment details:\n" + tps.chooseSeats(seats, userEmail));

		System.out.println("\nSuccesfully made the reservation");
	}

	private static void createNewEvent (AddEventService es, String eventName, String eventType, int companyId, List<DayPeriod> days ) throws ApplicationException {
		System.out.println("Event Types:");
		System.out.println(es.createNewEvent());
		System.out.println("Creating new event named \"" + eventName + "\", of type " + eventType);
		es.addEvent(eventName, eventType, companyId, days);
		System.out.println("Event sucessfully added!");
	}

	private static void startVenueAssignment (EventSys app, String eventDesignation, String venueName, LocalDate startSellingDate, double ticketPrice, double ticketPassPrice) throws ApplicationException {
		AssignVenueService vs = app.getVenueService();
		System.out.println("Venues:");
		System.out.println(vs.startVenueAssignment());
		System.out.println("Assigning venue named " + venueName + " to event \"" + eventDesignation + "\"...");
		vs.assignVenueToEvent(eventDesignation, venueName, startSellingDate, ticketPrice);
		vs.allowTicketPasses(ticketPassPrice);
		System.out.println("Venue sucessfully assigned!");
	}

	private static void startVenueAssignment (EventSys app, String eventDesignation, String venueName, LocalDate startSellingDate, double ticketPrice) throws ApplicationException {
		AssignVenueService vs = app.getVenueService();
		System.out.println("Venues:");
		System.out.println(vs.startVenueAssignment());
		System.out.println("Assigning venue named " + venueName + " to event \"" + eventDesignation + "\"...");
		vs.assignVenueToEvent(eventDesignation, venueName, startSellingDate, ticketPrice);
		System.out.println("Venue sucessfully assigned!");
	}

}