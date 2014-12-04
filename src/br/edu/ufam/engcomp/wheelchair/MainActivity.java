package br.edu.ufam.engcomp.wheelchair;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import br.edu.ufam.engcomp.wheelchair.adk.AdkActivity;
import br.edu.ufam.engcomp.wheelchair.joystick.JoyStickComponent;

public class MainActivity extends AdkActivity {

	private RelativeLayout joystickLayout;
	private JoyStickComponent joystick;

	@Override
	protected void doOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);

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
		//Read some information from the hardware
	}
}
