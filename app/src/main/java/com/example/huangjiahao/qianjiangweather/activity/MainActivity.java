package com.example.huangjiahao.qianjiangweather.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.huangjiahao.qianjiangweather.R;
import com.example.huangjiahao.qianjiangweather.base.BaseActivity;
import com.example.huangjiahao.qianjiangweather.fragment.MessageFragment;
import com.example.huangjiahao.qianjiangweather.fragment.MineFragment;
import com.example.huangjiahao.qianjiangweather.fragment.WeatherFragment;
import com.example.huangjiahao.qianjiangweather.util.SettingUtils;
import com.example.huangjiahao.qianjiangweather.util.Utils;


/**
 * Created by JiaHao.H on 2016/12/13.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private long mExitTime;
    private LinearLayout mTabWeather;
    private ImageView mTabweatherImg;
    private LinearLayout mTabInterview;
    private ImageView mInterviewImage;
    private LinearLayout mTabMessage;
    private ImageView mMessageImage;
    private LinearLayout mTabMine;
    private ImageView mTabMineImg;
    private FragmentManager manager;
    private int mCurrentItem = -1;
    private FragmentTransaction tr;
    private WeatherFragment weatherFragment;
    private MessageFragment messageFragment;
    private MineFragment mineFragment;
//    private InterviewFragment interviewFragment;
//    private MessageFragment messageFragment;
//    private MineFragment mineFragment;
    @Override
    protected int setLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        mTabWeather = (LinearLayout) findViewById(R.id.tab_weather);
        mTabweatherImg = (ImageView) findViewById(R.id.tab_weather_image);
        mTabInterview = (LinearLayout) findViewById(R.id.tab_interview);
        mInterviewImage = (ImageView) findViewById(R.id.tab_interview_image);
        mTabMessage = (LinearLayout) findViewById(R.id.tab_message);
        mMessageImage = (ImageView) findViewById(R.id.tab_message_image);
        mTabMine = (LinearLayout) findViewById(R.id.tab_mine);
        mTabMineImg = (ImageView) findViewById(R.id.tab_mine_image);
        selectItem(0);
        getDeviceId();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setClickEvent() {
        mTabWeather.setOnClickListener(this);
        mTabInterview.setOnClickListener(this);
        mTabMessage.setOnClickListener(this);
        mTabMine.setOnClickListener(this);
    }


    private void selectItem(int position) {
        if (mCurrentItem != position) {
            if (manager == null) {
                manager = getSupportFragmentManager();
            }
            tr = manager.beginTransaction();
            mCurrentItem = position;
            hideFragment(tr);
            getFragment(position);
            tr.commitAllowingStateLoss();
            mTabWeather.setSelected(position == 0 ? true : false);
            mTabweatherImg.setImageResource(position == 0 ? R.drawable.weather_selector : R.drawable.icon_weather_dibu);
            mTabInterview.setSelected(position == 1 ? true : false);
            mInterviewImage.setImageResource(position == 1 ? R.drawable.interview_selector : R.drawable.icon_mianshi_dibu);
            mTabMessage.setSelected(position == 2 ? true : false);
            mMessageImage.setImageResource(position == 2 ? R.drawable.message_selector : R.drawable.icon_xiaoxi_dibu);
            mTabMine.setSelected(position == 3 ? true : false);
            mTabMineImg.setImageResource(position == 3 ? R.drawable.mine_selector : R.drawable.icon_wode_dibu);

        }
    }
    private void getFragment(int position) {
        switch (position) {
            case 0:
                if (weatherFragment == null) {
                    weatherFragment = new WeatherFragment();
                    tr.add(R.id.contentlayout, weatherFragment);
                } else {
                    tr.show(weatherFragment);
                }
                break;
//            case 1:
//                if (interviewFragment == null) {
//                    interviewFragment = new InterviewFragment();
//                    tr.add(R.id.contentlayout, interviewFragment);
//                } else {
//                    tr.show(interviewFragment);
//                }
//                break;

            case 2:
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    tr.add(R.id.contentlayout, messageFragment);
                } else {
                    tr.show(messageFragment);
                }
                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    tr.add(R.id.contentlayout, mineFragment);
                } else {
                    tr.show(mineFragment);
                }
                break;
            default:
                break;
        }
    }
    private void hideFragment(FragmentTransaction tr) {
        if (weatherFragment != null) {
            tr.hide(weatherFragment);
        }
        mTabWeather.setSelected(false);
//        if (interviewFragment != null) {
//            tr.hide(interviewFragment);
//        }
//        mTabInterview.setSelected(false);
        if (messageFragment != null) {
            tr.hide(messageFragment);
        }
        mTabMessage.setSelected(false);
        if (mineFragment != null) {
            tr.hide(mineFragment);
        }
        mTabMine.setSelected(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序！", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_weather:
                selectItem(0);
                break;
            case R.id.tab_interview:
                selectItem(1);
                mTabInterview.setSelected(true);
                break;
            case R.id.tab_message:
                selectItem(2);
                mTabMessage.setSelected(true);
                break;
            case R.id.tab_mine:
                selectItem(3);
                mTabMine.setSelected(true);
                break;
        }
    }

    public void getDeviceId(){
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_PHONE_STATE)) {
                Toast.makeText(MainActivity.this, "您已拒绝该权限,请到应用设置中通过权限",Toast.LENGTH_LONG).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        111);
            }
            return;
        } else {
            String deviceid = Utils.getDeviceId(this);
            SettingUtils.setDeciceId(MainActivity.this,deviceid);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 111:
                // 如果请求被拒绝，那么通常grantResults数组为空
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String deviceid = Utils.getDeviceId(this);
                    SettingUtils.setDeciceId(MainActivity.this,deviceid);
                } else {
                    //申请失败，可以继续向用户解释。
                    String title = "提示\n\n您拒绝了获得设备号权限,无法进行继续操作!";
                    Toast.makeText(MainActivity.this, title,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        if (prefs.getString("weather",null) != null){
//            Intent intent = new Intent(this,WeatherActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }
}
