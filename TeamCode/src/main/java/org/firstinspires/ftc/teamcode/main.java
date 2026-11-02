package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "1", group = "main")
public class main extends OpMode
{
    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor backRightMotor = null;
    private final double deadzone = 0.2;
    private final long[] activatedAt = new long[4];
//    private DcMotorEx flywhellMotor = null;
//    private DcMotor intakeMotor = null;
//    private CRServo leftIntakeServo = null;
//    private CRServo rightIntakeServo = null;
//    private CRServo indexerServo = null;

//    public final int LAUNCHER_TARGET_VELOCITY = 1250;
//    public final int LAUNCHER_MIN_VELOCITY = 1200;

    @Override
    public void init()
    {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "1");
        frontRightMotor = hardwareMap.get(DcMotor.class, "3");
        backLeftMotor = hardwareMap.get(DcMotor.class, "2");
        backRightMotor = hardwareMap.get(DcMotor.class, "4");
//        flywhellMotor = hardwareMap.get(DcMotorEx.class, "5");
//        intakeMotor = hardwareMap.get(DcMotor.class, "6");
//        leftIntakeServo = hardwareMap.get(CRServo.class, "7");
//        rightIntakeServo = hardwareMap.get(CRServo.class, "8");
//        indexerServo = hardwareMap.get(CRServo.class, "9");
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    @Override
    public void loop()
    {
        double[] axes = {-gamepad1.left_stick_y, gamepad1.left_stick_x, -gamepad1.right_stick_y, gamepad1.right_stick_x};
        long now = System.nanoTime();
        int selected = -1;
        for (int i = 0; i < axes.length; i++)
        {
            if (Math.abs(axes[i]) > deadzone)
            {
                if (activatedAt[i] == 0)
                {
                    activatedAt[i] = now;
                }
                if (selected == -1 || activatedAt[i] > activatedAt[selected])
                {
                    selected = i;
                }
            }
            else
            {
                activatedAt[i] = 0;
            }
        }

        double y = 0;
        double x = 0;
        double rx = 0;
        if (selected == 0 || selected == 2)
        {
            y = Math.signum(axes[selected]);
        }
        else if (selected == 1)
        {
            x = Math.signum(axes[selected]);
        }
        else if (selected == 3)
        {
            rx = Math.signum(axes[selected]);
        }

        double frontLeftPower = y + x + rx;
        double backLeftPower = y - x + rx;
        double frontRightPower = y - x - rx;
        double backRightPower = y + x - rx;
        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
    }
}