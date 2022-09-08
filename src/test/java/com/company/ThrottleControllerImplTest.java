package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThrottleControllerImplTest {

    @Test
    void onTheWayUp() {
        ThrottleController throttleController = new ThrottleControllerImpl();

        throttleController.setCruiseSpeed(15);
        int throttleValue1 = throttleController.calculateThrottle(12);
        // at 3
        assertEquals(1, throttleValue1);

        throttleController.setCruiseSpeed(23);
        int throttleValue2 = throttleController.calculateThrottle(15);

        // at 8
        assertEquals(1, throttleValue2);

        throttleController.setCruiseSpeed(35);
        int throttleValue3 = throttleController.calculateThrottle(23);
        // in hysterisis range at 12
        assertEquals(1, throttleValue3);

        throttleController.setCruiseSpeed(54);
        int throttleValue4 = throttleController.calculateThrottle(35);
        // at 18
        assertEquals(2, throttleValue4);

        throttleController.setCruiseSpeed(76);
        int throttleValue5 = throttleController.calculateThrottle(54);
        // in hysterisis range at 22
        assertEquals(2, throttleValue5);

        throttleController.setCruiseSpeed(101);
        int throttleValue6 = throttleController.calculateThrottle(76);
        // at 25
        assertEquals(3, throttleValue6);
    }

    @Test
    void onTheWayDown() {
        ThrottleController throttleController = new ThrottleControllerImpl();

        throttleController.setCruiseSpeed(76);
        int throttleValue1 = throttleController.calculateThrottle(100);
        // at 24
        assertEquals(3, throttleValue1);

        throttleController.setCruiseSpeed(55);
        int throttleValue2 = throttleController.calculateThrottle(76);
        // In hysterisis range at 19
        assertEquals(3, throttleValue2);

        throttleController.setCruiseSpeed(41);
        int throttleValue3 = throttleController.calculateThrottle(55);
        // at 14
        assertEquals(2, throttleValue3);

        throttleController.setCruiseSpeed(31);
        int throttleValue4 = throttleController.calculateThrottle(41);
        //In hysterisis range at 10
        assertEquals(2, throttleValue4);

        throttleController.setCruiseSpeed(24);
        int throttleValue5 = throttleController.calculateThrottle(31);
        // at 7
        assertEquals(1, throttleValue5);
    }

    @Test
    void higherThan70() {
        ThrottleController throttleController = new ThrottleControllerImpl();
        throttleController.setCruiseSpeed(80);
        int throttleValue = throttleController.calculateThrottle(0);
        // >70
        assertEquals(7, throttleValue);
    }

    @Test
    void equalTo0() {
        ThrottleController throttleController = new ThrottleControllerImpl();
        throttleController.setCruiseSpeed(80);
        int throttleValue = throttleController.calculateThrottle(80);
        // <0
        assertEquals(0, throttleValue);
    }
}