package com.example.training_android.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.training_android.R
import com.example.training_android.databinding.CustomViewBinding
import com.example.training_android.listener.CustomViewListener


class CustomView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private val binding = CustomViewBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var customViewListener: CustomViewListener

    var editText: EditText
        get() = binding.edtValue
        set(value) {
            binding.edtValue.text = value.text
        }

    init {
        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.CustomView)

        val title = attributes.getString(R.styleable.CustomView_textView)
        val hint = attributes.getString(R.styleable.CustomView_hintText)
        val error = attributes.getString(R.styleable.CustomView_textError)
        attributes.recycle()

        binding.textTitle.text = error
        binding.edtValue.hint = hint
        binding.textTitle.text = title

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s.isNullOrEmpty()) {
                    binding.textError.visibility = View.VISIBLE
                } else {
                    binding.textError.visibility = View.GONE
                }
                customViewListener.onCustomViewValidated(isValid())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    fun isValid(): Boolean {
        return !editText.text.isNullOrEmpty()
    }

    fun setCustomViewListener(listener: CustomViewListener) {
        customViewListener = listener
    }

    fun getText(): String {
        return editText.text.toString()
    }
}

