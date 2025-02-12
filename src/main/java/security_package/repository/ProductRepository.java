package security_package.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import security_package.models.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
