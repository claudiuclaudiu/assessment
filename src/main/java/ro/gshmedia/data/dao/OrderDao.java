package ro.gshmedia.data.dao;

import ro.gshmedia.data.model.Order;
import ro.gshmedia.data.model.Status;

public interface OrderDao {

    Order saveOrder(Order order);

    Order getOrder(int id);

    Order updateOrderStatusById(int id, Status status);


}
