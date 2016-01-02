package info.izumin.android.rxblescanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by izumin on 1/2/2016 AD.
 */
abstract class AbstractRxBleScanner<T> {

    private final BluetoothManager manager;
    private final BluetoothAdapter adapter;
    private PublishSubject<T> subject;

    protected AbstractRxBleScanner(Context context) {
        manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        adapter = manager.getAdapter();
    }

    protected BluetoothManager getManager() {
        return manager;
    }

    protected BluetoothAdapter getAdapter() {
        return adapter;
    }

    protected PublishSubject<T> getSubject() {
        if (subject == null || subject.hasCompleted() || subject.hasThrowable()) {
            subject = PublishSubject.create();
        }
        return subject;
    }

    protected void onNext(T result) {
        getSubject().onNext(result);
    }

    public void stopScan() {
        getSubject().onCompleted();
    }

    public abstract Observable<T> startScan();
}
