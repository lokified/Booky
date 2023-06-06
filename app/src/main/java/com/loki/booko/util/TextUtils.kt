package com.loki.booko.util

import com.loki.booko.data.remote.response.Author

object TextUtils {

    fun getSubjectsAsString(subjects: List<String>, limit: Int): List<String> {
        val allSubjects = ArrayList<String>()
        // strip "--" from subjects.
        subjects.forEach { subject ->
            if (subject.contains("--")) {
                allSubjects.addAll(subject.split("--"))
            } else {
                allSubjects.add(subject)
            }
        }
        val truncatedSubs: List<String> = if (allSubjects.size > limit) {
            allSubjects.toSet().toList().subList(0, limit)
        } else {
            allSubjects.toSet().toList()
        }
        return truncatedSubs
    }

    fun getAuthorsAsString(author: List<Author>): String {
        return author.joinToString(separator = ",") { it.name }
    }

    fun getLanguagesAsString(language: List<String>): String {
        return  language.joinToString(separator = ",")
    }
}