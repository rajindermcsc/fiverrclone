package dreamguys.in.co.gigs.utils;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterMinMax implements InputFilter {

	private float min, max;

    public InputFilterMinMax(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public InputFilterMinMax(String min, String max) {
		this.min = Float.parseFloat(min);
		this.max = Float.parseFloat(max);
	}
	
	@Override
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		try {
			float input = Float.parseFloat(dest.toString() + source.toString());
			if (isInRange(min, max, input))
				return null;
		} catch (NumberFormatException nfe) { }		
		return "";
	}

	private boolean isInRange(float a, float b, float c) {
		return b > a ? c >= a && c <= b : c >= b && c <= a;
	}
}