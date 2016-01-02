package info.izumin.android.rxblescanner;

import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by izumin on 1/2/2016 AD.
 */
public class RxBleScanner extends AbstractRxBleScanner<ScanResult> {

    private final BluetoothLeScanner scanner;

    public RxBleScanner(Context context) {
        super(context);
        scanner = getAdapter().getBluetoothLeScanner();
    }

    @Override
    public Observable<ScanResult> startScan() {
        scanner.startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                switch (callbackType) {
                    case ScanSettings.CALLBACK_TYPE_ALL_MATCHES:
                        onNext(result);
                        break;
                    case ScanSettings.CALLBACK_TYPE_FIRST_MATCH:
                        break;
                    case ScanSettings.CALLBACK_TYPE_MATCH_LOST:
                        break;
                }
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                Observable.from(results).subscribe(new Action1<ScanResult>() {
                    @Override
                    public void call(ScanResult result) {
                        onNext(result);
                    }
                });
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
            }
        });
        return getSubject();
    }
}
