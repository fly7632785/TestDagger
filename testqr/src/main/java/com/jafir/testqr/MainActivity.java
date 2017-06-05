package com.jafir.testqr;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.Result;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.concurrent.TimeUnit;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final String TAG = "debug";
    private static final int TAKE_PHOTO_REQUEST_CODE = 1;
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScannerView = (ZXingScannerView) findViewById(R.id.scanner);
        View button = findViewById(R.id.button);
        EditText edit = (EditText) findViewById(R.id.edit);
        /**
         * 利用throttleFirst 来防止多次点击
         */
        RxView.clicks(button).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(v -> {
                    Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,Main.class));

                }
                );


        /**
         * 利用debounce防止多次edittext changge
         *
         */
        RxTextView.textChangeEvents(edit)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(e -> Toast.makeText(this, "change", Toast.LENGTH_SHORT).show());


        request();

        /**
         * zipwith操作符 就是
         * 流传下来然后和第一个参数的流 结合并转型
         * 结合转型方式就是 第二个参数的func2
         * 然后返回一个转型之后的流
         *
         */
        Observable.just("").zipWith(RxUtil.getTempFile(this, "123123123.txt"), (s, file) -> {
            Log.d("dbug", "total:" + s);
            return Log.d("dbug", "total:" + s);
//          string和file结合在一起  返回一个其他类型对象
        }).subscribe(i -> Log.d("dbug", "total:" + i));

        String ss = RxUtil.getTotalSize(this);
        Log.d("dbug", "total:" + ss);
    }

    private void request() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this,
                    new String[]{Manifest.permission.CAMERA},
                    TAKE_PHOTO_REQUEST_CODE);
        }

        RxPermissions.getInstance(this).request(Manifest.permission.CALL_PHONE)
                .subscribe(bool -> Toast.makeText(this, "call", Toast.LENGTH_SHORT).show()
                );


    }

    public void flash(View view) {
        if (!mScannerView.getFlash()) {
            mScannerView.setFlash(true);
        } else {
            mScannerView.setFlash(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, rawResult.getText()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        Toast.makeText(this, rawResult.getText(), Toast.LENGTH_SHORT).show();
        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }

    /**
     * rx 改造显示dialog
     * 显示title
     * 错误显示
     *
     * @param <T>
     * @return
     */
    <T> Observable.Transformer<T, T> showNetDialog() {
        return showNetDialog("");
    }

    <T> Observable.Transformer<T, T> showNetDialog(@StringRes int title) {
        return showNetDialog(getResources().getString(title));
    }

    <T> Observable.Transformer<T, T> showNetDialog(String title) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> o) {
                return o.doOnSubscribe(() -> Toast.makeText(MainActivity.this, "显示dialog", Toast.LENGTH_SHORT).show())
                        .doOnError((e) -> Toast.makeText(MainActivity.this, "显示错误dialog", Toast.LENGTH_SHORT).show())
                        .doOnCompleted(() -> Toast.makeText(MainActivity.this, "显示错误dialog", Toast.LENGTH_SHORT).show());
            }
        };
    }


}
