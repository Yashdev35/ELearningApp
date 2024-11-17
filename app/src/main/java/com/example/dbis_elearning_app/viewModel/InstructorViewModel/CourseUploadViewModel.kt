package com.example.dbis_elearning_app.viewModel.InstructorViewModel

import Chapter
import Subtopic
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CourseUploadViewModel : ViewModel() {
    var courseTitle = mutableStateOf("")
    var courseImageUri = mutableStateOf<Uri?>(null)
    var promoVideoUri = mutableStateOf<Uri?>(null)
    var chapters = mutableStateListOf<Chapter>()

    fun addChapter() {
        chapters.add(Chapter())
    }

    fun deleteChapter(index: Int) {
        if (index >= 0 && index < chapters.size) {
            chapters.removeAt(index)
        }
    }

    fun updateChapterName(index: Int, name: String) {
        chapters[index].name = name
    }

    fun addSubtopic(chapterIndex: Int) {
        chapters[chapterIndex].subtopics.add(Subtopic())
    }

    fun updateSubtopic(chapterIndex: Int, subtopicIndex: Int, updatedSubtopic: Subtopic) {
        chapters[chapterIndex].subtopics[subtopicIndex] = updatedSubtopic
    }

    fun deleteSubtopic(chapterIndex: Int, subtopicIndex: Int) {
        chapters[chapterIndex].subtopics.removeAt(subtopicIndex)
    }
}
