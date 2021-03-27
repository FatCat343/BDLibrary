-- create function some_function()
--     returns setof library_schema.reader
-- as
-- $$
-- SELECT
--     r.*
-- FROM
--     library_schema.reader r
--     JOIN library_schema.distribution d ON d.reader_id = r.reader_id
--     JOIN library_schema.edition e ON d.edition_id = e.edition_id
--     JOIN library_schema.outdoor o ON o.edition_id = e.edition_id
-- WHERE
--     d.date_give + o.rental_period < NOW ()
-- $$
-- language sql;

CREATE OR REPLACE PROCEDURE mvp_staff(INOUT _val text DEFAULT null)
    LANGUAGE plpgsql AS
$proc$
BEGIN
    SELECT res.name
    FROM (
        SELECT
            CONCAT(s.firstname, ' ', s.lastname) AS name,
            case when COUNT(*) > 1 then COUNT(*) else 0 end AS served_clients
        FROM
             library_schema.distribution d
             JOIN library_schema.reader r ON d.reader_id = r.reader_id
             FULL OUTER JOIN library_schema.staff s ON s.staff_id = d.staff_id
        WHERE
            s.staff_id != 0
        GROUP BY
            s.staff_id
        ORDER BY
            served_clients DESC
        LIMIT 1 ) res
    INTO _val;                              -- !!!
END
$proc$;