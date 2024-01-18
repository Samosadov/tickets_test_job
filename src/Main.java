import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
//    private static TreeMap<String, Answear> carriers = new TreeMap<>(Comparator.comparingInt(o -> o.getPrice));
    private static HashMap<String, Answear> carriers = new HashMap<>();

    public static void jsonRead(String fileName){
        int difPrice = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            Gson gson = new Gson();
            TicketsList ticketsList = gson.fromJson(new JsonReader(reader), TicketsList.class);
            Calendar date1 = new GregorianCalendar();
            Calendar date2 = new GregorianCalendar();
            for (Ticket ticket: ticketsList.tickets) {
                date1.setTime(new SimpleDateFormat("dd.MM.yy HH:mm", Locale.ROOT).
                        parse(ticket.departure_date + " " + ticket.departure_time));
                date2.setTime(new SimpleDateFormat("dd.MM.yy HH:mm", Locale.ROOT).
                        parse(ticket.arrival_date + " " + ticket.arrival_time));
                long millis = date2.getTimeInMillis() - date1.getTimeInMillis();

//                TreeMap<Person, Integer> map = new TreeMap<>(Comparator.comparingInt(o -> o.age));

                if (!carriers.containsKey(ticket.carrier)) {
                        if (ticket.origin.equals("VVO") && ticket.destination.equals("TLV")) {
                            carriers.put(ticket.carrier, new Answear(millis, ticket.price));
//                            carriers.get(ticket.carrier).setAvgPrice(ticket.price);
                        }
                }
                else {
                    if ((ticket.origin.equals("VVO") && ticket.destination.equals("TLV"))
                            && millis < carriers.get(ticket.carrier).getMinTime()) {
                        carriers.get(ticket.carrier).setMinTime(millis);
                        carriers.get(ticket.carrier).setAvgPrice(ticket.price);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("** Ошибка! ** Файл не найден.");
        } catch (ParseException e) {
            System.out.println("** Ошибка! ** Не могу прочитать файл.");
        }
    }

    public static void taskWriter(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String key : carriers.keySet()) {
                writer.write("Перевозчик: " + key + '\n' + carriers.get(key));
            }
            writer.flush();

        } catch (IOException e) {
            System.out.println("** Ошибка! ** Файл не создан.");
        }
    }

    public static void main(String[] args) {
        String fileName = "c:/job/job_test1/tickets.json";
        jsonRead(fileName);

        taskWriter("outdata.txt");
    }
}