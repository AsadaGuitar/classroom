package com.github.AsadaGuitar.classroom.adaptor.postgres

import slick.basic.DatabaseConfig
import slick.jdbc.PostgresProfile

trait DbConfigProvider {

  protected lazy val dbConfig: DatabaseConfig[PostgresProfile] = DatabaseConfig.forConfig("slick")
}