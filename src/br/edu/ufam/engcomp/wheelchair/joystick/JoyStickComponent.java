package br.edu.ufam.engcomp.wheelchair.joystick;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import br.edu.ufam.engcomp.wheelchair.utils.Constants;

public class JoyStickComponent {

	private int mStickAlpha;
	private int mLayoutAlpha;
	private int mOffset;

	private ViewGroup mLayout;
	private LayoutParams mLayoutParams;
	private int mStick_width, mStick_height;

	private int mPositionX, mPositionY, mMinDistance;
	private float mDistance, mAngle;

	private DrawCanvas mDraw;
	private Paint mPaint;
	private Bitmap mStick;

	private boolean mTouchState = false;

	public JoyStickComponent(Context context, ViewGroup layout, int stick_res_id) {
		mLayout = layout;

		mStick = BitmapFactory.decodeResource(context.getResources(),
				stick_res_id);

		mLayoutParams = mLayout.getLayoutParams();

		mStick_width = mStick.getWidth();
		mStick_height = mStick.getHeight();

		mDraw = new DrawCanvas(context);
		mPaint = new Paint();
	}

	public void drawStick() {
		mDraw.position(mLayoutParams.width / 2, mLayoutParams.height / 2);
		draw();
	}

	public void drawStick(MotionEvent event) {
		mPositionX = (int) (event.getX() - (mLayoutParams.width / 2));
		mPositionY = (int) (event.getY() - (mLayoutParams.height / 2));
		mDistance = (float) Math.sqrt(Math.pow(mPositionX, 2)
				+ Math.pow(mPositionY, 2));
		mAngle = (float) cal_angle(mPositionX, mPositionY);

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (mDistance <= (mLayoutParams.width / 2) - mOffset) {
				mDraw.position(event.getX(), event.getY());
				draw();
				mTouchState = true;
			}
		} else if (event.getAction() == MotionEvent.ACTION_MOVE && mTouchState) {
			if (mDistance <= (mLayoutParams.width / 2) - mOffset) {
				mDraw.position(event.getX(), event.getY());
				draw();
			} else if (mDistance > (mLayoutParams.width / 2) - mOffset) {
				float x = (float) (Math.cos(Math.toRadians(cal_angle(
						mPositionX, mPositionY))) * ((mLayoutParams.width / 2) - mOffset));
				float y = (float) (Math.sin(Math.toRadians(cal_angle(
						mPositionX, mPositionY))) * ((mLayoutParams.height / 2) - mOffset));
				x += (mLayoutParams.width / 2);
				y += (mLayoutParams.height / 2);
				mDraw.position(x, y);
				draw();
			} else {
				mLayout.removeView(mDraw);
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			mDraw.position(mLayoutParams.width / 2, mLayoutParams.height / 2);
			draw();
			mTouchState = false;
		}
	}

	public int[] getPosition() {
		if (mDistance > mMinDistance && mTouchState) {
			return new int[] { mPositionX, mPositionY };
		}
		return new int[] { 0, 0 };
	}

	public int getX() {
		if (mDistance > mMinDistance && mTouchState) {
			return mPositionX;
		}
		return 0;
	}

	public int getX1() {
		if (mDistance > mMinDistance && mTouchState) {
			if (mDistance >= Constants.LAYOUT_BORDER) {
				mDistance = Constants.LAYOUT_BORDER;
			}
			return (int) (mDistance * (Math.cos(Math.toRadians(getAngle()))));
		}
		return 0;
	}

	public int getY() {
		if (mDistance > mMinDistance && mTouchState) {
			return mPositionY;
		}
		return 0;
	}

	public int getY1() {
		if (mDistance > mMinDistance && mTouchState) {
			if (mDistance >= Constants.LAYOUT_BORDER) {
				mDistance = Constants.LAYOUT_BORDER;
			}
			return (int) (mDistance * (Math.sin(Math.toRadians(getAngle()))));
		}
		return 0;
	}

