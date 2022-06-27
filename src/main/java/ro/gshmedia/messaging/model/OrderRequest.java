package ro.gshmedia.messaging.model;

import java.util.List;

public class OrderRequest {

    private String clientName;
    private List<Item> items;


    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
