package com.viniciusmdc.AuthApi.enums;

public enum TipoTokenJwtEnum {
    ACCESS("access"), REFRESH("refresh");

    private final String tipoToken;

    TipoTokenJwtEnum(String tipoToken) {
        this.tipoToken = tipoToken;
    }

    public String getTipoToken() {
        return tipoToken;
    }

}
