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
    DcMotor motorLeft2;
    DcMotor motorLeft1;

    //Beater Bar Motor
    DcMotor beaterBarMotor;
    //Arm Motors
    DcMotor motorArmRotation;

    public void init()
    {
        //initializing motors
        motorRight = hardwareMap.dcMotor.get("motorRight");
        motorLeft1 = hardwareMap.dcMotor.get("motorLeftA");
        motorLeft2 = hardwareMap.dcMotor.get("motorLeftB");

        beaterBarMotor = hardwareMap.dcMotor.get("beaterBarMotor");

        motorArmRotation = hardwareMap.dcMotor.get("armRotation");
    }

    public void loop()
    {
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        //write path using method
        //methods to use: moveForward() , stopMotors() , turnLeft() , turnRight().
        //parameters are speed , distance

        moveForward(0.5, 3);

    }

    public void stopMotors()
    {
        motorRight.setPower(0);
        motorLeft1.setPower(0);
        motorLeft2.setPower(0);
    }

    public void driveForward(double motorPower)
    {
        motorRight.setPower(motorPower);
        motorLeft1.setPower(motorPower);
        motorLeft2.setPower(motorPower);
    }

    public void driveLeft(double motorPower)
    {
        motorRight.setPower(motorPower);
        motorLeft1.setPower(-motorPower);
        motorLeft2.setPower(-motorPower);
    }

    public void driveRight(double motorPower)
    {
        motorRight.setPower(-motorPower);
        motorLeft1.setPower(motorPower);
        motorLeft2.setPower(motorPower);
    }

    public void moveForward(double motorPower , int distance)
    {
        //restart encoders
        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        //set position
        motorRight.setTargetPosition(distance);
        motorLeft1.setTargetPosition(distance);
        motorLeft2.setTargetPosition(distance);

        //run to position
        motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorLeft1.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorLeft2.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        driveForward(motorPower);

        while(motorRight.isBusy() && motorLeft1.isBusy() && motorLeft2.isBusy())
        {
            //Run Beater Bar while moving
            beaterBarMotor.setPower(1);
        }

        //end
        stopMotors();
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    public void turnRight(double motorPower , int distance)
    {
        //resets motor encoders
        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        //sets target distance
        motorRight.setTargetPosition(-distance);
        motorLeft1.setTargetPosition(distance);
        motorLeft2.setTargetPosition(distance);

        //run to position
        motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorLeft1.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorLeft2.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        //ends
        stopMotors();
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    public void turnLeft(double motorPower , int distance)
    {
        //reset motors
        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        //set target position
        motorRight.setTargetPosition(distance);
        motorLeft1.setTargetPosition(-distance);
        motorLeft2.setTargetPosition(-distance);

        //move to target position
        motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorLeft1.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorLeft2.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        //ends
        stopMotors();
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    public void moveBackwards(double motorpower , int distance)
    {
        //reset motor encoders
        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        //set target position
        motorRight.setTargetPosition(-distance);
        motorLeft1.setTargetPosition(-distance);
        motorLeft2.setTargetPosition(-distance);

        //run to position
        motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorLeft1.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorLeft2.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        //end
        stopMotors();
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

    }

    public void armExtendsForward(double motorPower , int distance)
    {

    }


}
