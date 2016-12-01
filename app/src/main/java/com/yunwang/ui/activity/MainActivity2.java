package com.yunwang.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mmm.detectorsdk.DetectorUtility;
import com.yunwang.R;
import com.yunwang.base.BaseActivty;

public class MainActivity2 extends BaseActivty {

    private DetectorUtility detectorUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        detectorUtility = new DetectorUtility();

        if (DetectorUtility.DETECTOR_SUCCESS != detectorUtility
                .init(backgroudHandler, caliberationHandler, testHandler))
            Toast.makeText(this, "设备连接失败", Toast.LENGTH_SHORT).show();


        /*
         * 如果配对了多台蓝牙设备，需要进行选择
         * 如果只配对了1台蓝牙设备，则系统会自动进行连接
         */
        if (detectorUtility.mBTStatus == DetectorUtility.BTStatus.BT_DEVEICE_SELECT)
            selectDevice();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {

    }

    ProgressDialog pd;

    private void selectDevice() {
        new AlertDialog.Builder(MainActivity2.this)
                .setTitle("选择蓝牙设备")
                .setItems(detectorUtility.getDeviceNameList(), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        detectorUtility.connectDevice(which);
                        pd = ProgressDialog.show(MainActivity2.this, null, "连接中...");
                        return;
                    }
                })
                .setCancelable(false)
                .show();
    }

    public void caliberateBlack(View view) {
        detectorUtility.calibBlack();

    }

    public void caliberateWhite(View view) {
        int ra = Integer.parseInt(((EditText) findViewById(R.id.txt_ra)).getText().toString());
        detectorUtility.calibWhite(ra);

    }

    public void reConnect(View view) {
        detectorUtility.reConnect();
        /*
         * 如果配对了多台蓝牙设备，需要进行选择
         * 如果只配对了1台蓝牙设备，则系统会自动进行连接
         */
        if (detectorUtility.mBTStatus == DetectorUtility.BTStatus.BT_DEVEICE_SELECT)
            selectDevice();
        else
            pd = ProgressDialog.show(MainActivity2.this, null, "连接中...");
    }

    @SuppressLint("HandlerLeak")
    protected final Handler backgroudHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {


            switch (msg.what) {
                case DetectorUtility.EVENT_CONNECTION_FAILED:
                    if (pd != null)
                        pd.dismiss();
                    Toast.makeText(getBaseContext(), "设备连接失败", Toast.LENGTH_SHORT).show();
                    ((TextView) findViewById(R.id.txt_status)).setText("连接失败");
                    break;
                case DetectorUtility.EVENT_CONNECTION_SUCCESS:
                    if (pd != null)
                        pd.dismiss();
                    ((TextView) findViewById(R.id.txt_status)).setText("连接成功");
                    ((TextView) findViewById(R.id.txt_serial)).setText("序列号：" + (String) msg.obj);
                    break;
                case DetectorUtility.EVENT_CALIBERATION_DATE:
                    ((TextView) findViewById(R.id.txt_calibDate))
                            .setText(String.format("上次校准时间： %1$s天 %2$s小时",
                                    (Long.parseLong((String) msg.obj)) / 24,
                                    (Long.parseLong((String) msg.obj)) % 24));

                    break;
                case DetectorUtility.EVENT_RATE_UPDATED:
//
                    break;
                case DetectorUtility.EVENT_DEVICE_UNAUTHORIZED:
                    ((TextView) findViewById(R.id.txt_status)).setText("产品未授权");
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @SuppressLint("HandlerLeak")
    protected final Handler caliberationHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DetectorUtility.EVENT_CALIBERATION_BLACK_COMPLETE:
                    ((TextView) findViewById(R.id.txt_caliberation_result)).setText("黑板校准完成");
                    break;
                case DetectorUtility.EVENT_CALIBERATION_WHITE_COMPLETE:
                    ((TextView) findViewById(R.id.txt_caliberation_result)).setText("白板校准完成");

                    break;
                case DetectorUtility.EVENT_CONNECTION_FAILED:
                    ((TextView) findViewById(R.id.txt_caliberation_result)).setText("连接失败");
                    break;
                case DetectorUtility.EVENT_CALIBERATION_FAILED:
                    ((TextView) findViewById(R.id.txt_caliberation_result)).setText("校准失败");
                    break;
                default:
                    break;
            }
            //btUtil.caliberationHandler = null;
            super.handleMessage(msg);
        }
    };


    @SuppressLint("HandlerLeak")
    protected final Handler testHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DetectorUtility.EVENT_LABLE_TEST_RESULT:
                    showTestResult(DetectorUtility.EVENT_LABLE_TEST_RESULT, msg.arg1, (String) msg.obj);

                    break;
                case DetectorUtility.EVENT_BOARD_TEST_RESULT:
                    showTestResult(DetectorUtility.EVENT_BOARD_TEST_RESULT, msg.arg1, (String) msg.obj);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

   private void showTestResult(int evt, int color, String msg) {
        TextView reslutView = (TextView) findViewById(R.id.txt_test_result);
        String title = "";
        String content = "";
        if (evt == DetectorUtility.EVENT_LABLE_TEST_RESULT) {
            title = "反光标识：";
            if (msg == "low")
                content = getString(R.string.test_label_low);
            else if (msg == "high")
                content = getString(R.string.test_label_high);
            else
                content = msg;
        } else if (evt == DetectorUtility.EVENT_BOARD_TEST_RESULT) {
            title = "尾板：";
            if (msg == "low")
                content = getString(R.string.test_board_low);
            else if (msg == "high")
                content = getString(R.string.test_board_high);
            else
                content = getString(R.string.test_board_near);
        }

        switch (color) {
            case 0:
                reslutView.setTextColor(Color.RED);
                break;
            case 1:
                reslutView.setTextColor(Color.GREEN);
                break;
            case 2:
                reslutView.setTextColor(Color.YELLOW);
                break;
            default:
                reslutView.setTextColor(Color.BLACK);
        }
        reslutView.setText(title + content);
    }
}
