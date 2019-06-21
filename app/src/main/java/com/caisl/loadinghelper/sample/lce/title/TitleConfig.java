package com.caisl.loadinghelper.sample.lce.title;


import android.graphics.drawable.Drawable;

import java.util.HashMap;

/**
 * @author caisl
 * @since 2019/4/9
 */
public class TitleConfig {

  private String mTitleText;
  private Type mType;
  private Drawable mLeftIcon;
  private Runnable mLeftClickTask;
  private String mRightText;
  private Drawable mRightIcon;
  private Runnable mRightClickTask;
  private HashMap<Object, Object> mConfig;

  public String getTitleText() {
    return mTitleText;
  }

  public void setTitleText(String titleText) {
    mTitleText = titleText;
  }

  public Type getType() {
    return mType;
  }

  public void setType(Type type) {
    mType = type;
  }

  public Drawable getLeftIcon() {
    return mLeftIcon;
  }

  public void setLeftIcon(Drawable leftIcon) {
    mLeftIcon = leftIcon;
  }

  public Runnable getLeftClickTask() {
    return mLeftClickTask;
  }

  public void setLeftClickTask(Runnable leftClickTask) {
    mLeftClickTask = leftClickTask;
  }

  public String getRightText() {
    return mRightText;
  }

  public void setRightText(String rightText) {
    mRightText = rightText;
  }

  public Drawable getRightIcon() {
    return mRightIcon;
  }

  public void setRightIcon(Drawable rightIcon) {
    mRightIcon = rightIcon;
  }

  public Runnable getRightClickTask() {
    return mRightClickTask;
  }

  public void setRightClickTask(Runnable rightClickTask) {
    mRightClickTask = rightClickTask;
  }

  public void set(Object key, Object value) {
    getConfig().put(key, value);
  }

  public void get(Object key) {
    getConfig().get(key);
  }

  private HashMap<Object, Object> getConfig() {
    if (mConfig == null) {
      mConfig = new HashMap<>();
    }
    return mConfig;
  }

  public enum Type {
    BACK, NO_BACK
  }

}
