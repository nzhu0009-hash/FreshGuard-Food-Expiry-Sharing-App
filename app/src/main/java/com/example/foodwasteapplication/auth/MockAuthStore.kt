package com.example.foodwasteapplication.auth

data class MockUser(
    val name: String,
    val email: String,
    val password: String,
)

sealed interface RegisterResult {
    data class Success(val user: MockUser) : RegisterResult
    data class Failure(val message: String) : RegisterResult
}

object MockAuthStore {
    private val users = mutableListOf(
        MockUser(
            name = "Demo User",
            email = "user@test.com",
            password = "Password1!"
        )
    )

    var currentUser: MockUser? = null
        private set

    fun login(email: String, password: String): Boolean {
        val user = users.firstOrNull {
            it.email.equals(email.trim(), ignoreCase = true) &&
                it.password == password
        } ?: return false

        currentUser = user
        return true
    }

    fun register(
        name: String,
        email: String,
        password: String,
    ): RegisterResult {
        val normalizedEmail = email.trim().lowercase()
        val userExists = users.any { it.email.equals(normalizedEmail, ignoreCase = true) }
        if (userExists) {
            return RegisterResult.Failure("An account with this email already exists")
        }

        val user = MockUser(
            name = name.trim(),
            email = normalizedEmail,
            password = password
        )
        users += user
        return RegisterResult.Success(user)
    }

    fun logout() {
        currentUser = null
    }
}
