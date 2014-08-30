package zmuzik.czechstocks.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table STOCK_LIST_ITEM.
 */
public class StockListItem {

    private Long id;
    /** Not-null value. */
    private String isin;

    public StockListItem() {
    }

    public StockListItem(Long id) {
        this.id = id;
    }

    public StockListItem(Long id, String isin) {
        this.id = id;
        this.isin = isin;
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

}