#!/bin/bash


# clean

rm build/platforms/android/bin/Inspectors-debug.apk

adb shell pm uninstall -k com.feedhenry.kiermg


# build

grunt -v build

cd build
cordova -d build
cd ..


#deploy

cp build/platforms/android/bin/Inspectors-debug.apk .

adb install -r build/platforms/android/bin/Inspectors-debug.apk
adb shell am start -n com.feedhenry.kiermg/com.feedhenry.kiermg.Inspectors
