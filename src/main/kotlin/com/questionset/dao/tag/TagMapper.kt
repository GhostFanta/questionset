package com.questionset.dao.tag

import com.questionset.model.Tag
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class TagMapper: RowMapper<Tag> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Tag? {
        TODO("Not yet implemented")
    }
}