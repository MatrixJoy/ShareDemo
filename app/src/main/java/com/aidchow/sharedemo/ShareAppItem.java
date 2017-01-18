package com.aidchow.sharedemo;

import android.graphics.drawable.Drawable;

/**
 * Created by 78537 on 2017/1/17.
 */

public class ShareAppItem {
    private CharSequence appLabel;
    private Drawable appIcon;
    private String appName;
    private String pacageName;

    public ShareAppItem(CharSequence appLabel, Drawable appIcon, String appName, String pacageName) {
        this.appLabel = appLabel;
        this.appIcon = appIcon;
        this.appName = appName;
        this.pacageName = pacageName;
    }

    public CharSequence getAppLabel() {
        return appLabel;
    }


    public Drawable getAppIcon() {
        return appIcon;
    }

    public String getPacageName() {
        return pacageName;
    }

    public String getAppName() {
        return appName;
    }
}