	public double getAngle() {
		if (mDistance > mMinDistance && mTouchState) {
			return mAngle;
		}
		return 0;
	}

	public float getDistance() {
		if (mDistance > mMinDistance && mTouchState) {
			return mDistance;
		}
		return 0;
	}

	public void setMinimumDistance(int minDistance) {
		mMinDistance = minDistance;
	}

	public int getMinimumDistance() {
		return mMinDistance;
	}

	public int get8Direction() {
		if (mDistance > mMinDistance && mTouchState) {
			if (mAngle >= 247.5 && mAngle < 292.5)
				return Constants.STICK_UP;
			if (mAngle >= 292.5 && mAngle < 337.5)
				return Constants.STICK_UPRIGHT;
			if (mAngle >= 337.5 || mAngle < 22.5)
				return Constants.STICK_RIGHT;
			if (mAngle >= 22.5 && mAngle < 67.5)
				return Constants.STICK_DOWNRIGHT;
			if (mAngle >= 67.5 && mAngle < 112.5)
				return Constants.STICK_DOWN;
			if (mAngle >= 112.5 && mAngle < 157.5)
				return Constants.STICK_DOWNLEFT;
			if (mAngle >= 157.5 && mAngle < 202.5)
				return Constants.STICK_LEFT;
			if (mAngle >= 202.5 && mAngle < 247.5)
				return Constants.STICK_UPLEFT;

		}
		if (mDistance <= mMinDistance && mTouchState)
			return Constants.STICK_NONE;

		return 0;
	}

	public int get4Direction() {
		if (mDistance > mMinDistance && mTouchState) {
			if (mAngle >= 225 && mAngle < 315)
				return Constants.STICK_UP;
			if (mAngle >= 315 || mAngle < 45)
				return Constants.STICK_RIGHT;
			if (mAngle >= 45 && mAngle < 135)
				return Constants.STICK_DOWN;
			if (mAngle >= 135 && mAngle < 225)
				return Constants.STICK_LEFT;
		}
		if (mDistance <= mMinDistance && mTouchState)
			return Constants.STICK_NONE;

		return 0;
	}

	public void setOffset(int offset) {
		mOffset = offset;
	}

	public int getOffset() {
		return mOffset;
	}

	public void setStickAlpha(int alpha) {
		mStickAlpha = alpha;
		mPaint.setAlpha(alpha);
	}

	public int getStickAlpha() {
		return mStickAlpha;
	}

	public void setLayoutAlpha(int alpha) {
		mLayoutAlpha = alpha;
		mLayout.getBackground().setAlpha(alpha);
	}

	public int getLayoutAlpha() {
		return mLayoutAlpha;
	}

	public void setStickSize(int width, int height) {
		mStick = Bitmap.createScaledBitmap(mStick, width, height, false);
		mStick_width = mStick.getWidth();
		mStick_height = mStick.getHeight();
	}

	public void setStickWidth(int width) {
		mStick = Bitmap.createScaledBitmap(mStick, width, mStick_height, false);
		mStick_width = mStick.getWidth();
	}

	public void setStickHeight(int height) {
		mStick = Bitmap.createScaledBitmap(mStick, mStick_width, height, false);
		mStick_height = mStick.getHeight();
	}

	public int getStickWidth() {
		return mStick_width;
	}

	public int getStickHeight() {
		return mStick_height;
	}

	public void setLayoutSize(int width, int height) {
		mLayoutParams.width = width;
		mLayoutParams.height = height;
	}

	public int getLayoutWidth() {
		return mLayoutParams.width;
	}

