package com.ujuezeoke.homeproject

/**
  * Created by Obianuju Ezeoke on 19/06/2017.
  */
class Owling(mapOfStuff: Data) {
  val negate: Double => Double = number => -1 * number

  lazy val totalIncomeByMerchantName: Map[String, Double] =
    mapOfStuff.bankEntries
      .filter(bankEntry => bankEntry.isIncome)
      .groupBy(bankEntry => bankEntry.merchant)
      .map {
        case (merchantName, bankEntryStream) =>
          (merchantName, bankEntryStream.map(it => it.debitOrCredit).sum)
      }

  lazy val totalExpenditureByMerchantName: Map[String, Double] =
    mapOfStuff.bankEntries
      .filter(bankEntry => bankEntry.isExpenditure)
      .groupBy(bankEntry => bankEntry.merchant)
      .map {
        case (merchantName, bankEntryStream) =>
          (merchantName, negate(bankEntryStream.map(it => it.debitOrCredit).sum))
      }

  lazy val totalExpenditure: Double =
    negate(mapOfStuff
      .bankEntries
      .filter(bankEntry => bankEntry.isExpenditure)
      .map(bankEntry => bankEntry.debitOrCredit)
      .sum)

  lazy val totalIncome: Double =
    mapOfStuff.bankEntries
      .filter(bankEntry => bankEntry.isIncome)
      .map(bankEntry => bankEntry.debitOrCredit)
      .sum

  lazy val totalExpenditureByLabel: Map[String, Double] =
    mapOfStuff.bankEntries
      .filter(bankEntry => bankEntry.isExpenditure)
      .groupBy(bankEntry => label(bankEntry.merchant))
      .map {
        case (merchantName, bankEntryStream) =>
          (merchantName, negate(bankEntryStream.map(it => it.debitOrCredit).sum))
      }

  lazy val totalIncomeByLabel: Map[String, Double] =
    mapOfStuff.bankEntries
      .filter(bankEntry => bankEntry.isIncome)
      .groupBy(bankEntry => label(bankEntry.merchant))
      .map {
        case (merchantName, bankEntryStream) =>
          (merchantName, bankEntryStream.map(it => it.debitOrCredit).sum)
      }

  lazy val groupExpenditureByLabel: String => Map[String, Double] =
    labelToSearchFor =>
      mapOfStuff.bankEntries
        .filter(bankEntry => bankEntry.isExpenditure && label(bankEntry.merchant).equals(labelToSearchFor))
        .groupBy(bankEntry => bankEntry.merchant)
        .map {
          case (merchantName, bankEntryStream) =>
            (merchantName, negate(bankEntryStream.map(it => it.debitOrCredit).sum))
        }


  private lazy val label: String => String =
    name => mapOfStuff.labels
      .flatten(it => it._2.map(merchantName => (merchantName, it._1)))
      .find(it => it._1.contains(name))
      .getOrElse(("", "Other"))._2


}
