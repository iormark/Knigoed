package info.knigoed.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Jackson {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        //MAPPER.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        MAPPER.setSerializationInclusion(Include.NON_NULL);
    }

    public static ObjectMapper mapper() { 
        return MAPPER;
    }

}
