package schemas
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.PostgresProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Truliaoverview.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Truliaoverview
   *  @param street Database column street SqlType(varchar), Length(100,true)
   *  @param city Database column city SqlType(varchar), Length(100,true)
   *  @param state Database column state SqlType(varchar), Length(50,true)
   *  @param retrievedtimestamp Database column retrievedTimeStamp SqlType(varchar), Length(100,true)
   *  @param link Database column link SqlType(text)
   *  @param price Database column price SqlType(varchar), Length(50,true)
   *  @param beds Database column beds SqlType(varchar), Length(50,true)
   *  @param baths Database column baths SqlType(varchar), Length(50,true)
   *  @param sqft Database column sqFt SqlType(varchar), Length(50,true)
   *  @param zipcode Database column zipCode SqlType(varchar), Length(50,true) */
  case class TruliaoverviewRow(street: String, city: String, state: String, retrievedtimestamp: String, link: String, price: String, beds: String, baths: String, sqft: String, zipcode: String)
  /** GetResult implicit for fetching TruliaoverviewRow objects using plain SQL queries */
  implicit def GetResultTruliaoverviewRow(implicit e0: GR[String]): GR[TruliaoverviewRow] = GR{
    prs => import prs._
    TruliaoverviewRow.tupled((<<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table TruliaOverview. Objects of this class serve as prototypes for rows in queries. */
  class Truliaoverview(_tableTag: Tag) extends profile.api.Table[TruliaoverviewRow](_tableTag, "TruliaOverview") {
    def * = (street, city, state, retrievedtimestamp, link, price, beds, baths, sqft, zipcode) <> (TruliaoverviewRow.tupled, TruliaoverviewRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(street), Rep.Some(city), Rep.Some(state), Rep.Some(retrievedtimestamp), Rep.Some(link), Rep.Some(price), Rep.Some(beds), Rep.Some(baths), Rep.Some(sqft), Rep.Some(zipcode))).shaped.<>({r=>import r._; _1.map(_=> TruliaoverviewRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column street SqlType(varchar), Length(100,true) */
    val street: Rep[String] = column[String]("street", O.Length(100,varying=true))
    /** Database column city SqlType(varchar), Length(100,true) */
    val city: Rep[String] = column[String]("city", O.Length(100,varying=true))
    /** Database column state SqlType(varchar), Length(50,true) */
    val state: Rep[String] = column[String]("state", O.Length(50,varying=true))
    /** Database column retrievedTimeStamp SqlType(varchar), Length(100,true) */
    val retrievedtimestamp: Rep[String] = column[String]("retrievedTimeStamp", O.Length(100,varying=true))
    /** Database column link SqlType(text) */
    val link: Rep[String] = column[String]("link")
    /** Database column price SqlType(varchar), Length(50,true) */
    val price: Rep[String] = column[String]("price", O.Length(50,varying=true))
    /** Database column beds SqlType(varchar), Length(50,true) */
    val beds: Rep[String] = column[String]("beds", O.Length(50,varying=true))
    /** Database column baths SqlType(varchar), Length(50,true) */
    val baths: Rep[String] = column[String]("baths", O.Length(50,varying=true))
    /** Database column sqFt SqlType(varchar), Length(50,true) */
    val sqft: Rep[String] = column[String]("sqFt", O.Length(50,varying=true))
    /** Database column zipCode SqlType(varchar), Length(50,true) */
    val zipcode: Rep[String] = column[String]("zipCode", O.Length(50,varying=true))

    /** Primary key of Truliaoverview (database name TruliaOverview_street_city_state_retrievedTimeStamp) */
    val pk = primaryKey("TruliaOverview_street_city_state_retrievedTimeStamp", (street, city, state, retrievedtimestamp))
  }
  /** Collection-like TableQuery object for table Truliaoverview */
  lazy val Truliaoverview = new TableQuery(tag => new Truliaoverview(tag))
}
