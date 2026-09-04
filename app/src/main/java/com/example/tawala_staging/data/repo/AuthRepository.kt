package com.example.tawala_staging.data.repo

import com.example.tawala_staging.data.dao.SessionDao
import com.example.tawala_staging.data.dao.UserDao
import com.example.tawala_staging.data.entity.SessionEntity
import com.example.tawala_staging.data.entity.UserEntity

class AuthRepository(private val sessionDao: SessionDao, private val userDao: UserDao) {

    suspend fun login(badgeId: String, passwordHash: String): Boolean {
        val user = userDao.getUserByBadgeId(badgeId)
        if (user != null && user.passwordHash == passwordHash) {
            // Save active session locally
            sessionDao.saveSession(
                SessionEntity(
                    badgeId = user.badgeId,
                    fullName = user.fullName,
                    phoneNumber = user.phoneNumber
                )
            )
            return true
        }
        return false
    }

    suspend fun getLoggedInUser(): SessionEntity? {
        return sessionDao.getActiveSession()
    }
    suspend fun register(user: UserEntity) {
        userDao.registerUser(user)
    }

    suspend fun logout() {
        sessionDao.clearSession()
    }
}