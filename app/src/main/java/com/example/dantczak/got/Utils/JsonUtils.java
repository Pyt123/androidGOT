package com.example.dantczak.got.Utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonUtils
{
    private static final ObjectMapper mapper = new ObjectMapper();

    public static ObjectMapper getObjectMapper()
    {
        return mapper;
    }

    public static JavaType getObjectType(String name) throws ClassNotFoundException
    {
        return mapper.getTypeFactory().constructType(Class.forName(name));
    }

    public static JavaType getListType(Class clazz)
    {
        return mapper.getTypeFactory().constructCollectionType(List.class, clazz);

    }

    public static JavaType getGenericListType(String genericTypeName) throws ClassNotFoundException
    {
        return mapper.getTypeFactory().constructCollectionType(List.class, Class.forName(genericTypeName));
    }
}
