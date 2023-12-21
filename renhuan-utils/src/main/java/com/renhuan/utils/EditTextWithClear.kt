package com.renhuan.utils

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import com.noober.background.view.BLEditText

/**
 * created by renhuan
 * time : 2020/10/13 10:01
 * describe : 带清除按钮的EditText，如果什么都不设置，默认有显示
 *
 *  <attr name="et_drawable" format="reference" />   清除按钮的图片
 *   <attr name="et_drawableWidth" format="dimension" /> 清除按钮的图片的宽度
 *   <attr name="et_drawableHeight" format="dimension" /> 清除按钮的图片高度
 *   <attr name="et_drawableColor" format="color" />  清除按钮的图片的颜色，可以着色
 */

class EditTextWithClear(
    context: Context,
    attrs: AttributeSet? = null
) : BLEditText(context, attrs) {

    private var mIcon: Drawable? = null
    private var mWidth: Float = 0f
    private var mHeight: Float = 0f
    private var mIconColor: Int = 0

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.EditTextWithClear, android.R.attr.editTextStyle, 0)
            .apply {
                try {
                    mIcon =
                        Ext.getDrawable(getResourceId(R.styleable.EditTextWithClear_et_drawable, R.drawable.ic_close))
                    mWidth = getDimension(R.styleable.EditTextWithClear_et_drawableWidth, 0f)
                    mHeight = getDimension(R.styleable.EditTextWithClear_et_drawableHeight, 0f)
                    mIconColor = getColor(R.styleable.EditTextWithClear_et_drawableColor, 0)
                } finally {
                    recycle()
                }
            }
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        toggleClearIcon()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { e ->
            mIcon?.let {
                if (e.action == MotionEvent.ACTION_UP
                    && e.x > width - it.intrinsicWidth - 20
                    && e.x < width + 20
                    && e.y > height / 2 - it.intrinsicHeight / 2 - 20
                    && e.y < height / 2 + it.intrinsicHeight / 2 + 20
                ) {
                    text?.clear()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        toggleClearIcon()
    }

    private fun toggleClearIcon() {
        var icon = if (isFocused && text?.isNotEmpty() == true) mIcon else null
        if (mIconColor != 0) {
            icon = icon?.tintColor(mIconColor)
        }
        //默认的图片大小
        if (mWidth == 0f && mHeight == 0f) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
        } else {
            //自己设置宽高大小
            icon?.setBounds(0, 0, mWidth.toInt(), mHeight.toInt())
            setCompoundDrawables(null, null, icon, null)
        }
    }
}