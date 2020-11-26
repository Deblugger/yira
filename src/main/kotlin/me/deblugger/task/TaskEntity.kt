package me.deblugger.task

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tasks")
data class TaskEntity (
        @Id
        @GeneratedValue
        var id: Long = 1,
        var name: String,
        var projectId: Long,
        var description: String,
        var statusId: Long,
        var assignTo: Long, // UserId
        var type: TaskType,
        var parentId: Long
) {
}

enum class TaskType(var value: String) {
    STORY("story"),
    TASK("task"),
    SUBTASK("subtask"),
    BUG("bug"),
    EPIC("epic")
}