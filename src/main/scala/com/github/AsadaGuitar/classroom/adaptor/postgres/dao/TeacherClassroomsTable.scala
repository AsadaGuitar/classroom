package com.github.AsadaGuitar.classroom.adaptor.postgres.dao

// AUTO-GENERATED Slick data model for table TeacherClassrooms
trait TeacherClassroomsTable {

  self:Tables  =>

  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}
  /** Entity class storing rows of table TeacherClassrooms
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param teacherId Database column teacher_id SqlType(int4)
   *  @param classroomId Database column classroom_id SqlType(int4)
   *  @param close Database column close SqlType(bool), Default(false)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param modifiedAt Database column modified_at SqlType(timestamptz), Default(None)
   *  @param closedAt Database column closed_at SqlType(timestamptz), Default(None) */
  case class TeacherClassroomsRow(id: Int, teacherId: Int, classroomId: Int, close: Boolean = false, createdAt: java.sql.Timestamp, modifiedAt: Option[java.sql.Timestamp] = None, closedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching TeacherClassroomsRow objects using plain SQL queries */
  implicit def GetResultTeacherClassroomsRow(implicit e0: GR[Int], e1: GR[Boolean], e2: GR[java.sql.Timestamp], e3: GR[Option[java.sql.Timestamp]]): GR[TeacherClassroomsRow] = GR{
    prs => import prs._
    TeacherClassroomsRow.tupled((<<[Int], <<[Int], <<[Int], <<[Boolean], <<[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table teacher_classrooms. Objects of this class serve as prototypes for rows in queries. */
  class TeacherClassrooms(_tableTag: Tag) extends profile.api.Table[TeacherClassroomsRow](_tableTag, "teacher_classrooms") {
    def * = (id, teacherId, classroomId, close, createdAt, modifiedAt, closedAt) <> (TeacherClassroomsRow.tupled, TeacherClassroomsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(teacherId), Rep.Some(classroomId), Rep.Some(close), Rep.Some(createdAt), modifiedAt, closedAt).shaped.<>({ r=>import r._; _1.map(_=> TeacherClassroomsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column teacher_id SqlType(int4) */
    val teacherId: Rep[Int] = column[Int]("teacher_id")
    /** Database column classroom_id SqlType(int4) */
    val classroomId: Rep[Int] = column[Int]("classroom_id")
    /** Database column close SqlType(bool), Default(false) */
    val close: Rep[Boolean] = column[Boolean]("close", O.Default(false))
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column modified_at SqlType(timestamptz), Default(None) */
    val modifiedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("modified_at", O.Default(None))
    /** Database column closed_at SqlType(timestamptz), Default(None) */
    val closedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("closed_at", O.Default(None))

    /** Foreign key referencing Classrooms (database name teacher_classrooms_classroom_id_fkey) */
    lazy val classroomsFk = foreignKey("teacher_classrooms_classroom_id_fkey", classroomId, Classrooms)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Teachers (database name teacher_classrooms_teacher_id_fkey) */
    lazy val teachersFk = foreignKey("teacher_classrooms_teacher_id_fkey", teacherId, Teachers)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table TeacherClassrooms */
  lazy val TeacherClassrooms = new TableQuery(tag => new TeacherClassrooms(tag))
}