	public int getLayoutHeight() {
		return mLayoutParams.height;
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
			mLayout.removeView(mDraw);
		} catch (Exception e) {
		}
		mLayout.addView(mDraw);
	}

	private class DrawCanvas extends View {
		float x, y;

		private DrawCanvas(Context context) {
			super(context);
		}

		public void onDraw(Canvas canvas) {
			canvas.drawBitmap(mStick, x, y, mPaint);
		}

		private void position(float pos_x, float pos_y) {
			x = pos_x - (mStick_width / 2);
			y = pos_y - (mStick_height / 2);
		}
	}

	public void setJoystickParams() {
		this.setStickSize(Constants.STICK_WIDTH, Constants.STICK_HEIGHT);
		this.setLayoutAlpha(Constants.LAYOUT_ALPHA);
		this.setStickAlpha(Constants.STICK_ALPHA);
		this.setOffset(Constants.OFFSET);
		this.setMinimumDistance(Constants.MIN_DISTANCE);
	}

	public String getJoystickPosition() {
		return "CENTER - 0";
	}

	public String getJoystickPosition(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MOVE) {
			int direction = this.get8Direction();
			if (direction == Constants.STICK_UP) {
				if (this.getY() <= -200) {
					return "UP - 100%";
				} else {
					return "UP - " + (-this.getY() / 2) + "%";
				}
			}
			if (direction == Constants.STICK_RIGHT) {
				if (this.getX() >= 200) {
					return "RIGHT - 100%";
				} else {
					return "RIGHT - " + this.getX() / 2 + "%";
				}
			}
			if (direction == Constants.STICK_DOWN) {
				if (this.getY() >= 200) {
					return "DOWN - 100%";
				} else {
					return "DOWN - " + this.getY() / 2 + "%";
				}
			}
			if (direction == Constants.STICK_LEFT) {
				if (this.getX() <= -200) {
					return "LEFT - 100%";
				} else {
					return "LEFT - " + (-this.getX() / 2) + "%";
				}
			}
			if (direction == Constants.STICK_UPRIGHT) {
				if ((this.getY() > -200) && (this.getX1() >= 200)) {
					return "UP - " + (-this.getY1() / 2) + "% - RIGHT - 100%";
				}
				if ((this.getY1() <= -200) && (this.getX1() < 200)) {
					return "UP - 100%" + " - RIGHT - " + this.getX1() / 2 + "%";
				} else {
					return "UP - " + (-this.getY1() / 2) + "% - " + "RIGHT - "
							+ this.getX1() / 2 + "%";

				}
			}
			if (direction == Constants.STICK_DOWNRIGHT) {
				if ((this.getY1() < 200) && (this.getX1() >= 200)) {
					return "DOWN - " + (this.getY1() / 2) + "% - RIGHT - 100%";
				}
				if ((this.getY1() >= 200) && (this.getX1() < 200)) {
					return "DOWN - 100%" + " - RIGHT - " + this.getX1() / 2
							+ "%";
				} else {
					return "DOWN - " + this.getY1() / 2 + "% - " + "RIGHT - "
							+ this.getX1() / 2 + "%";
				}
			}
			if (direction == Constants.STICK_DOWNLEFT) {
				if ((this.getY1() < 200) && (this.getX1() <= -200)) {
					return "DOWN - " + this.getY1() / 2 + "% - LEFT - 100%";
				}
				if ((this.getY1() >= 200) && (this.getX1() > -200)) {
					return "DOWN - 100% - LEFT - " + (-this.getX1() / 2) + "%";
				} else {
					return "DOWN - " + this.getY1() / 2 + "% - " + "LEFT - "
							+ (-this.getX1() / 2) + "%";
				}
			}
			if (direction == Constants.STICK_UPLEFT) {
				if ((this.getY1() > -200) && (this.getX1() <= -200)) {
					return "UP - " + (-this.getY1() / 2) + "% - LEFT - 100%";
				}
				if ((this.getY1() <= -200) && (this.getX1() > -200)) {
					return "UP - 100%" + " - LEFT - " + (-this.getX1() / 2)
							+ "%";
				} else {
					return "UP - " + (-this.getY1() / 2) + "% - " + "LEFT - "
							+ (-this.getX1() / 2) + "%";
				}
			}
			if (direction == Constants.STICK_NONE) {
				return getJoystickPosition();
			}
		}
		return getJoystickPosition();

	}
}
