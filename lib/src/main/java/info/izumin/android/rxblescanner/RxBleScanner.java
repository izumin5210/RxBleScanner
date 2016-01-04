package info.izumin.android.rxblescanner;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanResult;
import android.os.Build;

import java.util.UUID;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by izumin on 1/2/2016 AD.
 */
public class RxBleScanner {

    private final BluetoothAdapter adapter;

    private RxBleScannerImpl scannerImpl;

    public RxBleScanner(BluetoothAdapter adapter) {
        this.adapter = adapter;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Observable<ScanResult> startScan(UUID... serviceUuids) {
        final RxBleScannerL scanner = new RxBleScannerL(adapter);
        scannerImpl = scanner;
        return scanner.startScan(serviceUuids);
    }

    public Observable<ScanResultJB> startScanJB(UUID... serviceUuids) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return startScan(serviceUuids).map(new Func1<ScanResult, ScanResultJB>() {
                @Override
                public ScanResultJB call(ScanResult result) {
                    return new ScanResultJB(result);
                }
            });
        } else {
            final RxBleScannerJB scanner = new RxBleScannerJB(adapter);
            scannerImpl = scanner;
            return scanner.startScan(serviceUuids);
        }
    }

    public void stopScan() {
        if (scannerImpl != null) {
            scannerImpl.stopScan();
        }
    }
}
