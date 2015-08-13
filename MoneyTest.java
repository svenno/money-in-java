package money;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;

import org.junit.Test;

/**
 * Class of Unit tests checking proper functionality of class Money
 */
public class MoneyTest {

	/**
	 * Test method for {@link money.Money#Money(java.math.BigDecimal)}.
	 */
	@Test
	public final void testMoneyBigDecimal() {
		final BigDecimal amount = new BigDecimal("98.76");
		final Money m = new Money(amount);
		assertTrue(m.getAmount().equals(amount) && m.getCurrency().equals("EUR"));
	}

	/**
	 * Test method for {@link money.Money#Money(java.math.BigDecimal, java.lang.String)}.
	 */
	@Test
	public final void testMoneyBigDecimalString() {
		final BigDecimal amount = new BigDecimal("98.76");
		final Money m = new Money(amount, "USD");
		assertTrue(m.getAmount().equals(amount) && m.getCurrency().equals("USD"));
	}

	/**
	 * Test method for {@link money.Money#Money(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testMoneyStringString() {
		final BigDecimal amount = new BigDecimal("98.76");
		final Money m = new Money("98.76", "USD");
		assertTrue(m.getAmount().equals(amount) && m.getCurrency().equals("USD"));
	}

	/**
	 * Test method for {@link money.Money#getAmount()}.
	 */
	@Test
	public final void testGetAmount() {
		final BigDecimal amount = new BigDecimal("98.76");
		final Money m = new Money("98.76", "USD");
		assertTrue(m.getAmount().equals(amount));
	}

	/**
	 * Test method for {@link money.Money#getCurrency()}.
	 */
	@Test
	public final void testGetCurrency() {
		final Money m = new Money("98.76", "USD");
		assertTrue(m.getCurrency().equals("USD"));
	}

	/**
	 * Test method for {@link money.Money#toString()}.
	 */
	@Test
	public final void testToString() {
		final Money m = new Money("98.76", "USD");
		assertTrue(m.toString().equals("USD 98.76"));
	}

	/**
	 * Test method for {@link money.Money#multiplyBy(int)}.
	 */
	@Test
	public final void testMultiplyBy() {
		final Money a = new Money("98.76", "USD");
		final Money aTimes5 = a.multiplyBy(5);
		assertTrue(aTimes5.toString().equals("USD 493.80"));
	}

	/**
	 * Test method for {@link money.Money#divideBy(int)}.
	 */
	@Test
	public final void testDivideBy() {
		final Money a = new Money("493.80", "USD");
		final Money aAgain = a.divideBy(5);
		assertTrue(aAgain.toString().equals("USD 98.76"));
	}

	/**
	 * Test method for {@link money.Money#convertToOtherCurrency(java.lang.String, java.util.HashMap)}.
	 */
	@Test
	public final void testConvertToOtherCurrency() {
		HashMap<String,BigDecimal> currencies = new HashMap<String,BigDecimal>();

		MathContext mc = new MathContext(4, RoundingMode.HALF_EVEN);
		
		currencies.put("EUR", new BigDecimal(1.0, mc));
		currencies.put("USD", new BigDecimal(1.11, mc));

		final Money a = new Money("98.76", "USD");
		final Money b = new Money("67.89", "EUR");
		final Money aConvertedToEUR = a.convertToOtherCurrency("EUR", currencies);
		final Money bConvertedToUSD = b.convertToOtherCurrency("USD", currencies);

		assertTrue(aConvertedToEUR.toString().equals("EUR 88.97") && bConvertedToUSD.toString().equals("USD 75.36"));
	}
}
