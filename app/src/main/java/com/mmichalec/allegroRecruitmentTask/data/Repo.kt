package com.mmichalec.allegroRecruitmentTask.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class Repo(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String? = null,
    val created_at: String,
    val updated_at: String? = null,
)