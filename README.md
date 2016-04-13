# RxBleScanner
[![Build Status](https://travis-ci.org/izumin5210/RxBleScanner.svg)](https://travis-ci.org/izumin5210/RxBleScanner)
[![Download](https://api.bintray.com/packages/izumin5210/maven/rxblescanner/images/download.svg)](https://bintray.com/izumin5210/maven/rxblescanner/_latestVersion)

## Installation
RxBleScanner depends on [RxJava](https://github.com/ReactiveX/RxJava). Add to your project build.gradle file:

```groovy
dependencies {
  compile 'io.reactivex:rxjava:1.1.0'
  compile 'info.izumin.android:rxblescanner:0.2.1'
}
```


## Usage

```java
BluetoothManager manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
BluetoothAdapter adapter = manager.getAdapter();

RxBleScanner scanner = new RxBleScanner(adapter);

// for API level 21 later
scanner.startScan()
  .subscribe((result) -> {
    // pass android.bluetooth.le.ScanResult instance
  });


// for API level 18
scanner.startScanJB()
  .subscribe((result) -> {
    // pass info.izumin.android.rxblescanner.ScanResultJB instance
  });
```

RxBleScanner has following methods:

* `Observable<ScanResult> startScan(List<ScanFilter>, ScanSettings)`: for API level 21 later
* `Observable<ScanResult> startScan(UUID...)`: for API level 21 later
* `Observable<ScanResultJB> startScanJB(UUID...)`: for API level 18 later
* `void stopScan()`


## License

```
Copyright 2015 izumin5210

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
