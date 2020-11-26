package com.enclave.decker.recipeapp.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.enclave.decker.recipeapp.model.Recipe
import com.enclave.decker.recipeapp.roomdatabase.dao.RecipeDao
import com.enclave.decker.recipeapp.roomdatabase.entity.RecipeDto
import java.util.concurrent.Executors


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
                ).addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    //pre-populate data
                    Executors.newSingleThreadExecutor().execute {
                        INSTANCE?.let {
                            it.recipeDao().insertRecipe(RecipeDto.fromRecipeDetails(
                                Recipe(
                                    title = "Chocolate Cake",
                                    type = "Make Ahead",
                                    ingredients = "- Chocolate\\n- Flour\\n- Milk\\n- Sugar\\n- Eggs\"",
                                    steps = "1. Mix Eggs with Milk\n2. Mix Flour with Chocolate and Sugar\n3. Mix all together\n4. Bake for 40min"

                                )
                            ))
                        }
                    }
                }
            })
//                .fallbackToDestructiveMigration()
//                .addCallback(roomCallBack)
                .build()

        private val roomCallBack: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Executors.newSingleThreadExecutor().execute {
                    INSTANCE?.let {
                        it.recipeDao().insertRecipe(RecipeDto.fromRecipeDetails(
                            Recipe(
                                title = "Chocolate Cake",
                                type = "Make Ahead",
                                ingredients = "- Chocolate\\n- Flour\\n- Milk\\n- Sugar\\n- Eggs\"",
                                steps = "1. Mix Eggs with Milk\n2. Mix Flour with Chocolate and Sugar\n3. Mix all together\n4. Bake for 40min"

                            )
                        ))
//                            .insertRecipe(
//                            RecipeDto(
//                                title = "Chocolate Cake",
//                                type = "Make Ahead",
//                                ingredients = "- Chocolate\\n- Flour\\n- Milk\\n- Sugar\\n- Eggs\"",
//                                steps = "1. Mix Eggs with Milk\n2. Mix Flour with Chocolate and Sugar\n3. Mix all together\n4. Bake for 40min"
//                        ))
                    }
                }
            }
        }
    }

    abstract fun recipeDao(): RecipeDao
}

