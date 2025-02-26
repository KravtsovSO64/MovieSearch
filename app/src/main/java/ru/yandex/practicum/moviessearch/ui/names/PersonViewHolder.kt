package ru.yandex.practicum.moviessearch.ui.names

import android.app.Person
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.yandex.practicum.moviessearch.R
import ru.yandex.practicum.moviessearch.domain.models.Name

class PersonViewHolder(parent: ViewGroup):
RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_person, parent,false)){

    var photo: ImageView = itemView.findViewById(R.id.photo)
    var name: TextView = itemView.findViewById(R.id.name)
    var description: TextView = itemView.findViewById(R.id.description)

    fun bind(person: Name) {
        Glide.with(itemView)
            .load(person.image)
            .placeholder(R.drawable.ic_person)
            .circleCrop()
            .into(photo)

        name.text = person.title
        description.text = person.description
    }
}