package info.izumin.android.rxblescanner;

import android.bluetooth.BluetoothAdapter;

import java.util.UUID;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by izumin on 1/2/2016 AD.
 */
abstract class RxBleScannerImpl<T> {

    private final BluetoothAdapter adapter;
    private Subscriber<? super T> subscriber;

    protected RxBleScannerImpl(BluetoothAdapter adapter) {
        this.adapter = adapter;
    }

    Observable<T> startScan(UUID... serviceUuids) {
        startScanImpl(serviceUuids);
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                setSubscriber(subscriber);
            }
        }).doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                if (subscriber.isUnsubscribed()) {
                    stopScanImpl();
                }
            }
        });
    }

    void stopScan() {
        subscriber.onCompleted();
        stopScanImpl();
    }

    protected BluetoothAdapter getAdapter() {
        return adapter;
    }

    protected void setSubscriber(Subscriber<? super T> subscriber) {
        this.subscriber = subscriber;
    }

    protected void onNext(T result) {
        subscriber.onNext(result);
    }

    protected void onError(Throwable e) {
        subscriber.onError(e);
        stopScanImpl();
    }

    abstract void startScanImpl(UUID... serviceUuids);
    abstract void stopScanImpl();
}
