package com.example.doctorfeedback;

import com.example.doctorfeedback.dto.LocationDTO;

public class HaversineDistance {

    public static double getDistance(LocationDTO firstLocation, LocationDTO secondLocation) {

        final int R = 6371; // Radius of the earth
        double latDistance = toRad(secondLocation.latitude - firstLocation.latitude);
        double lonDistance = toRad(secondLocation.longitude - firstLocation.longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(firstLocation.latitude)) * Math.cos(toRad(secondLocation.latitude)) *
                Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }
}
