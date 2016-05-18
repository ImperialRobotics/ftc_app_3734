package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by 4092694 on 3/11/2016.
 */

public class TheAutonomous extends OpMode {

     private enum state {
        INIT,
        DRIVE_FORWARD,
        SCORE_CLIMBERS,
        RETRACT_SERVO,
        STOP
    }

    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor beaterBar;

    Servo knock;

    state currentState;

    final static int KNOCK_START = 2100;
    final static int KNOCK_THROW = 2024;

    @Override
    public void init(){

        DcMotor motorRight = hardwareMap.dcMotor.get("motorRight");
        DcMotor motorLeft = hardwareMap.dcMotor.get("motorLeft");
        DcMotor beaterBar = hardwareMap.dcMotor.get("beaterBar");

        Servo knock = hardwareMap.servo.get("knock");
        knock.setPosition(servoNormalize(KNOCK_START));
        resetStartTime();
    }

    public void start(){
        currentState = state.INIT;
    }


    @Override
    public void loop(){
        switch (currentState){
            case INIT:
                resetStartTime();
                currentState = state.DRIVE_FORWARD;
                break;
            case DRIVE_FORWARD:
                driveForward(1 , 5.0);
                currentState = state.SCORE_CLIMBERS;
                break;
            case SCORE_CLIMBERS:
                knock.setPosition(servoNormalize(KNOCK_THROW));
                currentState = state.STOP;
                break;
            case RETRACT_SERVO:
                knock.setPosition(servoNormalize(KNOCK_START));
                break;
            case STOP:
                motorStop();
                break;
        }
    }

    @Override
    public void stop(){
        motorStop();
    }


    public void driveForward(double power , double time){
        double gameTime = getRuntime();
        if(gameTime < time){
            motorRight.setPower(power);
            motorLeft.setPower(power);
        }
    }

    public void turnLeft(double power , double time){
        double gameTime = getRuntime();
        if(gameTime < time){
            motorRight.setPower(0);
            motorLeft.setPower(power);
        }
    }

    public void turnRight(double power , double time){
        double gameTime = getRuntime();
        if(gameTime < time){
            motorRight.setPower(power);
            motorLeft.setPower(0);
        }
    }

    public double servoNormalize(int pulse){
        double normalized = (double)pulse;
        return (normalized-750.0)/1500.0 ;
    }

    public void motorStop(){
        //sets everything to default position.
        motorRight.setPower(0);
        motorLeft.setPower(0);
        beaterBar.setPower(0);
        knock.setPosition(servoNormalize(KNOCK_START));
    }
}
