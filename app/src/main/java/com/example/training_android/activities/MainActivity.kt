package com.example.training_android.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.training_android.adapter.Constance
import com.example.training_android.adapter.DataAdapter
import com.example.training_android.databinding.ActivityMainBinding
import com.example.training_android.listener.OnItemClick
import com.example.training_android.model.Data
import com.example.training_android.viewmodel.ViewModelData

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), OnItemClick {
    lateinit var binding: ActivityMainBinding
    private lateinit var mDataViewModel: ViewModelData
    private lateinit var adapter: DataAdapter

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = DataAdapter(this)
        binding.recListItem?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recListItem?.adapter = adapter

        mDataViewModel = ViewModelProvider(this)[ViewModelData::class.java]

        //Get liveData if data changed
        mDataViewModel.getAllData.observe(this) { data ->
            adapter.setListData(data)
            binding.textCountItem?.text = "LIST (${data.size})"
            if (data.isNotEmpty()) {
                binding.txtDeleteAll?.visibility = View.VISIBLE
            } else {
                binding.txtDeleteAll?.visibility = View.GONE
            }
            adapter.notifyDataSetChanged()
        }

        //Delete all data
        binding.txtDeleteAll?.setOnClickListener {
            showAlecDialogDeleteAllData()
//            mDataViewModel.deleteAllData()
        }

        //Add new data
        binding.textViewAddNewItem?.setOnClickListener {
            val intent = Intent(this@MainActivity, AddDataActivity::class.java)
            startActivityForResult(intent, Constance.REQUEST_ADD_DATA)
        }

        //Search data
        binding.searchData?.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.searchData(newText.toString())
                }
                return true
            }
        })
    }

    //Show AlertDialog to delete all data in table
    private fun showAlecDialogDeleteAllData() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Do you want to delete all data ?")
        builder.setMessage("Delete all data !")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            mDataViewModel.deleteAllData()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }


    //Click item to show data
    override fun onClickListener(data: Data) {
        val intent = Intent(this@MainActivity, AddDataActivity::class.java)
        intent.putExtra("current_Data", data)
        startActivityForResult(intent, Constance.REQUEST_SHOW_DATA)
    }

    //Click onLongClick to show AlertDialog to delete current data
    override fun onLongClickListener(data: Data) {
        showAlecDialog(data)
    }

    //Show dialog to delete current data
    private fun showAlecDialog(data: Data) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Do you want to delete this item ?")
        builder.setMessage("Delete item !")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            mDataViewModel.deleteData(data)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }


    //Activity result if AddData return REQUEST_ADD_DATA -> Insert new data , REQUEST_SHOW_DATA -> Update data
    @SuppressLint("NotifyDataSetChanged")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constance.REQUEST_ADD_DATA) {
            if (resultCode == RESULT_OK) {
                val getData = data?.getSerializableExtra("data") as Data
                mDataViewModel.insertData(getData)
                adapter.notifyDataSetChanged()
            }
        } else if (requestCode == Constance.REQUEST_SHOW_DATA) {
            if (resultCode == RESULT_OK) {
                val getData = data?.getSerializableExtra("data") as Data
                mDataViewModel.updateData(getData)
            }
        }
    }
}