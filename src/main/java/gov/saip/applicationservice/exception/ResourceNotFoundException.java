package gov.saip.applicationservice.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

    protected Integer code;
    protected String key;

    public ResourceNotFoundException(String message, Integer code) {
        super(message);
        this.code = code;
    }

}