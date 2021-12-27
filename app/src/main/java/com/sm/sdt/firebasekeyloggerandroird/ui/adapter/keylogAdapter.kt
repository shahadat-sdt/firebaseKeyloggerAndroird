package com.sm.sdt.firebasekeyloggerandroird.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sm.sdt.firebasekeyloggerandroird.databinding.KeylogItemBinding


class keylogAdapter(val list: ArrayList<String>) : RecyclerView.Adapter<keylogAdapter.KeylogViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeylogViewHolder {

        val binding = KeylogItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return KeylogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KeylogViewHolder, position: Int) {
        with(holder){
            with(list[position]){
                binding.keylog.text = this
            }
        }
    }

    override fun getItemCount() = list.size

    inner class KeylogViewHolder(val binding: KeylogItemBinding) : RecyclerView.ViewHolder(binding.root)

}
