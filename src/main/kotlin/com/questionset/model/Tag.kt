package com.questionset.model

import com.questionset.dto.TagDTO
import com.questionset.dto.TagMemberDTO
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name="tag")
data class Tag(

    @Column(name="name")
    var name: String,

    @ManyToMany(mappedBy = "tags")
    var questions: MutableSet<Question> = mutableSetOf(),

    ):Base() {
    constructor():this("",mutableSetOf())

    fun toDTO():TagDTO {
        return this.let {
            TagDTO(it.id, it.name,
                it.questions?.map { que -> que.toMemberDTO() },
                it.createdAt, it.updatedAt)
        }

    }

    fun toMemberDTO(): TagMemberDTO {
        return this.let {
            TagMemberDTO(it.id, it.name, it.createdAt, it.updatedAt)
        }
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name, createdAt, updatedAt)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tag

        if (name != other.name) return false
        if (questions != other.questions) return false

        return true
    }

    override fun toString(): String {
        return "Tag(name='$name', questions=$questions)"
    }

}
