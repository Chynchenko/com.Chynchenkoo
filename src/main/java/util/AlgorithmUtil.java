package util;

import model.Car;

public class AlgorithmUtil {

    public  Car [] bubbleSort (Car [] cars) {
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < cars.length - 1; i++) {

                if (cars[i].getId().compareTo(cars[i+1].getId()) > 0){
                    isSorted = false;
                    Car tempCar = cars[i];
                    cars[i] = cars[i + 1];
                    cars[i + 1] = tempCar;
                }
            }
        }
        return cars;
    }

    public  int binarySearch(Car[] array, Car car, int first, int last){
        if (first <= last) {
            int mid = first + (last - first)/2;
            if (array[mid].equals(car)){
                return mid;
            }
            if (array[mid].getId().compareTo(car.getId()) > 0){
                return binarySearch(array, car, first, mid-1);
            }else{
                return binarySearch(array, car, mid+1, last);
            }
        }
        return -1;
    }
}