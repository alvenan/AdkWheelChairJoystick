package br.edu.ufam.engcomp.wheelchair.joystick;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import br.edu.ufam.engcomp.wheelchair.utils.Constants;

public class JoyStickComponent {

	private int stickAlpha;
	private int layoutAlpha;
	private int OFFSET;

	private ViewGroup mLayout;
	private LayoutParams layoutParams;
	private int stick_width, stick_height;

	private int position_x, position_y, min_distance;
	private float distance, angle;

	private DrawCanvas draw;
	private Paint paint;
	private Bitmap stick;

	private boolean touch_state = false;

	public JoyStickComponent(Context context, ViewGroup layout, int stick_res_id) {
		mLayout = layout;

		stick = BitmapFactory.decodeResource(context.getResources(),
				stick_res_id);

		layoutParams = mLayout.getLayoutParams();

		stick_width = stick.getWidth();
		stick_height = stick.getHeight();

		draw = new DrawCanvas(context);
		paint = new Paint();
	}

	public void drawStick() {
		draw.position(layoutParams.width / 2, layoutParams.height / 2);
		draw();
	}

	public void drawStick(MotionEvent event) {
		position_x = (int) (event.getX() - (layoutParams.width / 2));
		position_y = (int) (event.getY() - (layoutParams.height / 2));
		distance = (float) Math.sqrt(Math.pow(position_x, 2)
				+ Math.pow(position_y, 2));
		angle = (float) cal_angle(position_x, position_y);

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (distance <= (layoutParams.width / 2) - OFFSET) {
				draw.position(event.getX(), event.getY());
				draw();
				touch_state = true;
			}
		} else if (event.getAction() == MotionEvent.ACTION_MOVE && touch_state) {
			if (distance <= (layoutParams.width / 2) - OFFSET) {
				draw.position(event.getX(), event.getY());
				draw();
			} else if (distance > (layoutParams.width / 2) - OFFSET) {
				float x = (float) (Math.cos(Math.toRadians(cal_angle(
						position_x, position_y))) * ((layoutParams.width / 2) - OFFSET));
				float y = (float) (Math.sin(Math.toRadians(cal_angle(
						position_x, position_y))) * ((layoutParams.height / 2) - OFFSET));
				x += (layoutParams.width / 2);
				y += (layoutParams.height / 2);
				draw.position(x, y);
				draw();
			} else {
				mLayout.removeView(draw);
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			draw.position(layoutParams.width / 2, layoutParams.height / 2);
			draw();
			touch_state = false;
		}
	}

	public int[] getPosition() {
		if (distance > min_distance && touch_state) {
			return new int[] { position_x, position_y };
		}
		return new int[] { 0, 0 };
	}

	public int getX() {
		if (distance > min_distance && touch_state) {
			return position_x;
		}
		return 0;
	}

	public int getX1() {
		if (distance > min_distance && touch_state) {
			if (distance >= Constants.LAYOUT_BORDER) {
				distance = Constants.LAYOUT_BORDER;
			}
			return (int) (distance * (Math.cos(Math.toRadians(getAngle()))));
		}
		return 0;
	}

	public int getY() {
		if (distance > min_distance && touch_state) {
			return position_y;
		}
		return 0;
	}

	public int getY1() {
		if (distance > min_distance && touch_state) {
			if (distance >= Constants.LAYOUT_BORDER) {
				distance = Constants.LAYOUT_BORDER;
			}
			return (int) (distance * (Math.sin(Math.toRadians(getAngle()))));
		}
		return 0;
	}

	public double getAngle() {
		if (distance > min_distance && touch_state) {
			return angle;
		}
		return 0;
	}

	public float getDistance() {
		if (distance > min_distance && touch_state) {
			return distance;
		}
		return 0;
	}

	public void setMinimumDistance(int minDistance) {
		min_distance = minDistance;
	}

	public int getMinimumDistance() {
		return min_distance;
	}

