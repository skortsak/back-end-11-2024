package ee.stefanie.veebipood.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.Date;
import java.util.NoSuchElementException;

// Tuleb peale globaalselt kõikidele Controlleritele ja hakkab nende veateateid (Exceptione) kinni püüdma
@ControllerAdvice
@Log4j2
public class ExceptionCatcher {

    @ExceptionHandler // Püüan kinni selle exceptioni, mille kirjutan siia sulgude sisse
    public ResponseEntity<ErrorMessage> handleException(RuntimeException e) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setDate(new Date());
        errorMessage.setName(e.getMessage());
        errorMessage.setStatusCode(400);
        log.error(Arrays.toString(e.getStackTrace()));
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(NoSuchElementException e) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setDate(new Date());
        errorMessage.setName(e.getMessage());
        errorMessage.setStatusCode(404);
        log.error(Arrays.toString(e.getStackTrace()));
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
