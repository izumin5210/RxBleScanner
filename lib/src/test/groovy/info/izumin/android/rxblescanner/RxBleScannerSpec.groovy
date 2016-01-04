package info.izumin.android.rxblescanner

import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.BluetoothLeScanner
import rx.observers.TestSubscriber
import spock.lang.Specification
/**
 * Created by izumin on 1/5/2016 AD.
 */
class RxBleScannerSpec extends Specification {

    def scanner

    def setup() {
        def adapter = Mock(BluetoothAdapter.class)
        adapter.getBluetoothLeScanner() >> Mock(BluetoothLeScanner.class)
        scanner = new RxBleScanner(adapter)
    }

    def "complete observable when the scanner stop scanning"() {
        when:
        def subscriber = new TestSubscriber()
        scanner.startScan().subscribe(subscriber)

        then:
        subscriber.assertNoTerminalEvent()

        when:
        scanner.stopScan()

        then:
        subscriber.assertCompleted()
    }

    def "complete observable when the scanner stop scanning for JB"() {
        when:
        def subscriber = new TestSubscriber()
        scanner.startScanJB().subscribe(subscriber)

        then:
        subscriber.assertNoTerminalEvent()

        when:
        scanner.stopScan()

        then:
        subscriber.assertCompleted()
    }
}
