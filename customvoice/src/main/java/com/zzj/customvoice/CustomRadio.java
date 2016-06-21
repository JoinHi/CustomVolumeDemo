package com.zzj.customvoice;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by bjh on 16/6/20.
 */
public class CustomRadio extends View {

    private Bitmap mImage;
    private int mFirstColor;
    private int mSecondColor;
    private int mCount;//音量的上限
    private int mSplitSize;
    private Paint mPaint;
    private Rect mRect;
    private int mCircleWidth;
    private int mCurrentCount;
    private int downY;

    private int spaceCount;//下方留的空白个数

    public CustomRadio(Context context) {
        this(context, null);
    }

    public CustomRadio(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRadio(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomRadio, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.CustomRadio_firstColor) {
                mFirstColor = a.getColor(attr, Color.GREEN);

            } else if (attr == R.styleable.CustomRadio_secondColor) {
                mSecondColor = a.getColor(attr, Color.CYAN);

            } else if (attr == R.styleable.CustomRadio_splitSize) {
                mSplitSize = a.getInt(attr, 20);

            } else if (attr == R.styleable.CustomRadio_bg) {
                mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));

            } else if (attr == R.styleable.CustomRadio_circleWidth) {
                mCircleWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.CustomRadio_spaceCount) {
                spaceCount = a.getInt(attr, 0);

            } else if (attr == R.styleable.CustomRadio_dotCount) {
                mCount = a.getInt(attr, 20);
            }
        }
        a.recycle();
        mCount += spaceCount;
        mPaint = new Paint();
        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        int center = getWidth() / 2;
        int radius = center - mCircleWidth / 2;
        drawOval(canvas, center, radius);//画圆弧
        int relRadius = center - mCircleWidth;
        mRect.left = (int) (center - Math.sqrt(1.0f * relRadius * relRadius / 2));
        mRect.top = (int) (center - Math.sqrt(1.0f * relRadius * relRadius / 2));
        mRect.right = (int) (center + Math.sqrt(1.0f * relRadius * relRadius / 2));
        mRect.bottom = (int) (center + Math.sqrt(1.0f * relRadius * relRadius / 2));
        if (mImage.getWidth() < Math.sqrt(2) * relRadius) {
            mRect.left = center - mImage.getWidth() / 2;
            mRect.top = center - mImage.getWidth() / 2;
            mRect.right = center + mImage.getWidth() / 2;
            mRect.bottom = center + mImage.getWidth() / 2;
        }
        canvas.drawBitmap(mImage, null, mRect, mPaint);
    }

    private void drawOval(Canvas canvas, int center, int radius) {
        float itemSize = (360 * 1.0f - mCount * mSplitSize) / mCount;
        float space = itemSize * spaceCount + mSplitSize * (spaceCount + 1);
        //圆的大小
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        mPaint.setColor(mFirstColor);
        for (int i = 0; i < mCount - spaceCount; i++) {
            canvas.drawArc(oval, (i) * (itemSize + mSplitSize) + 90 + space / 2, itemSize, false, mPaint);
        }
        mPaint.setColor(mSecondColor);
        for (int i = 0; i < mCurrentCount; i++) {
            canvas.drawArc(oval, (i) * (itemSize + mSplitSize) + 90 + space / 2, itemSize, false, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                int upY = (int) event.getY();
                if (downY > upY) {
                    up();
                } else {
                    down();
                }
                break;
        }
        return true;
    }

    private void down() {
        mCurrentCount--;
        if (mCurrentCount < 0) {
            mCurrentCount = 0;
        }
        changeVolume();
    }

    private void up() {
        mCurrentCount++;
        if (mCurrentCount > mCount - spaceCount) {
            mCurrentCount = mCount - spaceCount;
        }
        changeVolume();
    }

    private void changeVolume() {
        if (listener != null) {
            listener.volumeChange(mCurrentCount);
        }
        postInvalidate();
    }

    public interface OnVolumeChangeListener {
        void volumeChange(int level);
    }

    private OnVolumeChangeListener listener;

    public void setOnVolumeChangeListener(OnVolumeChangeListener listener) {
        this.listener = listener;
    }
}
