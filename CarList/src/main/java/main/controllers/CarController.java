package main.controllers;

import main.model.Car;
import main.model.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Car controller.
 */
@RestController
public class CarController
{
    /**
     * The logger.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarRepository carRepository;

    /**
     * Add car response entity.
     *
     * @param car the car
     * @return the response entity
     */
    @PostMapping("/cars/")
    public ResponseEntity<Integer> addCar(Car car)
    {
        if(car.isCorrect()){
            try {
                carRepository.save(car);
                LOGGER.info("addCar:success, id:" + car.getId());
                return new ResponseEntity<>(car.getId(), HttpStatus.CREATED);
            }
            catch (Exception e){
                LOGGER.error(e.getLocalizedMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        LOGGER.warn("addCar:failed, requested:" + car);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    /**
     * Car list response entity.
     *
     * @return the response entity
     */
    @GetMapping("/cars/")
    public ResponseEntity<List<Car>> carList()
    {
        Iterable<Car> carIterable = carRepository.findAll();
        ArrayList<Car> carArrayList = new ArrayList<>();
        try {
            for(Car car : carIterable){
                carArrayList.add(car);
            }
            if (carArrayList.isEmpty())
            {
                LOGGER.warn("carList:failed, no data");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
        catch (Exception e){
            LOGGER.warn("carList:failed, cause:" + e.getCause());
        }
        LOGGER.info("carList:success");
        return new ResponseEntity<>(carArrayList, HttpStatus.OK);
    }

    /**
     * Gets car.
     *
     * @param id the id
     * @return the car
     */
    @GetMapping("/cars/id={id}")
    public ResponseEntity<Car> getCar(@PathVariable int id)
    {
        Optional<Car> optionalCar = carRepository.findById(id);
        if(!optionalCar.isPresent()){
            LOGGER.warn("getCar:failed, no data, requested id" + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        LOGGER.info("getCar:success, requested id:" + id);
        return new ResponseEntity<>(optionalCar.get(), HttpStatus.OK);
    }

    /**
     * Remove car response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/cars/id={id}")
    public ResponseEntity<Integer> removeCar(@PathVariable int id)
    {
        Optional<Car> optionalCar = carRepository.findById(id);
        if(!optionalCar.isPresent()){
            LOGGER.warn("removeCar:failed, no data, requested id:" + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        try {
            carRepository.deleteById(id);
            LOGGER.info("removeCar:success, requested id:" + id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        catch (Exception e){
            LOGGER.error("removeCar:failed, cause: " + e.getCause());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Remove all cars response entity.
     *
     * @return the response entity
     */
    @DeleteMapping("/cars/")
    public ResponseEntity<Long> removeAllCars()
    {
        long totalCarsAmount = carRepository.count();
        try {
            carRepository.deleteAll();
            LOGGER.info("removeAllCars:success");
            return new ResponseEntity<>(totalCarsAmount, HttpStatus.OK);
        }
        catch (Exception e){
            LOGGER.error("removeAllCars:failed, cause:" + e.getCause());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Sets car colour.
     *
     * @param id     the id
     * @param colour the colour
     * @return the car colour
     */
    @PutMapping("/cars/id={id}-set-colour={colour}")
    public ResponseEntity<Integer> setCarColour(@PathVariable int id, @PathVariable String colour)
    {
        Optional<Car> optionalCar = carRepository.findById(id);
        try {
            if(!optionalCar.isPresent()){
                LOGGER.warn("setCarColour:failed, no data, requested id:" + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            Car updatedCar = optionalCar.get();
            updatedCar.setColour(colour);
            carRepository.save(updatedCar);
            LOGGER.info("setCarColour:success, requested id:" + id + " new value:" + colour);
            return new ResponseEntity<>(optionalCar.get().getId(), HttpStatus.OK);
        }
        catch (Exception e){
            LOGGER.error("setCarColour:failed, cause:" + e.getCause());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Sets car license number.
     *
     * @param id            the id
     * @param licenseNumber the license number
     * @return the car license number
     */
    @PutMapping("/cars/id={id}-set-license-number={licenseNumber}")
    public ResponseEntity<Integer> setCarLicenseNumber(@PathVariable int id, @PathVariable String licenseNumber)
    {
        Optional<Car> optionalCar = carRepository.findById(id);
        try {
            if(!optionalCar.isPresent()){
                LOGGER.warn("setCarLicenseNumber:failed, no data, requested id:" + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            Car updatedCar = optionalCar.get();
            updatedCar.setLicenseNumber(licenseNumber);
            carRepository.save(updatedCar);
            LOGGER.info("setCarLicenseNumber:success, requested id:" + id + " new value:" + licenseNumber);
            return new ResponseEntity<>(optionalCar.get().getId(), HttpStatus.OK);
        }
        catch (Exception e){
            LOGGER.error("setCarLicenseNumber:failed, cause:" + e.getCause());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Gets cars by colour.
     *
     * @param colour the colour
     * @return the cars by colour
     */
    @GetMapping("/cars/colour={colour}")
    public ResponseEntity<List<Car>> getCarsByColour(@PathVariable String colour)
    {
        Iterable<Car> cars = carRepository.findAll();
        List<Car> carsWithSelectedColour = new ArrayList<>();
        try {
            for (Car car : cars){
                if (car.getColour().equals(colour)){
                    carsWithSelectedColour.add(car);
                }
            }
            if (carsWithSelectedColour.isEmpty()){
                LOGGER.warn("getCarsByColour:failed, no data, requested colour:" + colour);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            LOGGER.info("getCarsByColour:success, requested colour:" + colour);
            return new ResponseEntity<>(carsWithSelectedColour, HttpStatus.OK);
        }
        catch (Exception e){
            LOGGER.error("getCarsByColour:failed, cause:" + e.getCause());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Gets cars by brand.
     *
     * @param brand the brand
     * @return the cars by brand
     */
    @GetMapping("/cars/brand={brand}")
    public ResponseEntity<List<Car>> getCarsByBrand(@PathVariable String brand)
    {
        Iterable<Car> cars = carRepository.findAll();
        List<Car> carsWithSelectedBrand = new ArrayList<>();
        try {
            for (Car car : cars){
                if (car.getBrand().equals(brand)){
                    carsWithSelectedBrand.add(car);
                }
            }
            if (carsWithSelectedBrand.isEmpty()){
                LOGGER.warn("getCarsByBrand:failed, no data, requested brand:" + brand);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            LOGGER.info("getCarsByBrand:success, requested brand:" + brand);
            return new ResponseEntity<>(carsWithSelectedBrand, HttpStatus.OK);
        }
        catch (Exception e){
            LOGGER.error("getCarsByBrand:failed, cause:" + e.getCause());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Gets first created car.
     *
     * @return the first created car
     */
    @GetMapping("/cars/first")
    public ResponseEntity<Car> getFirstCreatedCar()
    {
        Iterable<Car> cars = carRepository.findAll();
        List<Car> carList = new ArrayList<>();
        try{
            for (Car car : cars){
                carList.add(car);
            }
            Optional<Car> firstCreatedCar = carList
                    .stream()
                    .min(Comparator.comparing(Car::getCreationDate));
            if (!firstCreatedCar.isPresent()){
                LOGGER.warn("getFirstCreatedCar:failed, no data");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            LOGGER.info("getFirstCreatedCar:success");
            return new ResponseEntity<>(firstCreatedCar.get(), HttpStatus.OK);
        }
        catch (Exception e){
            LOGGER.error("getFirstCreatedCar:failed, cause:" + e.getCause());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Gets last created car.
     *
     * @return the last created car
     */
    @GetMapping("/cars/last")
    public ResponseEntity<Car> getLastCreatedCar()
    {
        Iterable<Car> cars = carRepository.findAll();
        List<Car> carList = new ArrayList<>();
        try{
            for (Car car : cars){
                carList.add(car);
            }
            Optional<Car> lastCreatedCar = carList
                    .stream()
                    .max(Comparator.comparing(Car::getCreationDate));
            if (!lastCreatedCar.isPresent()){
                LOGGER.warn("getLastCreatedCar:failed, no data");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            LOGGER.info("getLastCreatedCar:success");
            return new ResponseEntity<>(lastCreatedCar.get(), HttpStatus.OK);
        }
        catch (Exception e){
            LOGGER.error("getLastCreatedCar:failed, cause:" + e.getCause());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Gets cars sorted by year.
     *
     * @return the cars sorted by year
     */
    @GetMapping("/cars/sorted-by-year")
    public ResponseEntity<List<Car>> getCarsSortedByYear()
    {
        Iterable<Car> cars = carRepository.findAll();
        List<Car> carList = new ArrayList<>();
        try {
            for (Car car : cars){
                carList.add(car);
            }
            List<Car> carsSortedByYear = carList
                    .stream()
                    .sorted(Comparator.comparingInt(Car::getProductionYear))
                    .collect(Collectors.toList());
            if(carsSortedByYear.isEmpty()){
                LOGGER.warn("getCarsSortedByYear:failed, no data");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            LOGGER.info("getCarsSortedByYear:success");
            return new ResponseEntity<>(carsSortedByYear, HttpStatus.OK);
        }
        catch (Exception e){
            LOGGER.error("getCarsSortedByYear:failed, cause:" + e.getCause());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Get cars count response entity.
     *
     * @return the response entity
     */
    @GetMapping("/cars/count")
    public ResponseEntity<Integer> getCarsCount(){
        Iterable<Car> carIterable = carRepository.findAll();
        ArrayList<Car> carArrayList = new ArrayList<>();
        try {
            for(Car car : carIterable){
                carArrayList.add(car);
            }
            if (carArrayList.isEmpty())
            {
                LOGGER.warn("carList:success, no data");
                return new ResponseEntity<>(0, HttpStatus.OK);
            }
        }
        catch (Exception e){
            LOGGER.warn("carList:failed, cause:" + e.getCause());
        }
        LOGGER.info("carList:success");
        return new ResponseEntity<>(carArrayList.size(), HttpStatus.OK);
    }
}
