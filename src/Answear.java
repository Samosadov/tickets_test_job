import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;

public class Answear {
    private long minTime;
    private float difPrice;
    private float avgPrice;
    private float median;
    private final ArrayList<Integer> prices = new ArrayList<>();

    public Answear(long minTime) {
        this.minTime = minTime;
    }
    public Answear(long minTime, int price) {
        this(minTime);
        addValue(price);
    }

    public void addValue(int price) {
        prices.add(price);
        prices.sort(Comparator.naturalOrder());
        avgPrice = 0F;
        for (Integer i : prices) {
            avgPrice += i;
        }
        avgPrice /= prices.size();
        int index = prices.size() / 2;
        median = (prices.size() % 2 == 1) ? prices.get(index) : (prices.get(index - 1) + prices.get(index)) / 2;
        difPrice = abs(avgPrice - median);
    }

    public float getAvgPrice() {
        return avgPrice;
    }

    public float getMedian() {
        return median;
    }

    public long getMinTime() {
        return minTime;
    }

    public void setMinTime(long minTime) {
        this.minTime = minTime;
    }

    public float getDifPrice() {
        return difPrice;
    }

    public ArrayList<Integer> getPrices() {
        return prices;
    }

    @Override
    public String toString() {
        long mins = TimeUnit.MILLISECONDS.toMinutes(minTime) % 60;
        String minsStr = new DecimalFormat("00").format(mins);
        long hours = TimeUnit.MILLISECONDS.toHours(minTime);
        String hoursStr = new DecimalFormat("00").format(hours);

        String difPriceStr = new DecimalFormat("0.00").format(difPrice);
        String avgPriceStr = new DecimalFormat("0.00").format(avgPrice);
        String medianStr = new DecimalFormat("0.00").format(median);

        return "Минимальное время перелёта: " + hoursStr + ":" + minsStr +
                "\nСредняя цена билета: " + avgPriceStr +
                "\nМедиана цены полёта: " + medianStr +
                "\nРазница между средней ценой и медианой: " + difPriceStr +'\n';
    }
}
