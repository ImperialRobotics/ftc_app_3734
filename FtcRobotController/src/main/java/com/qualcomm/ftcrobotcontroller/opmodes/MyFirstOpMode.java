package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class MyFirstOpMode extends OpMode{

    /*

    p0 right , beater bar , motor 1 is drive , motor 2 is beater bar
    p1 no use
    p2 no use
    p3 servo
    p4 CD interface
    p5 arm, motor 1,arm extension, motor 2, bending/angle
    p6 left wheels, motor 1 drive , motor 2 no use

    We flip the "polarity" of the motors to allow for the y connection in the wiring to function proprerly
    Remind hardware team of specified requirement in the wiring

    */

    //wheelMotors
    private DcMotor motorLeft1;
    private DcMotor motorLeft2;

    private DcMotor motorRight1;
    private DcMotor motorRight2;

    //armMotors
    private DcMotor motorArm1;
    private DcMotor motorArm2;
    //servos
    private Servo trapDoor1;
    private Servo trapDoor2;

    //carwash motors
    DcMotor motorCrashWash;
    private boolean carWashOn = false;

    //arm position range
    private final static double armMinRange  = 0.0;//starting point 90 degrees
    private final static double armMaxRange  = 0.90;//max position 180 degrees
    private double armPosition = 0;

    //constructor
    public MyFirstOpMode(){

    }

    @Override
    public void init(){

        // motors
        motorLeft1 = hardwareMap.dcMotor.get("motor1");
        motorLeft2 = hardwareMap.dcMotor.get("motor2");

        motorRight1 = hardwareMap.dcMotor.get("motor3");
        motorRight2 = hardwareMap.dcMotor.get("motor4");

        motorArm1 = hardwareMap.dcMotor.get("motor5");
        motorArm2 = hardwareMap.dcMotor.get("motor6");

        motorCrashWash = hardwareMap.dcMotor.get("motor7");
        //servos
        //servo values: reverse:0-126, sill: 127 , forward: 128-255
        trapDoor1 = hardwareMap.servo.get("servo1");
        trapDoor2 = hardwareMap.servo.get("servo2");


    }
    @Override
    public void loop(){

        //movement
        float throttle = -gamepad1.left_stick_y;
        float direction = gamepad1.left_stick_x;

        float right = throttle - direction;
        float left = throttle + direction;

        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);

        // Wheel motors
        motorRight1.setPower(right);
        motorRight2.setPower(right);
        motorLeft1.setPower(left);
        motorLeft2.setPower(left);

        //Arm motors
        //rotating the arm
        if(gamepad1.right_bumper && armPosition>armMinRange && armPosition<armMaxRange){
            armPosition++;
            motorArm1.setPower(0.1);
            motorArm1.setDirection(DcMotor.Direction.FORWARD);
        }

        if(gamepad1.left_bumper && armPosition>armMinRange && armPosition< armMaxRange){
            motorArm1.setPower(0.1);
            motorArm1.setDirection(DcMotor.Direction.REVERSE);
        }
        //Extending the arm
        if(gamepad1.y){
            motorArm2.setPower(0.1);
            motorArm2.setDirection(DcMotor.Direction.FORWARD);
        }
        if (gamepad1.x){
            motorArm2.setPower(0.1);
            motorArm2.setDirection(DcMotor.Direction.REVERSE);
        }

        //car wash motor
        if(gamepad1.a){
            carWashOn = true;
            motorCrashWash.setPower(0.1);
            motorCrashWash.setDirection(DcMotor.Direction.FORWARD);
        }
        if(gamepad1.b) {
            carWashOn = true;
            motorCrashWash.setPower(0.1);
            motorCrashWash.setDirection(DcMotor.Direction.REVERSE);
        }
        if(!carWashOn){
            motorCrashWash.setPower(0.1);
        }
        telemetry.addData("arm" , "arm :" +String.format("%.2f", armPosition));
        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
    }

     double scaleInput(double dVal){
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

    @Override
    public void stop(){

    }
}
