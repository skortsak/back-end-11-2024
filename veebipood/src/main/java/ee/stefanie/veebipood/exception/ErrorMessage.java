package ee.stefanie.veebipood.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessage {
    private String name;
    private Date date;
    private int statusCode;
}
