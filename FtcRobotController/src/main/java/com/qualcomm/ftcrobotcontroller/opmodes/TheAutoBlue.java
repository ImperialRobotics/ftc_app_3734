package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by 4092694 on 2/4/2016.
 *
 * CONTROLLER 1 CONTROLS:
 *
 * Left Stick for movement
 * A:Start Beater Bar Rotation
 * Y:Reverse Beater Bar Rotation
 * Left Bumper: Stop
 *
 *--------------------------------------
 *
 * CONTROLLER 2 CONTROLS:
 *
 * A:Rotate Door Clockwise
 * B:Rotate Door CounterClockwise
 * DPAD Up: Rotate Arm Up
 * Right Bumper: Rotate Arm Down
 * X:Knock Rotation Clockwise
 * Y:Knock Rotation CounterClockwise
 *
 */

public class TheAutoBlue extends OpMode
{
    //Drive Train Motors
    DcMotor motorRight1;
    DcMotor motorLeft1;
    DcMotor motorLeft2;
    float left; //left and right motor power variables
    float right;
    int direction = 1; //to toggle which direction is considered forward
    int ticksPerMeter = 3511; //nominal ticks per meter on a 4" diameter wheel with neverrest 40 gear motor

    //Arm Control Motors
    DcMotor motorArmAngle;
    DcMotor motorArmExtension;

    //Door and Knock Servos and Restriction Integers
    Servo servoDoor;
    Servo servoKnock;

    final static double DOOR_MIN_RANGE = 0.20;
    final static double DOOR_MAX_RANGE  = 0.90;

    //2028
    //2024
    //final static double KNOCK_MIN_RANGE = 0.9;
    //final static double KNOCK_MAX_RANGE = 1.0;
    final static int KNOCK_START = 2100;
    final static int KNOCK_THROW = 2024;

    double doorPosition;
    double doorDelta = 0.05;

    int knockPosition;
    double knockDelta = 0.1;
    //Beater bar motor
    DcMotor motorBeaterBar;

    //Constructor
    public TheTeleOp()
    {

    }
    public void init()
    {
        //init drive train motors
        motorRight1 = hardwareMap.dcMotor.get("rightDrive");

        motorLeft1 = hardwareMap.dcMotor.get("leftDriveA");
        motorLeft2 = hardwareMap.dcMotor.get("leftDriveB");

        motorRight1.setDirection(DcMotor.Direction.REVERSE);
        motorLeft1.setDirection(DcMotor.Direction.FORWARD);
        motorLeft2.setDirection(DcMotor.Direction.REVERSE);

        //init arm motors
        motorArmAngle = hardwareMap.dcMotor.get("armAngle");
        motorArmAngle.setDirection(DcMotor.Direction.FORWARD);

        motorArmExtension = hardwareMap.dcMotor.get("armExtension");
        motorArmExtension.setDirection(DcMotor.Direction.FORWARD);

        //init beaterBar
        motorBeaterBar = hardwareMap.dcMotor.get("beaterBar");

        //init door and knock position. Starting points
        doorPosition = 0.2;
        knockPosition = KNOCK_START;

        servoDoor = hardwareMap.servo.get("Door");
        servoKnock = hardwareMap.servo.get("Knock");

        servoKnock.setPosition(ServoNormalize(KNOCK_START));

    }

    public void loop()
    {
        //Drive Train Movement Controller 1 using Joystick
        /*
        float power = -gamepad1.left_stick_y; //Foward, Backwards
        float direction = gamepad1.left_stick_x; // Left, Right
        float right = power - direction;
        float left = power + direction;
*/
        if(gamepad1.x) direction *= -1; //toggle which direction is forward

        //TANK Drive
        left = -gamepad1.left_stick_y;
        right = -gamepad1.right_stick_y;

        if ((left * right)>0){ // joysticks are in same direction - so apply possible direction toggle;

                left *= direction;
                right *= direction;
        }

        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);

        motorRight1.setPower(right);
        motorLeft1.setPower(left);
        motorLeft2.setPower(left);

        motorArmExtension.setPower(-gamepad2.right_stick_y);

        //Beater Bar Movement
        if(gamepad1.a)
        {
            motorBeaterBar.setPower(1);
        }
        if(gamepad1.y)
        {
            motorBeaterBar.setPower(-1);
        }
        if(gamepad1.left_bumper)
        {
            motorBeaterBar.setPower(0);
        }

        //Door servo movement
        //Controller 2
        if(gamepad2.a)
        {
            doorPosition+=doorDelta;
        }

        if(gamepad2.b)
        {
            doorPosition-=doorDelta;
        }

        //Clip the position from going out of bounds
        doorPosition = Range.clip(doorPosition ,DOOR_MIN_RANGE ,DOOR_MAX_RANGE );
        //set servo value
        servoDoor.setPosition(doorPosition);

        //Arm Rotation

        motorArmAngle.setPower(gamepad2.left_stick_y/3); //set arm angle with left joystick on second controller - scale down power
        /*motorArmAngle.setPower(0);

        if(gamepad2.dpad_up)
        {
            motorArmAngle.setPower(.1);
        }

        if(gamepad2.right_bumper) {
            motorArmAngle.setPower(-0.30);
        }
        */

        //Knock Movement
        if(gamepad2.x) //retract knocker
        {
            servoKnock.setPosition(ServoNormalize(KNOCK_START));
            //knockPosition+=knockDelta;
        }
        if(gamepad2.y) //deposit climbers
        {
            servoKnock.setPosition(ServoNormalize(KNOCK_THROW));
            //knockPosition-=knockDelta;
        }
        //Clip knockPosition
        //knockPosition = Range.clip(knockPosition ,KNOCK_MIN_RANGE ,KNOCK_MAX_RANGE );



        //telemetry
        telemetry.addData("Text" , "***CHOSEN ONE DATA***");

    }

    public void stop()
    {

    }

    double scaleInput(double dVal)  {
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

    double ServoNormalize(int pulse)
    {
     double normalized = (double)pulse;
      return (normalized-750.0)/1500.0 ;
    }
}
