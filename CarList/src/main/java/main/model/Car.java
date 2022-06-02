package main.model;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * The type Car.
 */
@Entity
@Table(name = "cars")
public class Car
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Car registration license number.
     */
    @Column(unique = true, nullable = false)
    private String licenseNumber;

    /**
     * Car brand(manufacturer).
     */
    @Column(nullable = false)
    private String brand;

    /**
     * Car model.
     */
    @Column(nullable = false)
    private String model;

    /**
     * Car colour.
     */
    @Column(nullable = false)
    private String colour;

    /**
     * Car production year.
     */
    @Column(nullable = false)
    private int productionYear;

    /**
     * Car body type.
     */
    @Enumerated(EnumType.STRING)
    private BodyType bodyType;

    @Column(nullable = false)
    private java.sql.Timestamp creationDate = new java.sql.Timestamp(System.currentTimeMillis());

    /**
     * Instantiates a new Car.
     */
    public Car(){}

    /**
     * Instantiates a new Car.
     *
     * @param licenseNumber  the license number
     * @param brand          the brand
     * @param model          the model
     * @param colour         the colour
     * @param productionYear the production year
     * @param bodyType       the body type
     */
    public Car(String licenseNumber,
               String brand,
               String model,
               String colour,
               int productionYear,
               BodyType bodyType)
    {
        this.licenseNumber = licenseNumber;
        this.brand = brand;
        this.model = model;
        this.colour = colour;
        this.productionYear = productionYear;
        this.bodyType =(BodyType) bodyType;
    }

    /**
     * Is correct boolean.
     *
     * @return the boolean
     */
    public boolean isCorrect(){
        return (licenseNumber.matches("^[АВЕКМНОРСТУХавекмнорстух]\\d{3}(?<!000)[АВЕКМНОРСТУХавекмнорстух]{2}\\d{2,3}$")
                && !brand.isEmpty()
                && !model.isEmpty()
                && !colour.isEmpty()
                && !(productionYear < 1885)
                && !(productionYear > Calendar.getInstance().get(Calendar.YEAR)));
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets license number.
     *
     * @return the license number
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * Sets license number.
     *
     * @param licenseNumber the license number
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    /**
     * Gets brand.
     *
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets brand.
     *
     * @param brand the brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets model.
     *
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets model.
     *
     * @param model the model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets colour.
     *
     * @return the colour
     */
    public String getColour() {
        return colour;
    }

    /**
     * Sets colour.
     *
     * @param colour the colour
     */
    public void setColour(String colour) {
        this.colour = colour;
    }

    /**
     * Gets production year.
     *
     * @return the production year
     */
    public int getProductionYear() {
        return productionYear;
    }

    /**
     * Sets production year.
     *
     * @param productionYear the production year
     */
    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    /**
     * Gets body type.
     *
     * @return the body type
     */
    public BodyType getBodyType() {
        return bodyType;
    }

    /**
     * Sets body type.
     *
     * @param bodyType the body type
     */
    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    /**
     * Gets creation date.
     *
     * @return the creation date
     */
    public Timestamp getCreationDate() {
        return creationDate;
    }

    /**
     * Sets creation date.
     *
     * @param creationDate the creation date
     */
    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
