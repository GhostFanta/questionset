package com.questionset.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.questionset.dto.QuestionDTO
import com.questionset.dto.QuestionMemberDTO
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name="question")
data class Question(
    @Column(name="content")
    var content: String,

    @Column(name="summary")
    var summary: String,

    @OneToMany(mappedBy = "question")
    var answers: MutableSet<Answer>,

    @ManyToMany(cascade = [CascadeType.DETACH], fetch = FetchType.EAGER, targetEntity = Tag::class)
    @JoinTable(name = "tag_question",
        joinColumns = [
            JoinColumn(name="question_id", nullable = false)
        ],
        inverseJoinColumns = [
            JoinColumn(name="tag_id", nullable = false)
        ]
    )
    @JsonIgnoreProperties("questions")
    var tags: MutableSet<Tag> = mutableSetOf(),

    @ManyToMany(cascade = [CascadeType.DETACH], fetch = FetchType.EAGER, targetEntity = Collection::class)
    @JoinTable(name = "collection_question",
        joinColumns = [
            JoinColumn(name="question_id", nullable = false)
        ],
        inverseJoinColumns = [
            JoinColumn(name="collection_id", nullable = false)
        ]
    )
    @JsonIgnoreProperties("questions")
    var collections: MutableSet<com.questionset.model.Collection>,


    @ManyToOne(cascade = [(CascadeType.DETACH)], fetch = FetchType.EAGER)
    @JoinColumn(name="status_id", nullable = false)
    var status: QuestionStatus,

):Base() {
    constructor():this("","", mutableSetOf(), mutableSetOf(), mutableSetOf(), QuestionStatus())

    fun toDTO(): QuestionDTO {
        return this.let {
            QuestionDTO(it.id, it.content, it.summary,
                it.answers.map { ans -> ans.toMemberDTO() }, it.tags.map{tag->tag.toMemberDTO()},
                it.collections.map { col -> col.toMemberDTO() }, it.status.toDTO(), it.createdAt, it.updatedAt)
        }
    }

    fun toMemberDTO(): QuestionMemberDTO {
        return this.let {
            QuestionMemberDTO(it.id, it.content, it.summary, it.createdAt, it.updatedAt)
        }
    }

    override fun hashCode(): Int {
        return Objects.hash(id, content, createdAt, updatedAt)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Question

        if (content != other.content) return false
        if (summary != other.summary) return false
        if (answers != other.answers) return false
        if (tags != other.tags) return false
        if (collections != other.collections) return false
        if (status != other.status) return false

        return true
    }

    override fun toString(): String {
        return "Question(content='$content', summary='$summary', answers=$answers, tags=$tags, collections=$collections, status=$status)"
    }

}
