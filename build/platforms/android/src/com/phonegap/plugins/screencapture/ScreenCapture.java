package com.phonegap.plugins.screencapture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;

/**
 * This class echoes a string called from JavaScript.
 */
public class ScreenCapture extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {

        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                String result = saveFile();
                if (result != null) {
                	callbackContext.success(result);
                } else {
                	callbackContext.error("Expected one non-empty string argument.");
                }
             }
        });
    	return true;
    }

	private String saveFile() {

		String result = null;

		try {

			View view = webView.getRootView();

			view.setDrawingCacheEnabled(true);
			Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
			view.setDrawingCacheEnabled(false);

			File folder = new File(Environment.getExternalStorageDirectory(), "Screenshots");
			if (!folder.exists()) {
				folder.mkdirs();
			}

			File f = new File(folder, "screenshot_" + System.currentTimeMillis() + ".png");

			FileOutputStream fos = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			result = f.getAbsolutePath();

		} catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		System.out.println("Return path:" + result);
		return result;
	}



}