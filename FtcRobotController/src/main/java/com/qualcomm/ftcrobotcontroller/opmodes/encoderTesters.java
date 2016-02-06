package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by 4092694 on 1/30/2016.
 */
public class encoderTesters extends OpMode
{
    DcMotor armRotation;

    public void init()
    {
        armRotation = hardwareMap.dcMotor.get("armRotation");

    }

    public void loop()
    {

        armRotation.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        armRotation.setTargetPosition(5);
        armRotation.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        telemetry.addData("Encoders " , armRotation);
        telemetry.addData("EncoderMotorPower" , armRotation.getCurrentPosition());


    }

}
