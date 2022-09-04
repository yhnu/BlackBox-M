package top.niunaijun.blackboxa.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Handler
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import java.lang.Exception
import java.lang.reflect.Field

object Resolution {
    private const val TAG = "UtilsScreen"

    /**
     * Gets the width of the display, in pixels.
     *
     *
     * Note that this value should not be used for computing layouts, since a
     * device will typically have screen decoration (such as a status bar) along
     * the edges of the display that reduce the amount of application space
     * available from the size returned here. Layouts should instead use the
     * window size.
     *
     *
     * The size is adjusted based on the current rotation of the display.
     *
     *
     * The size returned by this method does not necessarily represent the
     * actual raw size (native resolution) of the display. The returned size may
     * be adjusted to exclude certain system decoration elements that are always
     * visible. It may also be scaled to provide compatibility with older
     * applications that were originally designed for smaller displays.
     *
     * @return Screen width in pixels.
     */
    fun getScreenWidth(context: Context): Int {
        return getScreenSize(context, null).x
    }

    /**
     * Gets the height of the display, in pixels.
     *
     *
     * Note that this value should not be used for computing layouts, since a
     * device will typically have screen decoration (such as a status bar) along
     * the edges of the display that reduce the amount of application space
     * available from the size returned here. Layouts should instead use the
     * window size.
     *
     *
     * The size is adjusted based on the current rotation of the display.
     *
     *
     * The size returned by this method does not necessarily represent the
     * actual raw size (native resolution) of the display. The returned size may
     * be adjusted to exclude certain system decoration elements that are always
     * visible. It may also be scaled to provide compatibility with older
     * applications that were originally designed for smaller displays.
     *
     * @return Screen height in pixels.
     */
    fun getScreenHeight(context: Context): Int {
        return getScreenSize(context, null).y
    }

    /**
     * Gets the size of the display, in pixels.
     *
     *
     * Note that this value should not be used for computing layouts, since a
     * device will typically have screen decoration (such as a status bar) along
     * the edges of the display that reduce the amount of application space
     * available from the size returned here. Layouts should instead use the
     * window size.
     *
     *
     * The size is adjusted based on the current rotation of the display.
     *
     *
     * The size returned by this method does not necessarily represent the
     * actual raw size (native resolution) of the display. The returned size may
     * be adjusted to exclude certain system decoration elements that are always
     * visible. It may also be scaled to provide compatibility with older
     * applications that were originally designed for smaller displays.
     *
     * @param outSize null-ok. If it is null, will create a Point instance inside,
     * otherwise use it to fill the output. NOTE if it is not null,
     * it will be the returned value.
     * @return Screen size in pixels, the x is the width, the y is the height.
     */
    @SuppressLint("NewApi")
    fun getScreenSize(context: Context, outSize: Point?): Point {
        val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val ret = outSize ?: Point()
        val defaultDisplay = wm.defaultDisplay
        if (Build.VERSION.SDK_INT >= 13) {
            defaultDisplay.getSize(ret)
        } else {
            ret.x = defaultDisplay.width
            ret.y = defaultDisplay.height
        }
        return ret
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPixel(dp: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi / 160f)
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    fun convertPixelsToDp(px: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return px / (metrics.densityDpi / 160f)
    }
    ///////////////////////////////////////////////////////////////////////
    /**
     * 获取屏幕密度
     */
    fun getDensity(context: Context?): Float {
        var density = 0f
        if (context == null) {
            return density
        }
        try {
            density = context.resources.displayMetrics.density
        } catch (e: Exception) {
        }
        return density
    }

    /**
     * 检查分辨率是否为本机
     */
    fun checkPix(context: Activity, width: Int, height: Int): Boolean {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            val metrics = DisplayMetrics()
            context.windowManager.defaultDisplay.getRealMetrics(metrics)
            metrics.widthPixels == width && metrics.heightPixels == height
        } else {
            getScreenPixWidth(context) == width && getScreenPixHeight(
                context
            ) == height
        }
    }

    /**
     * 获取屏幕分辨率：宽
     */
    fun getScreenPixWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    /**
     * 获取屏幕分辨率：高
     */
    fun getScreenPixHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    /**
     * dipתpx
     */
    fun dipToPx(context: Context, dip: Int): Int {
        return (dip * context.resources.displayMetrics.density + 0.5f).toInt()
    }

    /**
     * pxתdip
     */
    fun pxToDip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue
     * @return
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * 隐藏软键盘
     */
    fun hideInputMethod(view: View) {
        val imm = view.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 显示软键盘
     */
    fun showInputMethod(view: View) {
        val imm = view.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * 多少时间后显示软键盘
     */
    fun showInputMethod(view: View, delayMillis: Long) {
        // 显示输入法
        Handler().postDelayed({ showInputMethod(view) }, delayMillis)
    }

    /**
     * 判断手机是否在锁屏状态 true锁屏 false未锁屏
     */
    fun isScreenLocked(c: Context): Boolean {
        val mKeyguardManager = c
            .getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        return !mKeyguardManager.inKeyguardRestrictedInputMode()
    }

    fun getBarHeight(context: Context): Int {
        var c: Class<*>? = null
        var obj: Any? = null
        var field: Field? = null
        var x = 0
        var sbar = 38 //默认为38，貌似大部分是这样的
        try {
            c = Class.forName("com.android.internal.R\$dimen")
            obj = c.newInstance()
            field = c.getField("status_bar_height")
            x = field[obj].toString().toInt()
            sbar = context.resources.getDimensionPixelSize(x)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
        return sbar
    }

    //http://stackoverflow.com/questions/20264268/how-to-get-height-and-width-of-navigation-bar-programmatically
    //获取屏幕下方导航栏高度
    fun getNavigationBarSize(context: Context): Point {
        val appUsableSize = getScreenSize(context, null)
        val realScreenSize = getRealScreenSize(context)

//        // navigation bar on the right
//        if (appUsableSize.x < realScreenSize.x) {
//            return new Point(realScreenSize.x - appUsableSize.x, appUsableSize.y);
//        }

        // navigation bar at the bottom
        return if (appUsableSize.y < realScreenSize.y) {
            Point(appUsableSize.x, realScreenSize.y - appUsableSize.y)
        } else Point()

        // navigation bar is not present
    }

    fun getRealScreenSize(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size)
        } else if (Build.VERSION.SDK_INT >= 14) {
            try {
                size.x = (Display::class.java.getMethod("getRawWidth").invoke(display) as Int)
                size.y = (Display::class.java.getMethod("getRawHeight").invoke(display) as Int)
            } catch (e: Exception) {
            }
        }
        return size
    }
}