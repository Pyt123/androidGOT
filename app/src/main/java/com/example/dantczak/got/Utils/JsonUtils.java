package com.example.dantczak.got.Utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * This is a class with static methods to help serialize and deserialize JSONs.
 * @author Dawid Antczak
 */
public class JsonUtils
{
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * A ObjectMapper getter
     * @return returns a ObjectMapper instance, which can perform serialization/deserialization
     */
    public static ObjectMapper getObjectMapper()
    {
        return mapper;
    }

    /**
     * Creates a JavaType object from class name
     * @param name a String with the class name (including package name)
     * @return returns a JavaType object, which can be use by the ObjectMapper
     * @throws ClassCastException when class with the specified name can't be find
     */
    public static JavaType getObjectType(String name) throws ClassNotFoundException
    {
        return mapper.getTypeFactory().constructType(Class.forName(name));
    }

    /**
     * Creates a JavaType object which describes a generic List of specified class
     * @param clazz a Class object of the generic type
     * @return returns a JavaType object, which can be use by the ObjectMapper
     */
    public static JavaType getGenericListType(Class clazz)
    {
        return mapper.getTypeFactory().constructCollectionType(List.class, clazz);
    }

    /**
     * Creates a JavaType object which describes a generic List of specified class
     * @param genericTypeName a String with the class name (including package name)
     * @return returns a JavaType object, which can be use by the ObjectMapper
     */
    public static JavaType getGenericListType(String genericTypeName) throws ClassNotFoundException
    {
        return mapper.getTypeFactory().constructCollectionType(List.class, Class.forName(genericTypeName));
    }
}
