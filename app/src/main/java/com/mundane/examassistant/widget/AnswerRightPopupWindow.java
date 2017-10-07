package com.mundane.examassistant.widget;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by mundane on 2017/10/4 下午12:48
 */

public class AnswerRightPopupWindow extends PopupWindow {
	public AnswerRightPopupWindow(View contentView) {
		super(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		setOutsideTouchable(true);
		setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}
}
