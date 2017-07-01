package com.ujuezeoke.homeproject

import java.util.Date

/**
  * Created by Obianuju Ezeoke on 20/06/2017.
  */
case class BankEntry(date: Date, bankEntryType: String, merchant: String, debitOrCredit: Double, balance: Double) {
  val isExpenditure : Boolean = debitOrCredit < 0
  val isIncome : Boolean = !isExpenditure
}
