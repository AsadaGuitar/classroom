package com.github.AsadaGuitar.classroom.adaptor.postgres.dao

// AUTO-GENERATED Slick data model for table Students
trait StudentsTable {

  self:Tables  =>

  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}
  /** Entity class storing rows of table Students
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param classroomId Database column classroom_id SqlType(int4)
   *  @param name Database column name SqlType(varchar), Length(255,true)
   *  @param loginId Database column login_id SqlType(varchar), Length(255,true)
   *  @param close Database column close SqlType(bool), Default(false)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param modifiedAt Database column modified_at SqlType(timestamptz), Default(None)
   *  @param closedAt Database column closed_at SqlType(timestamptz), Default(None) */
  case class StudentsRow(id: Int, classroomId: Int, name: String, loginId: String, close: Boolean = false, createdAt: java.sql.Timestamp, modifiedAt: Option[java.sql.Timestamp] = None, closedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching StudentsRow objects using plain SQL queries */
  implicit def GetResultStudentsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Boolean], e3: GR[java.sql.Timestamp], e4: GR[Option[java.sql.Timestamp]]): GR[StudentsRow] = GR{
    prs => import prs._
    StudentsRow.tupled((<<[Int], <<[Int], <<[String], <<[String], <<[Boolean], <<[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table students. Objects of this class serve as prototypes for rows in queries. */
  class Students(_tableTag: Tag) extends profile.api.Table[StudentsRow](_tableTag, "students") {
    def * = (id, classroomId, name, loginId, close, createdAt, modifiedAt, closedAt) <> (StudentsRow.tupled, StudentsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(classroomId), Rep.Some(name), Rep.Some(loginId), Rep.Some(close), Rep.Some(createdAt), modifiedAt, closedAt).shaped.<>({ r=>import r._; _1.map(_=> StudentsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7, _8)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column classroom_id SqlType(int4) */
    val classroomId: Rep[Int] = column[Int]("classroom_id")
    /** Database column name SqlType(varchar), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column login_id SqlType(varchar), Length(255,true) */
    val loginId: Rep[String] = column[String]("login_id", O.Length(255,varying=true))
    /** Database column close SqlType(bool), Default(false) */
    val close: Rep[Boolean] = column[Boolean]("close", O.Default(false))
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column modified_at SqlType(timestamptz), Default(None) */
    val modifiedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("modified_at", O.Default(None))
    /** Database column closed_at SqlType(timestamptz), Default(None) */
    val closedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("closed_at", O.Default(None))

    /** Foreign key referencing Classrooms (database name students_classroom_id_fkey) */
    lazy val classroomsFk = foreignKey("students_classroom_id_fkey", classroomId, Classrooms)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Students */
  lazy val Students = new TableQuery(tag => new Students(tag))
}
