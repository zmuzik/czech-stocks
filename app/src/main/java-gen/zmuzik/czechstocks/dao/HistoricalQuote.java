package zmuzik.czechstocks.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table HISTORICAL_QUOTE.
 */
public class HistoricalQuote {

    private Long id;
    /** Not-null value. */
    private String isin;
    private long stamp;
    private double price;
    private double volume;

    public HistoricalQuote() {
    }

    public HistoricalQuote(Long id) {
        this.id = id;
    }

    public HistoricalQuote(Long id, String isin, long stamp, double price, double volume) {
        this.id = id;
        this.isin = isin;
        this.stamp = stamp;
        this.price = price;
        this.volume = volume;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getIsin() {
        return isin;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setIsin(String isin) {
        this.isin = isin;
    }

    public long getStamp() {
        return stamp;
    }

    public void setStamp(long stamp) {
        this.stamp = stamp;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

}
