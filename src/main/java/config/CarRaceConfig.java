package config;

import controller.CarRaceController;
import repository.CarRaceResultRepositoryImpl;
import service.CarRaceService;
import service.CarRaceServiceImpl;
import service.RandomNumberGenerator;
import validation.InputValidationChain;
import validation.impl.EmptyValueValidationChain;
import validation.impl.InputValidateSuccessChain;
import validation.impl.NumberRangeValidationChain;
import validation.impl.PositiveNumberValidationChain;
import view.InputView;
import view.OutputView;

public class CarRaceConfig {

    public static CarRaceController config() {
        return new CarRaceController(new InputView(), new OutputView(),
            makeValidator(), makeService());
    }

    private static CarRaceService makeService() {
        return new CarRaceServiceImpl(new CarRaceResultRepositoryImpl(),
            new RandomNumberGenerator());
    }

    private static InputValidationChain makeValidator() {
        InputValidationChain emptyValueValidationChain = new EmptyValueValidationChain();
        InputValidationChain numberRangeValidationChain = new NumberRangeValidationChain();
        InputValidationChain positiveNumberValidationChain = new PositiveNumberValidationChain();
        InputValidationChain inputValidationChain = new InputValidateSuccessChain();
        emptyValueValidationChain.setNext(numberRangeValidationChain);
        numberRangeValidationChain.setNext(positiveNumberValidationChain);
        positiveNumberValidationChain.setNext(inputValidationChain);
        return emptyValueValidationChain;
    }
}