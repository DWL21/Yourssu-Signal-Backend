package com.yourssu.signal.domain.blacklist.implement

import com.yourssu.signal.domain.blacklist.implement.domain.Blacklist
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class BlacklistWriter(
    private val blacklistRepository: BlacklistRepository,
) {
    @Transactional
    fun save(blacklist: Blacklist): Blacklist {
        val savedBlacklist = blacklistRepository.save(blacklist)
        blacklistRepository.updateCache()
        return savedBlacklist
    }

    @Transactional
    fun deleteByProfileId(profileId: Long) {
        blacklistRepository.deleteByProfileId(profileId)
        blacklistRepository.updateCache()
    }
}
