package com.example.appresponsive_kelompok1

data class Course(
    var id: Int = 0,
    var bookmarkId: Int = 0,
    var name: String = "",
    var description: String = "",
    var level: String = "",
    var image: String = "",
    var isBookmark: Boolean = false
)
