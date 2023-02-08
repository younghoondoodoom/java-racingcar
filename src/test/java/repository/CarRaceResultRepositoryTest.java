package repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import model.Car;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CarRaceResultRepositoryTest {

    private final CarRaceResultRepository carRaceResultRepository = CarRaceResultRepositoryImpl.getInstance();

    @Test
    @DisplayName("차가 중복된 경우")
    void saveDuplicate() {
        //given
        Car car1 = new Car("car");
        Car car2 = new Car("car");
        carRaceResultRepository.save(car1);

        //when
        //then
        assertThatThrownBy(() -> carRaceResultRepository.save(car2))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("차 레이싱 결과 저장")
    void save() {
        //given
        Car car1 = new Car("car1");
        Car car2 = new Car("car2");

        //when
        carRaceResultRepository.save(car1);
        carRaceResultRepository.save(car2);

        //then
        assertThat(carRaceResultRepository.findByName(car1.getName())).isEqualTo(1);
        assertThat(carRaceResultRepository.findByName(car2.getName())).isEqualTo(1);
    }

    @Test
    @DisplayName("해당 이름의 차가 없을 때")
    void findByNameNotFound() {
        //given
        String name = "car";

        //when
        //then
        assertThatThrownBy(() -> carRaceResultRepository.findByName(name)).isInstanceOf(
            IllegalArgumentException.class);
    }

    @Test
    @DisplayName("차 이름으로 레이싱 결과 찾기")
    void findByName() {
        //given
        String name = "car";
        carRaceResultRepository.save(new Car(name));

        //when
        int result = carRaceResultRepository.findByName(name);

        //then
        assertThat(result).isEqualTo(1);
    }
}