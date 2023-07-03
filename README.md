# flutter_wallpaper_manager

A flutter plugin to set wallpaper on your android device. It support Home Screen, Lock Screen and Both Screen mode

## How to use flutter wallpaper manager?

Simply install latest version of flutter_wallpaper_manager and use the following code to set the wallpaper

```
int location = WallpaperManager.BOTH_SCREEN; //can be Home/Lock Screen
bool result = await WallpaperManager.setWallpaperFromFile(path, location); //provide image path

```

You can clear the wallpaper by using clear() of WallpaperManager class

```
bool result = await WallpaperManager.clear(); //returns true/false

```
More features are coming to this plugin. Meanwhile you can support the development by donating tiny amount. It will be really appreciated.

- [Support Development](https://paypal.me/prasadkirpekar)
