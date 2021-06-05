package com.pk.wallpapermanager.flutter_wallpaper_manager;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** FlutterWallpaperManagerPlugin */
public class FlutterWallpaperManagerPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_wallpaper_manager");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    }
    else if(call.method.equals("setWallpaperFromFile")){
      result.success(setWallpaperFromFile((String) call.argument("filePath"), (int) call.argument("wallpaperLocation")));
    }
    else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @SuppressLint("MissingPermission")
  private int setWallpaperFromFile(String filePath, int wallpaperLocation) {
      int result = -1;
      Bitmap bitmap = BitmapFactory.decodeFile(filePath);
      WallpaperManager wm = WallpaperManager.getInstance(context);
      try {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
              result = wm.setBitmap(bitmap, null, false, wallpaperLocation);
          } else {
              wm.setBitmap(bitmap);
              result = 1;
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
      return result;
  }
}
