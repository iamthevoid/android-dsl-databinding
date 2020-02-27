package iam.thevoid.noxml.demo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import iam.thevoid.noxml.demo.data.db.dao.ColorDao
import iam.thevoid.noxml.demo.data.db.entity.Color

@Database(entities = arrayOf(Color::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val colorDao: ColorDao
}