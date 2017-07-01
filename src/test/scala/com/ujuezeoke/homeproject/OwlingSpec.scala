package com.ujuezeoke.homeproject

import java.util.Date

import org.mockito.Mockito.{mock, when}
import org.scalatest.FlatSpec

/**
  * Created by Obianuju Ezeoke on 19/06/2017.
  */
class OwlingSpec extends FlatSpec {

  "Owling" should "return total expenditure with multiple credit entry" in {
    val stuff = mock(classOf[Data])

    val expectedBankEntryOne = BankEntry(new Date, "VIS", "SomeMerchant", -3.05d, 80.00d)
    val expectedBankEntryTwo = BankEntry(new Date, "VIS", "SomeMerchantAnother", 3.05d, 85.00d)
    val expectedBankEntryThree = BankEntry(new Date, "VIS", "SomeMerchantYetAnother", -2.05d, 85.00d)

    when(stuff.bankEntries).thenReturn(Stream(expectedBankEntryOne, expectedBankEntryTwo, expectedBankEntryThree))
    val totalExpenditure = new Owling(stuff).totalExpenditure

    assert(totalExpenditure == 5.10)
  }

  "Owling" should "return total income with multiple debit entry" in {
    val stuff = mock(classOf[Data])

    val expectedBankEntryOne = BankEntry(new Date, "VIS", "SomeMerchant", -3.05d, 80.00d)
    val expectedBankEntryTwo = BankEntry(new Date, "VIS", "SomeMerchantAnother", 3.05d, 85.00d)
    val expectedBankEntryThree = BankEntry(new Date, "VIS", "SomeMerchantYetAnother", -2.05d, 85.00d)
    val expectedBankEntryFour = BankEntry(new Date, "VIS", "SomeMerchantAnotherAgain", 10.00d, 85.00d)

    when(stuff.bankEntries)
      .thenReturn(Stream(
        expectedBankEntryOne,
        expectedBankEntryTwo,
        expectedBankEntryThree,
        expectedBankEntryFour))

    val totalExpenditure = new Owling(stuff).totalIncome

    assert(totalExpenditure == 13.05)
  }

  "Owling" should "return total expenditure to merchant name with multiple debit entry" in {
    val stuff = mock(classOf[Data])

    val expectedBankEntryOne = BankEntry(new Date, "VIS", "SomeMerchant", -3.05d, 80.00d)
    val expectedBankEntryTwo = BankEntry(new Date, "VIS", "SomeMerchantAnother", -3.05d, 85.00d)
    val expectedBankEntryThree = BankEntry(new Date, "VIS", "SomeMerchantAnother", -2.05d, 85.00d)
    val expectedBankEntryFour = BankEntry(new Date, "VIS", "SomeMerchant", -10.00d, 85.00d)

    when(stuff.bankEntries)
      .thenReturn(Stream(
        expectedBankEntryOne,
        expectedBankEntryTwo,
        expectedBankEntryThree,
        expectedBankEntryFour))

    val totalExpenditure = new Owling(stuff).totalExpenditureByMerchantName

    assert(totalExpenditure.equals(Map(("SomeMerchant", 13.05), ("SomeMerchantAnother", 5.10))))
  }

  "Owling" should "return total income to merchant name with multiple debit entry" in {
    val stuff = mock(classOf[Data])

    val expectedBankEntryOne = BankEntry(new Date, "VIS", "SomeMerchant", -3.05d, 80.00d)
    val expectedBankEntryTwo = BankEntry(new Date, "VIS", "SomeMerchantAnother", 3.05d, 85.00d)
    val expectedBankEntryThree = BankEntry(new Date, "VIS", "SomeMerchantAnother", 2.05d, 85.00d)
    val expectedBankEntryFour = BankEntry(new Date, "VIS", "SomeMerchant", -10.00d, 85.00d)

    when(stuff.bankEntries)
      .thenReturn(Stream(
        expectedBankEntryOne,
        expectedBankEntryTwo,
        expectedBankEntryThree,
        expectedBankEntryFour))

    val totalExpenditure = new Owling(stuff).totalIncomeByMerchantName

    assert(totalExpenditure.equals(Map(("SomeMerchantAnother", 5.10))))
  }

