package com.company.demo.lib;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FrankenFormat extends DateFormat
{
    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        toAppendTo.append("Franken[");
        toAppendTo.append(cal.get(Calendar.YEAR));
        toAppendTo.append('^');
        toAppendTo.append(cal.get(Calendar.MONTH)+1);
        toAppendTo.append('^');
        toAppendTo.append(cal.get(Calendar.DAY_OF_MONTH));
        toAppendTo.append(']');
        
        return toAppendTo;
    }

    @Override
    public Date parse(String source, ParsePosition pos)
    {
        // Don't care about this impl, just want the code to be "sane"
        return new SimpleDateFormat().parse(source,pos);
    }
}
