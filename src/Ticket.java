import java.util.Date;

public class Ticket {
        public String origin;
        public String origin_name;
        public String destination;
        public String destination_name;
        public String departure_date;
        public String departure_time;
        public String arrival_date;
        public String arrival_time;
        public String carrier;
        public int stops;

        @Override
        public String toString() {
                return "Ticket{" +
                        "origin='" + origin + '\'' +
                        ", origin_name='" + origin_name + '\'' +
                        ", destination='" + destination + '\'' +
                        ", destination_name='" + destination_name + '\'' +
                        ", departure_date='" + departure_date + '\'' +
                        ", departure_time='" + departure_time + '\'' +
                        ", arrival_date='" + arrival_date + '\'' +
                        ", arrival_time='" + arrival_time + '\'' +
                        ", carrier='" + carrier + '\'' +
                        ", stops=" + stops +
                        ", price=" + price +
                        '}' + '\n';
        }

        public int price;
}
