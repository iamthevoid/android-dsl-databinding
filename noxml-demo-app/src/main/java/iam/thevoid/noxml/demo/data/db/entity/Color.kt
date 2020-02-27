package iam.thevoid.noxml.demo.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Color(@PrimaryKey val id : Int = 1, var value : String)