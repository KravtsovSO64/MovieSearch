package ru.yandex.practicum.moviessearch.ui.names

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.yandex.practicum.moviessearch.domain.models.Name

class PersonsAdapter: RecyclerView.Adapter<PersonViewHolder>() {

    var person = ArrayList<Name>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder = PersonViewHolder(parent)

    override fun getItemCount(): Int = person.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(person[position])
    }
}