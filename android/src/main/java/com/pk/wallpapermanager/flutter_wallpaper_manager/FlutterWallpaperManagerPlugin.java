package com.pk.wallpapermanager.flutter_wallpaper_manager;

import androidx.annotation.NonNull;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.WallpaperManager;

import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.os.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

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
  private Context context;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    context = flutterPluginBinding.getApplicationContext();
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
    else if(call.method.equals("clearWallpaper")){
      result.success(clearWallpaper());
    }
    else if(call.method.equals("getDesiredMinimumHeight")){
      result.success(getDesiredMinimumHeight());
    }
    else if(call.method.equals("getDesiredMinimumWidth")){
      result.success(getDesiredMinimumWidth());
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

  @SuppressLint("MissingPermission")
  private boolean clearWallpaper() {
      Boolean result = false;
      WallpaperManager wm = WallpaperManager.getInstance(context);
      try {
          wm.clear();
          result = true;
      } catch (IOException e) {
      }
      return result;
  }

  @SuppressLint("MissingPermission")
  private int getDesiredMinimumHeight() {
    WallpaperManager wm = WallpaperManager.getInstance(context);
    return wm.getDesiredMinimumHeight();
  }

  @SuppressLint("MissingPermission")
  private int getDesiredMinimumWidth() {
    WallpaperManager wm = WallpaperManager.getInstance(context);
    return wm.getDesiredMinimumWidth();
  }

  

}
