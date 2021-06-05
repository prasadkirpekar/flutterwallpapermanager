import 'dart:async';

import 'package:flutter/services.dart';

class WallpaperManager {
  static const MethodChannel _channel =
      const MethodChannel('flutter_wallpaper_manager');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<bool> setWallpaperFromFile(
      String filePath, int wallpaperLocation) async {
    final int result = await _channel.invokeMethod('setWallpaperFromAsset',
        {'filePath': filePath, 'wallpaperLocation': wallpaperLocation});
    return result > 0 ? true : false;
  }
}
