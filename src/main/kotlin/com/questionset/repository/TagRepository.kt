package com.questionset.repository

import com.questionset.model.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
@Repository
interface TagRepository: JpaRepository<Tag, Int> {
    @Query("select * from tag where name like '%:name%'", nativeQuery = true)
    fun retrieveAll(@Param("name")name:String):List<Tag>

    fun findByName(name: String):List<Tag>
}