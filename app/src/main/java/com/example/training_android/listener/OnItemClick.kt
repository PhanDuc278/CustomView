package com.example.training_android.listener

import com.example.training_android.model.Data

interface OnItemClick {

    fun onClickListener(data: Data)

    fun onLongClickListener(data: Data)
}