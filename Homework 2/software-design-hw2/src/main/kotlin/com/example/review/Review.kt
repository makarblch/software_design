package com.example.review

import kotlinx.serialization.Serializable

@Serializable
class Review (var rate : Int, var comment : String){
}