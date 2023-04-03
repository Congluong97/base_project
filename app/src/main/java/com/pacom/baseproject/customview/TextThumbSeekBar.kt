package com.pacom.baseproject.customview

import android.R
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import com.pacom.baseproject.utils.dpToPx
import com.pacom.baseproject.utils.spToPx


class TextThumbSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatSeekBar(context, attrs, defStyleAttr) {
    private var mThumbSize //绘制滑块宽度
            = 0
    private lateinit var mTextPaint: TextPaint
    private val mSeekBarMin = 0 //滑块开始值


    @Synchronized
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val unsignedMin: Int = if (mSeekBarMin < 0) mSeekBarMin * -1 else mSeekBarMin
        val progressText = (progress + unsignedMin).toString()
        val bounds = Rect()
        mTextPaint.getTextBounds(progressText, 0, progressText.length, bounds)

        val leftPadding = paddingLeft - thumbOffset
        val rightPadding = paddingRight - thumbOffset
        val width = width - leftPadding - rightPadding
        val progressRatio = progress.toFloat() / max
        val thumbOffset = mThumbSize * (.5f - progressRatio)
        val thumbX = progressRatio * width + leftPadding + thumbOffset
        val thumbY = height / 2f + bounds.height() / 2f
        canvas.drawText(progressText, thumbX, thumbY, mTextPaint)
    }

    init {
        mThumbSize = 25f.dpToPx(context).toInt()
        mTextPaint = TextPaint()
        mTextPaint.setColor(Color.WHITE)
        mTextPaint.setTextSize(16f.spToPx(context))
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD)
        mTextPaint.setTextAlign(Paint.Align.CENTER)
    }
}