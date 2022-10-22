package com.github.AsadaGuitar.classroom.adaptor.postgres.dao

// AUTO-GENERATED Slick data model for table Classrooms
trait ClassroomsTable {

  self:Tables  =>

  import profile.api._
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}
  /** Entity class storing rows of table Classrooms
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(varchar), Length(255,true)
   *  @param close Database column close SqlType(bool), Default(false)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param modifiedAt Database column modified_at SqlType(timestamptz), Default(None)
   *  @param closedAt Database column closed_at SqlType(timestamptz), Default(None) */
  case class ClassroomsRow(id: Int, name: String, close: Boolean = false, createdAt: java.sql.Timestamp, modifiedAt: Option[java.sql.Timestamp] = None, closedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching ClassroomsRow objects using plain SQL queries */
  implicit def GetResultClassroomsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Boolean], e3: GR[java.sql.Timestamp], e4: GR[Option[java.sql.Timestamp]]): GR[ClassroomsRow] = GR{
    prs => import prs._
    ClassroomsRow.tupled((<<[Int], <<[String], <<[Boolean], <<[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table classrooms. Objects of this class serve as prototypes for rows in queries. */
  class Classrooms(_tableTag: Tag) extends profile.api.Table[ClassroomsRow](_tableTag, "classrooms") {
    def * = (id, name, close, createdAt, modifiedAt, closedAt) <> (ClassroomsRow.tupled, ClassroomsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), Rep.Some(close), Rep.Some(createdAt), modifiedAt, closedAt).shaped.<>({r=>import r._; _1.map(_=> ClassroomsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(varchar), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column close SqlType(bool), Default(false) */
    val close: Rep[Boolean] = column[Boolean]("close", O.Default(false))
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column modified_at SqlType(timestamptz), Default(None) */
    val modifiedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("modified_at", O.Default(None))
    /** Database column closed_at SqlType(timestamptz), Default(None) */
    val closedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("closed_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table Classrooms */
  lazy val Classrooms = new TableQuery(tag => new Classrooms(tag))
}
