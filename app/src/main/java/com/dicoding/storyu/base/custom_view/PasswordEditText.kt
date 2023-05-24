package com.dicoding.storyu.base.custom_view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged

class PasswordEditText : AppCompatEditText {
    private var isError: Boolean = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                doOnTextChanged { text, _, _, _ ->
                    val password = text.toString()
                    isError = password.length < MIN_PASSWORD_LENGTH
                    setErrorState()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun setErrorState() {
        if (isError) {
            error = "Your password is less than 8 characters!"
        } else {
            error = null
        }
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
    }
}