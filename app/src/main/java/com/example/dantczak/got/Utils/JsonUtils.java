package com.example.dantczak.got.Utils;

import com.example.dantczak.got.model.trasa.PunktTrasy;
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

    public static JavaType getObjectType(String name)
    {
        JavaType javaType = null;
        try
        {
            javaType = mapper.getTypeFactory().constructType(Class.forName(name));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return javaType;
    }

    public static JavaType getGenericListType(String genericTypeName)
    {
        JavaType javaType = null;
        try
        {
            javaType = mapper.getTypeFactory().constructCollectionType(List.class, Class.forName(genericTypeName));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return javaType;
    }
}
