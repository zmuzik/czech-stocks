package zmuzik.czechstocks.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table PORTFOLIO_ITEM.
 */
public class PortfolioItem {

    private Long id;
    /** Not-null value. */
    private String isin;
    private double price;
    private int quantity;

    public PortfolioItem() {
    }

    public PortfolioItem(Long id) {
        this.id = id;
    }

    public PortfolioItem(Long id, String isin, double price, int quantity) {
        this.id = id;
        this.isin = isin;
        this.price = price;
        this.quantity = quantity;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}