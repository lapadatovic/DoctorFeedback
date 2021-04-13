package com.example.doctorfeedback;

import com.example.doctorfeedback.dto.LocationDTO;

import org.junit.Test;
import static org.junit.Assert.*;

public class HaversineDistanceTest {

    @Test
    public void testGetDistance() {

        LocationDTO ucv = new LocationDTO(44.3184611479, 23.8005186684);
        LocationDTO englishParkCraiova = new LocationDTO(44.3184355456, 23.7965916451);
        LocationDTO auchanCraiovita = new LocationDTO(44.3282745192, 23.7748866385);
        double distance = HaversineDistance.getDistance(ucv, auchanCraiovita);
    }
}
