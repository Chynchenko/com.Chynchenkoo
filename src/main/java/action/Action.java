package action;

import service.CarService;

public interface Action {

    CarService CAR_SERVICE = CarService.getInstance();

    void execute() ;
}
