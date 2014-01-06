package com.chihan.NumberMagic;
/**
 * The dummy magic core object.
 * @author Chi-Han Wang
 * @author Jonathan Poston
 *
 */
public class MagicCore {
	private int digitsTotal;
	private boolean even;
	private final int NINE = 9;
	private int answer;
	/**
	 * The constructor of the core object. Which will receive the number  then find the answer.
	 * @param number The number that user put in.
	 * @param even If the number is even then true otherwise false.
	 */
	public MagicCore(int number, boolean even){
		this.even = even;
		String tempnum = Integer.toString(number);
		digitsTotal = 0;
		for(int i = 0 ; i < tempnum.length() ; i++ ){
			char tempn = tempnum.charAt(i);
			int temp = Character.getNumericValue(tempn);
			digitsTotal += temp;
		}
		int remainder = digitsTotal%NINE;
		int times = digitsTotal/NINE;
		int expected = NINE*(times + 1);
		if(remainder == 0){
			if(even){
				answer = 0;
			} else {
				answer = 9;
			}
		} else {
			answer = expected - digitsTotal;
		}
		
	}
	/**
	 * Method to get the answer.
	 * @return anwer.
	 */
	public int getAnswer(){
		return answer;
	}
	
	public boolean getEven(){
		return even;
	}

}
