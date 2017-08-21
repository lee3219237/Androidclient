package com.ftoul.androidclient.utils;

import android.app.Activity;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

/**
 * Created by ftoul106 on 2017/5/6 0006.
 */

public class FingerPrintUiHelper {
    private CancellationSignal signal;
    private FingerprintManagerCompat fingerprintManager;

    public FingerPrintUiHelper(Activity activity) {
        signal = new CancellationSignal();
        fingerprintManager = FingerprintManagerCompat.from(activity);
    }

    public void startFingerPrintListen(FingerprintManagerCompat.AuthenticationCallback callback) {
        fingerprintManager.authenticate(null, 0, signal, callback, null);
    }

    public void stopsFingerPrintListen() {
        signal.cancel();
        signal = null;
    }
}
