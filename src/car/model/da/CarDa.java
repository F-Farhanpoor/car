package car.model.da;

import car.model.entity.Car;
import car.model.entity.enums.Colors;
import car.model.tools.JDBC;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CarDa implements AutoCloseable {
    private Connection connection;
    private PreparedStatement preparedStatement;
    public CarDa() throws SQLException {
        connection = JDBC.getJdbc().getConnection();
    }
    public void save(Car car)throws Exception{
        preparedStatement=connection.prepareStatement(
                "insert into CAR (ID, NAME, COLOR, MAN_DATE,STATUS) values (ID_SEQ.nextval,?,?,?,?)"

        );
        preparedStatement.setString(1,car.getName());
        preparedStatement.setString(2,car.getColors().name());
        preparedStatement.setDate(3, Date.valueOf(car.getManDate()));
        preparedStatement.setBoolean(4,car.isStatus());
        preparedStatement.execute();

    }
    public void edit(Car car)throws Exception{
        preparedStatement=connection.prepareStatement(
                "update CAR set NAME =?, COLOR=? ,MAN_DATE=?, STATUS=? where id =?"

        );
        preparedStatement.setString(1,car.getName());
        preparedStatement.setString(2,car.getColors().name());
        preparedStatement.setDate(3, Date.valueOf(car.getManDate()));
        preparedStatement.setBoolean(4,car.isStatus());
        preparedStatement.setInt(5,car.getId());
        preparedStatement.execute();

    }
    public void remove(int id)throws Exception{
        preparedStatement=connection.prepareStatement(
                "delete from CAR where ID=?"

        );

        preparedStatement.setInt(1,id);
        preparedStatement.execute();
    }
    public List<Car> findAll()throws Exception{
        preparedStatement=connection.prepareStatement(
                "select * from CAR order by NAME,MAN_DATE"
        );
        ResultSet resultSet=preparedStatement.executeQuery();
        List<Car> carList=new ArrayList<>();
        while (resultSet.next()){
            Car car = Car.builder()
                    .id(resultSet.getInt("ID"))
                    .name(resultSet.getString("NAME"))
                    .colors(Colors.valueOf(resultSet.getString("COLOR")))
                    .manDate(resultSet.getDate("MAN_DATE").toLocalDate())
                    .status(resultSet.getBoolean("STATUS"))
                    .build();
            carList.add(car);

        }
        return carList;
    }
    public Car findById(int id)throws Exception{
        preparedStatement=connection.prepareStatement("select*from CAR where ID=?");
        preparedStatement.setInt(1,id);
        ResultSet resultSet=preparedStatement.executeQuery();
        Car car = null;
        if (resultSet.next()){
             car=Car.builder()
                    .id(resultSet.getInt("ID"))
                    .name(resultSet.getString("NAME"))
                    .colors(Colors.valueOf(resultSet.getString("COLOR")))
                    .manDate(resultSet.getDate("MAN_DATE").toLocalDate())
                    .status(resultSet.getBoolean("STATUS"))
                    .build();

        }
        return car;

    }
    public List<Car> findByName(String name )throws Exception{
        preparedStatement=connection.prepareStatement("select*from CAR where NAME like ?");
        preparedStatement.setString(1,name + "%");
        ResultSet resultSet=preparedStatement.executeQuery();
        List<Car> carList=new ArrayList<>();
        while (resultSet.next()){
            Car car = Car.builder()
                    .id(resultSet.getInt("ID"))
                    .name(resultSet.getString("NAME"))
                    .colors(Colors.valueOf(resultSet.getString("COLOR")))
                    .manDate(resultSet.getDate("MAN_DATE").toLocalDate())
                    .status(resultSet.getBoolean("STATUS"))
                    .build();
            carList.add(car);

        }
        return carList;
    }
    public List<Car> findByMandate (LocalDate mandate)throws Exception{
        preparedStatement=connection.prepareStatement("select*from CAR where MAN_DATE=?");
        preparedStatement.setDate(1, Date.valueOf(mandate));
        ResultSet resultSet=preparedStatement.executeQuery();
        List<Car> carList=new ArrayList<>();

        while (resultSet.next()){
             Car car =Car.builder()
                    .id(resultSet.getInt("ID"))
                    .name(resultSet.getString("NAME"))
                    .colors(Colors.valueOf(resultSet.getString("COLOR")))
                    .manDate(resultSet.getDate("MAN_DATE").toLocalDate())
                    .status(resultSet.getBoolean("STATUS"))
                    .build();
             carList.add(car);

        }
        return carList;

    }

    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }

}
