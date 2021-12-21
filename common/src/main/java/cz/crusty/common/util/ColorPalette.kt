package cz.crusty.common.util

import android.content.Context
import cz.crusty.common.R
import kotlin.random.Random

object ColorPalette {

    data class ColorPair(val colorBackground: Int, val colorForeground: Int)

    fun getRandomColors(context: Context): ColorPair {
        val colorForeground = context.resources.getIntArray(R.array.colorForeground)
        val colorBackground = context.resources.getIntArray(R.array.colorBackground)
        val index = Random.nextInt(0, colorForeground.size)
        return ColorPair(colorBackground[index], colorForeground[index])
    }

    fun getColorsByRange(context: Context, range: Long): ColorPair {
        val colorForeground = context.resources.getIntArray(R.array.colorForeground)
        val colorBackground = context.resources.getIntArray(R.array.colorBackground)

        // TODO variable range
        var range: Int = range.toInt()
        if (range >= 100) {
            range = 99
        }

        val index = range / 10
        return ColorPair(colorBackground[index], colorForeground[index])
    }
}