  "Owling" should "return total expenditure to Label with multiple debit entry and handle unlabeled merchants" in {
    val data = mock(classOf[Data])

    val expectedBankEntryOne = BankEntry(new Date, "VIS", "SomeMerchant", -3.05d, 80.00d)
    val expectedBankEntryTwo = BankEntry(new Date, "VIS", "SomeMerchantAnother", -3.05d, 85.00d)
    val expectedBankEntryThree = BankEntry(new Date, "VIS", "SomeMerchantAnother", -2.05d, 85.00d)
    val expectedBankEntryFour = BankEntry(new Date, "VIS", "SomeMerchant", -10.00d, 85.00d)
    val expectedBankEntryFive = BankEntry(new Date, "VIS", "New entry", -10.00d, 85.00d)

    when(data.bankEntries)
      .thenReturn(Stream(
        expectedBankEntryOne,
        expectedBankEntryTwo,
        expectedBankEntryThree,
        expectedBankEntryFour,
        expectedBankEntryFive
      ))

    when(data.labels)
      .thenReturn(Stream(
        ("HouseHold", List("SomeMerchant", "SomeMerchantAnother"))
      ))

    val totalExpenditure = new Owling(data).totalExpenditureByLabel

    assert(totalExpenditure.equals(Map(("HouseHold", 18.15), ("Other", 10.00))))
  }

  "Owling" should "return total income to Label with multiple debit entry and handle unlabeled merchants" in {
    val data = mock(classOf[Data])

    val expectedBankEntryOne = BankEntry(new Date, "VIS", "SomeMerchant", 3.05d, 80.00d)
    val expectedBankEntryTwo = BankEntry(new Date, "VIS", "SomeMerchantAnother", -3.05d, 85.00d)
    val expectedBankEntryThree = BankEntry(new Date, "VIS", "SomeMerchantAnother", -2.05d, 85.00d)
    val expectedBankEntryFour = BankEntry(new Date, "VIS", "SomeMerchant", 10.00d, 85.00d)
    val expectedBankEntryFive = BankEntry(new Date, "VIS", "New entry", 10.00d, 85.00d)

    when(data.bankEntries)
      .thenReturn(Stream(
        expectedBankEntryOne,
        expectedBankEntryTwo,
        expectedBankEntryThree,
        expectedBankEntryFour,
        expectedBankEntryFive
      ))

    when(data.labels)
      .thenReturn(Stream(
        ("Work", List("SomeMerchant"))
      ))

    val totalExpenditure = new Owling(data).totalIncomeByLabel

    assert(totalExpenditure.equals(Map(("Work", 13.05), ("Other", 10.00))))
  }


  "Owling" should "return expences of the same label" in {
    val data = mock(classOf[Data])

    val expectedBankEntryOne = BankEntry(new Date, "VIS", "SomeMerchant", -3.05d, 80.00d)
    val expectedBankEntryTwo = BankEntry(new Date, "VIS", "SomeMerchantAnother", -3.05d, 85.00d)
    val expectedBankEntryThree = BankEntry(new Date, "VIS", "SomeMerchantAnother", -2.05d, 85.00d)
    val expectedBankEntryFour = BankEntry(new Date, "VIS", "SomeMerchant", -10.00d, 85.00d)
    val expectedBankEntryFive = BankEntry(new Date, "VIS", "New entry", -10.00d, 85.00d)

    when(data.bankEntries)
      .thenReturn(Stream(
        expectedBankEntryOne,
        expectedBankEntryTwo,
        expectedBankEntryThree,
        expectedBankEntryFour,
        expectedBankEntryFive
      ))

    when(data.labels)
      .thenReturn(Stream(
        ("HouseHold", List("SomeMerchant", "SomeMerchantAnother"))
      ))

    val totalExpenditure = new Owling(data).groupExpenditureByLabel("HouseHold")

    assert(totalExpenditure.equals(Map(("SomeMerchant", 13.05), ("SomeMerchantAnother", 5.10))))
  }

}
