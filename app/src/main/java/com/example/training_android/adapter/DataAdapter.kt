package com.example.training_android.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.training_android.databinding.ItemBinding
import com.example.training_android.listener.OnItemClick
import com.example.training_android.model.Data

@Suppress("DEPRECATION")
class DataAdapter(val listener: OnItemClick) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    private val listData = ArrayList<Data>()
    private val fullListData = ArrayList<Data>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(list: List<Data>) {
        fullListData.clear()
        fullListData.addAll(list)

        listData.clear()
        listData.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun searchData(s: String) {
        listData.clear()

        for (item in fullListData) {
            if (item.value1.lowercase().contains(s.lowercase()) ||
                item.value2.lowercase().contains(s.lowercase()) ||
                item.value3.lowercase().contains(s.lowercase())
            )
                listData.add(item)
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: Data = listData[position]
        holder.bind(data)
        holder.itemView.setOnClickListener {
            listener.onClickListener(listData[holder.adapterPosition])
        }

        holder.itemView.setOnLongClickListener {
            listener.onLongClickListener(listData[holder.adapterPosition])
            true
        }
    }

    inner class ViewHolder(private val view: ItemBinding) : RecyclerView.ViewHolder(view.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Data) {
            view.txtItemValue1?.text = "QR発行前テキスト : " + data.value1
            view.txtValue2?.text = "QR発行前強調テキスト : " + data.value2
            view.txtValue3?.text = "QR発行後テキスト : " + data.value3
        }
    }
}