INSERT INTO clientes (id, nome)
    SELECT id, nome FROM (
        SELECT 1 AS id, 'Cliente 1' AS nome UNION ALL
        SELECT 2, 'Cliente 2' UNION ALL
        SELECT 3, 'Cliente 3' UNION ALL
        SELECT 4, 'Cliente 4' UNION ALL
        SELECT 5, 'Cliente 5' UNION ALL
        SELECT 6, 'Cliente 6' UNION ALL
        SELECT 7, 'Cliente 7' UNION ALL
        SELECT 8, 'Cliente 8' UNION ALL
        SELECT 9, 'Cliente 9' UNION ALL
        SELECT 10, 'Cliente 10'
    ) AS registers
WHERE NOT EXISTS (
    SELECT 1 FROM clientes WHERE clientes.id = registers.id
);
