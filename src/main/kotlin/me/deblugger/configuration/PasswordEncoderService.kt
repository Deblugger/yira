package me.deblugger.configuration

import java.security.MessageDigest
import javax.inject.Singleton

@Singleton
class PasswordEncoderService {

    fun encode(rawPassword: String): String {
        return hashString(rawPassword)
    }

    fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return encode(rawPassword) == encodedPassword
    }

    private fun hashString(input: String): String {
        return MessageDigest
                .getInstance("SHA-256")
                .digest(input.toByteArray())
                .fold("", { str, it -> str + "%02x".format(it) })
    }
}