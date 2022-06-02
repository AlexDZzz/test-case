package main.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    Car correctCar;
    Car carWrongLicense;
    Car carWrongYear;

    @BeforeEach
    void setUp()
    {
        correctCar = new Car("м626рт96", "Subaru", "Legacy", "gray", 1994, BodyType.WAGON);

        carWrongLicense = new Car("v626рт96", "Subaru", "Legacy", "gray", 1994, BodyType.WAGON);
        carWrongYear = new Car("м626рт96", "Subaru", "Legacy", "gray", 994, BodyType.WAGON);
    }

    @Test
    void isCorrect()
    {
        boolean expectedTrue = correctCar.isCorrect();
        boolean expectedFalse = carWrongLicense.isCorrect() && carWrongYear.isCorrect();
        assertTrue(expectedTrue);
        assertFalse(expectedFalse);
    }

    @Test
    void getId()
    {
        correctCar.setId(111);
        assertEquals(111, correctCar.getId());
    }

    @Test
    void setId()
    {
        int newId = 87630;
        correctCar.setId(newId);
        assertEquals(newId, correctCar.getId());
    }

    @Test
    void getLicenseNumber()
    {
        String expected = "м626рт96";
        assertEquals(expected, correctCar.getLicenseNumber());
    }

    @Test
    void setLicenseNumber()
    {
        String newLicenseNumber = "т423кн42";
        correctCar.setLicenseNumber(newLicenseNumber);
        assertEquals(newLicenseNumber, correctCar.getLicenseNumber());
    }

    @Test
    void getBrand()
    {
        String expected = "Subaru";
        assertEquals(expected, correctCar.getBrand());
    }

    @Test
    void setBrand()
    {
        String newBrand = "Nissan";
        correctCar.setBrand(newBrand);
        assertEquals(newBrand, correctCar.getBrand());
    }

    @Test
    void getModel()
    {
        String expected = "Legacy";
        assertEquals(expected, correctCar.getModel());
    }

    @Test
    void setModel()
    {
        String newModel = "Wingroad";
        correctCar.setModel(newModel);
        assertEquals(newModel, correctCar.getModel());
    }

    @Test
    void getColour()
    {
        String expected = "gray";
        assertEquals(expected, correctCar.getColour());
    }

    @Test
    void setColour()
    {
        String newColour = "white";
        correctCar.setColour(newColour);
        assertEquals(newColour, correctCar.getColour());
    }

    @Test
    void getProductionYear()
    {
        int expected = 1994;
        assertEquals(expected, correctCar.getProductionYear());
    }

    @Test
    void setProductionYear()
    {
        int newProductionYear = 2003;
        correctCar.setProductionYear(newProductionYear);
        assertEquals(newProductionYear, correctCar.getProductionYear());
    }

    @Test
    void getBodyType()
    {
        BodyType expected = BodyType.WAGON;
        assertEquals(expected, correctCar.getBodyType());
    }

    @Test
    void setBodyType()
    {
        BodyType newBody = BodyType.SEDAN;
        correctCar.setBodyType(newBody);
        assertEquals(newBody, correctCar.getBodyType());
    }
}