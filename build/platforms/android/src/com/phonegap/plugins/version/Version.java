package com.phonegap.plugins.version; //your package name


import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class Version extends CordovaPlugin {

    public final String ACTION_GET_VERSION_CODE = "GetVersionCode";
    public final String ACTION_GET_VERSION_NAME = "GetVersionName";

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        PackageManager packageManager = this.cordova.getActivity().getPackageManager();

        if (action.equals(ACTION_GET_VERSION_NAME)) {
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(this.cordova.getActivity().getPackageName(), 0);
                callbackContext.success(packageInfo.versionName);

            }
            catch(NameNotFoundException nnfe) {
                callbackContext.error(nnfe.getMessage());
            }

            return true;
        }
        return false;
    }

}
