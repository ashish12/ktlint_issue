class Class {
    private val students = mutableListOf<Student>()

    fun addStudentWithName(name: String) {
        students.add(Student(
            name
        ))
    }
}

data class Student(val name: String)
