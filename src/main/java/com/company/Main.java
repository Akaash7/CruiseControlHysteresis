package com.company;

public class Main {

    public static void main(String[] args) throws Exception {
    }
}

class ThrottleControllerImpl implements ThrottleController, Quantizer<Integer> {

    private int cruiseSpeed;
    private int currentThrottle;
    final int UPPER_INPUT_LIMIT = 70;
    final int LOWER_INPUT_LIMIT = 0;

    //Allows change to high values of hysterisis range
    final int HYSTERISIS_RANGE_CONSTANT = 2;


    @Override
    public int calculateThrottle (int currentSpeed){

        // Assuming the difference is to be quantized
        int netSpeed = Math.abs(cruiseSpeed - currentSpeed);
        int throttle = quantize(netSpeed);
        setThrottleValue(throttle);

        return throttle;
    }

    @Override
    public void setCruiseSpeed(int cruiseSpeed) {
        this.cruiseSpeed = cruiseSpeed;
    }

    public int getThrottleValue() {
        return this.currentThrottle;
    }

    public void setThrottleValue(int throttleValue) {
        this.currentThrottle = throttleValue;
    }

    @Override
    public int quantize(Integer inputNumber) {

            int prevThrottleValue = getThrottleValue();//3
            int nextThrottleValue = ( inputNumber + 9 ) / 10; //2   //18
            int hysterisisUpperBound = ( prevThrottleValue * 10 ) + HYSTERISIS_RANGE_CONSTANT; //32
            int hysterisisLowerBound = (( prevThrottleValue - 1 ) * 10 ) - HYSTERISIS_RANGE_CONSTANT;//

            if( inputNumber <= LOWER_INPUT_LIMIT ){
                return 0;
            }
            else if( inputNumber > UPPER_INPUT_LIMIT ){
                return 7;
            }
            else if( prevThrottleValue != 0 && inHysteresisRange( prevThrottleValue, nextThrottleValue, inputNumber,hysterisisUpperBound,hysterisisLowerBound)){
                return prevThrottleValue;
            }
            else{
                return nextThrottleValue;
            }
        }


        public boolean inHysteresisRange(int prevThrottleValue,int nextThrottleValue,int inputNumber,int hysterisisUpperBound,int hysterisisLowerBound){

            return nextThrottleValue > prevThrottleValue && inputNumber <= hysterisisUpperBound
                    || nextThrottleValue < prevThrottleValue && inputNumber >= hysterisisLowerBound;
        }
    }