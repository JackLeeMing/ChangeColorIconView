package com.android.androidview.widget;

import com.android.androidview.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class ChangeColorIconView extends View {
	private Paint mPaint;
	private Bitmap mBitmap;
	private Canvas mCanvas;
	//颜色
	private int mColor=0xFF45C01A;
	private float mAlpha=0f;
	private Bitmap mIconBitmap;
	private Rect mIconRect;
	private String mText="薇薇";
	private int mTextSize=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics());
	private Paint mTextPaint;
	private Rect mTextBound=new Rect();//a empty Rect

	public ChangeColorIconView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public ChangeColorIconView(Context context) {
		this(context,null);
	}
	/**
	 * 自定义属性获取
	 * */
	public ChangeColorIconView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.changeColorIconView);
		int n=a.getIndexCount();
		for (int i = 0; i <n; i++) {
			int attr=a.getIndex(i);
			switch (attr) {
			case R.styleable.changeColorIconView_icon_n:
				BitmapDrawable drawable=(BitmapDrawable) a.getDrawable(attr);
				mIconBitmap=drawable.getBitmap();
				break;
			case R.styleable.changeColorIconView_color:
				mColor=a.getColor(attr, 0x45c01a);
				break;
			case R.styleable.changeColorIconView_text:
				mText=a.getString(attr);
				break;
			case R.styleable.changeColorIconView_text_size:
				mTextSize=(int) a.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,10,getResources().getDisplayMetrics()));
				break;
			default:
				break;
			}
		}
		a.recycle();
		mTextPaint=new Paint();
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setColor(0xff555555);
		mTextPaint.getTextBounds(mText, 0,mText.length(), mTextBound);
	}
	/**
	 * 测控  测出图标绘制范围
	 * */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int bitmapWidth=Math.min(getMeasuredWidth()-getPaddingLeft()-getPaddingRight(), getMeasuredHeight()-getPaddingBottom()-getPaddingTop()-mTextBound.height());
		int left=getMeasuredWidth()/2-bitmapWidth/2;
		int top=(getMeasuredHeight()-mTextBound.height())/2-bitmapWidth/2;
		mIconRect=new Rect(left,top,left+bitmapWidth,top+bitmapWidth);
		//获得图标绘制的矩形区域
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		int alpha=(int) Math.ceil(255*mAlpha);//按规则把小数变为整数取不小于该数字的整数2.5=3  -1.5=-1
		canvas.drawBitmap(mIconBitmap, null,mIconRect,null);//普通绘制
		setupTargetBitmap(alpha);
		drawSourceText(canvas,alpha);//默认的字体
		draTargetText(canvas,alpha);//选中的字体
		//字体颜色控制===画了两层文字，通过控制透明度来设置字体颜色
		canvas.drawBitmap(mBitmap, 0, 0,null);
	}
	private void draTargetText(Canvas canvas, int alpha) {
		// TODO Auto-generated method stub
		mTextPaint.setColor(mColor);
		mTextPaint.setAlpha(alpha);
		canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2
				- mTextBound.width() / 2,
				mIconRect.bottom + mTextBound.height(), mTextPaint);

	}
	private void drawSourceText(Canvas canvas, int alpha) {
		// TODO Auto-generated method stub
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setColor(0xff333333);
		mTextPaint.setAlpha(255-alpha);
		canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2
				- mTextBound.width() / 2,
				mIconRect.bottom + mTextBound.height(), mTextPaint);

	}
	private void setupTargetBitmap(int alpha) {
		// TODO Auto-generated method stub
		mBitmap=Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(),Config.ARGB_8888);
		mCanvas=new Canvas(mBitmap);
		mPaint=new Paint();
		mPaint.setColor(mColor);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setAlpha(alpha);
		mCanvas.drawRect(mIconRect, mPaint);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mPaint.setAlpha(255);
		mCanvas.drawBitmap(mIconBitmap, null,mIconRect,mPaint);
	}
	public void setIconAlpha(float alpha){
		this.mAlpha=alpha;
		invalidateView();
	}
	private void invalidateView() {
		// TODO Auto-generated method stub
		if (Looper.getMainLooper()==Looper.myLooper()) {
			invalidate();
		}else{
			postInvalidate();
		}
	}
	public void setIconColor(int color)
	{
		mColor = color;
	}

	public void setIcon(int resId)
	{
		this.mIconBitmap = BitmapFactory.decodeResource(getResources(), resId);
		if (mIconRect != null)
			invalidateView();
	}

	public void setIcon(Bitmap iconBitmap)
	{
		this.mIconBitmap = iconBitmap;
		if (mIconRect != null)
			invalidateView();
	}

	private static final String INSTANCE_STATE = "instance_state";
	private static final String STATE_ALPHA = "state_alpha";

	@Override
	protected Parcelable onSaveInstanceState()
	{
		Bundle bundle = new Bundle();
		bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
		bundle.putFloat(STATE_ALPHA, mAlpha);
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state)
	{
		if (state instanceof Bundle)
		{
			Bundle bundle = (Bundle) state;
			mAlpha = bundle.getFloat(STATE_ALPHA);
			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
		} else
		{
			super.onRestoreInstanceState(state);
		}

	}

}
