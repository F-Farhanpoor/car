package car.controller;

import car.model.bl.CarBl;
import car.model.entity.Car;
import car.model.entity.enums.Colors;
import car.model.tools.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

@Log4j
public class CarController implements Initializable {
    @FXML
    private TextField idTxt, nameTxt, searchNameTxt, searchMandateTxt;
    @FXML
    private ComboBox<String> colorCmb;
    @FXML
    private DatePicker manDate , manDatefind;
    @FXML
    private CheckBox statusChk;
    @FXML
    private Button saveBtn, editBtn, removeBtn, addBtn;
    @FXML
    private TableView<Car> carTbl;
    @FXML
    private TableColumn<Car, String> idCol, nameCol, colorCol, manDateCol , statusCol;


    public static String save(String name, String color, LocalDate manDate,boolean status) {
        try {
            Car car = Car
                    .builder()
                    .name(Validator.validateName(name, "Invalid  Name!!!"))
                    .colors(Colors.valueOf(color))
                    .manDate(manDate)
                    .status(status)
                    .build();
            CarBl.save(car);
            log.info("Car Saved\n" + car.toString());
            return "Info : Car Saved\n" + car.toString();
        } catch (Exception e) {
            log.error("Error Save : " + e.getMessage());
            return "Error Save : " + e.getMessage();
        }
    }

    public static String edit(int id, String name, String color, LocalDate manDate,boolean status ) {
        try {
            Car car = Car
                    .builder()
                    .id(id)
                    .name(Validator.validateName(name, "Invalid  Name!!!"))
                    .colors(Colors.valueOf(color))
                    .manDate(manDate)
                    .status(status)
                    .build();
            CarBl.edit(car);
            log.info("Car Edited\n" + car.toString());
            return "Info : Car Edited\n" + car.toString();
        } catch (Exception e) {
            log.error("Error Edit : " + e.getMessage());
            return "Error Edit : " + e.getMessage();
        }
    }

    public static String remove(int id) {
        try {
            Car car = CarBl.remove(id);
            log.info("Car Removed\n" + car.toString());
            return "Info : Car Removed\n" + car.toString();
        } catch (Exception e) {
            log.error("Error Remove : " + e.getMessage());
            return "Error Remove : " + e.getMessage();
        }
    }

    public static List<Car> findAll() {
        try {
            log.info("FindAll");
            return CarBl.findAll();
        } catch (Exception e) {
            log.error("Error FindAll : " + e.getMessage());
            return null;
        }
    }

    public static Car findById(int id) {
        try {
            log.info("FindById : " + id);
            return CarBl.findById(id);
        } catch (Exception e) {
            log.error("Error FindById :" + e.getMessage());
            return null;
        }
    }

    public static List<Car> findByName(String name) {
        try {
            log.info("FindByName : " + name);
            return CarBl.findByName(name);
        } catch (Exception e) {
            log.error("Error FindByName : " + e.getMessage());
            return null;
        }
    }

    public static List<Car> findByMandate(LocalDate mandate) {
        try {
            log.info("FindByMandate : " + mandate);
            return CarBl.findByMandate(mandate);
        } catch (Exception e) {
            log.error("Error FindByMandate: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Colors value : Colors.values()) {
            colorCmb.getItems().add(value.name());
        }
        resetForm();

        addBtn.setOnAction((event) -> {
            resetForm();
        });

        saveBtn.setOnAction((event) -> {
            String message = save(
                    nameTxt.getText(),
                    colorCmb.getSelectionModel().getSelectedItem(),
                    manDate.getValue(),
                    statusChk.isSelected()


            );

            if (message.startsWith("Info")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
                alert.show();
                resetForm();
            } else if (message.startsWith("Error")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, message);
                alert.show();
            }
        });

        editBtn.setOnAction((event) -> {
            String message = edit(
                    Integer.parseInt(idTxt.getText()),
                    nameTxt.getText(),
                    colorCmb.getSelectionModel().getSelectedItem(),
                    manDate.getValue(),
                    statusChk.isSelected()
            );

            if (message.startsWith("Info")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
                alert.show();
                resetForm();
            } else if (message.startsWith("Error")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, message);
                alert.show();
            }
        });

        removeBtn.setOnAction((event) -> {
            String message = remove(Integer.parseInt(idTxt.getText()));

            if (message.startsWith("Info")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
                alert.show();
                resetForm();
            } else if (message.startsWith("Error")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, message);
                alert.show();
            }
        });

        searchNameTxt.setOnKeyReleased((event) -> {
            refreshTable(findByName(searchNameTxt.getText()));
        });

        manDatefind.setOnAction((event) -> {
            refreshTable(findByMandate(manDatefind.getValue()));
        });

        carTbl.setOnMouseReleased((event) -> {
            selectCar();
        });

        carTbl.setOnKeyReleased((event) -> {
            selectCar();
        });

    }

    public void resetForm() {
        idTxt.clear();
        nameTxt.clear();
        colorCmb.getSelectionModel().select(0);
        manDate.setValue(LocalDate.now());
        statusChk.setSelected(true);
        refreshTable(findAll());
    }

    public void refreshTable(List<Car> carList) {
        try {
            ObservableList<Car> observableList = FXCollections.observableList(carList);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
            manDateCol.setCellValueFactory(new PropertyValueFactory<>("manDate"));
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));


            carTbl.setItems(observableList);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    public void selectCar() {
        Car car = carTbl.getSelectionModel().getSelectedItem();
        idTxt.setText(String.valueOf(car.getId()));
        nameTxt.setText(car.getName());
        colorCmb.getSelectionModel().select(car.getColors().name());
        manDate.setValue(car.getManDate());
        statusChk.setSelected(car.isStatus());

    }
}
