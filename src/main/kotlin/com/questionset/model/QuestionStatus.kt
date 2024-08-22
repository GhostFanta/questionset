package com.questionset.model

import com.questionset.dto.QuestionStatusDTO
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name="")
data class QuestionStatus(

    @Column(name="name")
    var name: String,

):Base() {
    constructor():this("")

    fun toDTO(): QuestionStatusDTO{
        return this.let {
            QuestionStatusDTO(it.id, it.name, it.createdAt, it.updatedAt)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuestionStatus

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return "QuestionStatus(name='$name')"
    }


}