package br.edu.ufam.engcomp.wheelchair.utils;

public class Constants {
    public static final int STICK_NONE = 0;
    public static final int STICK_UP = 1;
    public static final int STICK_UPRIGHT = 2;
    public static final int STICK_RIGHT = 3;
    public static final int STICK_DOWNRIGHT = 4;
    public static final int STICK_DOWN = 5;
    public static final int STICK_DOWNLEFT = 6;
    public static final int STICK_LEFT = 7;
    public static final int STICK_UPLEFT = 8;

    public static final String CENTER_POSITION = "CENTER - 0";

    public static final byte COMMAND_UP = 0x66;
    public static final byte COMMAND_DOWN = 0x62;
    public static final byte COMMAND_LEFT = 0x6C;
    public static final byte COMMAND_RIGHT = 0x72;
    public static final byte COMMAND_CENTER = 0x30;

    public static final byte PATTERN_COMMAND_VALUE = 0x20;
    public static final int PATTERN_DAC_VALUE = 152;

    public static final int LAYOUT_BORDER = 200;
    public static final int STICK_ALPHA = 100;
    public static final int LAYOUT_ALPHA = 150;
    public static final int OFFSET = 90;
    public static final int MIN_DISTANCE = 20;
    public static final int STICK_WIDTH = 150;
    public static final int STICK_HEIGHT = 150;
    public static final int A_HUNDRED_PERCENT = 100;

    public static final int RQS_USB_PERMISSION = 0;
    public static final String ACTION_USB_PERMISSION = "arduino-er.usb_permission";

}
