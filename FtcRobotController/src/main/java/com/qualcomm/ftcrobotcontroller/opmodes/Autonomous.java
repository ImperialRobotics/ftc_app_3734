package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by 4092694 on 1/16/2016.
 */
public class Autonomous extends OpMode
{
    //Drive Motors
    DcMotor motorRight;
    DcMotor motorLeft;
    //Beater Bar Motor
    DcMotor beaterBarMotor;
    //Arm Motors
    DcMotor motorArmExtension;
    DcMotor motorArmRotation;

    public void init()
    {
        //initializing motors
        motorRight = hardwareMap.dcMotor.get("motorRight");
        motorLeft = hardwareMap.dcMotor.get("motorLeft");

        beaterBarMotor = hardwareMap.dcMotor.get("beaterBarMotor");

        motorArmExtension = hardwareMap.dcMotor.get("armExtension");
        motorArmRotation = hardwareMap.dcMotor.get("armRotation");
    }

    public void loop()
    {
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        //write path using methods
        //methods to use: moveForward() , stopMotors() , turnLeft() , turnRight().
        moveForward(0.5 , 5);
        turnRight(0.5 , 4);
        moveForward(0.5 , 5);
    }

    public void stopMotors()
    {
        motorRight.setPower(0);
        motorLeft.setPower(0);
    }


    public void driveForward(double motorPower)
    {
        motorRight.setPower(motorPower);
        motorLeft.setPower(motorPower);
    }

    public void driveLeft(double motorPower)
    {
        motorRight.setPower(motorPower);
        motorLeft.setPower(-motorPower);
    }

    public void driveRight(double motorPower)
    {
        motorRight.setPower(-motorPower);
        motorLeft.setPower(motorPower);
    }

    public void moveForward(double motorPower , int distance)
    {
        //restart encoders
        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        //set position
        motorRight.setTargetPosition(distance);
        motorLeft.setTargetPosition(distance);

        //run to position
        motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        driveForward(motorPower);

        while(motorRight.isBusy() && motorLeft.isBusy())
        {
            beaterBarMotor.setPower(1);
        }

        //end
        stopMotors();
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    public void turnRight(double motorPower , int distance)
    {
        //resets motor encoders
        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        //sets target distance
        motorRight.setTargetPosition(-distance);
        motorLeft.setTargetPosition(distance);

        //run to position
        motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        //ends
        stopMotors();
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    public void turnLeft(double motorPower , int distance)
    {
        //reset motors
        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        //set target position
        motorRight.setTargetPosition(distance);
        motorLeft.setTargetPosition(-distance);

        //move to target position
        motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        //ends
        stopMotors();
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    public void moveBackwards(double motorpower , int distance)
    {
        //reset motor encoders
        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        //set target position
        motorRight.setTargetPosition(-distance);
        motorLeft.setTargetPosition(-distance);

        //run to position
        motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        //end
        stopMotors();
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

    }

    public void armExtends(double motorPower)
    {

        motorArmExtension.setPower(motorPower);
    }

    public void armExtendsForward(double motorPower , int distance)
    {

    }


}
