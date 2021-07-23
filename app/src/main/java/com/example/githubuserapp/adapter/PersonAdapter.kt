package com.example.githubuserapp.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.DetailActivity
import com.example.githubuserapp.R
import com.example.githubuserapp.model.Person


var listUserData = ArrayList<Person>()
class PersonAdapter(private  val listuser: ArrayList<Person> ) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    init {
        listUserData = listuser
    }

    inner class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtName: TextView = itemView.findViewById(R.id.tv_item_name)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return PersonViewHolder(view)

    }

    override fun getItemCount(): Int {
        return listuser.size
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = listuser [position]
        Glide.with(holder.itemView.context)
            .load(person.avatar_url)
            .into(holder.imgPhoto)
        holder.txtName.text = person.login_username
        var personData = Person(
            person.login_username,
            person.avatar_url,
            person.company,
            person.location,
            person.repository,
            person.follower,
            person.following,

        )
        holder.itemView.setOnClickListener{
            val  intent = Intent(holder.itemView.context,DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_PERSON,personData)
            holder.itemView.context.startActivity(intent)
        }

    }
}