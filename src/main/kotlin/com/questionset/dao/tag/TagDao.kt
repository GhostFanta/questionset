package com.questionset.dao.tag

import com.questionset.model.Tag

interface TagDao {

    fun listAllTags(): List<Tag>

    fun createTag(): Tag

    fun getTagById(): Tag

    fun updateTag(): Tag

    fun deleteTag()
}