package com.questionset.dao.tag

import com.questionset.model.Tag
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class TagDaoImpl: TagDao {

    internal lateinit var jdbcTemplate: JdbcTemplate

    override fun listAllTags():List<Tag> {
        var sql = "select * from tag t left join tag_question tq on tq.tag_id = t.id left join question q on tq.question_id = q.id"
        return listOf()
    }

    override fun createTag(): Tag {
        return Tag()
    }

    override fun getTagById(): Tag {
        return Tag()
    }

    override fun updateTag(): Tag {
        return Tag()
    }

    override fun deleteTag() {

    }
}