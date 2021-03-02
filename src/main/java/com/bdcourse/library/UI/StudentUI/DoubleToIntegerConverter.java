package com.bdcourse.library.UI.StudentUI;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class DoubleToIntegerConverter implements Converter<Double, Integer> {
    @Override
    public Result<Integer> convertToModel(Double aDouble, ValueContext valueContext) {
        if (aDouble == null) return Result.ok(0);
        else return Result.ok(aDouble.intValue());
    }

    @Override
    public Double convertToPresentation(Integer integer, ValueContext valueContext) {
        if (integer == null) return (double) 0;
        return integer.doubleValue();
    }
}
