package com.phonegap.plugins.filehasher;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

/**
 * This class echoes a string called from JavaScript.
 */
public class FileHasher extends CordovaPlugin {

    public final String ACTION_HASH = "hash";

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals(ACTION_HASH)) {
            final String filePath = args.getString(0);
            //final String digestType = args.optString(0, "MD5");
            final String digestType = "MD5";
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    getChecksum(filePath, digestType, callbackContext);
                }
            });
            return true;
        }
        return false;
    }

    public static void getChecksum(String filePath, String digestType, CallbackContext callbackContext) {

        try {
            InputStream fis = new FileInputStream(filePath);

            byte[] buffer = new byte[1024];
            MessageDigest complete = MessageDigest.getInstance(digestType);
            int numRead;

            do {
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    complete.update(buffer, 0, numRead);
                }
            } while (numRead != -1);

            fis.close();

            callbackContext.success(createChecksum(complete.digest()));

        }
        catch(FileNotFoundException ex) {
            callbackContext.error("File not found");
        }
        catch(Exception ex) {
            callbackContext.error(ex.getMessage());
        }
    }


    private static String createChecksum(byte[] bytes) {
        String result = "";

        for (int i=0; i < bytes.length; i++) {
            result += Integer.toString( ( bytes[i] & 0xff ) + 0x100, 16).substring( 1 );
        }

        return result;
    }
}
