package br.edu.ufam.engcomp.wheelchair;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.edu.ufam.engcomp.wheelchair.adk.AdkActivity;
import br.edu.ufam.engcomp.wheelchair.joystick.JoystickComponent;

public class MainActivity extends AdkActivity {

	private RelativeLayout joystickLayout;
	private JoystickComponent joystick;
	private TextView buttonPosition;

	@Override
	protected void doOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);

		buttonPosition = (TextView) findViewById(R.id.position);
		joystickLayout = (RelativeLayout) findViewById(R.id.joystick_layout);

		joystick = new JoystickComponent(getApplicationContext(),
				joystickLayout, R.drawable.joystick_button);
		joystick.setJoystickParams();
		
		joystickLayout.setOnTouchListener(onTouchJoystickListener());

	}

	public OnTouchListener onTouchJoystickListener() {
		return new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				joystick.drawStick(event);
				buttonPosition.setText(joystick.getJoystickPositionInByte(event));
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
		// Read some information sent from the hardware
	}
}
