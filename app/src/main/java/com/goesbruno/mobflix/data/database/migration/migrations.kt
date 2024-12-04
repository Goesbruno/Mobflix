package com.goesbruno.mobflix.data.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // 1. Criar uma nova tabela temporária com o id como String
        database.execSQL("""
            CREATE TABLE video_table_new (
                id TEXT PRIMARY KEY NOT NULL,
                url TEXT,
                category TEXT
            )
        """)

        // 2. Copiar os dados da tabela antiga para a nova tabela, convertendo id para String
        database.execSQL("""
            INSERT INTO video_table_new (id, url, category)
            SELECT id || '', url, category FROM video_table
        """)

        // 3. Remover a tabela antiga
        database.execSQL("DROP TABLE video_table")

        // 4. Renomear a nova tabela para o nome da tabela original
        database.execSQL("ALTER TABLE video_table_new RENAME TO video_table")
    }
}
