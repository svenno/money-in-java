package money;

import java.util.Currency;
import java.util.HashMap;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Class Money is implementing a small money representation
 * 
 * Money can be multiplied and divided by an integer value as well as 
 * converted into other currency.
 * 
 * Unit tests can be found in class MoneyTest.
 *  
 *   todo: addTo(), reduceBy(), equals(), hashCode()
 */
 public final class Money {

	/**
	 * Constructor assigning EUR currency by default.
	 * @param amount of money
	 */
	public Money(BigDecimal amount) {
		this.amount = amount;
		this.currency = "EUR";
	}

	/**
	 * Constructor assigning amount and currency.
	 * @param amount of money
	 * @param currency of money
	 */
	public Money(BigDecimal amount, String currency) {
		this.amount = amount;
		this.currency = currency;
	}

	/**
	 * Constructor assigning amount and currency.
	 * @param amount of money
	 * @param currency of money
	 */
	public Money(String amountString, String currencyString) {
		this.amount = new BigDecimal(amountString);
		this.currency = currencyString;		
	}
	
	/**
	 * @return the amount of money
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @return the currency of money
	 */
	public String getCurrency() {
		return currency;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return currency + " " + amount;
	}

	/**
	  * Add the given Money to this Money. 
	  * First convert to matching currency if needed.
	  * 
	  * @param toBeAdded this Money is added to 
	  * @return resulting sum Money instance 
	  */
	public Money addTo(Money toBeAdded) {
		/* @todo */
		return this;
	}
	
	/**
	  * Reduce the given Money from this Money. 
	  * First convert to matching currency if needed.
	  * 
	  * @param toBeReduced this Money is reduced by 
	  * @return resulting reduced Money instance 
	  */
	public Money reduceBy(Money toBeReduced) {
		/* @todo */
		return this;
	}
	
	/**
	  * Multiply this Money by an integer factor.
	  * 
	  * @param factor this Money is multiplied by 
	  * @return resulting product Money instance 
	  */
	public Money multiplyBy(int factor) {
		BigDecimal factorInBigDecimal = new BigDecimal(factor);
		BigDecimal productInBigDecimal = this.amount.multiply(factorInBigDecimal);
		productInBigDecimal = productInBigDecimal.setScale(getNumberOfDecimalsOfCurrency(currency), RoundingMode.HALF_EVEN);
		return new Money(productInBigDecimal, this.getCurrency());
	}
	
	/**
	 * Divide this Money by an integer divisor.
	 * 
	 * @param divisor this Money is divided by 
	 * @return resulting quotient Money instance 
	 */
	public Money divideBy(int divisor) {
		BigDecimal divisorInBigDecimal = new BigDecimal(divisor);
		BigDecimal quotientInBigDecimal = this.amount.divide(divisorInBigDecimal, getNumberOfDecimalsOfCurrency(currency), RoundingMode.HALF_EVEN);
		return new Money(quotientInBigDecimal, this.getCurrency());
	}

	/**
	 * Convert this Money into the given currency.
	 * 
	 * @param newCurrency currency this Money will converted to 
	 * @param currencies hash map of available currencies 
	 * @return resulting Money instance based on given currency 
	 */
	public Money convertToOtherCurrency(String newCurrency, HashMap<String,BigDecimal> currencies) {
		if (newCurrency == this.currency) {
			return this;
		}
		BigDecimal oldCurrencyFactor = currencies.get(currency);
		BigDecimal newCurrencyFactor = currencies.get(newCurrency);
		BigDecimal convertedAmount = amount.multiply(newCurrencyFactor);
		convertedAmount = convertedAmount.divide(oldCurrencyFactor, getNumberOfDecimalsOfCurrency(currency), RoundingMode.HALF_EVEN);
		return new Money(convertedAmount, newCurrency);
	}

	/** The amount of money */
	private	BigDecimal amount;
	/** The currency the amount of Money is based on */
	private String currency;

	/**   
	 * Prints out the Money instance specifics to stdout 
	 */
	private void logToStdout(){
		System.out.println(this.toString());
	}
	
	/**
	 *  Returns the number of decimals of the given currency.
	 */
	private int getNumberOfDecimalsOfCurrency(String currency){
	    return Currency.getInstance(currency).getDefaultFractionDigits();
	}    
	
	/**
	 * Main method for playing with the class functionality.
	 */
	public static void main(String[] args) {
		
		HashMap<String,BigDecimal> currencies = new HashMap<String,BigDecimal>();

		MathContext mc = new MathContext(4, RoundingMode.HALF_EVEN);
		
		currencies.put("EUR", new BigDecimal(1.0, mc));
		currencies.put("USD", new BigDecimal(1.11, mc));
		currencies.put("CHF", new BigDecimal(1.09, mc));
		
		final Money a = new Money("67.89", "EUR"); //pass 67.89 EUR as parameters
		final Money b = new Money("98.76", "USD"); //pass 98.76 USD as parameters
		a.logToStdout();
		b.logToStdout();

		final Money aTimes5 = a.multiplyBy(5);
		final Money bTimes5 = b.multiplyBy(5);
		aTimes5.logToStdout();
		bTimes5.logToStdout();

		final Money aAgain = aTimes5.divideBy(5);
		final Money bAgain = bTimes5.divideBy(5);
		aAgain.logToStdout();
		bAgain.logToStdout();

		final Money aConvertedToUSD = aAgain.convertToOtherCurrency("USD", currencies);
		final Money bConvertedToEUR = bAgain.convertToOtherCurrency("EUR", currencies);
		aConvertedToUSD.logToStdout();
		bConvertedToEUR.logToStdout();

		final Money aConvertedToEUR = aAgain.convertToOtherCurrency("EUR", currencies);
		final Money bConvertedToUSD = bAgain.convertToOtherCurrency("USD", currencies);
		aConvertedToEUR.logToStdout();
		bConvertedToUSD.logToStdout();
	}
}
