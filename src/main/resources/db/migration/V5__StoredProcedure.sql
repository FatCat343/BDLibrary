create function some_function()
    returns setof library_schema.reader
as
$$
SELECT
    r.*
FROM
    library_schema.reader r
    JOIN library_schema.distribution d ON d.reader_id = r.reader_id
    JOIN library_schema.edition e ON d.edition_id = e.edition_id
    JOIN library_schema.outdoor o ON o.edition_id = e.edition_id
WHERE
    d.date_give + o.rental_period < NOW ()
$$
language sql;