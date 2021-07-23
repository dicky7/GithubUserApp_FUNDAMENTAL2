package com.example.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.model.Person

var listFollowingData = ArrayList<Person>()
class FollowingAdapter(private val listUser: ArrayList<Person>): RecyclerView.Adapter<FollowingAdapter.FollowViewHolder>(){
    inner class FollowViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        var txtName: TextView = itemView.findViewById(R.id.tv_item_name)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)

    }
    init {
        listFollowingData = listUser
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingAdapter.FollowViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return FollowViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowingAdapter.FollowViewHolder, position: Int) {
        val person = listUser[position]
        Glide.with(holder.itemView.context)
            .load(person.avatar_url)
            .into(holder.imgPhoto)
        holder.txtName.text = person.login_username
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}