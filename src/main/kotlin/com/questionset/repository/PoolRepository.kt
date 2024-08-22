package com.questionset.repository

import com.questionset.model.Pool
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PoolRepository: JpaRepository<Pool, Int> {

}