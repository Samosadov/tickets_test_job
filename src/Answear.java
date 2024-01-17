import java.util.Date;

public class Answear {
    private Date minTime;
    private int difPrice;

    public Date getMinTime() {
        return minTime;
    }

    public void setMinTime(long minTime) {
        this.minTime = new Date(minTime);
    }

    public int getDifPrice() {
        return difPrice;
    }

    public void setDifPrice(int difPrice) {
        this.difPrice = difPrice;
    }

    public Answear(long minTime, int difPrice) {
        this.minTime = new Date(minTime);
        this.difPrice = difPrice;
    }

    @Override
    public String toString() {
        return "Минимальное время: " + minTime +
                "\nРазница между средней ценой и медианой: " + difPrice +
                '}';
    }
}
