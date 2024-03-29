package com.example.repositories.extractor

import com.example.model.Conta
import com.example.repositories.JdbcContaRepository
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

open class ContaRowMapper : RowMapper<Conta> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Conta {
        val id = rs.getString(JdbcContaRepository.ID_COLUMN)
        val saldo = rs.getDouble(JdbcContaRepository.SALDO_COLUMN)
        val dataHora = rs.getTimestamp(JdbcContaRepository.DATA_HORA_COLUMN)


        return Conta(
            id = id,
            saldo = saldo,
            dataHora = dataHora.toLocalDateTime()
        )
    }
}