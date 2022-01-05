package com.sm.sdt.firebasekeyloggerandroird.ui.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.sm.sdt.firebasekeyloggerandroird.R

class UserAdapter(val list: ArrayList<String>, val listener: OnUserItemClicked) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.child_list_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.userName.text = list[position]
    }

    override fun getItemCount() = list.size
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val userName = itemView.findViewById<TextView>(R.id.user_name)

        init {
            itemView.setOnClickListener(this)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onClick(v: View?) {
            listener.itemClicked(adapterPosition, list[adapterPosition])
        }
    }

    interface OnUserItemClicked {
        fun itemClicked(adapterPosition: Int, item: String)
    }
}
