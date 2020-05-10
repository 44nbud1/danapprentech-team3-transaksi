package project.akhir.danapprentechteam3.payload.request;

import lombok.Data;

@Data
public class ForgotPassword<U, L extends Number> {
    private String noTelepon;
}
