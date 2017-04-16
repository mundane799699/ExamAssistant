package com.mundane.examassistant.utils;

import android.widget.Toast;
import com.mundane.examassistant.global.CommonUtils;

/**
 * Created by mundane on 2017/4/16.
 */

public class ToastUtils {
    private static Toast sToast;
    public static void shwoToast(String text){
        if(sToast ==null){
            //创建toast
            sToast = Toast.makeText(CommonUtils.getContext(),text,Toast.LENGTH_SHORT);
        }
        //如果吐司已经创建，那么直接更改吐司的文本即可
        sToast.setText(text);
        //最后显示
        sToast.show();
    }
}
