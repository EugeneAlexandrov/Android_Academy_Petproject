package com.mybclym.androidacademy.petproject.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mybclym.androidacademy.petproject.dataModel.domainModel.Actor
import com.mybclym.androidacademy.petproject.R

class ActorAdapter() : RecyclerView.Adapter<ItemActorViewHolder>() {

    private var actorsList: List<Actor>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemActorViewHolder {
        return ItemActorViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.actor_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemActorViewHolder, position: Int) {
        //в адаптере есть список актеров, передаем в холдер актера по позиции
        holder.bind(actorsList?.get(position))
    }

    override fun getItemCount(): Int {
        return actorsList?.size ?: 0
    }

    fun setUpActorsList(actors: List<Actor>?) {
        actorsList = actors
        notifyDataSetChanged()
    }
}

class ItemActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val image: ImageView? = itemView.findViewById(R.id.actor_iv)
    private val name: TextView? = itemView.findViewById(R.id.actor_tv)

    fun bind(actor: Actor?) {
        image?.load(actor?.picture){
            crossfade(true)
            placeholder(R.drawable.no_image)
        }
        name?.text = actor?.name
    }
}