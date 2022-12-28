package com.example.fitnesss.models.library

import com.example.fitnesss.models.detail.Detail
import com.example.fitnesss.models.workouts.Workouts
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LibraryItem @Inject constructor(): LibraryRepository {

    override fun getLibrary(): List<Library> = libraryItem()

    override fun getWorkouts(libraryId: Int): List<Workouts> = workoutsItem(libraryId)

    private fun libraryItem(): List<Library> {
        return listOf(
            Library(
                1,
                "https://imageup.ru/img183/4127872/lose_weight.jpg",
                "Сбросить вес"
            ),
            Library(
                2,
                "https://imageup.ru/img169/4127874/good_shape.jpg",
                "Быть в тонусе"
            ),
            Library(
                3,
                "https://imageup.ru/img105/4127866/gain_weight.jpg",
                "Набрать массу"
            ),
            Library(
                4,
                "https://imageup.ru/img190/4127875/high_intensity.jpg",
                "Высокая интенсивность"
            )
        )
    }

    private fun workoutsItem(libraryId: Int): List<Workouts> {
        return when (libraryId) {
            1 -> {
                listOf(
                    Workouts(
                        1,
                        "https://imageup.ru/img183/4127872/lose_weight.jpg",
                        "Название",
                        "50 калорий"
                    ),
                    Workouts(
                        2,
                        "https://imageup.ru/img183/4127872/lose_weight.jpg",
                        "Название",
                        "50 калорий"
                    ),
                    Workouts(
                        3,
                        "https://imageup.ru/img183/4127872/lose_weight.jpg",
                        "Название",
                        "50 калорий"
                    ),
                    Workouts(
                        4,
                        "https://imageup.ru/img183/4127872/lose_weight.jpg",
                        "Название",
                        "50 калорий"
                    )
                )
            }

            2 -> {
                listOf(
                    Workouts(
                        5,
                        "https://imageup.ru/img183/4127872/lose_weight.jpg",
                        "Название",
                        "50 калорий"
                    ),
                    Workouts(
                        6,
                        "https://imageup.ru/img183/4127872/lose_weight.jpg",
                        "Название",
                        "50 калорий"
                    ),
                    Workouts(
                        7,
                        "https://imageup.ru/img183/4127872/lose_weight.jpg",
                        "Название",
                        "50 калорий"
                    ),
                    Workouts(
                        8,
                        "https://imageup.ru/img183/4127872/lose_weight.jpg",
                        "Название",
                        "50 калорий"
                    )
                )
            }

            3 -> {
                listOf(
                    Workouts(
                        9,
                        "https://imageup.ru/img183/4127872/lose_weight.jpg",
                        "Название",
                        "50 калорий"
                    ),
                    Workouts(
                        10,
                        "https://imageup.ru/img183/4127872/lose_weight.jpg",
                        "Название",
                        "50 калорий"
                    ),
                    Workouts(
                        11,
                        "https://imageup.ru/img183/4127872/lose_weight.jpg",
                        "Название",
                        "50 калорий"
                    ),
                    Workouts(
                        12,
                        "https://imageup.ru/img183/4127872/lose_weight.jpg",
                        "Название",
                        "50 калорий"
                    )
                )
            }

            4 -> {
                listOf(
                    Workouts(
                        13,
                        "https://imageup.ru/img183/4127872/lose_weight.jpg",
                        "Название",
                        "50 калорий"
                    ),
                    Workouts(
                        14,
                        "https://imageup.ru/img183/4127872/lose_weight.jpg",
                        "Название",
                        "50 калорий"
                    ),
                    Workouts(
                        15,
                        "https://imageup.ru/img183/4127872/lose_weight.jpg",
                        "Название",
                        "50 калорий"
                    ),
                    Workouts(
                        16,
                        "https://imageup.ru/img183/4127872/lose_weight.jpg",
                        "Название",
                        "50 калорий"
                    )
                )
            }

            else -> throw RuntimeException("null")
        }
    }

    override fun getDetail(workoutsId: Int): Detail {
        return when(workoutsId){
            1 -> Detail(
                "https://imageup.ru/img183/4127872/lose_weight.jpg",
                "Description",
                "Name of exercise",
                "2:00",
                "5:00"
            )
            2 -> Detail(
                "https://imageup.ru/img183/4127872/lose_weight.jpg",
                "Description",
                "Name of exercise",
                "2:00",
                "5:00"
                )
            3 -> Detail(
                    "https://imageup.ru/img183/4127872/lose_weight.jpg",
                    "Description",
                    "Name of exercise",
                    "2:00",
                    "5:00"
                )
            4 -> Detail(
                    "https://imageup.ru/img183/4127872/lose_weight.jpg",
                    "Description",
                    "Name of exercise",
                    "2:00",
                    "5:00"
                )
            5 -> Detail(
                    "https://imageup.ru/img183/4127872/lose_weight.jpg",
                    "Description",
                    "Name of exercise",
                    "2:00",
                    "5:00"
                )
            6 -> Detail(
                    "https://imageup.ru/img183/4127872/lose_weight.jpg",
                    "Description",
                    "Name of exercise",
                    "2:00",
                    "5:00"
                )
            7 -> Detail(
                    "https://imageup.ru/img183/4127872/lose_weight.jpg",
                    "Description",
                    "Name of exercise",
                    "2:00",
                    "5:00"
                )
            8 -> Detail(
                    "https://imageup.ru/img183/4127872/lose_weight.jpg",
                    "Description",
                    "Name of exercise",
                    "2:00",
                    "5:00"
                )
            9 -> Detail(
                    "https://imageup.ru/img183/4127872/lose_weight.jpg",
                    "Description",
                    "Name of exercise",
                    "2:00",
                    "5:00"
                )
            10 -> Detail(
                    "https://imageup.ru/img183/4127872/lose_weight.jpg",
                    "Description",
                    "Name of exercise",
                    "2:00",
                    "5:00"
                )
            11 -> Detail(
                    "https://imageup.ru/img183/4127872/lose_weight.jpg",
                    "Description",
                    "Name of exercise",
                    "2:00",
                    "5:00"
                )
            12 -> Detail(
                    "https://imageup.ru/img183/4127872/lose_weight.jpg",
                    "Description",
                    "Name of exercise",
                    "2:00",
                    "5:00"
                )
            13 -> Detail(
                    "https://imageup.ru/img183/4127872/lose_weight.jpg",
                    "Description",
                    "Name of exercise",
                    "2:00",
                    "5:00"
                )
            14 -> Detail(
                    "https://imageup.ru/img183/4127872/lose_weight.jpg",
                    "Description",
                    "Name of exercise",
                    "2:00",
                    "5:00"
                )
            15 -> Detail(
                    "https://imageup.ru/img183/4127872/lose_weight.jpg",
                    "Description",
                    "Name of exercise",
                    "2:00",
                    "5:00"
                )
            16 -> Detail(
                    "https://imageup.ru/img183/4127872/lose_weight.jpg",
                    "Description",
                    "Name of exercise",
                    "2:00",
                    "5:00"
                )
            else -> throw RuntimeException("null")
        }
    }
}