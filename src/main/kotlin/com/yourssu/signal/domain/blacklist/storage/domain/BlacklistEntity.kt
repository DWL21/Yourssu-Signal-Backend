package com.yourssu.signal.domain.blacklist.storage.domain

import com.yourssu.signal.domain.blacklist.implement.domain.Blacklist
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "blacklist")
class BlacklistEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "profile_id", nullable = false, unique = true)
    val profileId: Long,
) {
    companion object {
        fun from(blacklist: Blacklist): BlacklistEntity {
            return BlacklistEntity(
                profileId = blacklist.profileId,
            )
        }
    }

    fun toDomain(): Blacklist {
        return Blacklist(
            id = id,
            profileId = profileId,
        )
    }
}
