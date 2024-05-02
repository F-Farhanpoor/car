package car.model.entity;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.log4j.Log4j;
import car.model.entity.enums.Colors;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Log4j
public class Car {
    private int id;
    private String name;
    private Colors colors;
    private LocalDate manDate;
    private boolean status;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }


}
