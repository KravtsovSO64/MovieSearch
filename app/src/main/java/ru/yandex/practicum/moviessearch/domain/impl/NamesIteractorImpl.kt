package ru.yandex.practicum.moviessearch.domain.impl

import ru.yandex.practicum.moviessearch.domain.api.NamesIteractor
import ru.yandex.practicum.moviessearch.domain.api.NamesRepository
import ru.yandex.practicum.moviessearch.util.Resource
import java.util.concurrent.Executors

class NamesIteractorImpl(private val repository: NamesRepository): NamesIteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchName(expression: String, consumer: NamesIteractor.NameConsumer) {
        executor.execute {
            when(val resource = repository.searchName(expression)) {
                is Resource.Success -> {consumer.consume(resource.data, null)}
                is Resource.Error -> {consumer.consume(resource.data, resource.message)}
            }
        }
    }
}