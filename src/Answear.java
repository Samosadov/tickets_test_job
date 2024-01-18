import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public class Answear {
    private long minTime;
    private int difPrice;
    private float avgPrice;
    private float median;
    private TreeSet<Integer> prices = new TreeSet<>();

    public Answear(long minTime, int price) {
        this.minTime = minTime;
        prices.add(price);
        avgPrice = 0.0F;
        median = 0.0F;
    }

    public float getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(int price) {
        prices.add(price);
        for (Integer i : prices) {
            avgPrice += i;
        }
        avgPrice /= prices.size();
//        median = (prices.size() % 2 == 1) ? prices. :;   Вычислить медиану
    }

    public float getMedian() {
        return median;
    }

    public void setMedian(int median) {
        this.median = median;
    }

    public long getMinTime() {
        return minTime;
    }

    public void setMinTime(long minTime) {
        this.minTime = minTime;
    }

    public int getDifPrice() {
        return difPrice;
    }

    public void setDifPrice(int difPrice) {
        this.difPrice = difPrice;
    }

    @Override
    public String toString() {
        long mins = TimeUnit.MILLISECONDS.toMinutes(minTime) % 60;
        long hours = TimeUnit.MILLISECONDS.toHours(minTime);
        return "Минимальное время перелёта: " + hours + ":" + mins +
                "\nРазница между средней ценой и медианой: " + difPrice + '\n';
    }
}
