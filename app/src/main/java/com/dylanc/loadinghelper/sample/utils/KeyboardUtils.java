package com.dylanc.loadinghelper.sample.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author Dylan Cai
 * @since 2019/6/27
 */
public class KeyboardUtils {

  public static void showKeyboard(Context context, EditText editText) {
    InputMethodManager imm = (InputMethodManager) context
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
        InputMethodManager.HIDE_IMPLICIT_ONLY);
  }

  public static void hideKeyboard(Context context, EditText editText) {
    InputMethodManager imm = (InputMethodManager) context
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
  }

}
