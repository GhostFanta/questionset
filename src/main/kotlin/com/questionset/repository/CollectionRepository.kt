package com.questionset.repository

import com.questionset.model.Collection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CollectionRepository: JpaRepository<Collection, Int> {
}