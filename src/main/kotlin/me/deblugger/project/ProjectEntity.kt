package me.deblugger.project

import me.deblugger.converter.StringListConverter
import javax.persistence.*

@Entity
@Table(name = "projects")
data class ProjectEntity (
        @Id
        @GeneratedValue
        var id: Long = 1,
        var name: String,
        var owner: String,
        @Convert(converter = StringListConverter::class)
        var states: List<String>
)