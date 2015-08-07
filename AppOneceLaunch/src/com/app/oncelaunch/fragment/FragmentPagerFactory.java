package com.app.oncelaunch.fragment;

import android.support.v4.app.Fragment;

import com.app.oncelaunch.MainActivity;
import com.app.oncelaunch.R;
import com.app.oncelaunch.util.ContextUtil;


public class FragmentPagerFactory {
	public final static String CHOOSE = ContextUtil.getInstance().getString(
			R.string.str_choose_app);
	public final static String CHOSEN = ContextUtil.getInstance().getString(
			R.string.str_chosen_app);

	public static Fragment create(String tag) {
		if (tag.equals(CHOOSE)) {
			return new FragmentPageChoose();
		}

		if (tag.equals(CHOSEN)) {
			return new FragmentPageChosen();
		}
		
		return null;
	}
	public static Fragment create(int index) {
		switch(index){
		case MainActivity.FRAGMENT_CHOOSE:
			return new FragmentPageChoose();
		case MainActivity.FRAGMENT_CHOSEN:
			return new FragmentPageChosen();
		default:
				return null;
		}
	}
}
