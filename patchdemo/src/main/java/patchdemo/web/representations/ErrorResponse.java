package patchdemo.web.representations;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private List<String> messages = new ArrayList<>();

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.messages.add(message);
    };
}
