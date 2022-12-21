package com.example.fitnesss.models.library

class LibraryItem: LibraryRepository {
    fun libraryItem(): List<Library> {
        return listOf(
        Library(
            0L,
            "https://imageup.ru/img183/4127872/lose_weight.jpg",
            "Сбросить вес"
        ),
        Library(
            1L,
            "https://imageup.ru/img169/4127874/good_shape.jpg",
            "Быть в тонусе"
        ),
        Library(
            2L,
            "https://imageup.ru/img105/4127866/gain_weight.jpg",
            "Набрать массу"
        ),
        Library(
            3L,
            "https://imageup.ru/img190/4127875/high_intensity.jpg",
            "Высокая интенсивность"
        )
        )
    }

    override fun getLibrary(): List<Library> = libraryItem()
}