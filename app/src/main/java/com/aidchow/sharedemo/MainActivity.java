package com.aidchow.sharedemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    private BottomSheetBehavior behavior;
    private LinkedList<ShareAppItem> shareAppItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        behavior = BottomSheetBehavior.from(findViewById(R.id.design_bottom_sheet));
        getCanSharedApps();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        ShareAppAater appAater = new ShareAppAater(shareAppItems);
        recyclerView.setAdapter(appAater);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        appAater.setOnItemClickListener(this);
    }

    /**
     * 获取系统内具有分享功能的app
     */
    private void getCanSharedApps() {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("*/*");
        PackageManager pm = this.getPackageManager();
        List<ResolveInfo> resInfos = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        shareAppItems = new LinkedList<>();
        for (ResolveInfo info : resInfos) {
            CharSequence appLabel = info.loadLabel(pm);//获取应用当前标签名
            Drawable appIcon = info.loadIcon(pm);//获取可分享应用的icon
            String appName = info.activityInfo.name;//当前activity的包名
            String packageName = info.activityInfo.packageName;//当前应用包名
            ShareAppItem shareAppItem = new ShareAppItem(appLabel, appIcon, appName, packageName);
            shareAppItems.add(shareAppItem);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {
            behavior.setHideable(true);
            if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            } else {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 分享想要分享的数据
     *
     * @param position
     * @param shareAppItems
     */
    private void share(int position, LinkedList<ShareAppItem> shareAppItems) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        sendIntent.setPackage(shareAppItems.get(position).getPacageName());
        sendIntent.setClassName(shareAppItems.get(position).getPacageName(), shareAppItems.get(position).getAppName());
        startActivity(sendIntent);
    }

    /**
     * 根据应用的包名确定具体的分享方案
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        switch (shareAppItems.get(position).getAppName()) {
            case "com.tencent.mm.ui.tools.ShareImgUI":
                Toast.makeText(MainActivity.this, "分享到微信", Toast.LENGTH_SHORT).show();
                break;
            case "com.tencent.mm.ui.tools.ShareToTimeLineUI":
                Toast.makeText(MainActivity.this, "分享到微信朋友圈", Toast.LENGTH_SHORT).show();
                break;
            default:
                share(position, shareAppItems);
        }
    }
}
