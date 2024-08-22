package com.questionset.model

import com.questionset.dto.PoolDTO
import com.questionset.dto.PoolMemberDTO
import jakarta.persistence.*


@Entity
@Table(name="pool")
data class Pool(
    @Column(name="name")
    var name: String,

    @Column(name="summary")
    var summary: String,


    @ManyToMany(cascade = [CascadeType.DETACH], fetch = FetchType.EAGER, targetEntity = Question::class)
    @JoinTable(name="pool_question",
        joinColumns = [
            JoinColumn(name="pool_id", nullable = false)
        ],
        inverseJoinColumns = [
            JoinColumn(name="question_id", nullable = false)
        ]
    )
    var questions: MutableSet<Question> = mutableSetOf(),
) :Base() {
    constructor(): this( "", "", mutableSetOf())

    fun toDTO(): PoolDTO {
        return this.let {
            PoolDTO(it.id, it.name, it.summary, it.questions.map { question -> question.toMemberDTO() }, it.createdAt, it.updatedAt)
        }
    }

    fun toMemberDTO(): PoolMemberDTO {
        return this.let {
            PoolMemberDTO(it.id, it.name, it.summary, it.createdAt, it.updatedAt)
        }
    }
}