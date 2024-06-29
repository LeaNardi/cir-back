package com.cir.cirback.dtos;

import lombok.Data;

@Data
public class ExistsResponse {
    private Boolean exists;
    private Boolean active;
}
