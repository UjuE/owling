package com.ujuezeoke.homeproject

/**
  * Created by Obianuju Ezeoke on 20/06/2017.
  */
trait Data {
  lazy val labels: Stream[(String, List[String])] = ???
  lazy val bankEntries: Stream[BankEntry] = ???
}
