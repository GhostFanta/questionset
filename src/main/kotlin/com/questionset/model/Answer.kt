package com.questionset.model

import com.questionset.dto.AnswerDTO
import com.questionset.dto.AnswerMemberDTO
import jakarta.persistence.*
import java.util.Objects

@Entity
@Table(name="answer")
data class Answer(
    @Column(name="content")
    var content: String,

    @Column(name="selected")
    var selected: Boolean,

    @ManyToOne(cascade = [(CascadeType.DETACH)], fetch = FetchType.EAGER)
    @JoinColumn(name="question_id", nullable = false)
    var question: Question,

):Base() {
    constructor(): this("", false, Question())

    fun toDTO(): AnswerDTO {
        return this.let {
            AnswerDTO(it.id, it.content, it.selected, it.question.toMemberDTO(), it.createdAt, it.updatedAt)
        }
    }

    fun toMemberDTO(): AnswerMemberDTO {
        return this.let {
            AnswerMemberDTO(it.id, it.content, it.selected, it.createdAt, it.updatedAt)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Answer

        if (content != other.content) return false
        if (selected != other.selected) return false
        if (question != other.question) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(this.id, this.content, this.selected)
    }

    override fun toString(): String {
        return "Answer(content='$content', selected=$selected, question=$question)"
    }
}