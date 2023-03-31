package com.example.shared

sealed class Resource<out T> {
    class Success<T>(val result :T) : Resource<T>()
    class Failure(val message:String,val status:Int,val stringIdResource : Int = 0):Resource<Nothing>()
    object IsLoading : Resource<Nothing>()
}


