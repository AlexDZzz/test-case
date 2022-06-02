package main.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Car repository.
 */
@Repository
public interface CarRepository extends CrudRepository<Car, Integer>
{

}
