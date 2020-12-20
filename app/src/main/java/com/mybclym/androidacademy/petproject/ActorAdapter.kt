package com.mybclym.androidacademy.petproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mybclym.androidacademy.petproject.DataModel.Actor

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
        Glide.with(itemView.context)
            .load(actor?.picture)
            .apply(imageOption)
            .into(image)
        name?.text = actor?.name
    }

    companion object {
        private val imageOption = RequestOptions()
            .placeholder(R.drawable.no_image)
            .fallback(R.drawable.no_image)
            .fitCenter()
    }
}