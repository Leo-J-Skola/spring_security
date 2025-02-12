package security_package.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import security_package.models.Order;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByCustomerId(String customerId);
}
