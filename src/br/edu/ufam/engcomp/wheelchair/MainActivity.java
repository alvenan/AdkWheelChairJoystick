package br.edu.ufam.engcomp.wheelchair;

import java.util.Arrays;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.edu.ufam.engcomp.wheelchair.adk.AbsAdkActivity;
import br.edu.ufam.engcomp.wheelchair.joystick.JoystickComponent;

public class MainActivity extends AbsAdkActivity {

	private RelativeLayout joystickLayout;
	private JoystickComponent joystick;
	private TextView buttonPosition;

	private boolean enableLogcatDebug = true;

	@Override
	protected void doOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);

		buttonPosition = (TextView) findViewById(R.id.position);
		joystickLayout = (RelativeLayout) findViewById(R.id.joystick_layout);

		joystick = new JoystickComponent(getApplicationContext(),
				joystickLayout, R.drawable.joystick_button);

		joystickLayout.setOnTouchListener(onTouchJoystickListener());

	}

	public OnTouchListener onTouchJoystickListener() {
		return new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				joystick.drawStick(event);
				buttonPosition.setText(Arrays.toString(joystick
						.getJoystickPositionInByte(event, enableLogcatDebug)));
				WriteAdk(joystick
						.getJoystickPositionInByte(event, enableLogcatDebug));
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
		// Read some information sent from the accessory
	}
}
