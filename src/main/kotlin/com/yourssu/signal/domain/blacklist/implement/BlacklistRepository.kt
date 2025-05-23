package com.yourssu.signal.domain.blacklist.implement

import com.yourssu.signal.domain.blacklist.implement.domain.Blacklist

interface BlacklistRepository {
    fun save(blacklist: Blacklist): Blacklist
    fun existsByProfileId(profileId: Long): Boolean
    fun getByProfileId(profileId: Long): Blacklist
    fun deleteByProfileId(profileId: Long)
    fun findAll(): Set<Long>
    fun updateCache(): Set<Long>
}
