import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    private static float avgPrice = 0F;
    private static String answear;
    private static final ArrayList<Integer> prices = new ArrayList<>();
    private static final HashMap<String, Long> carriers = new HashMap<>();

    public static void jsonRead(String fileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            Gson gson = new Gson();
            TicketsList ticketsList = gson.fromJson(new JsonReader(reader), TicketsList.class);
            Calendar date1 = new GregorianCalendar();
            Calendar date2 = new GregorianCalendar();
            // Перебор данных
            for (Ticket ticket: ticketsList.tickets) {
                date1.setTime(new SimpleDateFormat("dd.MM.yy HH:mm", Locale.ROOT).
                        parse(ticket.departure_date + " " + ticket.departure_time));
                date2.setTime(new SimpleDateFormat("dd.MM.yy HH:mm", Locale.ROOT).
                        parse(ticket.arrival_date + " " + ticket.arrival_time));
                long millis = date2.getTimeInMillis() - date1.getTimeInMillis();

                if (ticket.origin.equals("VVO") && ticket.destination.equals("TLV")) {
                    prices.add(ticket.price);

                    if (!carriers.containsKey(ticket.carrier)) {
                        carriers.put(ticket.carrier, millis);
                    }
                    else {
                        if (millis < carriers.get(ticket.carrier)) {
                            carriers.replace(ticket.carrier, millis);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("** Ошибка! ** Файл не найден.");
        } catch (ParseException e) {
            System.out.println("** Ошибка! ** Не могу прочитать файл.");
        }

    }

    public static void calculate() {
        prices.sort(Comparator.naturalOrder());
        for (Integer i : prices) {
            avgPrice += i;
        }
        avgPrice /= prices.size();
        int index = prices.size() / 2;
        float median = (prices.size() % 2 == 1) ? prices.get(index) : (float) (prices.get(index - 1) + prices.get(index)) / 2;

        float difPrice = avgPrice - median;

        String difPriceStr = new DecimalFormat("0.00").format(difPrice);
        String avgPriceStr = new DecimalFormat("0.00").format(avgPrice);
        String medianStr = new DecimalFormat("0.00").format(median);

        answear = "\nСредняя цена билета: " + avgPriceStr +
                "\nМедиана цены полёта: " + medianStr +
                "\nРазница между средней ценой и медианой: " + difPriceStr;
    }

    public static void taskWriter(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String key : carriers.keySet()) {

                long mins = TimeUnit.MILLISECONDS.toMinutes(carriers.get(key)) % 60;
                String minsStr = new DecimalFormat("00").format(mins);
                long hours = TimeUnit.MILLISECONDS.toHours(carriers.get(key));
                String hoursStr = new DecimalFormat("00").format(hours);
                writer.write("Перевозчик: " + key +
                        "\nМинимальное время перелёта: " + hoursStr + ":" + minsStr + '\n');
            }
            writer.write(answear);
            writer.flush();

        } catch (IOException e) {
            System.out.println("** Ошибка! ** Файл не создан.");
        }
    }

    public static void main(String[] args) {
        String fileName = "tickets.json";
        jsonRead(fileName);
        calculate();
        taskWriter("outdata.txt");
    }
}