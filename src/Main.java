import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
//- Минимальное время полета между городами Владивосток и Тель-Авив для каждого авиаперевозчика
//- Разницу между средней ценой  и медианой для полета между городами Владивосток и Тель-Авив

public class Main {

    public static void jsonRead(String fileName){

        HashMap<String, Answear> carriers = new HashMap<>();
        int difPrice = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            Gson gson = new Gson();
            TicketsList ticketsList = gson.fromJson(new JsonReader(reader), TicketsList.class);
// Отфильтровать перевозки по VVO - TLV, занести в словарь и
            Calendar date1 = new GregorianCalendar();
            Calendar date2 = new GregorianCalendar();
            for (Ticket ticket: ticketsList.tickets) {
                date1.setTime(new SimpleDateFormat("dd.MM.yy HH:mm", Locale.ROOT).
                        parse(ticket.departure_date + " " + ticket.departure_time));
                date2.setTime(new SimpleDateFormat("dd.MM.yy HH:mm", Locale.ROOT).
                        parse(ticket.arrival_date + " " + ticket.arrival_time));
                long millis = date2.getTimeInMillis() - date1.getTimeInMillis();

                if (carriers.containsKey(ticket.carrier)) {
                    carriers.put(ticket.carrier, new Answear(millis, difPrice));
                }

                System.out.println(ticket.carrier + " " + millis);
            }

        } catch (IOException | ParseException e) {
            System.out.println("** Ошибка! ** Файл не найден.");
        }
    }

    public static void taskWriter(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {


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