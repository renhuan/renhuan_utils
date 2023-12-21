package com.renhuan.utils

import android.R.attr.scaleHeight
import android.R.attr.scaleWidth
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin


/**
 * created by renhuan
 * time : 2023/12/21 09:19
 * describe :
 */
class PngImageStroke(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    var bms: Bitmap
    var bmm: Bitmap
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val step = 15 // 1...45


    private var mColor: Int
    private var mWidth: Float
    private var mSrc: Int
    private var mScale: Float

    init {
        context.theme.obtainStyledAttributes(attributeSet, R.styleable.PngImageStroke, 0, 0).apply {
            try {
                mColor = getColor(R.styleable.PngImageStroke_ps_color, Color.BLACK)
                mWidth = getDimension(R.styleable.PngImageStroke_ps_width, 1f)
                mScale = getFloat(R.styleable.PngImageStroke_ps_scale, 1f)
                mSrc = getResourceId(R.styleable.PngImageStroke_ps_src, R.drawable.ic_close)
            } finally {
                recycle()
            }
        }
        paint.color = mColor
        bms = decodeBitmap(mSrc)
        bmm = Bitmap.createBitmap(bms.width, bms.height, Bitmap.Config.ALPHA_8)

        // 使用Matrix进行缩放
        val matrix = Matrix().apply { postScale(mScale, mScale) }
        bms = Bitmap.createBitmap(bms, 0, 0, bms.width, bms.height, matrix, true)
        bmm = Bitmap.createBitmap(bmm, 0, 0, bmm.width, bmm.height, matrix, true)

        val canvas = Canvas(bmm)
        canvas.drawBitmap(bms, 0f, 0f, null)
    }

    private fun decodeBitmap(resId: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inMutable = true
        return BitmapFactory.decodeResource(resources, resId, options)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 默认宽度和高度
        val defaultWidth = bms.width
        val defaultHeight = bms.height

        // 获取宽度和高度的测量模式和大小
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        // 根据测量模式设置宽度和高度
        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> minOf(defaultWidth, widthSize)
            else -> defaultWidth
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> minOf(defaultHeight, heightSize)
            else -> defaultHeight
        }

        // 设置测量后的宽度和高度
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var i = 0
        while (i < 360) {
            val x = mWidth * cos(Math.toRadians(i.toDouble())).toFloat()
            val y = mWidth * sin(Math.toRadians(i.toDouble())).toFloat()
            canvas.drawBitmap(bmm, x, y, paint)
            i += step
        }
        canvas.drawBitmap(bms, 0f, 0f, null)
    }
}