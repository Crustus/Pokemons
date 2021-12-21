package cz.crusty.common.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import cz.crusty.common.R
import cz.crusty.common.util.ColorPalette
import cz.crusty.common.util.dpToPx

class CircleView : View {

    private lateinit var paint: Paint
    private var colorBackground: Int = Color.RED
    private var colorForeground: Int = Color.GREEN
    private var dimension: Float = 4.dpToPx.toFloat()
    private var angleStart: Float = 270f
    private var angleSweep: Float = 90f
    private val rect = RectF()

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.CircleView, defStyle, 0
        )

        colorBackground = a.getColor(R.styleable.CircleView_colorBackground, colorBackground)
        colorForeground = a.getColor(R.styleable.CircleView_colorForeground, colorForeground)
        dimension = a.getDimension(R.styleable.CircleView_dimension, dimension)
        angleStart = a.getDimension(R.styleable.CircleView_angleStart, angleStart)
        angleSweep = a.getDimension(R.styleable.CircleView_angleSweep, angleSweep)

        a.recycle()

        paint = Paint().apply {
            color = colorBackground
            strokeWidth = dimension
            style = Paint.Style.STROKE
            isAntiAlias = true
            isDither = true
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val dimensionHalf = dimension / 2
        rect.left = paddingLeft + dimensionHalf
        rect.right = width - paddingLeft - paddingRight - dimensionHalf
        rect.top = paddingTop + dimensionHalf
        rect.bottom = height - paddingTop - paddingBottom - dimensionHalf
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // draw background first
        paint.color = colorBackground
        paint.strokeWidth = dimension * 0.5f
        canvas.drawArc(rect, 0f, 360f, false, paint);

        // draw foreground
        paint.color = colorForeground
        paint.strokeWidth = dimension
        canvas.drawArc(rect, angleStart, angleSweep, false, paint);
    }

    /**
     * @param percentage value from 0 to 100
     */
    public fun setPercentage(percentage: Float) {
        var ratio = percentage
        if (ratio > 100) {
            ratio = 100f
        }
        angleSweep = 360 * (ratio / 100)
        invalidate()
    }

    fun setColors(colorPair: ColorPalette.ColorPair) {
        colorBackground = colorPair.colorBackground
        colorForeground = colorPair.colorForeground
    }
}