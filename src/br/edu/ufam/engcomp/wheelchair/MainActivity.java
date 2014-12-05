package br.edu.ufam.engcomp.wheelchair;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.edu.ufam.engcomp.wheelchair.adk.AdkActivity;
import br.edu.ufam.engcomp.wheelchair.joystick.JoyStickComponent;
import br.edu.ufam.engcomp.wheelchair.utils.Constants;

public class MainActivity extends AdkActivity {

	private RelativeLayout joystickLayout;
	private JoyStickComponent joystick;
	private TextView buttonPosition;

	@Override
	protected void doOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);

		buttonPosition = (TextView) findViewById(R.id.position);
		joystickLayout = (RelativeLayout) findViewById(R.id.joystick_layout);

		joystick = new JoyStickComponent(getApplicationContext(),
				joystickLayout, R.drawable.joystick_button);
		joystick.setJoystickParams();

		joystickLayout.setOnTouchListener(onTouchJoystickListener());

	}

	public OnTouchListener onTouchJoystickListener() {
		return new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				joystick.drawStick(event);
				if (event.getAction() == MotionEvent.ACTION_DOWN
						|| event.getAction() == MotionEvent.ACTION_MOVE) {
					int direction = joystick.get8Direction();
					if (direction == Constants.STICK_UP) {
						if (joystick.getY() <= -200) {
							buttonPosition.setText("UP - 100%");
						} else {
							buttonPosition.setText("UP - "
									+ (-joystick.getY() / 2) + "%");
						}
					} else if (direction == Constants.STICK_RIGHT) {
						if (joystick.getX() >= 200) {
							buttonPosition.setText("RIGHT - 100%");
						} else {
							buttonPosition.setText("RIGHT - " + joystick.getX()
									/ 2 + "%");
						}
					} else if (direction == Constants.STICK_DOWN) {
						if (joystick.getY() >= 200) {
							buttonPosition.setText("DOWN - 100%");
						} else {
							buttonPosition.setText("DOWN - " + joystick.getY()
									/ 2 + "%");
						}
					} else if (direction == Constants.STICK_LEFT) {
						if (joystick.getX() <= -200) {
							buttonPosition.setText("LEFT - 100%");
						} else {
							buttonPosition.setText("LEFT - "
									+ (-joystick.getX() / 2) + "%");
						}
					} else if (direction == Constants.STICK_UPRIGHT) {
						if ((joystick.getY() > -200)
								&& (joystick.getX1() >= 200)) {
							buttonPosition.setText("UP - "
									+ (-joystick.getY1() / 2)
									+ "% - RIGHT - 100%");
						} else if ((joystick.getY1() <= -200)
								&& (joystick.getX1() < 200)) {
							buttonPosition.setText("UP - 100%" + " - RIGHT - "
									+ joystick.getX1() / 2 + "%");
						} else {
							buttonPosition.setText("UP - "
									+ (-joystick.getY1() / 2) + "% - "
									+ "RIGHT - " + joystick.getX1() / 2 + "%");

						}
					} else if (direction == Constants.STICK_DOWNRIGHT) {
						if ((joystick.getY1() < 200)
								&& (joystick.getX1() >= 200)) {
							buttonPosition.setText("DOWN - "
									+ (joystick.getY1() / 2)
									+ "% - RIGHT - 100%");
						} else if ((joystick.getY1() >= 200)
								&& (joystick.getX1() < 200)) {
							buttonPosition.setText("DOWN - 100%"
									+ " - RIGHT - " + joystick.getX1() / 2
									+ "%");
						} else {
							buttonPosition.setText("DOWN - " + joystick.getY1()
									/ 2 + "% - " + "RIGHT - "
									+ joystick.getX1() / 2 + "%");
						}
					} else if (direction == Constants.STICK_DOWNLEFT) {
						if ((joystick.getY1() < 200)
								&& (joystick.getX1() <= -200)) {
							buttonPosition.setText("DOWN - " + joystick.getY1()
									/ 2 + "% - LEFT - 100%");
						} else if ((joystick.getY1() >= 200)
								&& (joystick.getX1() > -200)) {
							buttonPosition.setText("DOWN - 100% - LEFT - "
									+ (-joystick.getX1() / 2) + "%");
						} else {
							buttonPosition.setText("DOWN - " + joystick.getY1()
									/ 2 + "% - " + "LEFT - "
									+ (-joystick.getX1() / 2) + "%");
						}
					} else if (direction == Constants.STICK_UPLEFT) {
						if ((joystick.getY1() > -200)
								&& (joystick.getX1() <= -200)) {
							buttonPosition.setText("UP - "
									+ (-joystick.getY1() / 2)
									+ "% - LEFT - 100%");
						} else if ((joystick.getY1() <= -200)
								&& (joystick.getX1() > -200)) {
							buttonPosition.setText("UP - 100%" + " - LEFT - "
									+ (-joystick.getX1() / 2) + "%");
						} else {
							buttonPosition
									.setText("UP - " + (-joystick.getY1() / 2)
											+ "% - " + "LEFT - "
											+ (-joystick.getX1() / 2) + "%");
						}
					} else if (direction == Constants.STICK_NONE) {
						buttonPosition.setText("CENTER - 0");
					}
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					buttonPosition.setText("CENTER - 0");
				}
				return true;
			}
		};
	}

	@Override
	protected void onResume() {
		super.onResume();
		joystick.drawStick();
	}

	@Override
	protected void doAdkRead(String stringIn) {
		// Read some information from the hardware
	}
}
