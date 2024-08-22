package com.questionset.model

import com.questionset.dto.CollectionDTO
import com.questionset.dto.CollectionMemberDTO
import jakarta.persistence.*
import java.util.Objects

@Entity
@Table(name="collection")
data class Collection (

    @Column(name="name")
    var name: String,


    @ManyToMany(mappedBy = "collections")
    var questions: MutableSet<Question>,

):Base() {
    constructor(): this("", mutableSetOf())

    fun toDTO():CollectionDTO {
        return this.let {
            CollectionDTO(it.id, it.name, it.questions.map { question -> question.toMemberDTO() }, it.createdAt, it.updatedAt)
        }
    }

    fun toMemberDTO(): CollectionMemberDTO {
        return this.let {
            CollectionMemberDTO(it.id, it.name, it.createdAt, it.updatedAt)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Collection

        if (name != other.name) return false
        if (questions != other.questions) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(this.id, this.name)
    }

    override fun toString(): String {
        return "Collection(name='$name', questions=$questions)"
    }

}