package com.pawelsznuradev.whichcityiscloser.cityData;

import java.util.ArrayList;

/**
 * Created by Pawel Sznura on 09/12/2021.
 */
public interface CitiesList {
    default ArrayList<City> createListOfCities() {
        ArrayList<City> arrayList = new ArrayList<>();
        arrayList.add(new City(45633, "Q84", "London", -0.1275, 51.507222222));
        arrayList.add(new City(144809, "Q2379199", "Edinburgh", -3.19333, 55.94973));
        arrayList.add(new City(84640, "Q585", "Oslo", 10.752777777, 59.911111111));
        arrayList.add(new City(47394, "Q36405", "Aberdeen", -2.1, 57.15));
        arrayList.add(new City(3453309, "Q64", "Berlin", 13.383333333, 52.516666666));
        arrayList.add(new City(144571, "Q90", "Paris", 2.351388888, 48.856944444));
        arrayList.add(new City(3097353, "Q727", "Amsterdam", 4.9, 52.383333333));
        arrayList.add(new City(3452878, "Q220", "Rome", 12.482777777, 41.893055555));
        arrayList.add(new City(30988, "Q2807", "Madrid", -3.691944444, 40.418888888));
        arrayList.add(new City(160049, "Q597", "Lisbon", -9.13333, 38.71667));
        arrayList.add(new City(3453194, "Q649", "Moscow", 37.617777777, 55.755833333));
        arrayList.add(new City(92150, "Q270", "Warsaw", 21.033333333, 52.216666666));
        arrayList.add(new City(51643, "Q1781", "Budapest", 19.040833333, 47.498333333));
        arrayList.add(new City(3453053, "Q1741", "Vienna", 16.373064, 48.20833));
        arrayList.add(new City(25261, "Q1748", "Copenhagen", 12.568888888, 55.676111111));
        arrayList.add(new City(34314, "Q1757", "Helsinki", 24.93417, 60.17556));
        arrayList.add(new City(3020322, "Q1754", "Stockholm", 18.068611111, 59.329444444));
        arrayList.add(new City(48676, "Q1524", "Athens", 23.72784, 37.98376));
        arrayList.add(new City(3453093, "Q19660", "Bucharest", 26.083333333, 44.4));
        arrayList.add(new City(7448, "Q472", "Sofia", 23.321726, 42.697886));
        arrayList.add(new City(10908, "Q172", "Toronto", -79.386666666, 43.670277777));
        arrayList.add(new City(123214, "Q60", "New York City", -74.006015, 40.712728));
        arrayList.add(new City(126549, "Q65", "Los Angeles", -118.24368, 34.05223));
        arrayList.add(new City(3453102, "Q1489", "Mexico City", -99.145555555, 19.419444444));
        arrayList.add(new City(3453265, "Q2841", "Bogota", -74.0705, 4.612638888));
        arrayList.add(new City(68526, "Q1490", "Tokyo", 139.692222222, 35.689722222));
        arrayList.add(new City(68874, "Q3870", "Nairobi", 36.817222222, -1.286388888));
        arrayList.add(new City(137508, "Q8678", "Rio de Janeiro", -43.196388888, -22.908333333));
        arrayList.add(new City(126880, "Q16554", "Denver", -104.984722222, 39.739166666));
        arrayList.add(new City(38211, "Q23482", "Marseille", 5.376388888, 43.296666666));
        arrayList.add(new City(89176, "Q1461", "Manila", 120.9822, 14.6042));
        arrayList.add(new City(148750, "Q2656", "Palermo", 13.361262, 38.115658));
        arrayList.add(new City(4935, "Q3130", "Sydney", 151.20732, -33.86785));
        arrayList.add(new City(30385, "Q10282", "Pamplona", -1.65, 42.816666666));

        return arrayList;
    }
}
