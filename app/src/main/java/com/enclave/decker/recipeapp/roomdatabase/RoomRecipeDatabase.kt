package com.enclave.decker.recipeapp.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.enclave.decker.recipeapp.roomdatabase.dao.RecipeDao
import com.enclave.decker.recipeapp.roomdatabase.entity.RecipeDto

@Database(
    entities = [
        RecipeDto::class
    ],
    version = RoomRecipeDatabase.DATABASE_VERSION,
    exportSchema = false
)
abstract class RoomRecipeDatabase : RoomDatabase() {

    companion object {
        private val INSTANCE: RoomRecipeDatabase? = null
        internal const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "recipe"

        @JvmStatic
        @Suppress("SpreadOperator")
        fun createDatabase(context: Context): RoomRecipeDatabase =
            INSTANCE ?: Room.databaseBuilder(
                    context,
                    RoomRecipeDatabase::class.java,
                    DATABASE_NAME
                ).build()
    }

    abstract fun recipeDao(): RecipeDao
}

