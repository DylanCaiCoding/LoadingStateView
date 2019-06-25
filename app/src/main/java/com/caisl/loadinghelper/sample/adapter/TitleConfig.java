package com.caisl.loadinghelper.sample.adapter;


/**
 * @author caisl
 * @since 2019/4/9
 */
public class TitleConfig {

  private String mTitleText;
  private Type mType;
  private String mRightText;
  private Runnable mRightClickTask;

  public TitleConfig() {
  }

  public TitleConfig(String titleText, Type type) {
    mTitleText = titleText;
    mType = type;
  }

  public TitleConfig(String rightText, Runnable rightClickTask) {
    mRightText = rightText;
    mRightClickTask = rightClickTask;
  }

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

  public String getRightText() {
    return mRightText;
  }

  public void setRightText(String rightText) {
    mRightText = rightText;
  }

  public Runnable getRightClickTask() {
    return mRightClickTask;
  }

  public void setRightClickTask(Runnable rightClickTask) {
    mRightClickTask = rightClickTask;
  }

  public enum Type {
    BACK, NO_BACK
  }

}
