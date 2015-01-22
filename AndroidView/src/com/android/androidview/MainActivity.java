package com.android.androidview;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.android.androidview.widget.ChangeColorIconView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.os.Bundle;
public class MainActivity extends ActionBarActivity implements OnClickListener,OnPageChangeListener {
	private ViewPager mPager;
	private List<Fragment>mTabs=new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdpter;
	private String[] mTitles=new String[]{"First Fragment!",  
			"Second Fragment!", "Third Fragment!", "Fourth Fragment!" };
	private List<ChangeColorIconView>mTabIndicator=new ArrayList<ChangeColorIconView>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setOverflowShowingAlways(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		mPager=(ViewPager) findViewById(R.id.id_viewpager);
		initDatas();
		mPager.setAdapter(mAdpter);
		mPager.setOnPageChangeListener(this);
	}


	private void initDatas() {
		// TODO Auto-generated method stub
		for (String title:mTitles) {
			TabFragment tabFragment=new TabFragment();
			Bundle args=new Bundle();
			args.putString("title",title);
			tabFragment.setArguments(args);
			mTabs.add(tabFragment);
		}
		mAdpter=new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return mTabs.get(arg0);
			}
		};
		initTabIndicator();
	}

	private void initTabIndicator() {
		// TODO Auto-generated method stub
		ChangeColorIconView one=(ChangeColorIconView) findViewById(R.id.id_indicator_one);
		ChangeColorIconView two=(ChangeColorIconView) findViewById(R.id.id_indicator_two);
		ChangeColorIconView three=(ChangeColorIconView) findViewById(R.id.id_indicator_three);
		ChangeColorIconView four=(ChangeColorIconView) findViewById(R.id.id_indicator_four);
		mTabIndicator.add(one);
		mTabIndicator.add(two);
		mTabIndicator.add(three);
		mTabIndicator.add(four);
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		
		one.setIconAlpha(1.0f);

	}


	private void setOverflowShowingAlways(boolean b) {
		// TODO Auto-generated method stub
		try  
		{  
			// true if a permanent menu key is present, false otherwise.  
			ViewConfiguration config = ViewConfiguration.get(this);  
			Field menuKeyField = ViewConfiguration.class  
					.getDeclaredField("sHasPermanentMenuKey");  
			menuKeyField.setAccessible(true);  
			menuKeyField.setBoolean(config, false);  
		} catch (Exception e)  
		{  
			e.printStackTrace();  
		}  
	}
	/** 
	 * 重置其他的Tab 
	 */  
	private void resetOtherTabs()  
	{  
		for (int i = 0; i < mTabIndicator.size(); i++)  
		{  
			mTabIndicator.get(i).setIconAlpha(0);  
		}  
	}  

	@Override  
	public boolean onMenuOpened(int featureId, Menu menu)  
	{  
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null)  
		{  
			if (menu.getClass().getSimpleName().equals("MenuBuilder"))  
			{  
				try  
				{  
					Method m = menu.getClass().getDeclaredMethod(  
							"setOptionalIconsVisible", Boolean.TYPE);  
					m.setAccessible(true);  
					m.invoke(menu, true);  
				} catch (Exception e)  
				{  
				}  
			}  
		}  
		return super.onMenuOpened(featureId, menu);  
	} 

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		resetOtherTabs();  
		switch (v.getId())  
		{  
		case R.id.id_indicator_one:  
			mTabIndicator.get(0).setIconAlpha(1.0f);  
			mPager.setCurrentItem(0, false);  
			break;  
		case R.id.id_indicator_two:  
			mTabIndicator.get(1).setIconAlpha(1.0f);  
			mPager.setCurrentItem(1, false);  
			break;  
		case R.id.id_indicator_three:  
			mTabIndicator.get(2).setIconAlpha(1.0f);  
			mPager.setCurrentItem(2, false);  
			break;  
		case R.id.id_indicator_four:  
			mTabIndicator.get(3).setIconAlpha(1.0f);  
			mPager.setCurrentItem(3, false);  
			break;  

		}  
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels){
		// TODO Auto-generated method stub
		if (positionOffset>0) {
			ChangeColorIconView left=mTabIndicator.get(position);
			ChangeColorIconView right=mTabIndicator.get(position+1);
			left.setIconAlpha(1-positionOffset);
			right.setIconAlpha(positionOffset);
		}
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}
}
