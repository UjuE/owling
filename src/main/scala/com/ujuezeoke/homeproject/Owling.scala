package com.ujuezeoke.homeproject

import java.time.YearMonth

/**
  * Created by Obianuju Ezeoke on 19/06/2017.
  *
  * Intentionally Using Lazy Val. We want a quick startup.
  * We want a scenario where it is only initialized when it is called
  * and it does not need to process the data each time it is called.
  *
  */
class Owling(data: Data) {
  val negate: Double => Double = number => -1 * number

  lazy val totalIncomeByMerchantName: Map[String, Double] =
    data.bankEntries
      .filter(bankEntry => bankEntry.isIncome)
      .groupBy(bankEntry => bankEntry.merchant)
      .map {
        case (merchantName, bankEntryStream) =>
          (merchantName, bankEntryStream.map(it => it.debitOrCredit).sum)
      }

  lazy val groupExpenditureByYearMonth: Map[YearMonth, Double] =
    data.bankEntries
        .filter(bankEntry => bankEntry.isExpenditure)
          .groupBy(bankEntry => bankEntry.date)
              .map(theTuple => (theTuple._1, negate(theTuple._2.map(it => it.debitOrCredit).sum)))

  lazy val totalExpenditureByMerchantName: Map[String, Double] =
    data.bankEntries
      .filter(bankEntry => bankEntry.isExpenditure)
      .groupBy(bankEntry => bankEntry.merchant)
      .map {
        case (merchantName, bankEntryStream) =>
          (merchantName, negate(bankEntryStream.map(it => it.debitOrCredit).sum))
      }

  lazy val totalExpenditure: Double =
    negate(data
      .bankEntries
      .filter(bankEntry => bankEntry.isExpenditure)
      .map(bankEntry => bankEntry.debitOrCredit)
      .sum)

  lazy val totalIncome: Double =
    data.bankEntries
      .filter(bankEntry => bankEntry.isIncome)
      .map(bankEntry => bankEntry.debitOrCredit)
      .sum


  lazy val totalExpenditureByLabel: Map[String, Double] =
    data.bankEntries
      .filter(bankEntry => bankEntry.isExpenditure)
      .groupBy(bankEntry => label(bankEntry.merchant))
      .map {
        case (merchantName, bankEntryStream) =>
          (merchantName, negate(bankEntryStream.map(it => it.debitOrCredit).sum))
      }

  lazy val totalIncomeByLabel: Map[String, Double] =
    data.bankEntries
      .filter(bankEntry => bankEntry.isIncome)
      .groupBy(bankEntry => label(bankEntry.merchant))
      .map {
        case (merchantName, bankEntryStream) =>
          (merchantName, bankEntryStream.map(it => it.debitOrCredit).sum)
      }

  lazy val groupExpenditureByLabel: String => Map[String, Double] =
    labelToSearchFor =>
      data.bankEntries
        .filter(bankEntry => bankEntry.isExpenditure && label(bankEntry.merchant).equals(labelToSearchFor))
        .groupBy(bankEntry => bankEntry.merchant)
        .map {
          case (merchantName, bankEntryStream) =>
            (merchantName, negate(bankEntryStream.map(it => it.debitOrCredit).sum))
        }


  private lazy val label: String => String =
    name => data.labels
      .flatten(it => it._2.map(merchantName => (merchantName, it._1)))
      .find(it => it._1.contains(name))
      .getOrElse(("", "Other"))._2


}
