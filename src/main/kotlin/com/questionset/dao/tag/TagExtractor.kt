package com.questionset.dao.tag

import com.questionset.model.Tag
import org.springframework.jdbc.core.ResultSetExtractor
import java.sql.ResultSet

class TagExtractor:ResultSetExtractor<Tag> {
    override fun extractData(rs: ResultSet): Tag? {
        rs.next()
        return TagMapper().mapRow(rs,0)
    }
}