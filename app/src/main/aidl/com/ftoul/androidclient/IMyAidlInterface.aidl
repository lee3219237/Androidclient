// IMyAidlInterface.aidl
package com.ftoul.androidclient;
import com.ftoul.androidclient.bean.MyData;
// Declare any non-default types here with import statements
// aidl示例

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
      int getPid();
      MyData getMyData();
}
