package com.github.AsadaGuitar.classroom.adaptor.postgres.dao

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.PostgresProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.)
    Each generated XXXXTable trait is mixed in this trait hence allowing access to all the TableQuery lazy vals.
  */
trait Tables extends ClassroomsTable with StudentsTable with TeacherClassroomsTable with TeachersTable {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Classrooms.schema ++ Students.schema ++ TeacherClassrooms.schema ++ Teachers.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl: profile.DDL = schema

}
