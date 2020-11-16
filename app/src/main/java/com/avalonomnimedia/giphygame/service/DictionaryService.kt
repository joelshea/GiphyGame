package com.avalonomnimedia.giphygame.service

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable

class DictionaryService(private val client: HttpClient) : IDictionaryService {
    override suspend fun getSynonyms(word: String): List<DictionaryResponse> {
        return client.get("https://api.datamuse.com/words?ml=$word")
    }
}

interface IDictionaryService {
    suspend fun getSynonyms(word: String): List<DictionaryResponse>
}

@Serializable
data class DictionaryResponse(val word: String, val score: Int, val tags: List<String>)

