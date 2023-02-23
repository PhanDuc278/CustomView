package com.example.training_android.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.training_android.R
import com.example.training_android.customview.CustomView
import com.example.training_android.databinding.ActivityAddNoteBinding
import com.example.training_android.listener.CustomViewListener
import com.example.training_android.model.Data
import java.util.*

@Suppress("DEPRECATION")
class AddDataActivity : AppCompatActivity(), CustomViewListener {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var customGroupView1: CustomView
    private lateinit var customGroupView2: CustomView
    private lateinit var customGroupView3: CustomView
    private lateinit var data: Data
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customGroupView1 = findViewById(R.id.customView1)
        customGroupView2 = findViewById(R.id.customView2)
        customGroupView3 = findViewById(R.id.customView3)

        // Set the custom view listener for each CustomView
        customGroupView1.setCustomViewListener(this)
        customGroupView2.setCustomViewListener(this)
        customGroupView3.setCustomViewListener(this)

        //Data update or show
        try {
            data = intent.getSerializableExtra("current_Data") as Data
            customGroupView1.editText.setText(data.value1)
            customGroupView2.editText.setText(data.value2)
            customGroupView3.editText.setText(data.value3)
            isUpdate = true

        } catch (e: Exception) {
            e.printStackTrace()
        }

        //Back to main activity
        binding.imgBackHome?.setOnClickListener {
            onBackPressed()
        }

        //Button add new data or update data
        binding.btn?.setOnClickListener {
            // Get text from each custom view and do something with it
            val text1 = customGroupView1.getText()
            val text2 = customGroupView2.getText()
            val text3 = customGroupView3.getText()
            updateDataOrInsert(text1, text2, text3)
        }
    }

    //Function update or add new data
    private fun updateDataOrInsert(text1: String, text2: String, text3: String) {
        //if isUpdate = false -> Add new data , else -> Update data
        data = if (!isUpdate) {
            Data(null, text1, text2, text3)
        } else {
            Data(data.id, text1, text2, text3)
        }

        val intent = Intent()
        intent.putExtra("data", data)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCustomViewValidated(isValid: Boolean) {
        if (customGroupView1.isValid() && customGroupView2.isValid() && customGroupView3.isValid()) {
            binding.btn?.isEnabled = true
            binding.btn?.background = getDrawable(R.drawable.bg_btn)
        } else {
            binding.btn?.isEnabled = false
            binding.btn?.background = getDrawable(R.drawable.bg_disable)
        }
    }
}