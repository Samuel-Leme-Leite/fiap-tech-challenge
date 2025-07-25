package com.techchallenge.domain.shared.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Embeddable
@Data
public class Address {
    @Schema(description = "Nome da rua", example = "Rua das Flores")
    @NotBlank
    private String street;

    @Schema(description = "Número do endereço", example = "123")
    @NotBlank
    private String addressNumber;

    @Schema(description = "Complemento", example = "Apto 45")
    private String complement;

    @Schema(description = "Cidade", example = "São Paulo")
    @NotBlank
    private String city;

    @Schema(description = "Estado", example = "SP")
    @NotBlank
    private String state;

    @Schema(description = "CEP", example = "01234-567")
    @NotBlank
    private String zipCode;

    @Schema(description = "País", example = "Brasil")
    @NotBlank
    private String country;

    @Schema(description = "Bairro", example = "Centro")
    @NotBlank
    private String neighborhood;

    public String getAddress(){
        if(street == null || addressNumber == null || city == null || state == null || zipCode == null || country == null || neighborhood == null) {
            return "";
        }
        return street + ", " + addressNumber + ", " + neighborhood + ", " + city + ", " + state + ", " + zipCode + ", " + country;
    }
}
