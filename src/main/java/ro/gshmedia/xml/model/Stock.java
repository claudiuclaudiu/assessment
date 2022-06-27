package ro.gshmedia.xml.model;

public class Stock {

    private int productId;
    private int quantity;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Stock(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
