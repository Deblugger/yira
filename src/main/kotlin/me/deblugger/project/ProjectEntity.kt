package me.deblugger.project

import javax.persistence.*

@Entity
@Table(name = "projects")
data class ProjectEntity (
        var name: String,
        var owner: Long //UserId
) {
        @Id
        @GeneratedValue
        var id: Long? = null
}