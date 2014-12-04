package br.edu.ufam.engcomp.wheelchair;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import br.edu.ufam.engcomp.wheelchair.joystick.JoyStickComponent;

public class MainActivity extends Activity {

	private RelativeLayout joystickLayout;
	private JoyStickComponent joystick;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		joystickLayout = (RelativeLayout) findViewById(R.id.joystick_layout);
		joystick = new JoyStickComponent(getApplicationContext(),
				joystickLayout, R.drawable.joystick_button);
		joystick.drawStick();
		setJoystickParams(joystick);

		joystickLayout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				return onTouchJoystick(view, event);
			}
		});

	}

	public void setJoystickParams(JoyStickComponent js) {
		js.setStickSize(150, 150);
		js.setLayoutAlpha(150);
		js.setStickAlpha(100);
		js.setOffset(90);
		js.setMinimumDistance(20);
	}

	public boolean onTouchJoystick(View view, MotionEvent event) {
		joystick.drawStick(event);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		joystick.drawStick();
	}
}
