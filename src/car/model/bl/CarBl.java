package car.model.bl;

import car.controller.exceptions.NoCarFoundException;
import car.model.da.CarDa;
import car.model.entity.Car;

import java.time.LocalDate;
import java.util.List;

public class CarBl {
    public static Car save(Car car) throws Exception {
        try (CarDa carDa = new CarDa()) {
            carDa.save(car);
            return car;
        }
    }

    public static Car edit(Car car) throws Exception {
        try (CarDa carDa = new CarDa()) {
            if (carDa.findById(car.getId()) != null) {
                carDa.edit(car);
                return car;
            } else {
                throw new NoCarFoundException();
            }
        }
    }

    public static Car remove(int id) throws Exception {
        try (CarDa carDa = new CarDa()) {
            Car car = carDa.findById(id);
            if (car != null) {
                carDa.remove(id);
                return car;
            } else {
                throw new NoCarFoundException();
            }
        }
    }

    public static List<Car> findAll() throws Exception {
        try (CarDa carDa = new CarDa()) {
            List<Car> carList = carDa.findAll();
            if (!carList.isEmpty()) {
                return carList;
            } else {
                throw new NoCarFoundException();
            }
        }
    }

    public static Car findById(int id) throws Exception {
        try (CarDa carDa = new CarDa()) {
            return carDa.findById(id);
        }
    }

    public static List<Car> findByName(String name) throws Exception {
        try (CarDa carDa = new CarDa()) {
            List<Car> carList = carDa.findByName(name);
            if (!carList.isEmpty()) {
                return carList;
            } else {
                throw new NoCarFoundException();
            }
        }
    }

    public static List<Car> findByMandate(LocalDate mandate) throws Exception {
        try (CarDa carDa = new CarDa()) {
            List<Car> carList = carDa.findByMandate(mandate);
            if (!carList.isEmpty()) {
                return carList;
            } else {
                throw new NoCarFoundException();
            }
        }
    }

}
