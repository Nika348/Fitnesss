package com.example.fitnesss.models.library

interface LibraryRepository {

    fun getLibrary(): List<Library>
}