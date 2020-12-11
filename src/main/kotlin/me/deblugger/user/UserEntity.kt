package me.deblugger.user

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
class UserEntity (
        var name: String,
        var password: String,
        var email: String
) {
    @Id
    @GeneratedValue
    var id: Long = 0
}