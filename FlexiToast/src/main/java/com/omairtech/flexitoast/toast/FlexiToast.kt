package com.omairtech.flexitoast.toast

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.widget.LinearLayout
import com.airbnb.lottie.LottieAnimationView

import android.widget.Toast
import android.view.Gravity
import android.view.LayoutInflater
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import com.omairtech.flexitoast.R

/**
 * A customizable toast implementation for Android, offering flexible styling and configuration options.
 *
 * @param context The application context.
 * @param message The message to display in the toast.
 * @param style The toast style, which determines the icon and background color. Defaults to SUCCESS.
 * @param duration The duration of the toast (either Toast.LENGTH_SHORT or Toast.LENGTH_LONG). Defaults to Toast.LENGTH_LONG.
 * @param gravity The position on the screen where the toast will appear (TOP, CENTER, or BOTTOM). Defaults to TOP.
 * @param useIcon Whether to show an icon or not in the toast. Defaults to true.
 * @param jsonLottieIcon An optional path to a Lottie animation file to customize the icon. Defaults to null.
 * @param backgroundColor An optional background color for the toast. If not provided, it defaults based on the selected style.
 */
class FlexiToast(
    var context: Context,
    var message: String,
    var style: FlexiToastStyle = FlexiToastStyle.SUCCESS,
    var duration: Int = Toast.LENGTH_LONG,
    var gravity: FlexiToastGravity = FlexiToastGravity.TOP,
) {

    // Private variables for internal state
    private var toast: Toast? = null
    var useIcon: Boolean = true
    private var backgroundLayout: LinearLayout? = null
    private var imageLayout: RelativeLayout? = null
    private var imageView: ImageView? = null
    private var lottieAnimationView: LottieAnimationView? = null
    private var textView: TextView? = null

    init {
        // Initialize the toast and set its configuration
        toast = Toast(context)
        setDuration(duration)
        setGravity(gravity)
        toast?.view = buildViews()
    }

    /**
     * Builds and inflates the views required for the toast, such as the background and icon.
     *
     * @return A View representing the complete layout of the toast.
     */
    private fun buildViews(): View {
        val toastView = LayoutInflater.from(context).inflate(R.layout.toast, null, false)
        backgroundLayout = toastView.findViewById(R.id.backgroundLayout)
        imageLayout = toastView.findViewById(R.id.imageLayout)
        imageView = toastView.findViewById(R.id.image)
        lottieAnimationView = toastView.findViewById(R.id.lottie)
        textView = toastView.findViewById(R.id.txt)

        // Apply the style and message to the toast
        setStyle(style)
        setMessage(message)

        return toastView
    }

    /**
     * Sets the duration of the toast.
     *
     * @param duration Either Toast.LENGTH_SHORT or Toast.LENGTH_LONG.
     * @return The updated FlexiToast object.
     */
    fun setDuration(duration: Int): FlexiToast {
        toast?.duration = duration
        return this
    }

    /**
     * Sets the gravity (position on the screen) of the toast.
     *
     * @param gravity One of TOP, CENTER, or BOTTOM.
     * @return The updated FlexiToast object.
     */
    fun setGravity(gravity: FlexiToastGravity): FlexiToast {
        val yOffset = when (gravity) {
            FlexiToastGravity.TOP -> 20
            FlexiToastGravity.CENTER -> 0
            FlexiToastGravity.BOTTOM -> 20
        }

        val position = when (gravity) {
            FlexiToastGravity.TOP -> Gravity.TOP or Gravity.FILL_HORIZONTAL
            FlexiToastGravity.CENTER -> Gravity.CENTER or Gravity.FILL_HORIZONTAL
            FlexiToastGravity.BOTTOM -> Gravity.BOTTOM or Gravity.FILL_HORIZONTAL
        }
        toast?.setGravity(position, 0, yOffset)

        return this
    }

    /**
     * Sets the style of the toast, which controls the icon and background color.
     *
     * @param style One of SUCCESS, FAILURE, WARNING, INFO, or CUSTOM.
     * @return The updated FlexiToast object.
     */
    fun setStyle(style: FlexiToastStyle): FlexiToast {
        val animationFile = when (style) {
            FlexiToastStyle.SUCCESS -> "lottie/success_icon.json"
            FlexiToastStyle.FAILURE -> "lottie/failure_icon.json"
            FlexiToastStyle.WARNING -> "lottie/warning_icon.json"
            FlexiToastStyle.INFO -> "lottie/info_toast.json"
        }
        setIcon(animationFile = animationFile)

        val backgroundColor = when (style) {
            FlexiToastStyle.SUCCESS -> "#00C853"
            FlexiToastStyle.FAILURE -> "#d50000"
            FlexiToastStyle.WARNING -> "#FACB41"
            FlexiToastStyle.INFO -> "#0091EA"
        }
        setBackground(backgroundColor)
        return this
    }

    /**
     * Sets the icon of the toast. It can be a Lottie animation or a drawable resource.
     *
     * @param animationFile Path to the Lottie animation file (optional, can be null).
     * @param drawableResId Drawable resource ID to use as the icon (optional, can be 0).
     * @return The updated FlexiToast object.
     */
    fun setIcon(animationFile: String? = null, drawableResId: Int = 0): FlexiToast {
        if (useIcon) {
            if (animationFile != null) {
                // Use Lottie animation if provided
                lottieAnimationView?.setAnimation(animationFile)
                lottieAnimationView?.visibility = View.VISIBLE
                imageView?.visibility = View.GONE
            } else if (drawableResId != 0) {
                // Use drawable resource if provided
                imageView?.setImageResource(drawableResId)
                imageView?.visibility = View.VISIBLE
                lottieAnimationView?.visibility = View.GONE
            } else {
                // If no icon is provided, hide both the ImageView and Lottie view
                imageView?.visibility = View.GONE
                lottieAnimationView?.visibility = View.GONE
            }
        } else {
            // Hide both the ImageView and Lottie view if icon usage is disabled
            lottieAnimationView?.visibility = View.GONE
            imageView?.visibility = View.GONE
        }
        return this
    }

    /*
     * Hides the icon from the toast.
     */
    fun useIcon(use: Boolean = true): FlexiToast {
        useIcon = use
        imageLayout?.visibility = if (useIcon) View.VISIBLE else View.GONE
        return this
    }

    /**
     * Sets the background color of the toast.
     *
     * @param color The background color in hex format (e.g., "#00C853").
     * @return The updated FlexiToast object.
     */
    fun setBackground(color: String): FlexiToast {
        backgroundLayout?.background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 20f
            setColor(Color.parseColor(color))
            setStroke(3, Color.parseColor(color))
        }
        return this
    }

    /**
     * Sets the message to display in the toast.
     *
     * @param message The text message to display.
     * @return The updated FlexiToast object.
     */
    fun setMessage(message: String): FlexiToast {
        textView?.text = message
        return this
    }

    /**
     * Sets the font of the text from the assets folder.
     *
     * @param fontPath The path to the font file in the assets folder.
     * @return The updated FlexiToast object.
     */
    fun setTextFont(fontPath: String): FlexiToast {
        textView?.typeface = Typeface.createFromAsset(context.assets, fontPath)
        return this
    }

    /**
     * Sets the font of the text from the resources (res/font folder).
     *
     * @param fontResId The font resource ID from the res/font folder.
     * @return The updated FlexiToast object.
     */
    fun setTextFont(@FontRes fontResId: Int): FlexiToast {
        textView?.typeface = ResourcesCompat.getFont(context, fontResId)
        return this
    }


    /**
     * Cancels the toast if it is currently visible.
     */
    fun cancel() {
        toast?.cancel()
    }

    /**
     * Displays the toast on the screen.
     */
    fun show() {
        toast?.show()
    }
}