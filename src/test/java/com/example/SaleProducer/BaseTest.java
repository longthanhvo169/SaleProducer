package com.example.SaleProducer;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.TypePredicates;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class BaseTest {

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    protected  <T> T generateObject(Class<T> valueType) {
        EasyRandomParameters parameters = new EasyRandomParameters();
        parameters.stringLengthRange(5, 10);
        parameters.collectionSizeRange(1, 1);
        parameters.excludeType(TypePredicates.inPackage("not.existing.pkg"));
        EasyRandom generator = new EasyRandom(parameters);
        return generator.nextObject(valueType);
    }

    protected <T, C extends Collection<T>> C generateObjects(Class<T> valueType, Supplier<C> collectionType) {
        EasyRandomParameters parameters = new EasyRandomParameters();
        parameters.stringLengthRange(5, 10);
        parameters.collectionSizeRange(1, 1);
        parameters.excludeType(TypePredicates.inPackage("not.existing.pkg"));
        EasyRandom generator = new EasyRandom(parameters);
        return generator.objects(valueType, 1).collect(Collectors.toCollection(collectionType));
    }
}
