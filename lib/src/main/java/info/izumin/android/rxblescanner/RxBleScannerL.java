package info.izumin.android.rxblescanner;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Build;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by izumin on 1/3/2016 AD.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class RxBleScannerL extends RxBleScannerImpl<ScanResult> {

    private BluetoothLeScanner scanner;

    protected RxBleScannerL(BluetoothAdapter adapter) {
        super(adapter);
    }

    @Override
    protected void startScanImpl() {
        scanner = getAdapter().getBluetoothLeScanner();
        scanner.startScan(scanCallback);
    }

    @Override
    protected void stopScanImpl() {
        scanner.stopScan(scanCallback);
    }

    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            switch (callbackType) {
                case ScanSettings.CALLBACK_TYPE_ALL_MATCHES:
                    onNext(result);
                    break;
                case ScanSettings.CALLBACK_TYPE_FIRST_MATCH:
                    onNext(result);
                    break;
                case ScanSettings.CALLBACK_TYPE_MATCH_LOST:
                    // TODO: Not yet implemented.
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
            getSubject().onError(new RxBleScannerException(errorCode));
        }
    };
}
