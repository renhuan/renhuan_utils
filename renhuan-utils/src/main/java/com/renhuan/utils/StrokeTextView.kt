package com.renhuan.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


/**
 * created by renhuan
 * time : 2022/1/5 09:27
 * describe : 描边的TextView
 */
class StrokeTextView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    private var mStrokeColor = 0
    private var mStrokeWidth = 0f

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.StrokeTextView, 0, 0)
            .apply {
                try {
                    mStrokeColor = getColor(R.styleable.StrokeTextView_st_color, Color.parseColor("#6849A9"))
                    mStrokeWidth = getDimension(R.styleable.StrokeTextView_st_width, 2f)
                } finally {
                    recycle()
                }
            }
    }


    override fun onDraw(canvas: Canvas?) {
        val paint = paint
        setTextColor(mStrokeColor)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = mStrokeWidth
        super.onDraw(canvas)
    }

}