	public int get8Direction() {
		if (distance > min_distance && touch_state) {
			if (angle >= 247.5 && angle < 292.5) {
				return Constants.STICK_UP;
			} else if (angle >= 292.5 && angle < 337.5) {
				return Constants.STICK_UPRIGHT;
			} else if (angle >= 337.5 || angle < 22.5) {
				return Constants.STICK_RIGHT;
			} else if (angle >= 22.5 && angle < 67.5) {
				return Constants.STICK_DOWNRIGHT;
			} else if (angle >= 67.5 && angle < 112.5) {
				return Constants.STICK_DOWN;
			} else if (angle >= 112.5 && angle < 157.5) {
				return Constants.STICK_DOWNLEFT;
			} else if (angle >= 157.5 && angle < 202.5) {
				return Constants.STICK_LEFT;
			} else if (angle >= 202.5 && angle < 247.5) {
				return Constants.STICK_UPLEFT;
			}
		} else if (distance <= min_distance && touch_state) {
			return Constants.STICK_NONE;
		}
		return 0;
	}

	public int get4Direction() {
		if (distance > min_distance && touch_state) {
			if (angle >= 225 && angle < 315) {
				return Constants.STICK_UP;
			} else if (angle >= 315 || angle < 45) {
				return Constants.STICK_RIGHT;
			} else if (angle >= 45 && angle < 135) {
				return Constants.STICK_DOWN;
			} else if (angle >= 135 && angle < 225) {
				return Constants.STICK_LEFT;
			}
		} else if (distance <= min_distance && touch_state) {
			return Constants.STICK_NONE;
		}
		return 0;
	}

	public void setOffset(int offset) {
		OFFSET = offset;
	}

	public int getOffset() {
		return OFFSET;
	}

	public void setStickAlpha(int alpha) {
		stickAlpha = alpha;
		paint.setAlpha(alpha);
	}

	public int getStickAlpha() {
		return stickAlpha;
	}

	public void setLayoutAlpha(int alpha) {
		layoutAlpha = alpha;
		mLayout.getBackground().setAlpha(alpha);
	}

	public int getLayoutAlpha() {
		return layoutAlpha;
	}

	public void setStickSize(int width, int height) {
		stick = Bitmap.createScaledBitmap(stick, width, height, false);
		stick_width = stick.getWidth();
		stick_height = stick.getHeight();
	}

	public void setStickWidth(int width) {
		stick = Bitmap.createScaledBitmap(stick, width, stick_height, false);
		stick_width = stick.getWidth();
	}

	public void setStickHeight(int height) {
		stick = Bitmap.createScaledBitmap(stick, stick_width, height, false);
		stick_height = stick.getHeight();
	}

	public int getStickWidth() {
		return stick_width;
	}

	public int getStickHeight() {
		return stick_height;
	}

	public void setLayoutSize(int width, int height) {
		layoutParams.width = width;
		layoutParams.height = height;
	}

	public int getLayoutWidth() {
		return layoutParams.width;
	}

	public int getLayoutHeight() {
		return layoutParams.height;
	}

	private double cal_angle(float x, float y) {
		if (x >= 0 && y >= 0)
			return Math.toDegrees(Math.atan(y / x));
		else if (x < 0 && y >= 0)
			return Math.toDegrees(Math.atan(y / x)) + 180;
		else if (x < 0 && y < 0)
			return Math.toDegrees(Math.atan(y / x)) + 180;
		else if (x >= 0 && y < 0)
			return Math.toDegrees(Math.atan(y / x)) + 360;
		return 0;
	}

	private void draw() {
		try {
			mLayout.removeView(draw);
		} catch (Exception e) {
		}
		mLayout.addView(draw);
	}

	private class DrawCanvas extends View {
		float x, y;

		private DrawCanvas(Context context) {
			super(context);
		}

		public void onDraw(Canvas canvas) {
			canvas.drawBitmap(stick, x, y, paint);
		}

		private void position(float pos_x, float pos_y) {
			x = pos_x - (stick_width / 2);
			y = pos_y - (stick_height / 2);
		}
	}

	public void setJoystickParams() {
		this.setStickSize(Constants.STICK_WIDTH, Constants.STICK_HEIGHT);
		this.setLayoutAlpha(Constants.LAYOUT_ALPHA);
		this.setStickAlpha(Constants.STICK_ALPHA);
		this.setOffset(Constants.OFFSET);
		this.setMinimumDistance(Constants.MIN_DISTANCE);
	}
}
