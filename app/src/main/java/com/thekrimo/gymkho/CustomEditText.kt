package com.thekrimo.gymkho

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat

class CustomEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    private val regularFont: Typeface? = ResourcesCompat.getFont(context, R.font.montserratregular)
    private val hintFont: Typeface? = ResourcesCompat.getFont(context, R.font.montserrat)

    init {
        typeface = hintFont

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                typeface = if (s.isNullOrEmpty()) {
                    hintFont
                } else {
                    regularFont
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
}
