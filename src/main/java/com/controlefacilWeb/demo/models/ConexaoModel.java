package com.controlefacilWeb.demo.models;

import java.sql.Connection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConexaoModel {

    private Connection conn;
}
