package com.android.androidview.widget;

import com.android.androidview.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class DrawView extends View {
	private Paint mPaint;
	private TextPaint tPaint;
	private int mWidth,mHeight;
	private Context context;
	private Bitmap bitmap;
	private int x,y;
	private int width=150;

	public DrawView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public DrawView(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		this.context=context;
		initPaint();
		initRes(context);
		measureShow((Activity)context);
		
	}

	private void initRes(Context context) {
		// TODO Auto-generated method stub
		bitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.af);
	}

	private void initPaint() {
		// TODO Auto-generated method stub
		mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
		tPaint=new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.RED);
		setLayerType(LAYER_TYPE_SOFTWARE, null); 
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.FILL);
		 mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
		tPaint.setColor(Color.BLACK);
		tPaint.setStyle(Paint.Style.FILL);
		
	}

	private void measureShow(Activity activity){
		DisplayMetrics metrics=new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mWidth=metrics.widthPixels;
		mHeight=metrics.heightPixels;
		x=mWidth/2-bitmap.getWidth()/2;
		y=mHeight/2-bitmap.getHeight()/2;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		canvas.drawColor(Color.WHITE);
		//保存图层
	    Rect rect=new Rect(50, 50, 250,250);
        Bitmap mBitmap = Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(),Config.ARGB_8888);
        Canvas mCanvas=new Canvas(mBitmap);
        mPaint=new Paint();
		mPaint.setColor(Color.GREEN);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		//mCanvas.drawRect(rect, mPaint);
		mCanvas.drawCircle(150, 145, 95, mPaint);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		mCanvas.drawBitmap(bitmap, null,rect,mPaint);
		canvas.drawBitmap(mBitmap, 50, 50,null);
        //恢复图层
	}
	
	
	public Bitmap creatMBitmap(){ 
		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inJustDecodeBounds=true;
		BitmapFactory.decodeResource(getResources(), R.drawable.ag,options);
		int samleSize=calculateInSampleSize(options, width, width);
		options.inSampleSize=samleSize;
		options.inJustDecodeBounds=false;
		Bitmap resource = BitmapFactory.decodeResource(getResources(), R.drawable.ag,options);
		Bitmap reBitmap=Bitmap.createBitmap(width,width, Config.ARGB_8888);
		 Canvas canvas = new Canvas(reBitmap);
	        // 画圆
	        canvas.drawCircle(width / 2, width / 2, width / 2, mPaint);
	        // 选择交集去上层图片
	        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
	        canvas.drawBitmap(resource, new Rect(0, 0, resource.getWidth(), resource.getWidth()),
	                new Rect(0, 0,width, width), mPaint);
		return resource;
		
	}
    private int calculateInSampleSize(BitmapFactory.Options options,
            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
 
        if (height > reqHeight || width > reqWidth) {
 
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
 
            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
